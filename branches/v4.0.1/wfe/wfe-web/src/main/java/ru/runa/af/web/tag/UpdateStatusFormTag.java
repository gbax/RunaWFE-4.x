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

import org.apache.ecs.html.TD;

import ru.runa.af.web.action.UpdateStatusAction;
import ru.runa.af.web.html.StatusTableBuilder;
import ru.runa.common.web.Messages;
import ru.runa.wfe.security.Permission;
import ru.runa.wfe.user.Actor;
import ru.runa.wfe.user.ActorPermission;

/**
 * Created on 18.08.2004
 * 
 * @jsp.tag name = "updateStatusForm" body-content = "empty"
 */
public class UpdateStatusFormTag extends UpdateExecutorBaseFormTag {

    private static final long serialVersionUID = 1L;

    @Override
    public void fillFormData(TD formTd) {
        StatusTableBuilder builder = new StatusTableBuilder((Actor) getExecutor(), !isFormButtonEnabled(), pageContext);
        formTd.addElement(builder.build());
    }

    @Override
    protected Permission getPermission() {
        return ActorPermission.UPDATE_STATUS;
    }

    @Override
    public String getFormButtonName() {
        return Messages.getMessage(Messages.BUTTON_APPLY, pageContext);
    }

    @Override
    protected boolean isVisible() {
        boolean result = false;
        if (super.isFormButtonEnabled() && (getExecutor() instanceof Actor)) {
            result = true;
        }
        return result;
    }

    @Override
    protected String getTitle() {
        return Messages.getMessage(Messages.TITLE_ACTOR_STATUS, pageContext);
    }

    @Override
    public String getAction() {
        return UpdateStatusAction.ACTION_PATH;
    }
}
