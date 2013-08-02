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

import ru.runa.af.web.action.CreateRelationPairAction;
import ru.runa.af.web.form.RelationPairForm;
import ru.runa.common.web.Commons;
import ru.runa.common.web.Messages;
import ru.runa.common.web.tag.LinkTag;
import ru.runa.wfe.commons.web.PortletUrlType;
import ru.runa.wfe.relation.RelationPermission;
import ru.runa.wfe.security.SecuredObjectType;
import ru.runa.wfe.service.delegate.Delegates;

public class CreateRelationPairLinkTag extends LinkTag {
    private static final long serialVersionUID = 1L;
    private Long relationId;

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    @Override
    protected String getHref() {
        return Commons.getActionUrl(CreateRelationPairAction.VIEW_PATH, RelationPairForm.RELATION_ID, relationId, pageContext, PortletUrlType.Action);
    }

    @Override
    protected String getLinkText() {
        return Messages.getMessage(Messages.LINK_CREATE_RELATION_PAIR, pageContext);
    }

    @Override
    protected boolean isLinkEnabled() {
        return Delegates.getAuthorizationService().isAllowed(getUser(), RelationPermission.UPDATE, SecuredObjectType.RELATION, getRelationId());
    }
}
