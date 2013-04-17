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
package ru.runa.wf.jpdl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.cactus.ServletTestCase;

import ru.runa.wf.service.WfServiceTestHelper;

import com.google.common.collect.Lists;
import ru.runa.wfe.InternalApplicationException;
import ru.runa.wfe.definition.DefinitionPermission;
import ru.runa.wfe.presentation.BatchPresentation;
import ru.runa.wfe.security.Permission;
import ru.runa.wfe.service.ExecutionService;
import ru.runa.wfe.service.delegate.Delegates;
import ru.runa.wfe.task.dto.WfTask;

/**
 * Created on 14.05.2005
 * 
 * @author Gritsenko_S
 */
public class DecisionTest extends ServletTestCase {
    private ExecutionService executionService;

    private WfServiceTestHelper helper = null;

    private Map<String, Object> startVariables;

    private BatchPresentation batchPresentation;

    public static Test suite() {
        return new TestSuite(DecisionTest.class);
    }

    @Override
    protected void setUp() throws Exception {
        helper = new WfServiceTestHelper(getClass().getName());
        executionService = Delegates.getExecutionService();

        helper.deployValidProcessDefinition(WfServiceTestHelper.DECISION_JPDL_PROCESS_FILE_NAME);

        Collection<Permission> permissions = Lists.newArrayList(DefinitionPermission.START_PROCESS, DefinitionPermission.READ,
                DefinitionPermission.READ_STARTED_PROCESS);
        helper.setPermissionsToAuthorizedPerformerOnDefinitionByName(permissions, WfServiceTestHelper.DECISION_JPDL_PROCESS_NAME);

        batchPresentation = helper.getTaskBatchPresentation();

        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        helper.undeployValidProcessDefinition(WfServiceTestHelper.DECISION_JPDL_PROCESS_NAME);
        helper.releaseResources();
        executionService = null;
        super.tearDown();
    }

    public void testPath1() throws Exception {
        startVariables = new HashMap<String, Object>();
        startVariables.put("def_variable", "false");
        executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.DECISION_JPDL_PROCESS_NAME, startVariables);

        List<WfTask> tasks = executionService.getTasks(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getErpOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getHrOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 1, tasks.size());
        assertEquals("task name differs from expected", "state_2", tasks.get(0).getName());

        executionService.completeTask(helper.getHrOperatorUser(), tasks.get(0).getId(), new HashMap<String, Object>(), null);

