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

import java.util.Map;

import org.apache.ecs.Element;
import org.apache.ecs.Entities;
import org.apache.ecs.StringElement;
import org.apache.ecs.html.A;
import org.apache.ecs.html.Div;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.TR;
import org.apache.ecs.html.Table;

import ru.runa.common.WebResources;
import ru.runa.common.web.Commons;
import ru.runa.common.web.ConfirmationPopupHelper;
import ru.runa.common.web.Messages;
import ru.runa.common.web.Resources;
import ru.runa.common.web.form.IdForm;
import ru.runa.wf.web.action.CancelProcessAction;
import ru.runa.wf.web.action.RemoveProcessAction;
import ru.runa.wf.web.action.ShowGraphModeHelper;
import ru.runa.wf.web.action.UpgradeProcessToNextDefinitionVersionAction;
import ru.runa.wf.web.form.TaskIdForm;
import ru.runa.wfe.commons.CalendarUtil;
import ru.runa.wfe.commons.SystemProperties;
import ru.runa.wfe.commons.web.PortletUrlType;
import ru.runa.wfe.definition.DefinitionClassPresentation;
import ru.runa.wfe.definition.dto.WfDefinition;
import ru.runa.wfe.execution.ProcessClassPresentation;
import ru.runa.wfe.execution.ProcessPermission;
import ru.runa.wfe.execution.dto.WfProcess;
import ru.runa.wfe.security.Identifiable;
import ru.runa.wfe.security.Permission;
import ru.runa.wfe.service.delegate.Delegates;

import com.google.common.collect.Maps;

/**
 * Created on 29.11.2004
 * 
 * @jsp.tag name = "processInfoForm" body-content = "JSP"
 */
public class ProcessInfoFormTag extends ProcessBaseFormTag {
    private static final long serialVersionUID = -1275657878697999574L;

    private Long taskId;

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    /**
     * @jsp.attribute required = "false" rtexprvalue = "true"
     */
    public Long getTaskId() {
        return taskId;
    }

    @Override
    protected boolean isVisible() {
        return true;
    }

    // start #179

    @Override
    protected Permission getPermission() {
        boolean ended = getProcess().isEnded();
        if (ended) {
            return null;
        } else {
            return ProcessPermission.CANCEL_PROCESS;
        }
    }

    @Override
    protected boolean isFormButtonEnabled(Identifiable identifiable, Permission permission) {
        boolean ended = getProcess().isEnded();
        if (ended) {
            return WebResources.isProcessRemovalEnabled() && Delegates.getExecutorService().isAdministrator(getUser());
        } else {
            return super.isFormButtonEnabled(identifiable, ProcessPermission.CANCEL_PROCESS);
        }
    }

    @Override
    public String getAction() {
        boolean ended = getProcess().isEnded();
        return ended ? RemoveProcessAction.ACTION_PATH : CancelProcessAction.ACTION_PATH;
    }

    @Override
    public String getConfirmationPopupParameter() {
        boolean ended = getProcess().isEnded();
        return ended ? ConfirmationPopupHelper.REMOVE_PROCESS_PARAMETER : ConfirmationPopupHelper.CANCEL_PROCESS_PARAMETER;
    }

    @Override
    public String getFormButtonName() {
        boolean ended = getProcess().isEnded();
        return Messages.getMessage(ended ? Messages.BUTTON_REMOVE : Messages.BUTTON_CANCEL_PROCESS, pageContext);
    }

    // end #179

