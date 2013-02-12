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

import ru.runa.common.web.Messages;
import ru.runa.common.web.tag.IdLinkBaseTag;
import ru.runa.wfe.service.AuthorizationService;
import ru.runa.wfe.service.ExecutorService;
import ru.runa.wfe.service.delegate.Delegates;
import ru.runa.wfe.user.Executor;
import ru.runa.wfe.user.GroupPermission;

/**
 * Created on 03.09.2004
 * 
 * @jsp.tag name = "addGroupMembersLink" body-content = "empty"
 */
public class AddGroupMembersLinkTag extends IdLinkBaseTag {

    private static final long serialVersionUID = 2135918275689343980L;

    @Override
    protected boolean isLinkEnabled() {
        try {
            ExecutorService executorService = Delegates.getExecutorService();
            Executor executor = executorService.getExecutor(getUser(), getIdentifiableId());
            AuthorizationService authorizationService = Delegates.getAuthorizationService();
            return authorizationService.isAllowed(getUser(), GroupPermission.ADD_TO_GROUP, executor);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected String getLinkText() {
        return Messages.getMessage(Messages.BUTTON_ADD, pageContext);
    }

}
