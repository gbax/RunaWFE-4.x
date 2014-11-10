package ru.runa.wf.web.datafile.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import ru.runa.wfe.bot.Bot;
import ru.runa.wfe.bot.BotStation;
import ru.runa.wfe.bot.BotTask;
import ru.runa.wfe.commons.xml.XmlUtils;
import ru.runa.wfe.script.AdminScriptConstants;
import ru.runa.wfe.security.Identifiable;
import ru.runa.wfe.service.delegate.Delegates;
import ru.runa.wfe.user.User;

public class BotDataFileBuilder implements DataFileBuilder {
    private final User user;

    public BotDataFileBuilder(User user) {
        this.user = user;
    }

    @Override
    public void build(ZipOutputStream zos, Document script) throws Exception {
        List<BotStation> botStations = Delegates.getBotService().getBotStations();
        for (BotStation botStation : botStations) {
            populateBotStation(script, botStation);
            List<Bot> bots = Delegates.getBotService().getBots(user, botStation.getId());
            for (Bot bot : bots) {
                populateBot(script, bot, botStation.getName());
                List<BotTask> botTasks = Delegates.getBotService().getBotTasks(user, bot.getId());
                for (BotTask botTask : botTasks) {
                    populateBotTask(script, botTask, botStation.getName(), bot.getUsername());
                    byte[] conf = botTask.getConfiguration();
                    if (conf == null || conf.length == 0) {
                        continue;
                    }
                    zos.putNextEntry(new ZipEntry(PATH_TO_BOTTASK + botTask.getName() + ".conf"));
                    zos.write(conf);
                }
            }
        }

        new PermissionsDataFileBuilder(new ArrayList<Identifiable>(botStations), "addPermissionsOnBotStations", user).build(zos, script);
    }

    private void populateBotStation(Document script, BotStation botStation) {
        Element element = script.getRootElement().addElement("createBotStation", XmlUtils.RUNA_NAMESPACE);
        if (StringUtils.isNotEmpty(botStation.getName())) {
            element.addAttribute(AdminScriptConstants.NAME_ATTRIBUTE_NAME, botStation.getName());
        }
        if (StringUtils.isNotEmpty(botStation.getAddress())) {
            element.addAttribute(AdminScriptConstants.ADDRESS_ATTRIBUTE_NAME, botStation.getAddress());
        }
    }

    private void populateBot(Document script, Bot bot, String botStationName) {
        Element element = script.getRootElement().addElement("createBot", XmlUtils.RUNA_NAMESPACE);
        element.addAttribute(AdminScriptConstants.BOTSTATION_ATTRIBUTE_NAME, botStationName);
        if (StringUtils.isNotEmpty(bot.getUsername())) {
            element.addAttribute(AdminScriptConstants.NAME_ATTRIBUTE_NAME, bot.getUsername());
        }
        element.addAttribute(AdminScriptConstants.PASSWORD_ATTRIBUTE_NAME, "");
    }

    private void populateBotTask(Document script, BotTask botTask, String botStationName, String botName) {
        Element element = script.getRootElement().addElement("addConfigurationsToBot", XmlUtils.RUNA_NAMESPACE);
        element.addAttribute(AdminScriptConstants.BOTSTATION_ATTRIBUTE_NAME, botStationName);
        element.addAttribute(AdminScriptConstants.NAME_ATTRIBUTE_NAME, botName);
        Element subElement = element.addElement("botConfiguration", XmlUtils.RUNA_NAMESPACE);
        subElement.addAttribute(AdminScriptConstants.NAME_ATTRIBUTE_NAME, botTask.getName());
        if (StringUtils.isNotEmpty(botTask.getTaskHandlerClassName())) {
            subElement.addAttribute(AdminScriptConstants.HANDLER_ATTRIBUTE_NAME, botTask.getTaskHandlerClassName());
        }
        if (StringUtils.isNotEmpty(botTask.getName())) {
            subElement.addAttribute(AdminScriptConstants.NAME_ATTRIBUTE_NAME, botTask.getName());
        }
        if (botTask.getConfiguration() != null && botTask.getConfiguration().length > 0) {
            subElement.addAttribute(AdminScriptConstants.CONFIGURATION_STRING_ATTRIBUTE_NAME, botTask.getName() + ".conf");
        }
    }
}
