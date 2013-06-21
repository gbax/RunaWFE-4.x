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
package ru.runa.wf.delegate;

import com.google.common.collect.Lists;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.cactus.ServletTestCase;
import ru.runa.wf.service.WfServiceTestHelper;
import ru.runa.wfe.definition.DefinitionPermission;
import ru.runa.wfe.presentation.BatchPresentation;
import ru.runa.wfe.presentation.ClassPresentation;
import ru.runa.wfe.presentation.FieldDescriptor;
import ru.runa.wfe.presentation.filter.AnywhereStringFilterCriteria;
import ru.runa.wfe.security.AuthenticationException;
import ru.runa.wfe.security.Permission;
import ru.runa.wfe.service.ExecutionService;
import ru.runa.wfe.service.delegate.Delegates;
import ru.runa.wfe.task.dto.WfTask;
import ru.runa.wfe.user.User;
import ru.runa.wfe.var.dto.WfVariable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created on 23.04.2005
 * 
 * @author Gritsenko_S
 * @author Vitaliy S
 */
public class ExecutionServiceDelegateGetTasksTest extends ServletTestCase {
    private ExecutionService executionService;

    private WfServiceTestHelper helper = null;

    private BatchPresentation batchPresentation;

    @Override
    protected void setUp() throws Exception {
        helper = new WfServiceTestHelper(getClass().getName());
        executionService = Delegates.getExecutionService();

        helper.deployValidProcessDefinition(WfServiceTestHelper.SWIMLANE_PROCESS_FILE_NAME);

        Collection<Permission> permissions = Lists.newArrayList(DefinitionPermission.START_PROCESS);
        helper.setPermissionsToAuthorizedPerformerOnDefinitionByName(permissions, WfServiceTestHelper.SWIMLANE_PROCESS_NAME);

        executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.SWIMLANE_PROCESS_NAME, null);

        batchPresentation = helper.getTaskBatchPresentation();

        helper.addExecutorToGroup(helper.getAuthorizedPerformerActor(), helper.getBossGroup());

        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        helper.undeployValidProcessDefinition(WfServiceTestHelper.SWIMLANE_PROCESS_NAME);
        helper.releaseResources();
        executionService = null;
        batchPresentation = null;
        super.tearDown();
    }

    public void testGetTasksByAuthorizedSubject() throws Exception {
        List<WfTask> tasks = executionService.getTasks(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("Tasks not returned for Authorized Subject", 1, tasks.size());
        assertEquals("state name differs from expected", "evaluating", tasks.get(0).getName());
        assertEquals("task <evaluating> is assigned before completeTask()", helper.getAuthorizedPerformerUser(), tasks.get(0).getOwner());

        Map<String, Object> variables = WfServiceTestHelper.createVariablesMap("approved", "true");
        executionService.completeTask(helper.getAuthorizedPerformerUser(), tasks.get(0).getId(), variables, null);

        tasks = executionService.getTasks(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("Tasks not returned for Authorized Subject", 1, tasks.size());
        assertEquals("state name differs from expected", "treating collegues on cake and pie", tasks.get(0).getName());
        assertEquals("task <treating collegues on cake and pie> is not assigned after starting [requester]", helper.getAuthorizedPerformerActor(),
                tasks.get(0).getOwner());
        executionService.completeTask(helper.getAuthorizedPerformerUser(), tasks.get(0).getId(), variables, null);

        tasks = executionService.getTasks(helper.getErpOperatorUser(), batchPresentation);
        assertEquals("Tasks not returned for Erp Operator Subject", 1, tasks.size());
        assertEquals("state name differs from expected", "updating erp asynchronously", tasks.get(0).getName());
        assertEquals("task <updating erp asynchronously> is not assigned before competeTask()", helper.getErpOperator(), tasks.get(0).getOwner());

        executionService.completeTask(helper.getErpOperatorUser(), tasks.get(0).getId(), variables, null);

        tasks = executionService.getTasks(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("Tasks are returned for Authorized Subject", 0, tasks.size());

        tasks = executionService.getTasks(helper.getErpOperatorUser(), batchPresentation);
        assertEquals("Tasks not returned for Erp Operator Subject", 1, tasks.size());
        assertEquals("state name differs from expected", "notify", tasks.get(0).getName());
        assertEquals("task <notify> is in assigned swimlane", helper.getErpOperator(), tasks.get(0).getOwner());

        executionService.completeTask(helper.getErpOperatorUser(), tasks.get(0).getId(), variables, null);

        tasks = executionService.getTasks(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("Tasks are returned for Authorized Subject", 0, tasks.size());

        tasks = executionService.getTasks(helper.getErpOperatorUser(), batchPresentation);
        assertEquals("Tasks are returned for Erp Operator Subject", 0, tasks.size());
    }

    public void testGetTasksByVariableFilterByAuthorizedSubjectWithExactMatch() throws Exception {
        executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.SWIMLANE_PROCESS_NAME,
                WfServiceTestHelper.createVariablesMap("var1", "var1Value"));
        executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.SWIMLANE_PROCESS_NAME,
                WfServiceTestHelper.createVariablesMap("var2", "var2Value"));
        FieldDescriptor[] fields = batchPresentation.getAllFields();
        for (int i = 0; i < fields.length; ++i) {
            if (fields[i].displayName.startsWith(ClassPresentation.editable_prefix)) {
                batchPresentation.addDynamicField(i, "var1");
            }
        }
        batchPresentation.getFilteredFields().put(0, new AnywhereStringFilterCriteria(/*TODO new String[] { "var1Value" }*/));
        List<WfTask> tasks = executionService.getTasks(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals(1, tasks.size());
        List<WfVariable> variables = executionService.getVariables(helper.getAuthorizedPerformerUser(), tasks.get(0).getProcessId());
        assertEquals("var1Value", variables.get(0).getValue());
    }

    public void testGetTasksByVariableFilterByAuthorizedSubjectWithContainsMatch() throws Exception {
        executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.SWIMLANE_PROCESS_NAME,
                WfServiceTestHelper.createVariablesMap("var1", "var1Value"));
        executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.SWIMLANE_PROCESS_NAME,
                WfServiceTestHelper.createVariablesMap("var2", "var2Value"));
        FieldDescriptor[] fields = batchPresentation.getAllFields();
        for (int i = 0; i < fields.length; ++i) {
            if (fields[i].displayName.startsWith(ClassPresentation.editable_prefix)) {
                batchPresentation.addDynamicField(i, "var1");
            }
        }
        batchPresentation.getFilteredFields().put(0, new AnywhereStringFilterCriteria(/*TODO new String[] { "1Val" }*/));
        List<WfTask> tasks = executionService.getTasks(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals(1, tasks.size());
        List<WfVariable> variables = executionService.getVariables(helper.getAuthorizedPerformerUser(), tasks.get(0).getProcessId());
        assertEquals("var1Value", variables.get(0).getValue());
    }

    public void testGetTasksByUnauthorizedSubject() throws Exception {
        List<WfTask> tasks = executionService.getTasks(helper.getUnauthorizedPerformerUser(), batchPresentation);
        assertEquals("Tasks returned for Unauthorized Subject", 0, tasks.size());
    }

    public void testGetTasksByFakeSubject() throws Exception {
        try {
            executionService.getTasks(helper.getFakeUser(), batchPresentation);
            fail("testGetTasksByFakeSubject(), no AuthenticationException");
        } catch (AuthenticationException e) {
        }
    }

    public void testGetTasksByNullSubject() throws Exception {
        try {
            executionService.getTasks(null, batchPresentation);
            fail("testGetTasksByNullSubject(), no IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }
}
