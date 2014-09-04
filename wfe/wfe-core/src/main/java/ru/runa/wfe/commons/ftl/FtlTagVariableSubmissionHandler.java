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
package ru.runa.wfe.commons.ftl;

import java.util.Map;

import ru.runa.wfe.var.VariableDefinition;

/**
 * Interface allowed freemarker tags make custom parsing user input value in
 * task form.
 * 
 * @author dofs
 * @since 4.2.0
 */
public interface FtlTagVariableSubmissionHandler {
    public static final String KEY_PREFIX = "var_handler_";

    /**
     * Processing method
     * 
     * @param variableDefinition
     *            variable definition
     * @param userInput
     *            raw user input
     * @return parsed values
     * @throws Exception
     *             if any error occurs; message will be displayed to user
     */
    public Map<String, ? extends Object> extractVariables(VariableDefinition variableDefinition, Map<String, ? extends Object> userInput);

}
