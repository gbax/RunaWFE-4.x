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

import ru.runa.af.web.action.GrantLoginPermissionOnSystemAction;
import ru.runa.wfe.security.ASystem;
import ru.runa.wfe.security.Identifiable;

/**
 * Created on 31.08.2004
 * 
 * @author Vitaliy S aka Yilativs
 * @author Gordienko_m
 * @jsp.tag name = "listExecutorsWithoutPermissionsOnSystemForm" body-content =
 *          "JSP"
 */
public class ListExecutorsWithoutPermissionsOnSystemFormTag extends ListExecutorsWithoutPermissionsBase {

    private static final long serialVersionUID = -4086067034701870708L;

    @Override
    public String getAction() {
        return GrantLoginPermissionOnSystemAction.ACTION_PATH;
    }

    @Override
    protected Identifiable getIdentifiable() {
        return ASystem.INSTANCE;
    }
}
