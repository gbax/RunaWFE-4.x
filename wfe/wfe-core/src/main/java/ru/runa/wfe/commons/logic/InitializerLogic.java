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

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.beans.factory.annotation.Autowired;

import ru.runa.wfe.InternalApplicationException;
import ru.runa.wfe.commons.ApplicationContextFactory;
import ru.runa.wfe.commons.ClassLoaderUtil;
import ru.runa.wfe.commons.DBType;
import ru.runa.wfe.commons.SystemProperties;
import ru.runa.wfe.commons.dao.ConstantDAO;
import ru.runa.wfe.commons.dao.Localization;
import ru.runa.wfe.commons.dao.LocalizationDAO;
import ru.runa.wfe.commons.dbpatch.DBPatch;
import ru.runa.wfe.commons.dbpatch.UnsupportedPatch;
import ru.runa.wfe.commons.dbpatch.impl.AddHierarchyProcess;
import ru.runa.wfe.commons.dbpatch.impl.JbpmRefactoringPatch;
import ru.runa.wfe.commons.dbpatch.impl.NodeTypeChangePatch;
import ru.runa.wfe.commons.dbpatch.impl.PerformancePatch401;
import ru.runa.wfe.commons.dbpatch.impl.PermissionMappingPatch403;
import ru.runa.wfe.commons.dbpatch.impl.TaskEndDateRemovalPatch;
import ru.runa.wfe.commons.dbpatch.impl.TransitionLogPatch;
import ru.runa.wfe.security.SecuredObjectType;
import ru.runa.wfe.security.dao.PermissionDAO;
import ru.runa.wfe.user.Actor;
import ru.runa.wfe.user.Executor;
import ru.runa.wfe.user.Group;
import ru.runa.wfe.user.SystemExecutors;
import ru.runa.wfe.user.dao.ExecutorDAO;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

/**
 * Initial DB population and update during version change.
 * 
 * @author Dofs
 */
public class InitializerLogic {
    protected static final Log log = LogFactory.getLog(InitializerLogic.class);
    private static final String IS_DATABASE_INITIALIZED_VARIABLE_NAME = "ru.runa.database_initialized";
    private static final String DATABASE_VERSION_VARIABLE_NAME = "ru.runa.database_version";

    private static final List<Class<? extends DBPatch>> dbPatches = Lists.newArrayList();

    static {
        dbPatches.add(UnsupportedPatch.class);
        dbPatches.add(UnsupportedPatch.class);
        dbPatches.add(UnsupportedPatch.class);
        dbPatches.add(UnsupportedPatch.class);
        dbPatches.add(UnsupportedPatch.class);
        dbPatches.add(UnsupportedPatch.class);
        dbPatches.add(UnsupportedPatch.class);
        dbPatches.add(UnsupportedPatch.class);
        dbPatches.add(UnsupportedPatch.class);
        dbPatches.add(UnsupportedPatch.class);
        dbPatches.add(UnsupportedPatch.class);
        dbPatches.add(UnsupportedPatch.class);
        dbPatches.add(UnsupportedPatch.class);
        dbPatches.add(UnsupportedPatch.class);
        dbPatches.add(UnsupportedPatch.class);
        dbPatches.add(UnsupportedPatch.class);
        dbPatches.add(UnsupportedPatch.class);
        dbPatches.add(UnsupportedPatch.class);
        dbPatches.add(UnsupportedPatch.class);
        dbPatches.add(UnsupportedPatch.class); // 20
        // 4.x
        dbPatches.add(AddHierarchyProcess.class);
        dbPatches.add(JbpmRefactoringPatch.class); // 22
        dbPatches.add(TransitionLogPatch.class);
        dbPatches.add(PerformancePatch401.class);
        dbPatches.add(TaskEndDateRemovalPatch.class);
        dbPatches.add(PermissionMappingPatch403.class);
        dbPatches.add(NodeTypeChangePatch.class);
    };

    @Autowired
    private ConstantDAO constantDAO;
    @Autowired
    private ExecutorDAO executorDAO;
    @Autowired
    private PermissionDAO permissionDAO;
    @Autowired
    private LocalizationDAO localizationDAO;

    private boolean isAlreadyIntialized() {
        String version = constantDAO.getValue(IS_DATABASE_INITIALIZED_VARIABLE_NAME);
        return "true".equalsIgnoreCase(version);
    }

    /**
     * Initialize database if needed. Default hibernate session must be already
     * set to archive if required.
     */
    public void init(UserTransaction transaction) {
        try {
            if (!isAlreadyIntialized()) {
                initializeDatabase(transaction);
            } else {
                log.info("database is initialized. skipping initialization ...");
                applyPatches(transaction);
            }
            String localizedFileName = "localizations." + Locale.getDefault().getLanguage() + ".xml";
            InputStream stream = ClassLoaderUtil.getAsStream(localizedFileName, getClass());
            if (stream == null) {
                stream = ClassLoaderUtil.getAsStreamNotNull("localizations.xml", getClass());
            }
            List<Localization> localizations = LocalizationParser.parseLocalizations(stream);
            stream = ClassLoaderUtil.getAsStream(SystemProperties.RESOURCE_EXTENSION_PREFIX + localizedFileName, getClass());
            if (stream == null) {
                stream = ClassLoaderUtil.getAsStream(SystemProperties.RESOURCE_EXTENSION_PREFIX + "localizations.xml", getClass());
            }
            if (stream != null) {
                localizations.addAll(LocalizationParser.parseLocalizations(stream));
            }
            localizationDAO.saveLocalizations(localizations, false);
        } catch (Exception e) {
            log.error("initialization failed", e);
        }
    }

