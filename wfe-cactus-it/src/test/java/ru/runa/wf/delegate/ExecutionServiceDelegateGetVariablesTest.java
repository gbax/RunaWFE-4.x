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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.cactus.ServletTestCase;

import ru.runa.junit.ArrayAssert;
import ru.runa.wf.service.WfServiceTestHelper;
import ru.runa.wfe.definition.DefinitionPermission;
import ru.runa.wfe.execution.ProcessDoesNotExistException;
import ru.runa.wfe.security.AuthenticationException;
import ru.runa.wfe.security.AuthorizationException;
import ru.runa.wfe.security.Permission;
import ru.runa.wfe.service.ExecutionService;
import ru.runa.wfe.service.delegate.Delegates;
import ru.runa.wfe.task.dto.WfTask;
import ru.runa.wfe.var.dto.WfVariable;

import com.google.common.collect.Lists;

/**
 * Created on 23.04.2005
 * 
 * @author Gritsenko_S
 */
public class ExecutionServiceDelegateGetVariablesTest extends ServletTestCase {
    private ExecutionService executionService;

    private WfServiceTestHelper helper = null;

    private Long taskId;

    private Long processId;

    private final String variableName = "var1";

    private final String variableValue = "var1Value";

    @Override
    protected void setUp() throws Exception {
        helper = new WfServiceTestHelper(getClass().getName());
        executionService = Delegates.getExecutionService();

        helper.deployValidProcessDefinition(WfServiceTestHelper.SWIMLANE_PROCESS_FILE_NAME);

        Collection<Permission> permissions = Lists.newArrayList(DefinitionPermission.START_PROCESS, DefinitionPermission.READ_STARTED_PROCESS);
        helper.setPermissionsToAuthorizedPerformerOnDefinitionByName(permissions, WfServiceTestHelper.SWIMLANE_PROCESS_NAME);

        HashMap<String, Object> variablesMap = new HashMap<String, Object>();
        variablesMap.put(variableName, variableValue);
        processId = executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.SWIMLANE_PROCESS_NAME, variablesMap);

        helper.addExecutorToGroup(helper.getAuthorizedPerformerActor(), helper.getBossGroup());
        WfTask taskStub = executionService.getTasks(helper.getAuthorizedPerformerUser(), helper.getTaskBatchPresentation()).get(0);
        taskId = taskStub.getId();
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        helper.undeployValidProcessDefinition(WfServiceTestHelper.SWIMLANE_PROCESS_NAME);
        helper.releaseResources();
        executionService = null;
        super.tearDown();
    }

    public void testGetVariablesByUnauthorizedSubject() throws Exception {
        try {
            executionService.getVariables(helper.getUnauthorizedPerformerUser(), processId);
            fail("testGetVariablesByUnauthorizedSubject(), no AuthorizationException");
        } catch (AuthorizationException e) {
        }
    }

    public void testGetVariablesByFakeSubject() throws Exception {
        try {
            executionService.getVariables(helper.getFakeUser(), processId);
            fail("testGetVariablesByFakeSubject(), no AuthenticationException");
        } catch (AuthenticationException e) {
        }
    }

    public void testGetVariablesByNullSubject() throws Exception {
        try {
            executionService.getVariables(null, processId);
            fail("testGetVariablesByNullSubject(), no IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    public void testGetVariablesByAuthorizedSubjectWithInvalidProcessId() throws Exception {
        try {
            executionService.getVariables(helper.getAuthorizedPerformerUser(), -1l);
            fail("testGetVariablesByAuthorizedSubjectWithInvalidTaskId(), no TaskDoesNotExistException");
        } catch (ProcessDoesNotExistException e) {
        }
    }

    public void testGetVariablesByAuthorizedSubject() throws Exception {
        List<WfVariable> variables = executionService.getVariables(helper.getAuthorizedPerformerUser(), processId);
        List<String> names = new ArrayList<String>();
        for (WfVariable v : variables) {
            names.add(v.getDefinition().getName());
        }

        List<String> expectedNames = Lists.newArrayList(variableName);
        ArrayAssert.assertWeakEqualArrays("variable names are not equal", expectedNames, names);

        HashMap<String, Object> variables2 = new HashMap<String, Object>();
        variables2.put("var2", "var2Value");
        variables2.put("var3", "var3Value");
        variables2.put("approved", "true");
        executionService.completeTask(helper.getAuthorizedPerformerUser(), taskId, variables2, null);

        taskId = executionService.getTasks(helper.getErpOperatorUser(), helper.getTaskBatchPresentation()).get(0).getId();

        variables = executionService.getVariables(helper.getAdminUser(), processId);

        names = new ArrayList<String>();
        HashMap<String, Object> vars = new HashMap<String, Object>();
        for (WfVariable v : variables) {
            names.add(v.getDefinition().getName());
            vars.put(v.getDefinition().getName(), v.getValue());
        }
        expectedNames = Lists.newArrayList("var2", "var3", "approved", variableName);
        ArrayAssert.assertWeakEqualArrays("variable names are not equal", expectedNames, names);

        assertEquals(" variable value: <var1> differs from expected", "var1Value", vars.get("var1"));
        assertEquals(" variable value: <var2> differs from expected", "var2Value", vars.get("var2"));
        assertEquals(" variable value: <var3> differs from expected", "var3Value", vars.get("var3"));
        assertEquals(" variable value: <approved> differs from expected", "true", vars.get("approved"));
    }
}
