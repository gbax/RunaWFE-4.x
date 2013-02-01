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

package ru.runa.wfe.security.logic;

import java.security.Principal;
import java.util.List;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;

import ru.runa.wfe.commons.logic.CommonLogic;
import ru.runa.wfe.security.AuthenticationException;
import ru.runa.wfe.security.auth.KerberosCallbackHandler;
import ru.runa.wfe.security.auth.KerberosLoginModuleResources;
import ru.runa.wfe.security.auth.LoginModuleConfiguration;
import ru.runa.wfe.security.auth.PasswordLoginModuleCallbackHandler;
import ru.runa.wfe.security.auth.PrincipalCallbackHandler;
import ru.runa.wfe.security.auth.SubjectPrincipalsHelper;
import ru.runa.wfe.user.Actor;
import ru.runa.wfe.user.User;

/**
 * Created on 14.03.2005
 * 
 */
public class AuthenticationLogic extends CommonLogic {
    private static final Log log = LogFactory.getLog(AuthenticationLogic.class);
    private static final String LOGIN_MODULE_CONFIGURATION = "LoginModuleConfiguration";
    private List<LoginHandler> loginHandlers;
    private LoginModuleConfiguration loginModuleConfiguration;

    @Required
    public void setLoginHandlers(List<LoginHandler> loginHandlers) {
        this.loginHandlers = loginHandlers;
    }

    @Required
    public void setLoginModuleConfiguration(LoginModuleConfiguration loginModuleConfiguration) {
        this.loginModuleConfiguration = loginModuleConfiguration;
    }

    public User authenticate(Principal principal) throws AuthenticationException {
        return authenticate(new PrincipalCallbackHandler(principal), AuthType.OTHER);
    }

    public User authenticate(byte[] kerberosToken, KerberosLoginModuleResources res) throws AuthenticationException {
        return authenticate(new KerberosCallbackHandler(kerberosToken, res), AuthType.KERBEROS);
    }

    public User authenticate(String name, String password) throws AuthenticationException {
        return authenticate(new PasswordLoginModuleCallbackHandler(name, password), AuthType.DB);
    }

    private User authenticate(CallbackHandler callbackHandler, AuthType authType) throws AuthenticationException {
        try {
            LoginContext loginContext = new LoginContext(LOGIN_MODULE_CONFIGURATION, null, callbackHandler, loginModuleConfiguration);
            loginContext.login();
            Subject subject = loginContext.getSubject();
            callHandlers(SubjectPrincipalsHelper.getUser(subject).getActor(), authType);
            String actorName = SubjectPrincipalsHelper.getUser(subject).getName();
            log.debug(actorName + " successfully authenticated");
            return SubjectPrincipalsHelper.getUser(subject);
        } catch (Exception e) {
            log.warn("Failed to authenticate because of: " + e.getMessage());
            throw new AuthenticationException(e);
        }
    }

    private void callHandlers(Actor actor, AuthType type) {
        for (LoginHandler handler : loginHandlers) {
            try {
                handler.onUserLogin(actor, type);
            } catch (Throwable e) {
                log.warn("Exception while calling loginHandler " + handler, e);
            }
        }
    }

}
