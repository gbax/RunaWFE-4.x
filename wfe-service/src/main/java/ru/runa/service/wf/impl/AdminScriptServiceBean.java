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
package ru.runa.service.wf.impl;

import java.io.ByteArrayInputStream;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import ru.runa.service.af.AuthenticationService;
import ru.runa.service.delegate.DelegateFactory;
import ru.runa.service.interceptors.EjbExceptionSupport;
import ru.runa.service.interceptors.EjbTransactionSupport;
import ru.runa.service.wf.AdminScriptService;
import ru.runa.wfe.script.WfeScriptException;
import ru.runa.wfe.script.WfeScriptRunner;
import ru.runa.wfe.security.AuthenticationException;

import com.google.common.base.Preconditions;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@Interceptors({ EjbExceptionSupport.class, EjbTransactionSupport.class, SpringBeanAutowiringInterceptor.class })
public class AdminScriptServiceBean implements AdminScriptService {
    @Autowired
    private WfeScriptRunner runner;

    @Override
    public void run(String login, String password, byte[] configData, byte[][] processDefinitionsBytes) throws WfeScriptException,
            AuthenticationException {
        Preconditions.checkNotNull(configData);
        Preconditions.checkNotNull(processDefinitionsBytes);
        AuthenticationService delegate = DelegateFactory.getAuthenticationService();
        Subject subject = delegate.authenticate(login, password);
        runner.setSubject(subject);
        runner.setProcessDefinitionsBytes(processDefinitionsBytes);
        runner.runScript(new ByteArrayInputStream(configData));
    }
}