    /**
     * Initialize database.
     * 
     * @param daoHolder
     *            Helper object for getting DAO's.
     */
    private void initializeDatabase(UserTransaction transaction) {
        log.info("database is not initialized. initializing...");
        SchemaExport schemaExport = new SchemaExport(ApplicationContextFactory.getConfiguration());
        schemaExport.create(true, true);
        try {
            transaction.begin();
            insertInitialData();
            constantDAO.saveOrUpdateConstant(IS_DATABASE_INITIALIZED_VARIABLE_NAME, String.valueOf(Boolean.TRUE));
            constantDAO.saveOrUpdateConstant(DATABASE_VERSION_VARIABLE_NAME, String.valueOf(dbPatches.size()));
            transaction.commit();
        } catch (Throwable th) {
            rollbackTransaction(transaction);
            throw Throwables.propagate(th);
        }
    }

    private void insertInitialData() {
        // create privileged Executors
        String administratorDescription = "Default System Administrator";
        Actor admin = new Actor(SystemProperties.getAdministratorName(), administratorDescription, administratorDescription);
        admin = executorDAO.create(admin);
        executorDAO.setPassword(admin, SystemProperties.getAdministratorDefaultPassword());
        Group adminGroup = executorDAO.create(new Group(SystemProperties.getAdministratorsGroupName(), "Default Group For System Administrators"));
        executorDAO.create(new Group(SystemProperties.getBotsGroupName(), SystemProperties.getBotsGroupName()));
        List<? extends Executor> adminWithGroupExecutors = Lists.newArrayList(adminGroup, admin);
        executorDAO.addExecutorToGroup(admin, adminGroup);
        executorDAO.create(new Actor(SystemExecutors.PROCESS_STARTER_NAME, SystemExecutors.PROCESS_STARTER_DESCRIPTION));
        // define executor permissions
        permissionDAO.addType(SecuredObjectType.ACTOR, adminWithGroupExecutors);
        permissionDAO.addType(SecuredObjectType.GROUP, adminWithGroupExecutors);
        // define system permissions
        permissionDAO.addType(SecuredObjectType.SYSTEM, adminWithGroupExecutors);
        permissionDAO.addType(SecuredObjectType.RELATIONGROUP, adminWithGroupExecutors);
        permissionDAO.addType(SecuredObjectType.RELATION, adminWithGroupExecutors);
        permissionDAO.addType(SecuredObjectType.RELATIONPAIR, adminWithGroupExecutors);
        permissionDAO.addType(SecuredObjectType.BOTSTATION, adminWithGroupExecutors);
        permissionDAO.addType(SecuredObjectType.DEFINITION, adminWithGroupExecutors);
        permissionDAO.addType(SecuredObjectType.PROCESS, adminWithGroupExecutors);
    }

    /**
     * Apply patches to initialized database.
     */
    private void applyPatches(UserTransaction transaction) {
        String versionString = constantDAO.getValue(DATABASE_VERSION_VARIABLE_NAME);
        int dbVersion = Strings.isNullOrEmpty(versionString) ? 0 : Integer.parseInt(versionString);
        DBType dbType = ApplicationContextFactory.getDBType();
        boolean isDDLTransacted = (dbType == DBType.MSSQL || dbType == DBType.POSTGRESQL);
        String isDDLTransactedProperty = System.getProperty("runawfe.transacted.ddl");
        if (isDDLTransactedProperty != null) {
            try {
                isDDLTransacted = Boolean.valueOf(isDDLTransactedProperty);
            } catch (Exception e) {
                log.warn("Unable to parse system property 'runawfe.transacted.ddl' as boolean: " + e);
            }
        }
        log.info("Database version: " + dbVersion + ", code version: " + dbPatches.size());
        while (dbVersion < dbPatches.size()) {
            DBPatch patch = ApplicationContextFactory.createAutowiredBean(dbPatches.get(dbVersion));
            dbVersion++;
            log.info("Applying patch " + patch + " (" + dbVersion + ")");
            try {
                if (isDDLTransacted) {
                    transaction.begin();
                }
                patch.executeDDLBefore(isDDLTransacted);
                if (!isDDLTransacted) {
                    transaction.begin();
                }
                patch.executeDML();
                if (!isDDLTransacted) {
                    constantDAO.saveOrUpdateConstant(DATABASE_VERSION_VARIABLE_NAME, String.valueOf(dbVersion));
                    transaction.commit();
                }
                patch.executeDDLAfter(isDDLTransacted);
                if (isDDLTransacted) {
                    constantDAO.saveOrUpdateConstant(DATABASE_VERSION_VARIABLE_NAME, String.valueOf(dbVersion));
                    transaction.commit();
                }
                log.info("Patch " + patch.getClass().getName() + "(" + dbVersion + ") is applied to database successfuly.");
            } catch (Throwable th) {
                log.error("Can't apply patch " + patch.getClass().getName() + "(" + dbVersion + ").", th);
                rollbackTransaction(transaction);
                break;
            }
        }
    }

    // TODO to helper
    private void rollbackTransaction(UserTransaction transaction) {
        int status = -1;
        try {
            status = transaction.getStatus();
            if (status != Status.STATUS_NO_TRANSACTION && status != Status.STATUS_ROLLEDBACK) {
                transaction.rollback();
            } else {
                log.warn("Unable to rollback, status: " + status);
            }
        } catch (Exception e) {
            throw new InternalApplicationException("Unable to rollback, status: " + status, e);
        }
    }

}
