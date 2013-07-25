package ru.runa.wfe.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import ru.runa.wfe.bot.Bot;
import ru.runa.wfe.bot.BotStation;
import ru.runa.wfe.bot.BotTask;
import ru.runa.wfe.bot.logic.BotLogic;
import ru.runa.wfe.commons.ApplicationContextFactory;
import ru.runa.wfe.service.decl.BotServiceLocal;
import ru.runa.wfe.service.decl.BotServiceRemote;
import ru.runa.wfe.service.delegate.WfeScriptForBotStations;
import ru.runa.wfe.service.interceptors.EjbExceptionSupport;
import ru.runa.wfe.service.interceptors.EjbTransactionSupport;
import ru.runa.wfe.service.interceptors.PerformanceObserver;
import ru.runa.wfe.user.User;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;

/**
 * Implements BotsService as bean.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@Interceptors({ EjbExceptionSupport.class, PerformanceObserver.class, EjbTransactionSupport.class, SpringBeanAutowiringInterceptor.class })
@WebService(name = "BotAPI", serviceName = "BotWebService")
@SOAPBinding
public class BotServiceBean implements BotServiceLocal, BotServiceRemote {
    @Autowired
    private BotLogic botLogic;

    @Override
    public BotStation createBotStation(@WebParam(name = "user") User user, @WebParam(name = "botStation") BotStation botStation) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(botStation);
        return botLogic.createBotStation(user, botStation);
    }

    @Override
    public BotStation getBotStation(@WebParam(name = "id") Long id) {
        Preconditions.checkNotNull(id);
        return botLogic.getBotStationNotNull(id);
    }

    @Override
    public BotStation getBotStationByName(@WebParam(name = "name") String name) {
        Preconditions.checkNotNull(name);
        return botLogic.getBotStation(name);
    }

    @Override
    public List<BotStation> getBotStations() {
        return botLogic.getBotStations();
    }

    @Override
    public void removeBotStation(@WebParam(name = "user") User user, @WebParam(name = "id") Long id) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(id);
        botLogic.removeBotStation(user, id);
    }

    @Override
    public void updateBotStation(@WebParam(name = "user") User user, @WebParam(name = "botStation") BotStation botStation) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(botStation);
        botLogic.updateBotStation(user, botStation);
    }

    @Override
    public Bot getBot(@WebParam(name = "user") User user, @WebParam(name = "id") Long id) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(id);
        return botLogic.getBotNotNull(user, id);
    }

    @Override
    public void removeBot(@WebParam(name = "user") User user, @WebParam(name = "id") Long id) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(id);
        botLogic.removeBot(user, id);
    }

    @Override
    public List<Bot> getBots(@WebParam(name = "user") User user, @WebParam(name = "botStationId") Long botStationId) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(botStationId);
        return botLogic.getBots(user, botStationId);
    }

    @Override
    public Bot createBot(@WebParam(name = "user") User user, @WebParam(name = "bot") Bot bot) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(bot);
        return botLogic.createBot(user, bot);
    }

    @Override
    public void updateBot(@WebParam(name = "user") User user, @WebParam(name = "bot") Bot bot) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(bot);
        botLogic.updateBot(user, bot);
    }

    @Override
    public List<BotTask> getBotTasks(@WebParam(name = "user") User user, @WebParam(name = "id") Long id) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(id);
        return botLogic.getBotTasks(user, id);
    }

    @Override
    public BotTask createBotTask(@WebParam(name = "user") User user, @WebParam(name = "botTask") BotTask botTask) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(botTask);
        return botLogic.createBotTask(user, botTask);
    }

    @Override
    public void updateBotTask(@WebParam(name = "user") User user, @WebParam(name = "botTask") BotTask botTask) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(botTask);
        botLogic.updateBotTask(user, botTask);
    }

    @Override
    public void removeBotTask(@WebParam(name = "user") User user, @WebParam(name = "id") Long id) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(id);
        botLogic.removeBotTask(user, id);
    }

    @Override
    public BotTask getBotTask(@WebParam(name = "user") User user, @WebParam(name = "id") Long id) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(id);
        return botLogic.getBotTaskNotNull(user, id);
    }

    @Override
    public byte[] exportBot(@WebParam(name = "user") User user, @WebParam(name = "bot") Bot bot) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(bot);
        List<BotTask> tasks = botLogic.getBotTasks(user, bot.getId());
        return exportBotWithTasks(bot, tasks);
    }

    private byte[] exportBotWithTasks(Bot bot, List<BotTask> tasks) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zipStream = new ZipOutputStream(baos);
            zipStream.putNextEntry(new ZipEntry("script.xml"));
            byte[] script = WfeScriptForBotStations.createScriptForBotLoading(bot, tasks);
            zipStream.write(script);
            for (BotTask task : tasks) {
                byte[] conf = task.getConfiguration();
                if (conf == null || conf.length == 0) {
                    continue;
                }
                zipStream.putNextEntry(new ZipEntry(task.getName() + ".conf"));
                zipStream.write(conf);
            }
            zipStream.close();
            baos.flush();
            return baos.toByteArray();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public byte[] exportBotStation(@WebParam(name = "user") User user, @WebParam(name = "botStation") BotStation botStation) {
        try {
            Preconditions.checkNotNull(user);
            Preconditions.checkNotNull(botStation);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zipStream = new ZipOutputStream(baos);
            zipStream.putNextEntry(new ZipEntry("botstation"));
            zipStream.write(botStation.getName().getBytes(Charsets.UTF_8));
            zipStream.write('\n');
            zipStream.write(botStation.getAddress().getBytes(Charsets.UTF_8));
            for (Bot bot : getBots(user, botStation.getId())) {
                zipStream.putNextEntry(new ZipEntry(bot.getUsername() + ".bot"));
                byte[] botArchive = exportBot(user, bot);
                zipStream.write(botArchive);
            }
            zipStream.close();
            baos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public void importBot(@WebParam(name = "user") User user, @WebParam(name = "botStation") BotStation botStation,
            @WebParam(name = "archive") byte[] archive, @WebParam(name = "replace") boolean replace) {
        try {
            Preconditions.checkNotNull(user);
            Preconditions.checkNotNull(botStation);
            Preconditions.checkNotNull(archive);
            ZipInputStream zin = new ZipInputStream(new ByteArrayInputStream(archive));
            Map<String, byte[]> files = new HashMap<String, byte[]>();
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {
                byte[] bytes = ByteStreams.toByteArray(zin);
                files.put(entry.getName(), bytes);
            }
            byte[] scriptXml = files.remove("script.xml");
            Preconditions.checkNotNull(scriptXml, "Incorrect bot archive: no script.xml inside");
            WfeScriptForBotStations wfeScriptForBotStations = new WfeScriptForBotStations(user, replace);
            ApplicationContextFactory.autowireBean(wfeScriptForBotStations);
            wfeScriptForBotStations.setBotStation(botStation);
            wfeScriptForBotStations.setConfigs(files);
            wfeScriptForBotStations.runScript(scriptXml);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public void importBotStation(@WebParam(name = "user") User user, @WebParam(name = "archive") byte[] archive,
            @WebParam(name = "replace") boolean replace) {
        try {
            Preconditions.checkNotNull(user);
            Preconditions.checkNotNull(archive);
            ZipInputStream zin = new ZipInputStream(new ByteArrayInputStream(archive));
            ZipEntry entry;
            BotStation station = null;
            while ((entry = zin.getNextEntry()) != null) {
                if (entry.getName().equals("botstation")) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(zin, Charsets.UTF_8));
                    String name = r.readLine();
                    String addr = r.readLine();
                    station = getBotStationByName(name);
                    if (station == null) {
                        station = createBotStation(user, new BotStation(name, addr));
                    }
                    continue;
                }
                if (station == null) {
                    throw new IOException("Incorrect bot archive");
                }
                importBot(user, station, ByteStreams.toByteArray(zin), replace);
            }
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public byte[] exportBotTask(@WebParam(name = "user") User user, @WebParam(name = "bot") Bot bot,
            @WebParam(name = "botTaskName") String botTaskName) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(bot);
        Preconditions.checkNotNull(botTaskName);
        List<BotTask> tasks = Lists.newArrayList();
        tasks.add(botLogic.getBotTaskNotNull(user, bot.getId(), botTaskName));
        return exportBotWithTasks(bot, tasks);
    }

}
