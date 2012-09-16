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

import org.apache.ecs.html.TD;

import ru.runa.af.Identifiable;
import ru.runa.af.Permission;
import ru.runa.af.RelationPermission;
import ru.runa.af.RelationsGroupSecure;
import ru.runa.af.web.action.UpdatePermissionOnGroupsOfRelations;
import ru.runa.common.web.Messages;
import ru.runa.common.web.html.PermissionTableBuilder;
import ru.runa.common.web.tag.IdentifiableFormTag;

/**
 * @jsp.tag name = "updatePermissionsOnRelationGroupsForm" body-content = "JSP"
 */
public class UpdatePermissionOnRelationGroupsFormTag extends IdentifiableFormTag {
    private static final long serialVersionUID = 1L;

    @Override
    protected void fillFormData(TD tdFormElement) throws JspException {
        PermissionTableBuilder tableBuilder = new PermissionTableBuilder(getIdentifiable(), getSubject(), pageContext);
        tdFormElement.addElement(tableBuilder.buildTable());
    }

    @Override
    protected Identifiable getIdentifiable() throws JspException {
        return RelationsGroupSecure.INSTANCE;
    }

    @Override
    protected Permission getPermission() throws JspException {
        return RelationPermission.READ;
    }

    public String getAction() {
        return UpdatePermissionOnGroupsOfRelations.ACTION_PATH_NAME;
    }

    protected String getFormButtonName() {
        return Messages.getMessage(Messages.BUTTON_APPLY, pageContext);
    }

    protected String getTitle() {
        return Messages.getMessage(Messages.TITLE_PERMISSION_OWNERS, pageContext);
    }
}
