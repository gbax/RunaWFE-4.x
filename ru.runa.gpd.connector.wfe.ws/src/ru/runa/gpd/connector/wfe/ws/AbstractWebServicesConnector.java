package ru.runa.gpd.connector.wfe.ws;

import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.xml.ws.soap.SOAPFaultException;

import org.eclipse.core.runtime.IProgressMonitor;

import ru.runa.gpd.Activator;
import ru.runa.gpd.PluginLogger;
import ru.runa.gpd.wfe.WFEServerConnector;
import ru.runa.wfe.bot.Bot;
import ru.runa.wfe.bot.BotStation;
import ru.runa.wfe.bot.BotStationDoesNotExistException;
import ru.runa.wfe.bot.BotTask;
import ru.runa.wfe.definition.DefinitionDoesNotExistException;
import ru.runa.wfe.definition.dto.WfDefinition;
import ru.runa.wfe.webservice.AuthenticationAPI;
import ru.runa.wfe.webservice.AuthenticationWebService;
import ru.runa.wfe.webservice.BotAPI;
import ru.runa.wfe.webservice.BotWebService;
import ru.runa.wfe.webservice.DefinitionAPI;
import ru.runa.wfe.webservice.DefinitionWebService;
import ru.runa.wfe.webservice.Executor;
import ru.runa.wfe.webservice.ExecutorAPI;
import ru.runa.wfe.webservice.ExecutorWebService;
import ru.runa.wfe.webservice.Relation;
import ru.runa.wfe.webservice.RelationAPI;
import ru.runa.wfe.webservice.RelationWebService;
import ru.runa.wfe.webservice.User;
import ru.runa.wfe.webservice.WfExecutor;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public abstract class AbstractWebServicesConnector extends WFEServerConnector {
    private User user;

    protected abstract URL getUrl(String serviceName);

    @Override
    public void connect() {
        AuthenticationAPI authenticationAPI = new AuthenticationWebService(getUrl("Authentication")).getAuthenticationAPIPort();
        if (LOGIN_MODE_LOGIN_PASSWORD.equals(Activator.getPrefString(P_WFE_CONNECTION_LOGIN_MODE))) {
            String login = Activator.getPrefString(P_WFE_CONNECTION_LOGIN);
            String password = getPassword();
            if (password == null) {
                return;
            }
            user = authenticationAPI.authenticateByLoginPassword(login, password);
        } else {
            user = authenticationAPI.authenticateByKerberos(getKerberosToken());
        }
    }

    @Override
    public void disconnect() throws Exception {
        user = null;
    }

    private User getUser() {
        if (user == null) {
            connect();
        } else {
            try {
                // check user is up to date
                getExecutorService().getExecutor(user, user.getActor().getId());
            } catch (SOAPFaultException e) {
                if (e.getMessage() == null || !e.getMessage().contains("Error in subject decryption")) {
                    Throwables.propagate(e);
                }
                connect();
            }
        }
        return user;
    }

    private ExecutorAPI getExecutorService() {
        return new ExecutorWebService(getUrl("Executor")).getExecutorAPIPort();
    }

    @Override
    public Map<String, Boolean> getExecutors() {
        List<WfExecutor> executors = getExecutorService().getExecutors(getUser(), null);
        Map<String, Boolean> result = Maps.newHashMapWithExpectedSize(executors.size());
        for (Executor executor : executors) {
            // group sign
            result.put(executor.getName(), executor.getFullName() == null);
        }
        return result;
    }

    @Override
    public List<String> getRelationNames() {
        RelationAPI api = new RelationWebService(getUrl("Relation")).getRelationAPIPort();
        List<Relation> relations = api.getRelations(getUser(), null);
        List<String> result = Lists.newArrayListWithExpectedSize(relations.size());
        for (Relation relation : relations) {
            result.add(relation.getName());
        }
        return result;
    }

    private DefinitionAPI getDefinitionService() {
        return new DefinitionWebService(getUrl("Definition")).getDefinitionAPIPort();
    }

    @Override
    public Map<WfDefinition, List<WfDefinition>> getProcessDefinitions(IProgressMonitor monitor) {
        DefinitionAPI api = getDefinitionService();
        List<WfDefinition> latestDefinitions = WfDefinitionAdapter.toDTOs(api.getLatestProcessDefinitions(getUser(), null));
        Map<WfDefinition, List<WfDefinition>> result = Maps.newHashMapWithExpectedSize(latestDefinitions.size());
        monitor.worked(30);
        double perDefinition = (double) 70 / latestDefinitions.size();
        for (WfDefinition latestDefinition : latestDefinitions) {
            List<WfDefinition> historyDefinitions;
            try {
                historyDefinitions = WfDefinitionAdapter.toDTOs(api.getProcessDefinitionHistory(getUser(), latestDefinition.getName()));
            } catch (Exception e) {
                PluginLogger.logErrorWithoutDialog("definition '" + latestDefinition.getName() + "' sync", e);
                historyDefinitions = Lists.newArrayList();
            }
            result.put(latestDefinition, historyDefinitions);
            monitor.internalWorked(perDefinition);
        }
        return result;
    }

    @Override
    public byte[] getProcessDefinitionArchive(WfDefinition definition) {
        return getDefinitionService().getProcessDefinitionFile(getUser(), definition.getId(), "par");
    }

    @Override
    public WfDefinition deployProcessDefinitionArchive(byte[] par) {
        return WfDefinitionAdapter.toDTO(getDefinitionService().deployProcessDefinition(getUser(), par, Lists.newArrayList("GPD")));
    }

    @Override
    public WfDefinition redeployProcessDefinitionArchive(Long definitionId, byte[] par, List<String> types) {
        try {
            return WfDefinitionAdapter.toDTO(getDefinitionService().redeployProcessDefinition(getUser(), definitionId, par, types));
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("Definition "+definitionId+" does not exists")) {
                throw new DefinitionDoesNotExistException(String.valueOf(definitionId));
            }
            throw Throwables.propagate(e);
        }
    }

    private BotAPI getBotService() {
        return new BotWebService(getUrl("Bot")).getBotAPIPort();
    }

    @Override
    public Map<Bot, List<BotTask>> getBots() {
        Map<Bot, List<BotTask>> result = Maps.newHashMap();
        List<BotStation> botStations = BotStationAdapter.toDTOs(getBotService().getBotStations());
        for (BotStation botStation : botStations) {
            for (Bot bot : BotAdapter.toDTOs(getBotService().getBots(getUser(), botStation.getId()))) {
                result.put(bot, BotTaskAdapter.toDTOs(getBotService().getBotTasks(getUser(), bot.getId())));
            }
        }
        return result;
    }

    @Override
    public byte[] getBotFile(Bot bot) {
        return getBotService().exportBot(getUser(), BotAdapter.toJAXB(bot));
    }

    @Override
    public byte[] getBotTaskFile(Bot bot, String botTask) {
        return getBotService().exportBotTask(getUser(), BotAdapter.toJAXB(bot), botTask);
    }

    @Override
    public void deployBot(String botStationName, byte[] archive) {
        BotStation botStation = BotStationAdapter.toDTO(getBotService().getBotStationByName(botStationName));
        if (botStation == null) {
            throw new BotStationDoesNotExistException(botStationName);
        }
        getBotService().importBot(getUser(), BotStationAdapter.toJAXB(botStation), archive, true);
    }

    @Override
    public byte[] getBotStationFile(BotStation botStation) {
        return getBotService().exportBotStation(getUser(), BotStationAdapter.toJAXB(botStation));
    }

    @Override
    public void deployBotStation(byte[] archive) {
        getBotService().importBotStation(getUser(), archive, true);
    }

    @Override
    public List<BotStation> getBotStations() {
        return BotStationAdapter.toDTOs(getBotService().getBotStations());
    }
}
