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
import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.cactus.ServletTestCase;
import ru.runa.af.service.ServiceTestHelper;
import ru.runa.junit.ArrayAssert;
import ru.runa.wfe.security.AuthenticationException;
import ru.runa.wfe.security.Permission;
import ru.runa.wfe.service.ExecutorService;
import ru.runa.wfe.service.delegate.Delegates;
import ru.runa.wfe.user.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ExecutorServiceDelegateGetAllTest extends ServletTestCase {
    private ServiceTestHelper th;

    private ExecutorService executorService;

    private static String testPrefix = ExecutorServiceDelegateGetAllTest.class.getName();

    private Group group;

    private Actor actor;

    private Map<String, Executor> executorsMap;

    protected void setUp() throws Exception {
        executorService = Delegates.getExecutorService();
        th = new ServiceTestHelper(testPrefix);
        th.createDefaultExecutorsMap();
        List<Permission> readUpdatePermissions = Lists.newArrayList(Permission.READ, ExecutorPermission.UPDATE);
        executorsMap = th.getDefaultExecutorsMap();

        actor = (Actor) executorsMap.get(ServiceTestHelper.BASE_GROUP_ACTOR_NAME);
        th.setPermissionsToAuthorizedPerformer(readUpdatePermissions, actor);
        group = (Group) executorsMap.get(ServiceTestHelper.BASE_GROUP_NAME);
        th.setPermissionsToAuthorizedPerformer(readUpdatePermissions, group);
        th.setPermissionsToAuthorizedPerformer(readUpdatePermissions, (Actor) executorsMap.get(ServiceTestHelper.SUB_GROUP_ACTOR_NAME));
        th.setPermissionsToAuthorizedPerformer(readUpdatePermissions, (Group) executorsMap.get(ServiceTestHelper.SUB_GROUP_NAME));
        super.setUp();
    }

    final public void testgetExecutorsByAuthorizedPerformer() throws Exception {
        List<? extends Executor> executors = executorService.getExecutors(th.getAuthorizedPerformerUser(), th.getExecutorBatchPresentation());
        LinkedList<Executor> realExecutors = new LinkedList<Executor>(executorsMap.values());
        Actor authorizedPerformerActor = th.getAuthorizedPerformerActor();
        realExecutors.add(authorizedPerformerActor);
        ArrayAssert.assertWeakEqualArrays("businessDelegate.getExecutors() returns wrong executor set", realExecutors, executors);
    }

    public void testgetExecutorsByUnauthorizedPerformer() throws Exception {
        List<? extends Executor> executors = executorService.getExecutors(th.getUnauthorizedPerformerUser(), th.getExecutorBatchPresentation());
        List<Actor> unauthorizedPerformerArray = Lists.newArrayList(th.getUnauthorizedPerformerActor());
        ArrayAssert.assertWeakEqualArrays("businessDelegate.getExecutors() returns wrong executor set", unauthorizedPerformerArray, executors);
    }

    public void testgetExecutorswithNullSubject() throws Exception {
        try {
            executorService.getExecutors(null, th.getExecutorBatchPresentation());
            fail("businessDelegate.getExecutors() with null subject throws no IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //That's what we expect
        }
    }

    public void testgetExecutorswithoutFakeSubject() throws Exception {
        try {
            User fakeUser = th.getFakeUser();
            executorService.getExecutors(fakeUser, th.getExecutorBatchPresentation());
            fail("businessDelegate.getExecutors() with fake subject throws no AuthenticationException");
        } catch (AuthenticationException e) {
            //That's what we expect
        }
    }

    protected void tearDown() throws Exception {
        th.releaseResources();
        executorService = null;
        actor = null;
        group = null;
        super.tearDown();
    }
}
