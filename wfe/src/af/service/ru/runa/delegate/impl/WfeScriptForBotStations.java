/*
 * This file is part of the RUNA WFE project.
 * 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation; version 2.1 
 * of the License. 
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU Lesser General Public License for more details. 
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this program; if not, write to the Free Software 
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package ru.runa.delegate.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import ru.runa.af.AuthenticationException;
import ru.runa.af.AuthorizationException;
import ru.runa.af.Bot;
import ru.runa.af.BotStation;
import ru.runa.af.BotTask;
import ru.runa.commons.xml.SimpleErrorHandler;
import ru.runa.commons.xml.XMLHelper;
import ru.runa.wf.logic.WfeScriptRunner;

public class WfeScriptForBotStations extends WfeScriptRunner {

    private final boolean replace;
    private BotStation botStation = null;
    private Map<String, byte[]> configs;

    public WfeScriptForBotStations(Subject subject, boolean replace) {
        this.replace = replace;
        setSubject(subject);
        setProcessDefinitionsBytes(new byte[0][0]);
    }

    public void setBotStation(BotStation bs) {
        botStation = bs;
    }

    public void setConfigs(Map<String, byte[]> configs) {
        this.configs = configs;
    }

    public static Document createScriptForBotLoading(Bot bot, List<BotTask> tasks) throws ParserConfigurationException, FactoryConfigurationError,
            AuthorizationException, AuthenticationException, SAXException, IOException {
        Document script = XMLHelper.newDocument(PATH_ENTITY_RESOLVER, SimpleErrorHandler.getInstance());
        Element rootElement = script.createElement("workflowScript");
        rootElement.setAttribute("xmlns", "http://runa.ru/xml");
        rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        rootElement.setAttribute("xsi:schemaLocation", "http://runa.ru/xml workflowScript.xsd");
        script.appendChild(rootElement);
        Element createBotElement = script.createElement("createBot");
        createBotElement.setAttribute(NAME_ATTRIBUTE_NAME, bot.getWfeUser());
        createBotElement.setAttribute(PASSWORD_ATTRIBUTE_NAME, "");
        createBotElement.setAttribute(STARTTIMEOUT_ATTRIBUTE_NAME, "" + bot.getLastInvoked());
        rootElement.appendChild(createBotElement);

        if (tasks.size() > 0) {
            Element removeTasks = script.createElement("removeConfigurationsFromBot");
            removeTasks.setAttribute(NAME_ATTRIBUTE_NAME, bot.getWfeUser());
            for (BotTask task : tasks) {
                Element taskElement = script.createElement("botConfiguration");
                taskElement.setAttribute(NAME_ATTRIBUTE_NAME, task.getName());
                removeTasks.appendChild(taskElement);
            }
            rootElement.appendChild(removeTasks);
            Element addTasks = script.createElement("addConfigurationsToBot");
            addTasks.setAttribute(NAME_ATTRIBUTE_NAME, bot.getWfeUser());
            for (BotTask task : tasks) {
                Element taskElement = script.createElement("botConfiguration");
                taskElement.setAttribute(NAME_ATTRIBUTE_NAME, task.getName());
                taskElement.setAttribute(HANDLER_ATTRIBUTE_NAME, task.getClazz());
                if (task.getConfiguration() != null) {
                    taskElement.setAttribute(CONFIGURATION_STRING_ATTRIBUTE_NAME, task.getName() + ".conf");
                }
                addTasks.appendChild(taskElement);
            }
            rootElement.appendChild(addTasks);
        }
        return script;
    }

    @Override
    public void removeConfigurationsFromBot(Element element) throws Exception {
        if (replace) {
            super.removeConfigurationsFromBotCommon(element, botStation);
        }
    }

    @Override
    public void addConfigurationsToBot(Element element) throws Exception {
        addConfigurationsToBotCommon(element, botStation);
    }

    @Override
    public void createBot(Element element) throws Exception {
        createBotCommon(element, botStation);
    }

    @Override
    protected byte[] getBotTaskConfiguration(String config) throws IOException {
        return configs.get(config);
    }
}
