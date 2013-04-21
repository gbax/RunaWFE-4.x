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
import ru.runa.wfe.user.*;

import javax.security.auth.Subject;
import java.util.List;
import java.util.Map;

/**
 * Created on 01.11.2004
 */
public class ExecutorServiceDelegateGetActorByIdTest extends ServletTestCase {
    private ServiceTestHelper th;
    private ExecutorService executorService;
    private static String testPrefix = ExecutorServiceDelegateGetActorByIdTest.class.getName();
    private Group group;
    private Actor actor;
    private Map<String, Executor> executorsMap;

    public static TestSuite suite() {
        return new TestSuite(ExecutorServiceDelegateGetActorByIdTest.class);
    }

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
        super.setUp();
    }

    public void testGetActorByAuthorizedPerformer() throws Exception {
        Actor returnedBaseGroupActor = executorService.getExecutor(th.getAuthorizedPerformerUser(), actor.getId());
        assertEquals("actor retuned by buisnessDelegete differes with expected", actor, returnedBaseGroupActor);
        Actor subGroupActor = (Actor) executorsMap.get(ServiceTestHelper.SUB_GROUP_ACTOR_NAME);
        Actor returnedSubGroupActor = executorService.getExecutor(th.getAuthorizedPerformerUser(), subGroupActor.getId());
        assertEquals("actor retuned by buisnessDelegete differes with expected", subGroupActor, returnedSubGroupActor);
    }

    public void testGetActorByUnauthorizedPerformer() throws Exception {
        try {
            executorService.getExecutor(th.getUnauthorizedPerformerUser(), actor.getId());
            fail("buisnessDelegete allow to getActor() to performer with UnauthorizedPerformerSubject");
        } catch (AuthorizationException e) {
            //That's what we expect
        }
        try {
            executorService.getExecutor(th.getUnauthorizedPerformerUser(), th.getSubGroupActor().getId());
            fail("buisnessDelegete allow to getActor() to performer with UnauthorizedPerformerSubject");
        } catch (AuthorizationException e) {
            //That's what we expect
        }
    }

    public void testGetUnexistedActorByAuthorizedPerformer() throws Exception {
        try {
            executorService.getExecutor(th.getAuthorizedPerformerUser(), -1l);
            fail("buisnessDelegete does not throw Exception to getActor()");
        } catch (ExecutorDoesNotExistException e) {
            //That's what we expect
        }
    }

    public void testGetNullActorByAuthorizedPerformer() throws Exception {
        try {
            executorService.getExecutorByName(th.getAuthorizedPerformerUser(), null);
            fail("buisnessDelegete allow to getActor()with null actor.");
        } catch (NullPointerException e) {
            //That's what we expect 
        }
    }

    public void testGetActorByNullPerformer() throws Exception {
        try {
            executorService.getExecutor(null, actor.getId());
            fail("buisnessDelegete allow to getActor() to performer with null subject.");
        } catch (NullPointerException e) {
            //That's what we expect 
        }
    }

    public void testGetActorInsteadOfGroup() throws Exception {
        try {
            executorService.getExecutor(th.getAuthorizedPerformerUser(), group.getId());
            fail("buisnessDelegete allow to getActor() where the group really is returned.");
        } catch (ExecutorDoesNotExistException e) {
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