        tasks = executionService.getTasks(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getErpOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getHrOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());
    }

    public void testPath2() throws Exception {
        startVariables = new HashMap<String, Object>();
        startVariables.put("def_variable", "true");
        executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.DECISION_JPDL_PROCESS_NAME, startVariables);

        List<WfTask> tasks = executionService.getTasks(helper.getErpOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getHrOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 1, tasks.size());
        assertEquals("task name differs from expected", "state_1", tasks.get(0).getName());

        Map<String, Object> state1Variables = new HashMap<String, Object>();
        state1Variables.put("monitoring_variable", "end");
        executionService.completeTask(helper.getAuthorizedPerformerUser(), tasks.get(0).getId(), state1Variables, null);

        tasks = executionService.getTasks(helper.getErpOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getHrOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());
    }

    public void testPath3() throws Exception {
        startVariables = new HashMap<String, Object>();
        startVariables.put("def_variable", "true");
        executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.DECISION_JPDL_PROCESS_NAME, startVariables);

        List<WfTask> tasks = executionService.getTasks(helper.getErpOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getHrOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 1, tasks.size());
        assertEquals("task name differs from expected", "state_1", tasks.get(0).getName());

        Map<String, Object> state1Variables = new HashMap<String, Object>();
        state1Variables.put("monitoring_variable", "2");
        executionService.completeTask(helper.getAuthorizedPerformerUser(), tasks.get(0).getId(), state1Variables, null);

        tasks = executionService.getTasks(helper.getErpOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getHrOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 1, tasks.size());
        assertEquals("task name differs from expected", "state_2", tasks.get(0).getName());

        executionService.completeTask(helper.getHrOperatorUser(), tasks.get(0).getId(), state1Variables, null);

        tasks = executionService.getTasks(helper.getErpOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getHrOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());
    }

    public void testPath4() throws Exception {
        startVariables = new HashMap<String, Object>();
        startVariables.put("def_variable", "true");
        executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.DECISION_JPDL_PROCESS_NAME, startVariables);

        List<WfTask> tasks = executionService.getTasks(helper.getErpOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getHrOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 1, tasks.size());
        assertEquals("task name differs from expected", "state_1", tasks.get(0).getName());

        Map<String, Object> state1Variables = new HashMap<String, Object>();
        state1Variables.put("monitoring_variable", "3");
        executionService.completeTask(helper.getAuthorizedPerformerUser(), tasks.get(0).getId(), state1Variables, null);

        tasks = executionService.getTasks(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getHrOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getErpOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 1, tasks.size());
        assertEquals("task name differs from expected", "state_3", tasks.get(0).getName());

        executionService.completeTask(helper.getErpOperatorUser(), tasks.get(0).getId(), state1Variables, null);

        tasks = executionService.getTasks(helper.getErpOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getHrOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());
    }

    public void testPath5() throws Exception {
        startVariables = new HashMap<String, Object>();
        startVariables.put("def_variable", "true");
        executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.DECISION_JPDL_PROCESS_NAME, startVariables);

        List<WfTask> tasks = executionService.getTasks(helper.getErpOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getHrOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 1, tasks.size());
        assertEquals("task name differs from expected", "state_1", tasks.get(0).getName());

        Map<String, Object> state1Variables = new HashMap<String, Object>();
        state1Variables.put("monitoring_variable", "1");
        executionService.completeTask(helper.getAuthorizedPerformerUser(), tasks.get(0).getId(), state1Variables, null);

        tasks = executionService.getTasks(helper.getErpOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getHrOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 1, tasks.size());
        assertEquals("task name differs from expected", "state_1", tasks.get(0).getName());

        state1Variables.clear();
        state1Variables.put("monitoring_variable", "1");
        for (int i = 0; i < 7; i++) {
            executionService.completeTask(helper.getAuthorizedPerformerUser(), tasks.get(0).getId(), state1Variables, null);
            tasks = executionService.getTasks(helper.getErpOperatorUser(), batchPresentation);
            assertEquals("tasks length differs from expected", 0, tasks.size());
            tasks = executionService.getTasks(helper.getHrOperatorUser(), batchPresentation);
            assertEquals("tasks length differs from expected", 0, tasks.size());
            tasks = executionService.getTasks(helper.getAuthorizedPerformerUser(), batchPresentation);
            assertEquals("tasks length differs from expected", 1, tasks.size());
            assertEquals("task name differs from expected", "state_1", tasks.get(0).getName());
        }

        state1Variables.clear();
        state1Variables.put("monitoring_variable", "4");
        executionService.completeTask(helper.getAuthorizedPerformerUser(), tasks.get(0).getId(), state1Variables, null);

        tasks = executionService.getTasks(helper.getErpOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getHrOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());
    }

    public void testPath6() throws Exception {
        try {
            startVariables = new HashMap<String, Object>();
            startVariables.put("def_variable", "Error_Var");
            executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.DECISION_JPDL_PROCESS_NAME, startVariables);

            List<WfTask> tasks = executionService.getTasks(helper.getErpOperatorUser(), batchPresentation);
            assertEquals("tasks length differs from expected", 0, tasks.size());

            tasks = executionService.getTasks(helper.getHrOperatorUser(), batchPresentation);
            assertEquals("tasks length differs from expected", 1, tasks.size());

            tasks = executionService.getTasks(helper.getAuthorizedPerformerUser(), batchPresentation);
            assertEquals("tasks length differs from expected", 0, tasks.size());
        } catch (InternalApplicationException e) {
            // may be throwed in future
        }
    }

    public void testPath7() throws Exception {
        startVariables = new HashMap<String, Object>();
        startVariables.put("def_variable", "true");
        executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.DECISION_JPDL_PROCESS_NAME, startVariables);

        List<WfTask> tasks = executionService.getTasks(helper.getErpOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getHrOperatorUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 0, tasks.size());

        tasks = executionService.getTasks(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("tasks length differs from expected", 1, tasks.size());
        assertEquals("task name differs from expected", "state_1", tasks.get(0).getName());

        Map<String, Object> state1Variables = new HashMap<String, Object>();
        state1Variables.put("monitoring_variable", "Error_Var2");

        try {
            executionService.completeTask(helper.getAuthorizedPerformerUser(), tasks.get(0).getId(), state1Variables, null);
            assertFalse(" Integer in decision parsed value 'Error_Var2' ", true);
        } catch (Exception e) {
            // expected
        }
    }
}
