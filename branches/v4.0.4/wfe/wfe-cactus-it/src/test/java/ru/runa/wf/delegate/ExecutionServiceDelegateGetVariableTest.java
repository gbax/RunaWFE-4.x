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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.cactus.ServletTestCase;

import ru.runa.wfe.security.AuthenticationException;
import ru.runa.wfe.security.AuthorizationException;
import ru.runa.wfe.security.Permission;
import ru.runa.wfe.service.delegate.Delegates;
import ru.runa.wfe.service.ExecutionService;
import ru.runa.wfe.definition.DefinitionPermission;
import ru.runa.wfe.task.dto.WfTask;
import ru.runa.wf.service.WfServiceTestHelper;

import com.google.common.collect.Lists;
import ru.runa.wfe.var.dto.WfVariable;

/**
 * Created on 23.04.2005
 * 
 * @author Gritsenko_S
 */
public class ExecutionServiceDelegateGetVariableTest extends ServletTestCase {
    private ExecutionService executionService;

    private WfServiceTestHelper helper = null;

    private final String variableName = "var1";

    private final String variableValue = "var1Value";

    private Long processId;

    @Override
    protected void setUp() throws Exception {
        helper = new WfServiceTestHelper(getClass().getName());
        executionService = Delegates.getExecutionService();

        helper.deployValidProcessDefinition();

        Collection<Permission> permissions = Lists.newArrayList(DefinitionPermission.START_PROCESS,
                DefinitionPermission.READ_STARTED_PROCESS);
        helper.setPermissionsToAuthorizedPerformerOnDefinitionByName(permissions, WfServiceTestHelper.VALID_PROCESS_NAME);

        HashMap<String, Object> variablesMap = new HashMap<String, Object>();
        variablesMap.put(variableName, variableValue);
        executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.VALID_PROCESS_NAME, variablesMap);

        // taskId = executionDelegate.getTasks(helper.getAdminUser(), helper.getTaskBatchPresentation())[0].getId();

        super.setUp();
    }

    private void initTaskId() throws AuthorizationException, AuthenticationException {
        List<WfTask> tasks = executionService.getTasks(helper.getAdminUser(), helper.getTaskBatchPresentation());
        assertNotNull(tasks);
        assertEquals(tasks.size() > 0, true);
        processId = tasks.get(0).getProcessId();
    }

    @Override
    protected void tearDown() throws Exception {
        helper.undeployValidProcessDefinition();
        helper.releaseResources();
        executionService = null;
        super.tearDown();
    }

    public void testGetVariableByAuthorizedSubject() throws Exception {
        initTaskId();
        WfVariable variable = executionService.getVariable(helper.getAuthorizedPerformerUser(), processId, variableName);
        assertEquals("variable has incorrect value", variableValue, variable.getValue());
    }
}
