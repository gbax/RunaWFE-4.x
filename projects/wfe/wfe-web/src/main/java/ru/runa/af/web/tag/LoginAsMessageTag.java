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
package ru.runa.af.web.tag;

import javax.security.auth.Subject;
import javax.servlet.jsp.JspException;

import org.apache.ecs.html.A;

import ru.runa.af.web.SubjectHttpSessionHelper;
import ru.runa.common.WebResources;
import ru.runa.common.web.Commons;
import ru.runa.common.web.form.IdForm;
import ru.runa.common.web.tag.MessageTag;
import ru.runa.service.af.AuthenticationService;
import ru.runa.service.delegate.Delegates;
import ru.runa.wfe.commons.web.PortletUrlType;
import ru.runa.wfe.user.Actor;

/**
 * Created on 06.09.2004
 * 
 * @jsp.tag name = "loginAsMessage" body-content = "empty"
 */
public class LoginAsMessageTag extends MessageTag {

    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage() throws JspException {
        try {
            Subject subject = SubjectHttpSessionHelper.getActorSubject(pageContext.getSession());
            AuthenticationService authenticationService = Delegates.getAuthenticationService();
            Actor actor = authenticationService.getActor(subject);
            String url = Commons.getActionUrl(WebResources.ACTION_MAPPING_UPDATE_EXECUTOR, IdForm.ID_INPUT_NAME, actor.getId(), pageContext,
                    PortletUrlType.Render);
            A a = new A(url, "<I>" + actor.getName() + "</I>");
            return super.getMessage() + " " + a.toString();
        } catch (Exception e) {
            return "";
        }
    }
}