    @Override
    protected void fillFormData(TD tdFormElement) {
        WfProcess process = getProcess();
        Table table = new Table();
        tdFormElement.addElement(table);
        table.setClass(Resources.CLASS_LIST_TABLE);

        TR nameTR = new TR();
        table.addElement(nameTR);
        String definitionName = Messages.getMessage(DefinitionClassPresentation.NAME, pageContext);
        nameTR.addElement(new TD(definitionName).setClass(Resources.CLASS_LIST_TABLE_TD));

        Element processDefinitionHref;
        try {
            WfDefinition definition = Delegates.getDefinitionService().getProcessDefinition(getUser(), process.getDefinitionId());
            String url = Commons.getActionUrl(ru.runa.common.WebResources.ACTION_MAPPING_MANAGE_DEFINITION, IdForm.ID_INPUT_NAME, definition.getId(),
                    pageContext, PortletUrlType.Render);
            processDefinitionHref = new A(url, process.getName());
        } catch (Exception e) {
            processDefinitionHref = new StringElement(process.getName());
        }
        nameTR.addElement(new TD(processDefinitionHref).setClass(Resources.CLASS_LIST_TABLE_TD));

        TR processIdTR = new TR();
        table.addElement(processIdTR);
        String idName = Messages.getMessage(ProcessClassPresentation.PROCESS_ID, pageContext);
        processIdTR.addElement(new TD(idName).setClass(Resources.CLASS_LIST_TABLE_TD));
        processIdTR.addElement(new TD(new Long(process.getId()).toString()).setClass(Resources.CLASS_LIST_TABLE_TD));

        TR versionTR = new TR();
        table.addElement(versionTR);
        String definitionVersion = Messages.getMessage(DefinitionClassPresentation.VERSION, pageContext);
        versionTR.addElement(new TD(definitionVersion).setClass(Resources.CLASS_LIST_TABLE_TD));
        Element versionElement = new StringElement(String.valueOf(process.getVersion()));
        try {
            if (SystemProperties.isUpgradeProcessToNextDefinitionVersionEnabled() && !process.isEnded()) {
                WfDefinition latestDefinition = Delegates.getDefinitionService().getLatestProcessDefinition(getUser(), process.getName());
                if (latestDefinition.getVersion().intValue() != process.getVersion()) {
                    Div div = new Div();
                    div.addElement(versionElement);
                    div.addElement(Entities.NBSP);
                    String url = Commons.getActionUrl(UpgradeProcessToNextDefinitionVersionAction.ACTION_PATH, IdForm.ID_INPUT_NAME, process.getId(),
                            pageContext, PortletUrlType.Render);
                    div.addElement(new A(url, Commons.getMessage(Messages.PROCESS_UPGRADE_TO_NEXT_DEFINITION_VERSION, pageContext)));
                    versionElement = div;
                }
            }
        } catch (Exception e) {
        }
        versionTR.addElement(new TD(versionElement).setClass(Resources.CLASS_LIST_TABLE_TD));

        TR startedTR = new TR();
        table.addElement(startedTR);
        String startedName = Messages.getMessage(ProcessClassPresentation.PROCESS_START_DATE, pageContext);
        startedTR.addElement(new TD(startedName).setClass(Resources.CLASS_LIST_TABLE_TD));
        startedTR.addElement(new TD(CalendarUtil.formatDateTime(process.getStartDate())).setClass(Resources.CLASS_LIST_TABLE_TD));

        if (process.isEnded()) {
            TR endedTR = new TR();
            table.addElement(endedTR);
            String endedName = Messages.getMessage(ProcessClassPresentation.PROCESS_END_DATE, pageContext);
            endedTR.addElement(new TD(endedName).setClass(Resources.CLASS_LIST_TABLE_TD));
            endedTR.addElement(new TD(CalendarUtil.formatDateTime(process.getEndDate())).setClass(Resources.CLASS_LIST_TABLE_TD));
        }

        WfProcess parentProcess = Delegates.getExecutionService().getParentProcess(getUser(), getIdentifiableId());
        if (parentProcess != null) {
            TR parentTR = new TR();
            table.addElement(parentTR);
            String parentNameString = Messages.getMessage(Messages.LABEL_PARENT_PROCESS, pageContext);
            parentTR.addElement(new TD(parentNameString).setClass(Resources.CLASS_LIST_TABLE_TD));
            TD td = new TD();
            td.setClass(Resources.CLASS_LIST_TABLE_TD);
            Element inner;
            String parentProcessDefinitionName = parentProcess.getName();
            if (checkReadable(parentProcess)) {
                Map<String, Object> params = Maps.newHashMap();
                params.put(IdForm.ID_INPUT_NAME, parentProcess.getId());
                params.put(TaskIdForm.TASK_ID_INPUT_NAME, taskId);
                params.put("childProcessId", process.getId());
                inner = new A(Commons.getActionUrl(ShowGraphModeHelper.getManageProcessAction(), params, pageContext, PortletUrlType.Render),
                        parentProcessDefinitionName);
            } else {
                inner = new StringElement(parentProcessDefinitionName);
            }
            td.addElement(inner);
            parentTR.addElement(td);
        }
    }

    private boolean checkReadable(WfProcess parentProcess) {
        return Delegates.getAuthorizationService().isAllowed(getUser(), ProcessPermission.READ, parentProcess);
    }

    @Override
    protected String getTitle() {
        return Messages.getMessage(Messages.TITLE_PROCESS, pageContext);
    }

}
