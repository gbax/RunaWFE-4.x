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
import ru.runa.junit.ArrayAssert;
import ru.runa.wf.service.WfServiceTestHelper;
import ru.runa.wfe.definition.DefinitionPermission;
import ru.runa.wfe.execution.dto.WfProcess;
import ru.runa.wfe.presentation.BatchPresentation;
import ru.runa.wfe.presentation.ClassPresentation;
import ru.runa.wfe.presentation.filter.AnywhereStringFilterCriteria;
import ru.runa.wfe.presentation.filter.FilterCriteria;
import ru.runa.wfe.security.AuthenticationException;
import ru.runa.wfe.security.Permission;
import ru.runa.wfe.service.ExecutionService;
import ru.runa.wfe.service.delegate.Delegates;
import ru.runa.wfe.var.Variable;
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
public class ExecutionServiceDelegateGetProcessInstanceStubsTest extends ServletTestCase {
    private ExecutionService executionService;

    private WfServiceTestHelper helper = null;

    private BatchPresentation batchPresentation;

    @Override
    protected void setUp() throws Exception {
        helper = new WfServiceTestHelper(getClass().getName());
        executionService = Delegates.getExecutionService();

        helper.deployValidProcessDefinition();

        helper.deployValidProcessDefinition(WfServiceTestHelper.SWIMLANE_PROCESS_FILE_NAME);
        Collection<Permission> permissions = Lists.newArrayList(DefinitionPermission.START_PROCESS);
        helper.setPermissionsToAuthorizedPerformerOnDefinitionByName(permissions, WfServiceTestHelper.SWIMLANE_PROCESS_NAME);

        Collection<Permission> startPermissions = Lists.newArrayList(DefinitionPermission.START_PROCESS,
                DefinitionPermission.READ_STARTED_PROCESS);
        helper.setPermissionsToAuthorizedPerformerOnDefinitionByName(startPermissions, WfServiceTestHelper.VALID_PROCESS_NAME);
        batchPresentation = helper.getProcessInstanceBatchPresentation();
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        helper.undeployValidProcessDefinition(WfServiceTestHelper.SWIMLANE_PROCESS_NAME);
        helper.undeployValidProcessDefinition();
        helper.releaseResources();
        executionService = null;
        batchPresentation = null;
        super.tearDown();
    }

