package ru.runa.af.authenticaion;

import java.security.Principal;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import ru.runa.af.Actor;
import ru.runa.af.ExecutorOutOfDateException;

public class PrincipalLoginModule extends LoginModuleBase {

    @Override
    protected Actor login(CallbackHandler callbackHandler) throws Exception {
        Callback[] callbacks = new Callback[1];
        callbacks[0] = new PrincipalCallback();
        callbackHandler.handle(callbacks);
        Principal principal = ((PrincipalCallback) callbacks[0]).getPrincipal();
        if (principal == null) {
            throw new LoginException("No actor logged in.");
        }
        try {
            return executorDAO.getActor(principal.getName());
        } catch (ExecutorOutOfDateException e) {
            // do nothing here, we must not let external system know whether
            // actor exist or not
            log.warn(e);
            return null;
        }
    }

}
