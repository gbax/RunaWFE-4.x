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

import ru.runa.af.Identifiable;
import ru.runa.af.service.ExecutorService;
import ru.runa.af.web.action.GrantReadPermissionOnExecutorAction;
import ru.runa.delegate.DelegateFactory;

/**
 * Created on 23.08.2004
 * 
 * @jsp.tag name = "listExecutorsWithoutPermissionsOnExecutorForm" body-content = "JSP"
 */
public class ListExecutorsWithoutPermissionsOnExecutorFormTag extends ListExecutorsWithoutPermissionsBase {

    private static final long serialVersionUID = 3876313306976711266L;

    @Override
    public String getAction() {
        return GrantReadPermissionOnExecutorAction.ACTION_PATH;
    }

    @Override
    protected Identifiable getIdentifiable() throws JspException {
        try {
            ExecutorService executorService = DelegateFactory.getInstance().getExecutorService();
            return executorService.getExecutor(getSubject(), getIdentifiableId());
        } catch (Exception e) {
            throw new JspException(e);
        }
    }
}