    public void testGetProcessInstanceStubsByVariableFilterByAuthorizedSubject() throws Exception {
        String name = "reason";
        String value = "intention";
        Map<String, Object> variablesMap = WfServiceTestHelper.createVariablesMap(name, value);
        executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.SWIMLANE_PROCESS_NAME, variablesMap);
        executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.SWIMLANE_PROCESS_NAME, variablesMap);
        executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.SWIMLANE_PROCESS_NAME, variablesMap);
        for (int i = 0; i < batchPresentation.getAllFields().length; ++i) {
            if (batchPresentation.getAllFields()[i].dbSources[0].equals(Variable.class)
                    && batchPresentation.getAllFields()[i].displayName.startsWith(ClassPresentation.editable_prefix)) {
                batchPresentation.addDynamicField(i, name);
            }
        }
        Map<Integer, FilterCriteria> filters = batchPresentation.getFilteredFields();
        filters.put(new Integer(0), new AnywhereStringFilterCriteria(/*TODO new String[] { value }*/));
        List<WfProcess> processes = executionService.getProcesses(helper.getAuthorizedPerformerUser(), batchPresentation);
        for (WfProcess processInstanceStub : processes) {
            List<WfVariable> instanceVariablesMap = executionService.getVariables(helper.getAuthorizedPerformerUser(),
                    processInstanceStub.getId());
            int instancesFound = 0;
            for (WfVariable WfVariable : instanceVariablesMap) {
                if (name.equals(WfVariable.getDefinition().getName()) && value.equals(WfVariable.getValue())) {
                    instancesFound++;
                }
            }
            assertEquals(instancesFound, 3);
        }
    }

    public void testGetProcessInstanceStubsByVariableFilterWithWrongMatcherByAuthorizedSubject() throws Exception {
        String name = "reason";
        String value = "intention";
        Map<String, Object> variablesMap = WfServiceTestHelper.createVariablesMap(name, value);
        executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.SWIMLANE_PROCESS_NAME, variablesMap);
        executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.SWIMLANE_PROCESS_NAME, variablesMap);
        executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.SWIMLANE_PROCESS_NAME, variablesMap);
        for (int i = 0; i < batchPresentation.getAllFields().length; ++i) {
            if (batchPresentation.getAllFields()[i].dbSources[0].equals(Variable.class)
                    && batchPresentation.getAllFields()[i].displayName.startsWith(ClassPresentation.editable_prefix)) {
                batchPresentation.addDynamicField(i, name);
            }
        }
        Map<Integer, FilterCriteria> filters = batchPresentation.getFilteredFields();
        filters.put(new Integer(0), new AnywhereStringFilterCriteria(/*TODO new String[] { "bad matcher" }*/));
        List<WfProcess> processes = executionService.getProcesses(helper.getAuthorizedPerformerUser(), batchPresentation);
        for (WfProcess processInstanceStub : processes) {
            List<WfVariable> instanceVariablesMap = executionService.getVariables(helper.getAuthorizedPerformerUser(),
                    processInstanceStub.getId());
            int instancesFound = 0;
            for (WfVariable WfVariable : instanceVariablesMap) {
                if (name.equals(WfVariable.getDefinition().getName()) && value.equals(WfVariable.getValue())) {
                    instancesFound++;
                }
            }
            assertEquals(instancesFound, 0);
        }
    }

    public void testGetProcessInstanceStubsByAuthorizedSubject() throws Exception {
        List<WfProcess> processes = executionService.getProcesses(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("Incorrect processes array", 0, processes.size());
        executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.VALID_PROCESS_NAME, null);
        processes = executionService.getProcesses(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("Incorrect processes array", 1, processes.size());
    }

    public void testGetProcessInstanceStubsByUnauthorizedSubject() throws Exception {
        List<WfProcess> processes = executionService.getProcesses(helper.getUnauthorizedPerformerUser(), batchPresentation);
        assertEquals("Incorrect processes array", 0, processes.size());
        executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.VALID_PROCESS_NAME, null);
        processes = executionService.getProcesses(helper.getUnauthorizedPerformerUser(), batchPresentation);
        assertEquals("Incorrect processes array", 0, processes.size());
    }

    public void testGetProcessInstanceStubsByFakeSubject() throws Exception {
        try {
            executionService.getProcesses(helper.getFakeUser(), batchPresentation);
            assertFalse("testGetAllProcessInstanceStubsByFakeSubject, no AuthenticationException", true);
        } catch (AuthenticationException e) {
        }
    }

    public void testGetProcessInstanceStubsByNullSubject() throws Exception {
        try {
            executionService.getProcesses(null, batchPresentation);
            assertFalse("testGetAllProcessInstanceStubsByNullSubject, no IllegalArgumentException", true);
        } catch (IllegalArgumentException e) {
        }
    }

    public void testGetProcessInstanceStubsByAuthorizedSubjectWithoutREADPermission() throws Exception {
        List<WfProcess> processes = executionService.getProcesses(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("Incorrect processes array", 0, processes.size());
        executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.VALID_PROCESS_NAME, null);
        processes = executionService.getProcesses(helper.getAdminUser(), batchPresentation);
        assertEquals("Incorrect processes array", 1, processes.size());
        Collection<Permission> nullPermissions = Lists.newArrayList();
        helper.setPermissionsToAuthorizedPerformerOnProcessInstance(nullPermissions, processes.get(0));
        processes = executionService.getProcesses(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("Incorrect processes array", 0, processes.size());
    }

    public void testGetProcessInstanceStubsPagingByAuthorizedSubject() throws Exception {
        List<WfProcess> processes = executionService.getProcesses(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("Incorrect processes array", 0, processes.size());

        int rangeSize = 10;
        batchPresentation.setRangeSize(rangeSize);
        batchPresentation.setPageNumber(1);

        int expectedCount = 14;
        for (int i = 0; i < expectedCount; i++) {
            executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.VALID_PROCESS_NAME, null);
        }
        processes = executionService.getProcesses(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("Incorrect processes array", rangeSize, processes.size());

        batchPresentation.setPageNumber(2);

        processes = executionService.getProcesses(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("Incorrect processes array", expectedCount - rangeSize, processes.size());

        rangeSize = 50;
        batchPresentation.setRangeSize(rangeSize);
        batchPresentation.setPageNumber(1);

        processes = executionService.getProcesses(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("Incorrect processes array", expectedCount, processes.size());
    }

    public void testGetProcessInstanceStubsUnexistentPageByAuthorizedSubject() throws Exception {
        List<WfProcess> processes = executionService.getProcesses(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("Incorrect processes array", 0, processes.size());

        int rangeSize = 10;
        batchPresentation.setRangeSize(rangeSize);
        batchPresentation.setPageNumber(1);
        batchPresentation.setFieldsToSort(new int[] { 3 }, new boolean[] { true });

        int expectedCount = 17;
        for (int i = 0; i < expectedCount; i++) {
            executionService.startProcess(helper.getAuthorizedPerformerUser(), WfServiceTestHelper.VALID_PROCESS_NAME, null);
        }
        List<WfProcess> firstTenProcesses = executionService.getProcesses(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("Incorrect processes array", rangeSize, firstTenProcesses.size());

        batchPresentation.setPageNumber(2);
        processes = executionService.getProcesses(helper.getAuthorizedPerformerUser(), batchPresentation);
        assertEquals("Incorrect processes array", expectedCount - 10, processes.size());

        // the wrong page is replaced by last page in case it contains 0 objects
        batchPresentation.setPageNumber(3);

        List<WfProcess> wrongPageProcesses = executionService.getProcesses(helper.getAuthorizedPerformerUser(), batchPresentation);
        // due to
        // ru.runa.wfe.presentation.BatchPresentation.setFilteredFieldsMap(Map<Integer,
        // FilterCriteria>) in hibernate.update
        ArrayAssert.assertEqualArrays("Incorrect returned", processes, wrongPageProcesses);
    }

}
