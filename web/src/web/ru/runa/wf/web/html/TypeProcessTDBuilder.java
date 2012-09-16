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

import org.apache.ecs.html.TD;

import ru.runa.common.web.html.TDBuilder;
import ru.runa.wf.ProcessDefinition;

/**
 * Created on 10.08.2007
 * 
 * @author kana
 */
public class TypeProcessTDBuilder implements TDBuilder {

    public TypeProcessTDBuilder() {
    }

    public TD build(Object object, Env env) {
        TD td = new TD();
        td.setClass(ru.runa.common.web.Resources.CLASS_LIST_TABLE_TD);
        String type = getValue(object, env);
        td.addElement(type);
        return td;
    }

    public String getValue(Object object, Env env) {
        ProcessDefinition pd = (ProcessDefinition) object;
        String result = pd.getType()[pd.getType().length - 1];
        if (result == null) {
            result = "";
        }
        return result;
    }

    public String[] getSeparatedValues(Object object, Env env) {
        ProcessDefinition pd = (ProcessDefinition) object;
        return pd.getType();
    }

    public int getSeparatedValuesCount(Object object, Env env) {
        ProcessDefinition pd = (ProcessDefinition) object;
        return pd.getType().length;
    }
}
