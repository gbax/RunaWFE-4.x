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
package ru.runa.wf.web.html;

import javax.servlet.jsp.PageContext;

import org.apache.ecs.html.TD;

import ru.runa.common.web.Messages;
import ru.runa.common.web.Resources;
import ru.runa.common.web.html.TDBuilder;
import ru.runa.wfe.audit.ProcessDefinitionDeleteLog;
import ru.runa.wfe.audit.ProcessDeleteLog;
import ru.runa.wfe.audit.SystemLog;

/**
 * {@link TDBuilder} implementation to show system log as human readable
 * message.
 */
public class SystemLogTDBuilder implements TDBuilder {

    /**
     * Process place holder name. All occurrences of this place holder will be
     * replaced with process instance identity.
     */
    private static String placeHolderProcess = null;

    /**
     * Process definition name place holder name. All occurrences of this place
     * holder will be replaced with process definition name.
     */
    private static String placeHolderProcessDefinition = null;

    /**
     * Process definition version place holder name. All occurrences of this
     * place holder will be replaced with process definition version.
     */
    private static String placeHolderVersion = null;

    private static synchronized void initPlacehlders(PageContext pageContext) {
        if (placeHolderProcess != null) {
            return;
        }
        placeHolderProcess = Messages.getMessage(Messages.HISTORY_SYSTEM_PH_PI, pageContext);
        placeHolderProcessDefinition = Messages.getMessage(Messages.HISTORY_SYSTEM_PH_PD, pageContext);
        placeHolderVersion = Messages.getMessage(Messages.HISTORY_SYSTEM_PH_VERSION, pageContext);
    }

    @Override
    public TD build(Object object, Env env) {
        TD result = new TD(getValue(object, env));
        result.setClass(Resources.CLASS_LIST_TABLE_TD);
        return result;
    }

    @Override
    public String getValue(Object object, Env env) {
        initPlacehlders(env.getPageContext());
        SystemLog systemLog = (SystemLog) object;
        if (systemLog instanceof ProcessDeleteLog) {
            ProcessDeleteLog log = (ProcessDeleteLog) systemLog;
            return Messages.getMessage(Messages.SYSTEM_LOG_PROCESS_DELETED, env.getPageContext())
                    .replaceAll("\\{" + placeHolderProcessDefinition + "\\}", log.getName() != null ? log.getName() : "")
                    .replaceAll("\\{" + placeHolderProcess + "\\}", String.valueOf(log.getProcessId()));
        } else if (systemLog instanceof ProcessDefinitionDeleteLog) {
            ProcessDefinitionDeleteLog log = (ProcessDefinitionDeleteLog) systemLog;
            return Messages.getMessage(Messages.SYSTEM_LOG_DEFINITION_DELETED, env.getPageContext())
                    .replaceAll("\\{" + placeHolderProcessDefinition + "\\}", log.getName())
                    .replaceAll("\\{" + placeHolderVersion + "\\}", String.valueOf(log.getVersion()));
        }
        return "Unsupported log instance";
    }

    @Override
    public String[] getSeparatedValues(Object object, Env env) {
        return new String[] { getValue(object, env) };
    }

    @Override
    public int getSeparatedValuesCount(Object object, Env env) {
        return 1;
    }
}
