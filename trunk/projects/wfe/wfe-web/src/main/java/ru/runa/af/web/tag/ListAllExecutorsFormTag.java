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

import java.util.List;

import org.apache.ecs.html.TD;

import ru.runa.af.web.BatchExecutorPermissionHelper;
import ru.runa.af.web.action.RemoveExecutorsAction;
import ru.runa.common.WebResources;
import ru.runa.common.web.ConfirmationPopupHelper;
import ru.runa.common.web.Messages;
import ru.runa.common.web.PagingNavigationHelper;
import ru.runa.common.web.form.IdForm;
import ru.runa.common.web.html.HeaderBuilder;
import ru.runa.common.web.html.IdentifiableCheckboxTDBuilder;
import ru.runa.common.web.html.ReflectionRowBuilder;
import ru.runa.common.web.html.RowBuilder;
import ru.runa.common.web.html.SortingHeaderBuilder;
import ru.runa.common.web.html.TDBuilder;
import ru.runa.common.web.html.TableBuilder;
import ru.runa.common.web.tag.BatchReturningTitledFormTag;
import ru.runa.service.ExecutorService;
import ru.runa.service.delegate.Delegates;
import ru.runa.wfe.presentation.BatchPresentation;
import ru.runa.wfe.user.Executor;
import ru.runa.wfe.user.ExecutorPermission;

/**
 * Created on 18.08.2004
 * 
 * @author Gordienko_m
 * @author Vitaliy S aka Yilativs
 * @jsp.tag name = "listAllExecutorsForm" body-content = "JSP"
 */
public class ListAllExecutorsFormTag extends BatchReturningTitledFormTag {

    private static final long serialVersionUID = -7478022960008761625L;

    private boolean isButtonEnabled;

    @Override
    protected void fillFormElement(TD tdFormElement) {
        ExecutorService executorService = Delegates.getExecutorService();
        int executorsCount = executorService.getExecutorsCount(getUser(), getBatchPresentation());
        List<Executor> executors = executorService.getAll(getUser(), getBatchPresentation());
        BatchPresentation batchPresentation = getBatchPresentation();
        setupFormButton(executors);
        PagingNavigationHelper navigation = new PagingNavigationHelper(pageContext, batchPresentation, executorsCount, getReturnAction());
        navigation.addPagingNavigationTable(tdFormElement);
        TableBuilder tableBuilder = new TableBuilder();
        TDBuilder[] prefixBuilders = new TDBuilder[] { new IdentifiableCheckboxTDBuilder(ExecutorPermission.UPDATE) };
        TDBuilder[] builders = getBuilders(prefixBuilders, batchPresentation, new TDBuilder[] {});
        RowBuilder rowBuilder = new ReflectionRowBuilder(executors, batchPresentation, pageContext, WebResources.ACTION_MAPPING_UPDATE_EXECUTOR,
                getReturnAction(), IdForm.ID_INPUT_NAME, builders);
        HeaderBuilder headerBuilder = new SortingHeaderBuilder(batchPresentation, 1, 0, getReturnAction(), pageContext);
        tdFormElement.addElement(tableBuilder.build(headerBuilder, rowBuilder));
        navigation.addPagingNavigationTable(tdFormElement);
    }

    /**
     * Check, if exists executor to remove (to enable or disable form button).
     * 
     * @param subject
     *            Current actor subject.
     * @param executors
     *            Executors, shown on form.
     */
    private void setupFormButton(List<Executor> executors) {
        BatchPresentation batchPresentation = getBatchPresentation();
        for (boolean isEnable : BatchExecutorPermissionHelper
                .getEnabledCheckboxes(getUser(), executors, batchPresentation, ExecutorPermission.UPDATE)) {
            if (isEnable) {
                isButtonEnabled = true;
                break;
            }
        }
    }

    @Override
    public String getFormButtonName() {
        return Messages.getMessage(Messages.BUTTON_REMOVE, pageContext);
    }

    @Override
    protected boolean isFormButtonEnabled() {
        return isButtonEnabled;
    }

    @Override
    protected String getTitle() {
        return Messages.getMessage(Messages.TITLE_EXECUTORS, pageContext);
    }

    @Override
    public String getAction() {
        return RemoveExecutorsAction.ACTION_PATH;
    }

    @Override
    public String getConfirmationPopupParameter() {
        return ConfirmationPopupHelper.REMOVE_EXECUTORS_PARAMETER;
    }
}
