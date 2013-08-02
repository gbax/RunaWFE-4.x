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
package ru.runa.wf.web.tag;

import ru.runa.common.web.Messages;
import ru.runa.common.web.tag.IdLinkBaseTag;
import ru.runa.wfe.security.Permission;
import ru.runa.wfe.security.SecuredObjectType;
import ru.runa.wfe.service.delegate.Delegates;

/**
 * @jsp.tag name = "grantReadPermissionOnProcessLink" body-content = "empty"
 */
public class GrantReadPermissionOnProcessLinkTag extends IdLinkBaseTag {

    private static final long serialVersionUID = -8445857392805848169L;

    @Override
    protected boolean isLinkEnabled() {
        return Delegates.getAuthorizationService()
                .isAllowed(getUser(), Permission.UPDATE_PERMISSIONS, SecuredObjectType.PROCESS, getIdentifiableId());
    }

    @Override
    protected String getLinkText() {
        return Messages.getMessage(Messages.BUTTON_ADD, pageContext);
    }

}
