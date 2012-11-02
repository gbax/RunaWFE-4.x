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
package ru.runa.wfe.security.auth;

import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Value;

import ru.runa.wfe.commons.ftl.ExpressionEvaluator;
import ru.runa.wfe.user.Actor;

import com.google.common.collect.Maps;

/**
 * MS Active Directory based login module. Created on 17.06.2005
 */
public class ADPasswordLoginModule extends LoginModuleBase {
    private Hashtable<String, String> env = new Hashtable<String, String>();
    @Value(value = "${authentication.domain.name}")
    private String domainName;
    @Value(value = "${authentication.ldap.server.url}")
    private String serverUrl;
    @Value(value = "${authentication.ldap.userName.format}")
    private String userNameFormat;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, serverUrl);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put("java.naming.ldap.version", "3");
        super.initialize(subject, callbackHandler, sharedState, options);
    }

    private String getCredential(String username) {
        Map<String, String> variables = Maps.newHashMap();
        variables.put("domain.name", domainName);
        variables.put("username", username);
        return ExpressionEvaluator.substitute(userNameFormat, variables);
    }

    @Override
    protected Actor login(CallbackHandler callbackHandler) throws Exception {
        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("actor name: ");
        callbacks[1] = new PasswordCallback("password: ", false);
        callbackHandler.handle(callbacks);
        String actorName = ((NameCallback) callbacks[0]).getName();
        if (actorName == null) {
            throw new LoginException("No actor name was provided.");
        }
        char[] tmpPasswordChars = ((PasswordCallback) callbacks[1]).getPassword();
        if ((tmpPasswordChars == null) || (tmpPasswordChars.length == 0)) {
            throw new LoginException("No password was provided.");
        }
        String password = new String(tmpPasswordChars);
        env.put(Context.SECURITY_PRINCIPAL, getCredential(actorName));
        env.put(Context.SECURITY_CREDENTIALS, password);
        DirContext ctx = new InitialDirContext(env);
        ctx.close();
        return executorDAO.getActorCaseInsensitive(actorName);
    }

}
