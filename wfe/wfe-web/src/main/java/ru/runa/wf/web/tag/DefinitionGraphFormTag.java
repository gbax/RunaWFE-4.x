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

import java.util.List;

import org.apache.ecs.html.Center;
import org.apache.ecs.html.IMG;
import org.apache.ecs.html.TD;

import ru.runa.common.web.Commons;
import ru.runa.common.web.Messages;
import ru.runa.common.web.form.IdForm;
import ru.runa.service.delegate.Delegates;
import ru.runa.service.wf.DefinitionService;
import ru.runa.wf.web.action.ProcessDefinitionGraphImageAction;
import ru.runa.wfe.commons.web.PortletUrlType;
import ru.runa.wfe.definition.DefinitionPermission;
import ru.runa.wfe.graph.view.GraphElementPresentation;
import ru.runa.wfe.security.Permission;

/**
 * Created on 30.08.2004
 * 
 * @jsp.tag name = "definitionGraphForm" body-content = "empty"
 */
public class DefinitionGraphFormTag extends ProcessDefinitionBaseFormTag {

    private static final long serialVersionUID = 880745425325952663L;

    @Override
    protected void fillFormData(final TD tdFormElement) {

        String href = Commons.getActionUrl(ProcessDefinitionGraphImageAction.ACTION_PATH, IdForm.ID_INPUT_NAME, getIdentifiableId(), pageContext,
                PortletUrlType.Resource);
        Center center = new Center();

        IMG processGraphImage = new IMG(href);
        processGraphImage.setBorder(0);

        DefinitionService definitionService = Delegates.getDefinitionService();
        List<GraphElementPresentation> elements = definitionService.getProcessDefinitionGraphElements(getUser(), getIdentifiableId());
        DefinitionGraphElementPresentationVisitor operation = new DefinitionGraphElementPresentationVisitor(pageContext);
        for (GraphElementPresentation graphElementPresentation : elements) {
            graphElementPresentation.visit(operation);
        }
        if (!operation.getResultMap().isEmpty()) {
            tdFormElement.addElement(operation.getResultMap());
            processGraphImage.setUseMap("#processMap");
        }
        center.addElement(processGraphImage);
        tdFormElement.addElement(center);

    }

    @Override
    protected Permission getPermission() {
        return DefinitionPermission.READ;
    }

    @Override
    protected boolean isFormButtonVisible() {
        return false;
    }

    @Override
    protected String getTitle() {
        return Messages.getMessage(Messages.TITLE_PROCESS_GRAPH, pageContext);
    }

}
