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
package ru.runa.af.web.action;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import ru.runa.af.AuthenticationException;
import ru.runa.af.ExecutorOutOfDateException;
import ru.runa.af.service.SubstitutionService;
import ru.runa.af.web.SubjectHttpSessionHelper;
import ru.runa.common.web.ActionExceptionHelper;
import ru.runa.common.web.Resources;
import ru.runa.common.web.form.IdsForm;
import ru.runa.delegate.DelegateFactory;

/**
 * Created on 03.02.2006
 * 
 * @struts:action path="/switchSubstitutionsPositions" name="idsForm" validate="true" input = "/WEB-INF/af/manage_executor.jsp"
 * @struts.action-forward name="success" path="/WEB-INF/af/manage_executor.jsp"
 * @struts.action-forward name="failure" path="/WEB-INF/af/manage_executor.jsp"
 * @struts.action-forward name="failure_executor_does_not_exist" path="/WEB-INF/af/manage_executors.jsp"
 */

public class SwitchSubstitutionsPositionsAction extends Action {

    public static final String ACTION_PATH = "/switchSubstitutionsPositions";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        ActionMessages errors = new ActionMessages();
        IdsForm form = (IdsForm) actionForm;
        String errorForwardName = Resources.FORWARD_FAILURE;
        try {
            SubstitutionService substitutionService = DelegateFactory.getInstance().getSubstitutionService();
            Subject subject = SubjectHttpSessionHelper.getActorSubject(request.getSession());
            substitutionService.switchSubstitutionsPositions(subject, form.getIds()[0], form.getIds()[1]);
        } catch (ExecutorOutOfDateException e) {
            ActionExceptionHelper.addException(errors, e);
            errorForwardName = ru.runa.af.web.Resources.FORWARD_FAILURE_EXECUTOR_DOES_NOT_EXIST;
        } catch (Exception e) {
            ActionExceptionHelper.addException(errors, e);
        }
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return mapping.findForward(errorForwardName);
        }
        return mapping.findForward(Resources.FORWARD_SUCCESS);
    }

}
