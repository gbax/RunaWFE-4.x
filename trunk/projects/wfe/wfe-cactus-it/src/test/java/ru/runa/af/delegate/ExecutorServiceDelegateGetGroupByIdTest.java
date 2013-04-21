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

package ru.runa.af.delegate;

import com.google.common.collect.Lists;
import junit.framework.TestSuite;
import org.apache.cactus.ServletTestCase;
import ru.runa.af.service.ServiceTestHelper;
import ru.runa.wfe.security.AuthorizationException;
import ru.runa.wfe.security.Permission;
import ru.runa.wfe.service.ExecutorService;
import ru.runa.wfe.service.delegate.Delegates;
import ru.runa.wfe.user.Executor;
import ru.runa.wfe.user.ExecutorDoesNotExistException;
import ru.runa.wfe.user.Group;

import java.util.List;
import java.util.Map;

public class ExecutorServiceDelegateGetGroupByIdTest extends ServletTestCase {
    private ServiceTestHelper th;

    private ExecutorService executorService;

    private static String testPrefix = ExecutorServiceDelegateGetGroupByIdTest.class.getName();

    private Group group;

    private Map<String, Executor> executorsMap;

    public static TestSuite suite() {
        return new TestSuite(ExecutorServiceDelegateGetGroupByIdTest.class);
    }

    protected void setUp() throws Exception {
        executorService = Delegates.getExecutorService();
        th = new ServiceTestHelper(testPrefix);
        th.createDefaultExecutorsMap();
        List<Permission> readPermissions = Lists.newArrayList(Permission.READ);
        executorsMap = th.getDefaultExecutorsMap();

        group = (Group) executorsMap.get(ServiceTestHelper.BASE_GROUP_NAME);
        th.setPermissionsToAuthorizedPerformer(readPermissions, group);
        th.setPermissionsToAuthorizedPerformer(readPermissions, th.getSubGroup());
        super.setUp();
    }

    public void testgetExecutorByAuthorizedPerformer() throws Exception {
        Group returnedBaseGroup = executorService.getExecutor(th.getAuthorizedPerformerUser(), group.getId());
        assertEquals("actor retuned by buisnessDelegete differes with expected", group, returnedBaseGroup);
        Group subGroup = (Group) executorsMap.get(ServiceTestHelper.SUB_GROUP_NAME);
        Group returnedSubGroup = executorService.getExecutor(th.getAuthorizedPerformerUser(), subGroup.getId());
        assertEquals("actor retuned by buisnessDelegete differes with expected", subGroup, returnedSubGroup);
    }

    public void testgetExecutorByUnauthorizedPerformer() throws Exception {
        try {
            executorService.getExecutor(th.getUnauthorizedPerformerUser(), group.getId());
            assertTrue("buisnessDelegete allow to getExecutor()", false);
        } catch (AuthorizationException e) {
            //That's what we expect
        }
        try {
            executorService.getExecutor(th.getUnauthorizedPerformerUser(), th.getSubGroup().getId());
            assertTrue("buisnessDelegete allow to getSubGroup()", false);
        } catch (AuthorizationException e) {
            //That's what we expect
        }
    }

    public void testGetUnexistedGroupByAuthorizedPerformer() throws Exception {
        try {
            executorService.getExecutor(th.getAuthorizedPerformerUser(), -1l);
            assertTrue("buisnessDelegete does not throw Exception to getExecutor() in testGetUnexistedGroupByAuthorizedPerformer", false);
        } catch (ExecutorDoesNotExistException e) {
            //That's what we expect
        }
    }

    public void testGetNullGroupByAuthorizedPerformer() throws Exception {
        try {
            executorService.getExecutor(th.getAuthorizedPerformerUser(), null);
            assertTrue("buisnessDelegete allow to getExecutor()with null group.", false);
        } catch (NullPointerException e) {
            //That's what we expect
        }
    }

    public void testgetExecutorByNullPerformer() throws Exception {
        try {
            executorService.getExecutor(null, group.getId());
            assertTrue("buisnessDelegete allow to getExecutor() to performer with null subject.", false);
        } catch (NullPointerException e) {
            //That's what we expect
        }
    }

    public void testGetActorInsteadOfGroup() throws Exception {
        try {
            executorService.getExecutor(th.getAuthorizedPerformerUser(), th.getBaseGroupActor().getId());
            assertTrue("buisnessDelegete allow to getExecutor() where the actor really is returned.", false);
        } catch (ExecutorDoesNotExistException e) {
            //That's what we expect
        }
    }

    protected void tearDown() throws Exception {
        th.releaseResources();
        executorService = null;
        group = null;
        executorsMap = null;
        super.tearDown();
    }
}
