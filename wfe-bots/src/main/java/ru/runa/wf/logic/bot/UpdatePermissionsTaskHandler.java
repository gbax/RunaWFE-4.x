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
package ru.runa.wf.logic.bot;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import ru.runa.service.af.AuthorizationService;
import ru.runa.service.delegate.Delegates;
import ru.runa.wf.logic.bot.updatepermission.Method;
import ru.runa.wf.logic.bot.updatepermission.UpdatePermissionsSettings;
import ru.runa.wf.logic.bot.updatepermission.UpdatePermissionsXmlParser;
import ru.runa.wfe.InternalApplicationException;
import ru.runa.wfe.handler.bot.TaskHandlerBase;
import ru.runa.wfe.os.OrgFunction;
import ru.runa.wfe.os.OrgFunctionHelper;
import ru.runa.wfe.security.Identifiable;
import ru.runa.wfe.security.Permission;
import ru.runa.wfe.task.dto.WfTask;
import ru.runa.wfe.user.Executor;
import ru.runa.wfe.user.User;
import ru.runa.wfe.var.IVariableProvider;

import com.google.common.collect.Lists;

/**
 * Sets permissions to current process.
 * 
 * @author dofs
 * @since 2.0
 */
public class UpdatePermissionsTaskHandler extends TaskHandlerBase {
    private UpdatePermissionsSettings settings;

    @Override
    public void setConfiguration(String configuration) {
        settings = UpdatePermissionsXmlParser.read(configuration);
    }

    @Override
    public Map<String, Object> handle(User user, IVariableProvider variableProvider, WfTask task) throws Exception {
        boolean allowed = true;
        if (settings.isConditionExists()) {
            String conditionVar = variableProvider.getValue(String.class, settings.getConditionVarName());
            if (!settings.getConditionVarValue().equals(conditionVar)) {
                allowed = false;
            }
        }
        if (allowed) {
            List<? extends Executor> executors = evaluateOrgFunctions(variableProvider, settings.getOrgFunctions());
            AuthorizationService authorizationService = ru.runa.service.delegate.Delegates.getAuthorizationService();
            List<Collection<Permission>> allPermissions = Lists.newArrayListWithExpectedSize(executors.size());
            Identifiable identifiable = Delegates.getExecutionService().getProcess(user, task.getProcessId());
            List<Long> executorIds = Lists.newArrayList();
            for (Executor executor : executors) {
                Collection<Permission> oldPermissions = authorizationService.getOwnPermissions(user, executor, identifiable);
                allPermissions.add(getNewPermissions(oldPermissions, settings.getPermissions(), settings.getMethod()));
                executorIds.add(executor.getId());
            }
            authorizationService.setPermissions(user, executorIds, allPermissions, identifiable);
        }
        return null;
    }

    private List<? extends Executor> evaluateOrgFunctions(IVariableProvider variableProvider, List<OrgFunction> orgFunctions) {
        List<Executor> executors = Lists.newArrayList();
        for (OrgFunction orgFunction : orgFunctions) {
            executors.addAll(OrgFunctionHelper.evaluateOrgFunction(orgFunction, variableProvider));
        }
        return executors;
    }

    private Collection<Permission> getNewPermissions(Collection<Permission> oldPermissions, Collection<Permission> permissions, Method method) {
        if (Method.add == method) {
            return Permission.mergePermissions(oldPermissions, permissions);
        } else if (Method.set == method) {
            return permissions;
        } else if (Method.delete == method) {
            return Permission.subtractPermissions(oldPermissions, permissions);
        } else {
            // should never happened
            throw new InternalApplicationException("Unknown method provided: " + method);
        }
    }
}
