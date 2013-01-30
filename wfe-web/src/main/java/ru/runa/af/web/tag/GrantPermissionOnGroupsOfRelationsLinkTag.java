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

import ru.runa.common.web.Commons;
import ru.runa.common.web.Messages;
import ru.runa.common.web.tag.LinkTag;
import ru.runa.service.af.AuthorizationService;
import ru.runa.service.delegate.Delegates;
import ru.runa.wfe.commons.web.PortletUrlType;
import ru.runa.wfe.relation.RelationPermission;
import ru.runa.wfe.relation.RelationsGroupSecure;

/**
 * @jsp.tag name = "grantPermissionOnGroupsOfRelationsLink" body-content =
 *          "empty"
 */
public class GrantPermissionOnGroupsOfRelationsLinkTag extends LinkTag {

    private static final long serialVersionUID = 1L;

    private static final String HREF = "/grant_group_of_relations_permission.do";

    @Override
    protected boolean isLinkEnabled() throws JspException {
        try {
            AuthorizationService authorizationService = Delegates.getAuthorizationService();
            return authorizationService.isAllowed(getUser(), RelationPermission.UPDATE_PERMISSIONS, RelationsGroupSecure.INSTANCE);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected String getHref() {
        return Commons.getActionUrl(HREF, pageContext, PortletUrlType.Action);
    }

    @Override
    protected String getLinkText() {
        return Messages.getMessage(Messages.BUTTON_ADD, pageContext);
    }

}
