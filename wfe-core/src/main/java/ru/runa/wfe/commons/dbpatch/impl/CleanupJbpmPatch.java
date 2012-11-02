package ru.runa.wfe.commons.dbpatch.impl;

import java.util.List;

import org.hibernate.Session;

import ru.runa.wfe.InternalApplicationException;
import ru.runa.wfe.commons.DBType;
import ru.runa.wfe.commons.dbpatch.DBPatch;

public class CleanupJbpmPatch extends DBPatch {

    @Override
    protected List<String> getDDLQueriesBefore() {
        if (dbType == DBType.MySQL) {
            throw new InternalApplicationException(
                    "MySQL DB update to version RunaWFE4.x is not supported because of mass column (which are foreign keys) renames [Error on rename of (errno: 150)]");
        }
        List<String> sql = super.getDDLQueriesBefore();
        // TODO PATCH
        return sql;
    }

    @Override
    protected void applyPatch(Session session) throws Exception {
        // try {
        // removeTable("JBPM_ID_MEMBERSHIP");
        // } catch (Exception e) {
        // // may not exist
        // log.warn("Unable to delete: " + e.getMessage());
        // }
        // try {
        // removeTable("JBPM_ID_PERMISSIONS");
        // } catch (Exception e) {
        // // may not exist
        // log.warn("Unable to delete: " + e.getMessage());
        // }
        // try {
        // removeTable("JBPM_ID_GROUP");
        // } catch (Exception e) {
        // // may not exist
        // log.warn("Unable to delete: " + e.getMessage());
        // }
        // try {
        // removeTable("JBPM_ID_USER");
        // } catch (Exception e) {
        // // may not exist
        // log.warn("Unable to delete: " + e.getMessage());
        // }
        //
        // removeColumn("JBPM_DELEGATION", "CONFIGTYPE_");
        // try {
        // removeTable("PROPERTY_IDS");
        // } catch (Exception e) {
        // // may not exist
        // log.warn("Unable to delete: " + e.getMessage());
        // }
        // removeColumn("JBPM_PROCESSDEFINITION", "CLASS_");
        //
        // // ru.runa.wfe.wfe.bpm.graph.action.Script
        // removeForeignKey("JBPM_VARIABLEACCESS", "FK_VARACC_SCRIPT");
        // removeColumn("JBPM_VARIABLEACCESS", "SCRIPT_");
        // removeForeignKey("JBPM_NODE", "FK_NODE_SCRIPT");
        // removeColumn("JBPM_NODE", "SCRIPT_");
        //
        // // ru.runa.wfe.wfe.bpm.execution.Comment
        // removeTable("JBPM_COMMENT");
        //
        // // ru.runa.wfe.wfe.bpm.element.ExceptionHandler
        // removeForeignKey("JBPM_ACTION", "FK_ACTION_EXPTHDL");
        // removeColumn("JBPM_ACTION", "EXCEPTIONHANDLER_");
        // removeColumn("JBPM_ACTION", "EXCEPTIONHANDLERINDEX_");
        // removeTable("JBPM_EXCEPTIONHANDLER");
        //
        // // ru.runa.wfe.wfe.bpm.execution.RuntimeAction
        // removeTable("JBPM_RUNTIMEACTION");
        //
        // // ru.runa.wfe.wfe.bpm.graph.node.DecisionCondition
        // removeTable("JBPM_DECISIONCONDITIONS");
        //
        // // ru.runa.wfe.wfe.bpm.taskmgmt.exe.PooledActor
        // removeColumn("JBPM_TASK", "POOLEDACTORSEXPRESSION_");
        // removeColumn("JBPM_SWIMLANE", "POOLEDACTORSEXPRESSION_");
        // removeTable("JBPM_TASKACTORPOOL");
        // removeTable("JBPM_POOLEDACTOR");
        //
        // // ru.runa.wfe.wfe.bpm.taskmgmt.def.TaskController
        // removeForeignKey("JBPM_VARIABLEACCESS", "FK_VARACC_TSKCTRL");
        // removeColumn("JBPM_VARIABLEACCESS", "TASKCONTROLLER_");
        // removeColumn("JBPM_VARIABLEACCESS", "INDEX_");
        // removeForeignKey("JBPM_TASK", "FK_TSK_TSKCTRL");
        // removeColumn("JBPM_TASK", "TASKCONTROLLER_");
        // removeTable("JBPM_TASKCONTROLLER");
        //
        // // logs
        // removeColumn("JBPM_LOG", "EXCEPTION_");
        // // ru.runa.wfe.wfe.bpm.taskmgmt.log.SwimlaneLog
        // // ru.runa.wfe.wfe.bpm.taskmgmt.log.SwimlaneCreateLog
        // // ru.runa.wfe.wfe.bpm.taskmgmt.log.SwimlaneAssignLog
        // removeForeignKey("JBPM_LOG", "FK_LOG_SWIMINST");
        // removeColumn("JBPM_LOG", "SWIMLANEINSTANCE_");
        // removeColumn("JBPM_LOG", "OLDLONGIDCLASS_");
        // removeColumn("JBPM_LOG", "OLDLONGIDVALUE_");
        // removeColumn("JBPM_LOG", "NEWLONGIDCLASS_");
        // removeColumn("JBPM_LOG", "NEWLONGIDVALUE_");
        // removeColumn("JBPM_LOG", "OLDSTRINGIDCLASS_");
        // removeColumn("JBPM_LOG", "OLDSTRINGIDVALUE_");
        // removeColumn("JBPM_LOG", "NEWSTRINGIDCLASS_");
        // removeColumn("JBPM_LOG", "NEWSTRINGIDVALUE_");
        // removeForeignKey("JBPM_LOG", "FK_LOG_NODE");
        // removeColumn("JBPM_LOG", "NODE_");
        // removeForeignKey("JBPM_LOG", "FK_LOG_TRANSITION");
        // removeColumn("JBPM_LOG", "TRANSITION_");
        // removeForeignKey("JBPM_LOG", "FK_LOG_SOURCENODE");
        // removeColumn("JBPM_LOG", "SOURCENODE_");
        // removeForeignKey("JBPM_LOG", "FK_LOG_DESTNODE");
        // removeColumn("JBPM_LOG", "DESTINATIONNODE_");
        // removeForeignKey("JBPM_LOG", "FK_LOG_ACTION");
        // removeColumn("JBPM_LOG", "ACTION_");
        //
        // // definition
        // removeColumn("JBPM_PROCESSDEFINITION", "STARTSTATE_");
        // removeColumn("JBPM_PROCESSDEFINITION", "ISTERMINATIONIMPLICIT_");
        //
        // // removeForeignKey("JBPM_MODULEDEFINITION", "FK_MODDEF_PROCDEF");
        // // TODO PROCESS_FILES CHANGE DEFINITION_ID
        // removeTable("JBPM_MODULEDEFINITION");
        //
        // // execution
        // removeColumn("JBPM_TOKEN", "ISTERMINATIONIMPLICIT_");
        // removeColumn("JBPM_TOKEN", "ISSUSPENDED_");
        // removeColumn("JBPM_TOKEN", "LOCK_");
        // removeForeignKey("JBPM_TOKEN", "FK_TOKEN_NODE");
        // removeColumn("JBPM_TOKEN", "NODE_");
        //
        // // removeIndex("JBPM_PROCESSINSTANCE", "IDX_PROCIN_KEY");
        // removeColumn("JBPM_PROCESSINSTANCE", "KEY_");
        // removeColumn("JBPM_PROCESSINSTANCE", "ISSUSPENDED_");
        //
        // removeColumn("JBPM_TASKINSTANCE", "ISBLOCKING_");
        // removeColumn("JBPM_TASKINSTANCE", "PRIORITY_");
        // removeColumn("JBPM_TASKINSTANCE", "ISCANCELLED_");
        // removeColumn("JBPM_TASKINSTANCE", "ISSUSPENDED_");
        // removeColumn("JBPM_TASKINSTANCE", "CLASS_");
        // removeForeignKey("JBPM_TASKINSTANCE", "FK_TASKINST_TASK");
        // removeColumn("JBPM_TASKINSTANCE", "TASK_");
        //
        // removeColumn("JBPM_JOB", "ISSUSPENDED_");
        // // TODO почистить job c фиктивным LOCKOWNER_
        // removeColumn("JBPM_JOB", "LOCKOWNER_");
        // removeColumn("JBPM_JOB", "LOCKTIME_");
        // removeColumn("JBPM_JOB", "ISEXCLUSIVE_");
        // removeColumn("JBPM_JOB", "EXCEPTION_");
        // removeColumn("JBPM_JOB", "RETRIES_");
        // removeColumn("JBPM_JOB", "NODE_");
        //
        // removeForeignKey("JBPM_MODULEINSTANCE", "FK_TASKMGTINST_TMD");
        // removeColumn("JBPM_MODULEINSTANCE", "TASKMGMTDEFINITION_");
        //
        // removeForeignKey("JBPM_NODE_SUBPROC", "FK_NODE_SUBPROC_NODE");
        // removeColumn("JBPM_NODE_SUBPROC", "NODE_");
        //
        // removeForeignKey("JBPM_PASSTRANS", "FK_PASSTRANS_TRANS");
        // removeColumn("JBPM_PASSTRANS", "TRANSITION_");
        //
        // removeForeignKey("JBPM_SWIMLANEINSTANCE", "FK_SWIMLANEINST_SL");
        // removeColumn("JBPM_SWIMLANEINSTANCE", "SWIMLANE_");

    }

}
