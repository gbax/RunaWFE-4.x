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

import javax.servlet.jsp.JspException;

import org.apache.ecs.html.TD;

import ru.runa.af.web.BatchExecutorPermissionHelper;
import ru.runa.common.WebResources;
import ru.runa.common.web.PagingNavigationHelper;
import ru.runa.common.web.form.IdForm;
import ru.runa.common.web.html.HeaderBuilder;
import ru.runa.common.web.html.IdentifiableCheckboxTDBuilder;
import ru.runa.common.web.html.ReflectionRowBuilder;
import ru.runa.common.web.html.RowBuilder;
import ru.runa.common.web.html.SortingHeaderBuilder;
import ru.runa.common.web.html.TDBuilder;
import ru.runa.common.web.html.TableBuilder;
import ru.runa.common.web.tag.BatchedTag;
import ru.runa.common.web.tag.ReturningTag;
import ru.runa.wfe.presentation.BatchPresentation;
import ru.runa.wfe.security.Permission;
import ru.runa.wfe.user.Executor;

/**
 * Created on 06.09.2004
 * 
 * @author Gordienko_m
 * @author Vitaliy S aka Yilativs
 */
abstract public class ListExecutorsBaseFormTag extends UpdateExecutorBaseFormTag implements BatchedTag, ReturningTag {
    private static final long serialVersionUID = 1L;
    protected boolean isButtonEnabled;

    @Override
    protected boolean isFormButtonEnabled() throws JspException {
        return isButtonEnabled && (super.isFormButtonEnabled());
    }

    private String batchPresentationId;

    /**
     * @jsp.attribute required = "true" rtexprvalue = "true"
     */
    @Override
    public void setBatchPresentationId(String batchPresentationId) {
        this.batchPresentationId = batchPresentationId;
    }

    @Override
    public String getBatchPresentationId() {
        return batchPresentationId;
    }

    @Override
    public BatchPresentation getBatchPresentation() {
        return getProfile().getActiveBatchPresentation(batchPresentationId);
    }

    @Override
    protected void fillFormData(TD tdFormElement) throws JspException {
        try {
            int executorsCount = getExecutorsCount();
            List<? extends Executor> executors = getExecutors();
            if (super.isFormButtonEnabled()) {
                for (boolean enable : BatchExecutorPermissionHelper.getEnabledCheckboxes(getUser(), executors, getBatchPresentation(),
                        getExecutorsPermission())) {
                    if (enable) {
                        isButtonEnabled = true;
                        break;
                    }
                }
            }
            BatchPresentation batchPresentation = getBatchPresentation();
            PagingNavigationHelper navigation = new PagingNavigationHelper(pageContext, batchPresentation, executorsCount, getReturnAction());
            navigation.addPagingNavigationTable(tdFormElement);
            TableBuilder tableBuilder = new TableBuilder();
            TDBuilder[] builders = getBuilders(new TDBuilder[] { new IdentifiableCheckboxTDBuilder(getExecutorsPermission()) }, batchPresentation,
                    new TDBuilder[] {});
            RowBuilder rowBuilder = new ReflectionRowBuilder(executors, batchPresentation, pageContext, WebResources.ACTION_MAPPING_UPDATE_EXECUTOR,
                    getReturnAction(), IdForm.ID_INPUT_NAME, builders);
            HeaderBuilder headerBuilder = new SortingHeaderBuilder(batchPresentation, 1, 0, returnAction, pageContext);
            tdFormElement.addElement(tableBuilder.build(headerBuilder, rowBuilder));
            navigation.addPagingNavigationTable(tdFormElement);
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Load executors to show.
     * 
     * @return Array of executors to show in tag.
     */
    protected abstract List<? extends Executor> getExecutors();

    /**
     * Load count of all executors may be shown in tag. Used to setup pages before and after executor list.
     * 
     * @return Executors count.
     */
    protected abstract int getExecutorsCount();

    protected abstract Permission getExecutorsPermission();

    private String returnAction;

    @Override
    public String getReturnAction() {
        return returnAction;
    }

    /**
     * @jsp.attribute required = "false" rtexprvalue = "true"
     */
    @Override
    public void setReturnAction(String returnAction) {
        this.returnAction = returnAction;
    }

}
