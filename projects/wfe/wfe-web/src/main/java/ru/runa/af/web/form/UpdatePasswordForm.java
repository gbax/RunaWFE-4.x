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
package ru.runa.af.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import ru.runa.common.WebResources;
import ru.runa.common.web.Messages;
import ru.runa.common.web.form.IdForm;

import com.google.common.base.Strings;

/**
 * Created on 24.08.2004
 * 
 * @struts:form name = "updatePasswordForm"
 */
public class UpdatePasswordForm extends IdForm {
    private static final long serialVersionUID = -6455786382107126804L;

    public static final String PASSWORD_INPUT_NAME = "password";

    private String password;

    public static final String PASSWORD_CONFIRM_INPUT_NAME = "passwordConfirm";

    private String passwordConfirm;

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        if (Strings.isNullOrEmpty(password)) {
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(Messages.ERROR_FILL_REQUIRED_VALUES));
        } else if (password.length() > WebResources.VALIDATOR_STRING_255) {
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(Messages.ERROR_VALIDATION));
        } else if (passwordConfirm == null || passwordConfirm.length() < 1) {
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(Messages.ERROR_NULL_VALUE));
        } else if (!password.equals(passwordConfirm)) {
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(Messages.ERROR_PASSWORDS_NOT_MATCH));
        }
        return errors;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPassword(String string) {
        password = string;
    }

    public void setPasswordConfirm(String string) {
        passwordConfirm = string;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }
}
