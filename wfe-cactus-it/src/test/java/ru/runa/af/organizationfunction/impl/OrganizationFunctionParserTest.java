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

/*
 * Created on 29.11.2005
 */
package ru.runa.af.organizationfunction.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.cactus.ServletTestCase;

import ru.runa.wf.service.WfServiceTestHelper;

import com.google.common.collect.Lists;
import ru.runa.wfe.definition.DefinitionPermission;
import ru.runa.wfe.task.dto.WfTask;

/**
 * @author Gritsenko_S
 */
public class OrganizationFunctionParserTest extends ServletTestCase {
    private final static String PREFIX = OrganizationFunctionParserTest.class.getName();

    private WfServiceTestHelper th;

    private static final String PROCESS_PATH = "organizationProcess.par";
    private static final String PROCESS_NAME = "organizationProcess";

    private Map<String, Object> legalVariables;

    public static Test suite() {
        return new TestSuite(OrganizationFunctionParserTest.class);
    }

    @Override
    protected void setUp() throws Exception {

        th = new WfServiceTestHelper(PREFIX);

        th.addExecutorToGroup(th.getHrOperator(), th.getBossGroup());

        th.deployValidProcessDefinition(PROCESS_PATH);

        th.setPermissionsToAuthorizedPerformerOnDefinitionByName(Lists.newArrayList(DefinitionPermission.READ,
                DefinitionPermission.START_PROCESS, DefinitionPermission.UNDEPLOY_DEFINITION), PROCESS_NAME);

        legalVariables = new HashMap<String, Object>();
        legalVariables.put("approved", "true");

        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        th.undeployValidProcessDefinition(PROCESS_NAME);
        th.releaseResources();
        th = null;
        super.tearDown();
    }

    public void testOrganizationFunction() throws Exception {
        th.getExecutionService().startProcess(th.getAuthorizedPerformerUser(), PROCESS_NAME, null);

        List<WfTask> tasks = th.getExecutionService().getTasks(th.getAdminUser(), th.getTaskBatchPresentation());
        assertEquals("tasks count differs from expected", 0, tasks.size());
    }
}
