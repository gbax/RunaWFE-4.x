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
import ru.runa.wfe.security.AuthenticationException;
import ru.runa.wfe.security.AuthorizationException;
import ru.runa.wfe.security.SystemPermission;
import ru.runa.wfe.service.SystemService;
import ru.runa.wfe.service.delegate.Delegates;

/**
 * Created on 16.08.2004
 */
public class AASystemServiceDelegateLoginTest extends ServletTestCase {
    private ServiceTestHelper th;
    private SystemService systemService;
    private static String testPrefix = AASystemServiceDelegateLoginTest.class.getName();

    public static TestSuite suite() {
        return new TestSuite(AASystemServiceDelegateLoginTest.class);
    }

    protected void tearDown() throws Exception {
        th.releaseResources();
        systemService = null;
        super.tearDown();
    }

    protected void setUp() throws Exception {
        systemService = Delegates.getSystemService();
        th = new ServiceTestHelper(testPrefix);
        th.createDefaultExecutorsMap();
        th.setPermissionsToAuthorizedPerformerOnSystem(Lists.newArrayList(SystemPermission.LOGIN_TO_SYSTEM));
        super.setUp();
    }

    public void testLoginWithNullUser() throws Exception {
        try {
            systemService.login(null);
            fail("SystemServiceDelegate does not throw IllegalArgumentException on login(null) call.");
        } catch (NullPointerException e) {
            //that's what we expected
        }
    }

    public void testLoginWithUnauthorizedPerformer() throws Exception {
        try {
            systemService.login(th.getUnauthorizedPerformerUser());
            fail("SystemServiceDelegate does not throw AuthorizationFailedException on login() with unauthorized performer user call.");
        } catch (AuthorizationException e) {
            //that's what we expected
        }
    }

    public void testLoginWithAuthorizedPerformer() throws Exception {
        systemService.login(th.getAuthorizedPerformerUser());
        assertTrue("SystemServiceDelegate.login() works.", true);
    }

    public void testLoginWithFakeUser() throws Exception {
        try {
            systemService.login(th.getFakeUser());
            fail("SystemServiceDelegate does not throw AuthorizationFailedException on login() with fakeUser call.");
        } catch (AuthenticationException e) {
            //that's what we expected	
        }
    }

}
