package ru.runa.gpd.util;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import ru.runa.gpd.lang.model.BotTask;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class BotScriptUtils {
    private final static String NAME_ATTRIBUTE_NAME = "name";
    private final static String PASSWORD_ATTRIBUTE_NAME = "password";
    private final static String STARTTIMEOUT_ATTRIBUTE_NAME = "startTimeout";
    private final static String HANDLER_ATTRIBUTE_NAME = "handler";
    private final static String CONFIGURATION_STRING_ATTRIBUTE_NAME = "configuration";
    private final static String ADD_BOT_CONFIGURATION_ELEMENT_NAME = "addConfigurationsToBot";
    private final static String BOT_CONFIGURATION_ELEMENT_NAME = "botConfiguration";

    public static Document createScriptForBotLoading(String botName, List<BotTask> tasks) {
        Document script = XmlUtil.createDocument("workflowScript", XmlUtil.RUNA_NAMESPACE, "workflowScript.xsd");
        Element rootElement = script.getRootElement();
        Element createBotElement = rootElement.addElement("createBot", XmlUtil.RUNA_NAMESPACE);
        createBotElement.addAttribute(NAME_ATTRIBUTE_NAME, botName);
        createBotElement.addAttribute(PASSWORD_ATTRIBUTE_NAME, "");
        createBotElement.addAttribute(STARTTIMEOUT_ATTRIBUTE_NAME, "");
        if (tasks.size() > 0) {
            Element removeTasks = rootElement.addElement("removeConfigurationsFromBot", XmlUtil.RUNA_NAMESPACE);
            removeTasks.addAttribute(NAME_ATTRIBUTE_NAME, botName);
            for (BotTask task : tasks) {
                Element taskElement = removeTasks.addElement("botConfiguration", XmlUtil.RUNA_NAMESPACE);
                taskElement.addAttribute(NAME_ATTRIBUTE_NAME, task.getName());
            }
            Element addTasks = rootElement.addElement("addConfigurationsToBot", XmlUtil.RUNA_NAMESPACE);
            addTasks.addAttribute(NAME_ATTRIBUTE_NAME, botName);
            for (BotTask task : tasks) {
                Element taskElement = addTasks.addElement("botConfiguration");
                taskElement.addAttribute(NAME_ATTRIBUTE_NAME, task.getName());
                taskElement.addAttribute(HANDLER_ATTRIBUTE_NAME, task.getDelegationClassName());
                if (!Strings.isNullOrEmpty(task.getDelegationClassName())) {
                    taskElement.addAttribute(CONFIGURATION_STRING_ATTRIBUTE_NAME, task.getName() + ".conf");
                }
            }
        }
        return script;
    }

    /**
     * 
     * @param inputStream xml script stream
     * @return map of bot task without configuration set -> configuration file name
     */
    public static List<BotTask> getBotTasksFromScript(byte[] scriptXml, Map<String, byte[]> files) {
        List<BotTask> botTasks = Lists.newArrayList();
        Document document = XmlUtil.parseWithXSDValidation(scriptXml, "workflowScript.xsd");
        List<Element> taskElements = document.getRootElement().elements(ADD_BOT_CONFIGURATION_ELEMENT_NAME);
        for (Element taskElement : taskElements) {
            List<Element> botList = taskElement.elements(BOT_CONFIGURATION_ELEMENT_NAME);
            for (Element botElement : botList) {
                String name = botElement.attributeValue(NAME_ATTRIBUTE_NAME);
                if (Strings.isNullOrEmpty(name)) {
                    continue;
                }
                String handler = botElement.attributeValue(HANDLER_ATTRIBUTE_NAME, "");
                String configurationFileName = botElement.attributeValue(CONFIGURATION_STRING_ATTRIBUTE_NAME);
                byte[] configurationFileData = files.remove(configurationFileName);
                String configuration = configurationFileData != null ? new String(configurationFileData, Charsets.UTF_8) : "";
                botTasks.add(BotTaskUtils.createBotTask(name, handler, configuration));
            }
        }
        return botTasks;
    }
}
