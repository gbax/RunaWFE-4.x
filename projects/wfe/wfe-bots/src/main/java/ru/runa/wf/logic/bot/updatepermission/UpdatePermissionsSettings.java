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
package ru.runa.wf.logic.bot.updatepermission;

import java.util.Collection;
import java.util.List;

import ru.runa.wfe.os.OrgFunction;
import ru.runa.wfe.security.Permission;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class UpdatePermissionsSettings {
    private final Collection<Permission> permissions = Lists.newArrayList();
    private final List<OrgFunction> orgFunctions = Lists.newArrayList();
    private Method method;
    private String conditionVarName;
    private String conditionVarValue;

    public void setCondition(String conditionVarName, String conditionVarValue) {
        this.conditionVarName = conditionVarName;
        this.conditionVarValue = conditionVarValue;
    }

    public boolean isConditionExists() {
        return !Strings.isNullOrEmpty(conditionVarName);
    }

    public String getConditionVarName() {
        return conditionVarName;
    }

    public String getConditionVarValue() {
        return conditionVarValue;
    }

    public List<OrgFunction> getOrgFunctions() {
        return orgFunctions;
    }

    public Collection<Permission> getPermissions() {
        return permissions;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
