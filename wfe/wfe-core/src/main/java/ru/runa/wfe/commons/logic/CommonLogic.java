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
package ru.runa.wfe.commons.logic;

import java.util.List;

import javax.security.auth.Subject;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.timer.ScheduledTimerTask;

import ru.runa.wfe.commons.ApplicationContextFactory;
import ru.runa.wfe.commons.SystemProperties;
import ru.runa.wfe.commons.dao.Localization;
import ru.runa.wfe.commons.dao.LocalizationDAO;
import ru.runa.wfe.commons.dao.WfPropertyDAO;
import ru.runa.wfe.execution.dao.ProcessDAO;
import ru.runa.wfe.presentation.BatchPresentation;
import ru.runa.wfe.security.AuthorizationException;
import ru.runa.wfe.security.Identifiable;
import ru.runa.wfe.security.Permission;
import ru.runa.wfe.security.SecuredObjectType;
import ru.runa.wfe.security.dao.PermissionDAO;
import ru.runa.wfe.user.Actor;
import ru.runa.wfe.user.Executor;
import ru.runa.wfe.user.SystemExecutors;
import ru.runa.wfe.user.User;
import ru.runa.wfe.user.dao.ExecutorDAO;

import com.google.common.collect.Lists;

/**
 * Created on 14.03.2005
 */
public class CommonLogic {
    @Autowired
    protected PermissionDAO permissionDAO;
    @Autowired
    protected ExecutorDAO executorDAO;
    @Autowired
    protected LocalizationDAO localizationDAO;
    @Autowired
    protected ProcessDAO processDAO;
    @Autowired
    protected WfPropertyDAO wfPropertyDAO;

    protected <T extends Executor> T checkPermissionsOnExecutor(User user, T executor, Permission permission) {
        if (executor.getName().equals(SystemExecutors.PROCESS_STARTER_NAME) && permission.equals(Permission.READ)) {
            return executor;
        }
        checkPermissionAllowed(user, executor, permission);
        return executor;
    }

    protected <T extends Executor> List<T> checkPermissionsOnExecutors(User user, List<T> executors, Permission permission) {
        for (Executor executor : executors) {
            checkPermissionsOnExecutor(user, executor, permission);
        }
        return executors;
    }

    protected void checkPermissionAllowed(User user, Identifiable identifiable, Permission permission) throws AuthorizationException {
        if (!isPermissionAllowed(user, identifiable, permission)) {
            throw new AuthorizationException(user + " does not have " + permission + " to " + identifiable);
        }
    }

    public boolean isPermissionAllowed(User user, Identifiable identifiable, Permission permission) {
        return permissionDAO.isAllowed(user, permission, identifiable);
    }

    protected <T extends Identifiable> List<T> filterIdentifiable(User user, List<T> identifiables, Permission permission) {
        boolean[] allowedArray = permissionDAO.isAllowed(user, permission, identifiables);
        List<T> identifiableList = Lists.newArrayListWithExpectedSize(identifiables.size());
        for (int i = 0; i < allowedArray.length; i++) {
            if (allowedArray[i]) {
                identifiableList.add(identifiables.get(i));
            }
        }
        return identifiableList;
    }

    /**
     * Load objects list according to {@linkplain BatchPresentation} with
     * permission check for subject.
     * 
     * @param subject
     *            Current actor {@linkplain Subject}.
     * @param batchPresentation
     *            {@linkplain BatchPresentation} to load objects.
     * @param permission
     *            {@linkplain Permission}, which current actor must have on
     *            loaded objects.
     * @param securedObjectClasses
     *            Classes, loaded by query. Must be subset of classes, loaded by
     *            {@linkplain BatchPresentation}. For example {@linkplain Actor}
     *            for {@linkplain BatchPresentation}, which loads
     *            {@linkplain Executor}.
     * @param enablePaging
     *            Flag, equals true, if paging must be enabled; false to load
     *            all objects.
     * @return Loaded according to {@linkplain BatchPresentation} objects list.
     */
    @SuppressWarnings("unchecked")
    public <T extends Object> List<T> getPersistentObjects(User user, BatchPresentation batchPresentation, Permission permission,
            SecuredObjectType[] securedObjectTypes, boolean enablePaging) {
        return (List<T>) permissionDAO.getPersistentObjects(user, batchPresentation, permission, securedObjectTypes, enablePaging);
    }

    /**
     * Load objects count according to {@linkplain BatchPresentation} with
     * permission check for subject.
     * 
     * @param subject
     *            Current actor {@linkplain Subject}.
     * @param batchPresentation
     *            {@linkplain BatchPresentation} to load objects count.
     * @param permission
     *            {@linkplain Permission}, which current actor must have on
     *            loaded objects.
     * @param securedObjectClasses
     *            Classes, loaded by query. Must be subset of classes, loaded by
     *            {@linkplain BatchPresentation}. For example {@linkplain Actor}
     *            for {@linkplain BatchPresentation}, which loads
     *            {@linkplain Executor}.
     * @return Objects count, which will be loaded according to
     *         {@linkplain BatchPresentation}.
     */
    public int getPersistentObjectCount(User user, BatchPresentation batchPresentation, Permission permission, SecuredObjectType[] securedObjectTypes) {
        return permissionDAO.getPersistentObjectCount(user, batchPresentation, permission, securedObjectTypes);
    }

    public List<Localization> getLocalizations(User user) {
        // TODO permissions
        return localizationDAO.getAll();
    }

    public String getLocalized(User user, String name) {
        // TODO permissions
        return localizationDAO.getLocalized(name);
    }

    public void saveLocalizations(User user, List<Localization> localizations) {
        // TODO permissions
        localizationDAO.saveLocalizations(localizations, true);
    }
    
    public String getWfProperty(String fileName, String name) {
    	return wfPropertyDAO.getValue(fileName, name);
    }
    
    public void setWfProperty(String fileName, String name, String value) {
    	wfPropertyDAO.setValue(fileName, name, value);
    	if (fileName.equals("system.properties")) {
    		String bean = null;
    		if (name.equals("timertask.period.millis.job.execution")) bean = "jobExecutorTask";
    		if (name.equals("timertask.period.millis.unassigned.tasks.execution")) bean = "tasksAssignTask";
    		if (name.equals("timertask.period.millis.ldap.sync")) bean = "ldapSynchronizerTask";
    		if (bean != null) {
    			try {
					Long period = SystemProperties.getResources().getLongProperty(name, 60000);
					ScheduledTimerTask t = ApplicationContextFactory.getContext().getBean(bean, ScheduledTimerTask.class);
					t.setPeriod(period);
				} catch (BeansException e) {
					e.printStackTrace();
				}
    		}
    	}
    }
    
    public void clearWfProperties() {
    	wfPropertyDAO.clear();
    }

}
