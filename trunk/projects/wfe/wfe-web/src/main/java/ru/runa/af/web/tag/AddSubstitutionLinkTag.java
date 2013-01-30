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

import javax.servlet.jsp.JspException;

import ru.runa.common.web.Messages;
import ru.runa.common.web.tag.IdLinkBaseTag;
import ru.runa.service.af.AuthorizationService;
import ru.runa.service.af.ExecutorService;
import ru.runa.service.delegate.Delegates;
import ru.runa.wfe.user.Actor;
import ru.runa.wfe.user.ActorPermission;

/**
 * Created on 01.02.2006
 * 
 * @jsp.tag name = "addSubstitutionLink" body-content = "empty"
 */
public class AddSubstitutionLinkTag extends IdLinkBaseTag {
    private static final long serialVersionUID = 1L;
    private String text;

    /**
     * @jsp.attribute required = "true" rtexprvalue = "true"
     */
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    protected boolean isLinkEnabled() throws JspException {
        ExecutorService executorService = Delegates.getExecutorService();
        Actor actor = executorService.getExecutor(getUser(), getIdentifiableId());
        AuthorizationService authorizationService = Delegates.getAuthorizationService();
        return authorizationService.isAllowed(getUser(), ActorPermission.UPDATE, actor);
    }

    @Override
    protected String getLinkText() {
        return Messages.getMessage(text, pageContext);
    }
}
