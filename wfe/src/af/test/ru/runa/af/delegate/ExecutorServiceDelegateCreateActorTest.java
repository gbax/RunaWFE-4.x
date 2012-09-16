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

import java.util.Collection;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.cactus.ServletTestCase;

import ru.runa.af.Actor;
import ru.runa.af.AuthenticationException;
import ru.runa.af.AuthorizationException;
import ru.runa.af.ExecutorAlreadyExistsException;
import ru.runa.af.Permission;
import ru.runa.af.SystemPermission;
import ru.runa.af.service.ExecutorService;
import ru.runa.af.service.ServiceTestHelper;
import ru.runa.delegate.DelegateFactory;

import com.google.common.collect.Lists;

public class ExecutorServiceDelegateCreateActorTest extends ServletTestCase {
    private final static String testPrefix = ExecutorServiceDelegateCreateActorTest.class.getName();

    private final static String NAME = "Name" + testPrefix;

    private final static String DESC = "Desc" + testPrefix;

    private final static String FULL_NAME = "FullName" + testPrefix;

    private final static long CODE = System.currentTimeMillis();

    private ServiceTestHelper th;

    private ExecutorService executorService;

    private Actor actor;

    public static Test suite() {
        return new TestSuite(ExecutorServiceDelegateCreateActorTest.class);
    }

    protected void setUp() throws Exception {
        executorService = DelegateFactory.getInstance().getExecutorService();
        th = new ServiceTestHelper(testPrefix);
        Collection<Permission> createPermissions = Lists.newArrayList(SystemPermission.CREATE_EXECUTOR);
        th.setPermissionsToAuthorizedPerformerOnSystem(createPermissions);
        actor = new Actor(NAME, DESC, FULL_NAME, CODE);
        super.setUp();
    }

    protected void tearDown() throws Exception {
        th.removeExecutorIfExists(actor);
        actor = null;

        th.releaseResources();
        executorService = null;

        super.tearDown();
    }

    public void testCreateActorByAuthorizedPerformer() throws Exception {
        actor = executorService.create(th.getAuthorizedPerformerSubject(), actor);
        assertTrue("Executor does not exists ", th.isExecutorExist(actor));
        Actor returnedActor = executorService.getActor(th.getAuthorizedPerformerSubject(), actor.getName());
        assertEquals("Returned actor differes with created one", actor, returnedActor);
    }

    public void testCreateExecutorByUnAuthorizedPerformer() throws Exception {
        try {
            executorService.create(th.getUnauthorizedPerformerSubject(), actor);
            fail("ExecutorServiceDelegate allow unauthorized create");
        } catch (AuthorizationException e) {
            // This is supposed result of operation
        }
    }

    public void testCreateAlreadyExistedExecutor() throws Exception {
        Actor actor2 = executorService.create(th.getAuthorizedPerformerSubject(), actor);
        assertTrue("Executor does not exists ", th.isExecutorExist(actor));
        try {
            executorService.create(th.getAuthorizedPerformerSubject(), actor);
            fail("ExecutorServiceDelegate allow create actor with same name");
        } catch (ExecutorAlreadyExistsException e) {
            // This is supposed result of operation
        }
        Actor returnedActor = executorService.getActor(th.getAuthorizedPerformerSubject(), actor2.getId());
        assertEquals("Returned actor differes with created one", actor, returnedActor);
    }

    public void testCreateNullExecutorByAuthorizedPerformer() throws Exception {
        try {
            executorService.create(th.getAuthorizedPerformerSubject(), (Actor) null);
            fail("null executor created");
        } catch (IllegalArgumentException e) {
            // This is supposed result of operation
        }
    }

    public void testCreateExecutorWithNullSubject() throws Exception {
        try {
            executorService.create(null, actor);
            fail("executor with null subject created");
        } catch (IllegalArgumentException e) {
            // This is supposed result of operation
        }
    }

    public void testCreateExecutorWithFakeSubject() throws Exception {
        try {
            executorService.create(th.getFakeSubject(), actor);
            fail("executor with fake subject created");
        } catch (AuthenticationException e) {
        }
    }

}
