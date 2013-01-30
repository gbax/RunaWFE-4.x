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

import ru.runa.af.web.action.UpdatePermissionOnSystemAction;
import ru.runa.common.web.Messages;
import ru.runa.common.web.html.PermissionTableBuilder;
import ru.runa.wfe.security.ASystem;
import ru.runa.wfe.security.Permission;

/**
 * Created on 24.08.2004
 * 
 * @jsp.tag name = "updatePermissionsOnSystemForm" body-content = "JSP"
 */
public class UpdatePermissionsOnSystemFormTag extends UpdateSystemBaseFormTag {
    private static final long serialVersionUID = 2102558048751151714L;

    protected void fillFormData(TD tdFormElement) throws JspException {
        ASystem system = getSystem();
        PermissionTableBuilder tableBuilder = new PermissionTableBuilder(system, getUser(), pageContext);
        tdFormElement.addElement(tableBuilder.buildTable());
    }

    protected Permission getPermission() {
        return Permission.UPDATE_PERMISSIONS;
    }

    public String getFormButtonName() {
        return Messages.getMessage(Messages.BUTTON_APPLY, pageContext);
    }

    protected String getTitle() {
        return Messages.getMessage(Messages.TITLE_PERMISSION_OWNERS, pageContext);
    }

    public String getAction() {
        return UpdatePermissionOnSystemAction.ACTION_PATH_NAME;
    }

}
