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
import ru.runa.wfe.InternalApplicationException;
import ru.runa.wfe.security.AuthenticationException;
import ru.runa.wfe.security.AuthorizationException;
import ru.runa.wfe.security.Permission;
import ru.runa.wfe.service.AuthorizationService;
import ru.runa.wfe.service.delegate.Delegates;
import ru.runa.wfe.user.ExecutorDoesNotExistException;
import ru.runa.wfe.user.ExecutorPermission;

import java.util.Collection;

/**
 * Created on 20.08.2004
 * 
 */
public class AuthorizationServiceDelegateGetPermissionsTest extends ServletTestCase {
    private ServiceTestHelper helper;

    private AuthorizationService authorizationService;

    public static Test suite() {
        return new TestSuite(AuthorizationServiceDelegateGetPermissionsTest.class);
    }

    protected void setUp() throws Exception {
        helper = new ServiceTestHelper(AuthorizationServiceDelegateGetPermissionsTest.class.getName());
        helper.createDefaultExecutorsMap();

        Collection<Permission> p = Lists.newArrayList(Permission.READ, Permission.UPDATE_PERMISSIONS);
        helper.setPermissionsToAuthorizedPerformerOnSystem(p);

        Collection<Permission> executorP = Lists.newArrayList(Permission.READ, Permission.UPDATE_PERMISSIONS);
        helper.setPermissionsToAuthorizedPerformer(executorP, helper.getBaseGroupActor());
        helper.setPermissionsToAuthorizedPerformer(executorP, helper.getBaseGroup());
        helper.setPermissionsToAuthorizedPerformer(executorP, helper.getSubGroupActor());

        authorizationService = Delegates.getAuthorizationService();
        super.setUp();
    }

    protected void tearDown() throws Exception {
        helper.releaseResources();
        authorizationService = null;
        super.tearDown();
    }

    public void testGetPermissionsNullUser() throws Exception {
        try {
            authorizationService.getIssuedPermissions(null, helper.getBaseGroupActor(), helper.getBaseGroupActor());
            fail("AuthorizationDelegate.getIssuedPermissions() allows null subject");
        } catch (NullPointerException e) {
        }
    }

    public void testGetPermissionsFakeSubject() throws Exception {
        try {
            authorizationService.getIssuedPermissions(helper.getFakeUser(), helper.getBaseGroupActor(), helper.getBaseGroupActor());
            fail("AuthorizationDelegate.getIssuedPermissions() allows fake subject");
        } catch (AuthenticationException e) {
        }
    }

    public void testGetPermissionsNullExecutor() throws Exception {
        try {
            authorizationService.getIssuedPermissions(helper.getAuthorizedPerformerUser(), null, helper.getBaseGroupActor());
            fail("AuthorizationDelegate.getIssuedPermissions() allows null executor");
        } catch (NullPointerException e) {
        }
    }

    public void testGetPermissionsFakeExecutor() throws Exception {
        try {
            authorizationService.getIssuedPermissions(helper.getAuthorizedPerformerUser(), helper.getFakeActor(), helper.getBaseGroupActor());
            fail("AuthorizationDelegate.getIssuedPermissions() allows fake executor");
        } catch (ExecutorDoesNotExistException e) {
        }
    }

    public void testGetPermissionsNullIdentifiable() throws Exception {
        try {
            authorizationService.getIssuedPermissions(helper.getAuthorizedPerformerUser(), helper.getBaseGroupActor(), null);
            fail("AuthorizationDelegate.getIssuedPermissions() allows null identifiable");
        } catch (NullPointerException e) {
        }
    }

    public void testGetPermissionsFakeIdentifiable() throws Exception {
        try {
            authorizationService.getIssuedPermissions(helper.getAuthorizedPerformerUser(), helper.getBaseGroupActor(), helper.getFakeActor());
            fail("AuthorizationDelegate.getIssuedPermissions() allows fake identifiable");
        } catch (InternalApplicationException e) {
        }
    }

    public void testGetPermissions() throws Exception {
        Collection<Permission> noPermission = Lists.newArrayList();
        Collection<Permission> expected = Lists.newArrayList(Permission.READ);

        Collection<Permission> actual = authorizationService.getIssuedPermissions(helper.getAuthorizedPerformerUser(), helper.getBaseGroupActor(), helper.getAASystem());
        ArrayAssert.assertWeakEqualArrays("AuthorizationDelegate.getIssuedPermissions() returns wrong permissions", noPermission, actual);

        authorizationService.setPermissions(helper.getAuthorizedPerformerUser(), helper.getBaseGroupActor().getId(), expected, helper.getAASystem());
        actual = authorizationService.getIssuedPermissions(helper.getAuthorizedPerformerUser(), helper.getBaseGroupActor(), helper.getAASystem());
        ArrayAssert.assertWeakEqualArrays("AuthorizationDelegate.getIssuedPermissions() returns wrong permissions", expected, actual);

        actual = authorizationService.getIssuedPermissions(helper.getAuthorizedPerformerUser(), helper.getBaseGroup(), helper.getBaseGroupActor());
        ArrayAssert.assertWeakEqualArrays("AuthorizationDelegate.getIssuedPermissions() returns wrong permissions", noPermission, actual);

        authorizationService.setPermissions(helper.getAuthorizedPerformerUser(), helper.getBaseGroup().getId(), expected, helper.getBaseGroupActor());
        actual = authorizationService.getIssuedPermissions(helper.getAuthorizedPerformerUser(), helper.getBaseGroup(), helper.getBaseGroupActor());
        ArrayAssert.assertWeakEqualArrays("AuthorizationDelegate.getIssuedPermissions() returns wrong permissions", expected, actual);
    }

    public void testGetPermissionsRecursive() throws Exception {
        Collection<Permission> expected = Lists.newArrayList(Permission.READ, ExecutorPermission.UPDATE);

        authorizationService.setPermissions(helper.getAuthorizedPerformerUser(), helper.getBaseGroup().getId(), expected, helper.getBaseGroupActor());

        Collection<Permission> actual = authorizationService.getIssuedPermissions(helper.getAuthorizedPerformerUser(), helper.getSubGroupActor(), helper.getBaseGroupActor());
        ArrayAssert.assertWeakEqualArrays("AuthorizationDelegate.getPermission returns wrong recursive permission", expected, actual);
    }

    public void testGetPermissionsUnauthorized() throws Exception {
        try {
            authorizationService.getIssuedPermissions(helper.getUnauthorizedPerformerUser(), helper.getBaseGroupActor(), helper.getAASystem());
            fail("AuthorizationDelegate.getIssuedPermissions() allows unauthorized operation");
        } catch (AuthorizationException e) {
        }

        try {
            authorizationService.getIssuedPermissions(helper.getUnauthorizedPerformerUser(), helper.getBaseGroupActor(), helper.getBaseGroupActor());
            fail("AuthorizationDelegate.getIssuedPermissions() allows unauthorized operation");
        } catch (AuthorizationException e) {
        }
    }
}
