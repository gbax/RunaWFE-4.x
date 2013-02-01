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
package ru.runa.wf.web.action;

import java.util.List;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import ru.runa.common.web.ActionExceptionHelper;
import ru.runa.common.web.Commons;
import ru.runa.common.web.Resources;
import ru.runa.common.web.form.FileForm;
import ru.runa.common.web.form.IdForm;
import ru.runa.service.delegate.Delegates;
import ru.runa.wfe.definition.DefinitionDoesNotExistException;
import ru.runa.wfe.definition.dto.WfDefinition;
import ru.runa.wfe.user.User;

import com.google.common.base.Strings;

/**
 * Created on 06.10.2004
 * 
 * @struts:action path="/redeployProcessDefinition" name="fileForm"
 *                validate="false"
 * @struts.action-forward name="success" path="/manage_process_definition.do"
 *                        redirect = "true"
 * @struts.action-forward name="failure" path="/manage_process_definition.do"
 *                        redirect = "false"
 * @struts.action-forward name="failure_process_definition_does_not_exist"
 *                        path="/manage_process_definitions.do" redirect =
 *                        "true"
 */
public class RedeployProcessDefinitionAction extends BaseDeployProcessDefinitionAction {
    public static final String ACTION_PATH = "/redeployProcessDefinition";

    private boolean definitionExists = false;

    private Long definitionId;

    @Override
    protected void doAction(User user, FileForm fileForm, List<String> processType, ActionMessages errors) {
        try {
            WfDefinition definition = Delegates.getDefinitionService().getProcessDefinition(user, fileForm.getId());
            byte[] data = Strings.isNullOrEmpty(fileForm.getFile().getFileName()) ? null : fileForm.getFile().getFileData();
            definition = Delegates.getDefinitionService().redeployProcessDefinition(user, fileForm.getId(), data, processType);
            definitionId = definition.getId();
        } catch (DefinitionDoesNotExistException e) {
            ActionExceptionHelper.addException(errors, e);
            definitionExists = false;
        } catch (Exception e) {
            ActionExceptionHelper.addException(errors, e);
        }
    }

    @Override
    protected ActionForward getSuccessAction(ActionMapping mapping) {
        return Commons.forward(mapping.findForward(Resources.FORWARD_SUCCESS), IdForm.ID_INPUT_NAME, definitionId);
    }

    @Override
    protected ActionForward getErrorForward(ActionMapping mapping) {
        if (definitionExists) {
            return Commons.forward(mapping.findForward(Resources.FORWARD_FAILURE), IdForm.ID_INPUT_NAME, definitionId);
        }
        return mapping.findForward(ru.runa.common.WebResources.FORWARD_FAILURE_PROCESS_DEFINITION_DOES_NOT_EXIST);
    }

    @Override
    protected void prepare(FileForm fileForm) {
        definitionExists = true;
        definitionId = fileForm.getId();
    }
}
