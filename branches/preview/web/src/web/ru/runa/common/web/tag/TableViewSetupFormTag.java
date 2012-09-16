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
package ru.runa.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.ecs.Entities;
import org.apache.ecs.StringElement;
import org.apache.ecs.html.Form;
import org.apache.ecs.html.Input;
import org.apache.ecs.html.Option;
import org.apache.ecs.html.Select;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.TH;
import org.apache.ecs.html.TR;
import org.apache.ecs.html.Table;

import ru.runa.af.presentation.BatchPresentation;
import ru.runa.af.presentation.BatchPresentationNotFoundException;
import ru.runa.af.presentation.ClassPresentation;
import ru.runa.af.presentation.FieldDescriptor;
import ru.runa.af.presentation.FieldFilterMode;
import ru.runa.af.presentation.FieldState;
import ru.runa.af.presentation.Profile;
import ru.runa.common.web.Commons;
import ru.runa.common.web.Commons.PortletUrl;
import ru.runa.common.web.Messages;
import ru.runa.common.web.ProfileHttpSessionHelper;
import ru.runa.common.web.Resources;
import ru.runa.common.web.action.TableViewSetupFormAction;
import ru.runa.common.web.form.TableViewSetupForm;
import ru.runa.common.web.html.format.FilterFormatsFactory;
import ru.runa.common.web.html.format.FilterTDFormatter;
import ru.runa.commons.system.CommonResources;

/**
 * Created on 24.01.2005
 * 
 * @jsp.tag name = "tableViewSetupForm" body-content = "JSP"
 */
public class TableViewSetupFormTag extends AbstractReturningTag implements BatchedTag {

    private static final long serialVersionUID = 6534068425896008626L;
    
    private static final String GROUP_BY_SUBPROCESS_ENABLED = "group.subprocess.enabled";

    private static boolean groupBySubprocessEnabled = false;

    static {
        try {
            String groupBySubprocessEnabledStr = new CommonResources().readPropertyIfExist(GROUP_BY_SUBPROCESS_ENABLED);
            if (groupBySubprocessEnabledStr != null) {
                groupBySubprocessEnabled = Boolean.parseBoolean(groupBySubprocessEnabledStr.trim());
            }
        } catch (Exception e) {
        }
    }

    public String getApplyButtonName() {
        return Messages.getMessage(Messages.BUTTON_APPLY, pageContext);
    }

    public String getSaveButtonName() {
        return Messages.getMessage(Messages.BUTTON_SAVE, pageContext);
    }

    public String getCreateNewButtonName() {
        return Messages.getMessage(Messages.BUTTON_SAVE_AS, pageContext);
    }

    public String getRemoveButtonName() {
        return Messages.getMessage(Messages.BUTTON_REMOVE, pageContext);
    }

    private String batchPresentationId;

    /**
     * @jsp.attribute required = "true" rtexprvalue = "true"
     */
    public void setBatchPresentationId(String id) {
        batchPresentationId = id;
    }

    public String getBatchPresentationId() {
        return batchPresentationId;
    }

    public BatchPresentation getBatchPresentation() {
        throw new UnsupportedOperationException("getBatchPresentation() is unsupported");
    }

    protected BatchPresentation getActiveBatchPresentation() throws JspException {
        try {
            Profile profile = ProfileHttpSessionHelper.getProfile(pageContext.getSession());
            return profile.getActiveBatchPresentation(getBatchPresentationId());
        } catch (BatchPresentationNotFoundException e) {
            throw new JspException(e);
        }
    }

    @Override
    public int doStartTag() throws JspException {
        Form form = new Form(getAction(), getMethod());
        form.setName(TableViewSetupForm.FORM_NAME);
        form.addElement(new Input(Input.HIDDEN, TableViewSetupForm.BATCH_PRESENTATION_ID, getBatchPresentationId()));
        form.addElement(new Input(Input.HIDDEN, TableViewSetupForm.RETURN_ACTION, getReturnAction()));
        BatchPresentation activeBatchPresentation = getActiveBatchPresentation();
        Table table = buildBatchTable(activeBatchPresentation);
        form.addElement(table);

        if (activeBatchPresentation.isWithPaging()) {
            TR tr = new TR();
            table.addElement(tr);
            TD td = new TD();
            td.setColSpan(6);
            tr.addElement(td);
            td.setClass(Resources.CLASS_VIEW_SETUP_TD);
            //			td.addElement(Entities.NBSP);
            td.addElement(Messages.getMessage(Messages.LABEL_VIEW_SIZE, pageContext));
            td.addElement(Entities.NBSP);
            Select selectSize = new Select(TableViewSetupForm.VIEW_SIZE_NAME);
            int[] allowedSizes = activeBatchPresentation.getAllowedViewSizes();
            for (int i = 0; i < allowedSizes.length; i++) {
                Option option = new Option();
                option.setValue(allowedSizes[i]);
                option.addElement(String.valueOf(allowedSizes[i]));
                if (activeBatchPresentation.getRangeSize() == allowedSizes[i]) {
                    option.setSelected(true);
                }
                selectSize.addElement(option);
            }
            td.addElement(selectSize);
        }
        addAdditionalInfo(table, activeBatchPresentation);
        addSaveSection(table, activeBatchPresentation);

        JspWriter writer = pageContext.getOut();
        form.output(writer);
        return super.doStartTag();
    }

    //allows to add addition presentation elements
    protected void addAdditionalInfo(Table table, BatchPresentation activeBatchPresentation) {
    }

    private void addSaveSection(Table table, BatchPresentation activeBatchPresentation) {
        TR saveTR = new TR();
        table.addElement(saveTR);
        TD saveTD = new TD();
        saveTD.setColSpan(6);
        saveTR.addElement(saveTD);
        saveTD.setClass(Resources.CLASS_VIEW_SETUP_TD);

        //TODO implemnt support of apply button
        //		Input applyInput = new Input(Input.SUBMIT, TableViewSetupFormAction.PARAMETER_NAME, getApplyButtonName());
        //		applyInput.setClass(Resources.CLASS_BUTTON);
        //		td.addElement(applyInput);
        //		td.addElement(Entities.NBSP);

        Input saveInput = new Input(Input.SUBMIT, TableViewSetupFormAction.PARAMETER_NAME, getSaveButtonName());
        saveInput.setClass(Resources.CLASS_BUTTON);
        saveTD.addElement(saveInput);
        saveTD.addElement(Entities.NBSP);

        Input saveAsInput = new Input(Input.SUBMIT, TableViewSetupFormAction.PARAMETER_NAME, getCreateNewButtonName());
        saveAsInput.setClass(Resources.CLASS_BUTTON);
        saveTD.addElement(saveAsInput);

        saveTD.addElement(Entities.NBSP);
        saveTD.addElement(new Input(Input.TEXT, TableViewSetupForm.SAVE_AS_NAME, "").setClass(Resources.CLASS_BUTTON));

        if (!activeBatchPresentation.isDefault()) {
            saveTD.addElement(Entities.NBSP);
            Input deleteInput = new Input(Input.SUBMIT, TableViewSetupFormAction.PARAMETER_NAME, getRemoveButtonName());
            deleteInput.setClass(Resources.CLASS_BUTTON);
            saveTD.addElement(deleteInput);
        }
    }

    protected Table buildBatchTable(BatchPresentation batchPresentation) {
        Table table = new Table();
        table.setClass(Resources.CLASS_VIEW_SETUP_TABLE);
        table.addElement(getHeaderRow());

        {
            FieldDescriptor[] displayedFields = batchPresentation.getDisplayFields();
            for (int i = 0; i < displayedFields.length; ++i) {
                if (displayedFields[i].displayName.startsWith(ClassPresentation.filterable_prefix)) {
                    continue;
                }
                if (!displayedFields[i].displayName.startsWith(ClassPresentation.editable_prefix)
                        && displayedFields[i].fieldState == FieldState.ENABLED) {
                    table.addElement(buildViewRow(batchPresentation, displayedFields[i].fieldIdx, i));
                }
            }
        }
        {
            FieldDescriptor[] hiddenFields = batchPresentation.getHiddenFields();
            for (int i = 0; i < hiddenFields.length; ++i) {
                if (hiddenFields[i].displayName.startsWith(ClassPresentation.filterable_prefix)) {
                    continue;
                }
                if (!hiddenFields[i].displayName.startsWith(ClassPresentation.editable_prefix) && hiddenFields[i].fieldState == FieldState.ENABLED) {
                    table.addElement(buildViewRow(batchPresentation, hiddenFields[i].fieldIdx, -1));
                }
            }
        }
        {
            FieldDescriptor[] allFields = batchPresentation.getAllFields();
            for (int i = 0; i < allFields.length; ++i) {
                if ((allFields[i].displayName.startsWith(ClassPresentation.editable_prefix) && allFields[i].fieldState == FieldState.ENABLED)
                        || (allFields[i].displayName.startsWith(ClassPresentation.filterable_prefix) && groupBySubprocessEnabled)) {
                    table.addElement(buildViewRow(batchPresentation, allFields[i].fieldIdx, -1));
                }
            }
        }

        return table;
    }

    protected TR buildViewRow(BatchPresentation batchPresentation, int fieldIdx, int fieldDisplayPosition) {
        int noneOptionPosition = batchPresentation.getAllFields().length + 1;
        TR tr = new TR();

        FieldDescriptor field = batchPresentation.getAllFields()[fieldIdx];
        boolean isEditable = field.displayName.startsWith(ClassPresentation.editable_prefix);
        boolean isDynamic = field.displayName.startsWith(ClassPresentation.removable_prefix);
        boolean isFilterable = field.displayName.startsWith(ClassPresentation.filterable_prefix);

        { //field name section
            TD td = null;
            if (isEditable) {
                td = new TD(Messages.getMessage(field.displayName.substring(field.displayName.lastIndexOf(':') + 1), pageContext) + ":");
                td.addElement(new Input(Input.TEXT, TableViewSetupForm.EDITABLE_FIELDS, ""));
            } else if (isDynamic) {
                int end = field.displayName.lastIndexOf(':');
                int begin = field.displayName.lastIndexOf(':', end - 1) + 1;
                td = new TD(new Input(Input.CHECKBOX, TableViewSetupForm.REMOVABLE_FIELD_IDS, fieldIdx).setChecked(true));
                td.addElement(Messages.getMessage(field.displayName.substring(begin, end), pageContext) + ":"
                        + field.displayName.substring(field.displayName.lastIndexOf(':') + 1));
            } else if (isFilterable) {
                Input groupingInput = new Input(Input.CHECKBOX, TableViewSetupForm.GROUPING_POSITIONS, fieldIdx);
                if (batchPresentation.isFieldGroupped(fieldIdx)) {
                    groupingInput.setChecked(true);
                }
                td = new TD(groupingInput);
                td.addElement(Messages.getMessage("batch_presentation.process_instance.group_by_id", pageContext));
            } else {
                td = new TD(Messages.getMessage(field.displayName, pageContext));
            }
            td.setClass(Resources.CLASS_VIEW_SETUP_TD);
            td.addElement(new Input(Input.HIDDEN, TableViewSetupForm.IDS_INPUT_NAME, String.valueOf(fieldIdx)));
            tr.addElement(td);
        }
        if (isEditable || isFilterable) { // Editable fields havn't fields for sorting/filtering e t.c.
            for (int idx = 0; idx < 5; ++idx) {
                tr.addElement(new TD().setClass(Resources.CLASS_VIEW_SETUP_TD));
            }
            return tr;
        }
        { //field display position section
            Select displayFieldPositionSelect = new Select(TableViewSetupForm.DISPLAY_POSITIONS, createPositionOptions(batchPresentation, fieldIdx));
            tr.addElement(new TD(displayFieldPositionSelect).setClass(Resources.CLASS_VIEW_SETUP_TD));
            if (fieldDisplayPosition >= 0 && !isEditable) {
                displayFieldPositionSelect.selectOption(fieldDisplayPosition + 1);
            } else {
                displayFieldPositionSelect.selectOption(noneOptionPosition);
            }
        }
        {// field sorting/groupping section
            if (field.isSortable) {
                Select sortingModeSelect = new Select(TableViewSetupForm.SORTING_MODE_NAMES, createSortModeOptions());
                tr.addElement(new TD(sortingModeSelect).setClass(Resources.CLASS_VIEW_SETUP_TD));
                Select sortingFieldPositoinSelect = new Select(TableViewSetupForm.SORTING_POSITIONS, createPositionOptions(batchPresentation,
                        fieldIdx));
                tr.addElement(new TD(sortingFieldPositoinSelect).setClass(Resources.CLASS_VIEW_SETUP_TD));
                selectSortingMode(batchPresentation, fieldIdx, sortingModeSelect, sortingFieldPositoinSelect);

                Input groupingInput = new Input(Input.CHECKBOX, TableViewSetupForm.GROUPING_POSITIONS, fieldIdx);
                if (batchPresentation.isFieldGroupped(fieldIdx)) {
                    groupingInput.setChecked(true);
                }
                tr.addElement(new TD(groupingInput).addElement(
                        new Input(Input.HIDDEN, TableViewSetupForm.SORTING_FIELD_IDS, String.valueOf(fieldIdx))).setClass(
                        Resources.CLASS_VIEW_SETUP_TD));
            } else {
                for (int idx = 0; idx < 3; ++idx) {
                    tr.addElement(new TD().setClass(Resources.CLASS_VIEW_SETUP_TD));
                }
            }
        }

        // filtering
        if (field.filterMode != FieldFilterMode.NONE) {
            FilterTDFormatter formatter = FilterFormatsFactory.getFormatter(batchPresentation.getAllFields()[fieldIdx].fieldType);
            tr.addElement(formatter.format(pageContext, batchPresentation.getFieldFilteredCriteria(fieldIdx), fieldIdx,
                    batchPresentation.isFieldFiltered(fieldIdx)).addElement(
                    new Input(Input.HIDDEN, TableViewSetupForm.FILTERING_FIELD_IDS, String.valueOf(fieldIdx))));
        } else {
            tr.addElement(new TD().setClass(Resources.CLASS_VIEW_SETUP_TD));
        }

        return tr;
    }

    protected void selectSortingMode(BatchPresentation batchPresentation, int fieldIndex, Select sortingModeSelect, Select sortingFieldPositoinSelect) {
        if (batchPresentation.isSortingField(fieldIndex)) {
            int sortedFieldIndex = batchPresentation.getSortingFieldPosition(fieldIndex);
            if (batchPresentation.getFieldsToSortModes()[sortedFieldIndex]) {
                sortingModeSelect.selectOption(0);
            } else {
                sortingModeSelect.selectOption(1);
            }
            sortingFieldPositoinSelect.selectOption(sortedFieldIndex + 1);
        } else {
            sortingModeSelect.selectOption(0);
            sortingFieldPositoinSelect.selectOption(0);
        }
    }

    protected Option[] createPositionOptions(BatchPresentation batchPresentation, int fieldIdx) {
        FieldDescriptor[] fields = batchPresentation.getAllFields();
        if (fields[fieldIdx].displayName.startsWith(ClassPresentation.editable_prefix)) {
            return new Option[] { new Option("-1").addElement(new StringElement(Messages.getMessage(Messages.LABEL_NONE, pageContext))) };
        }
        int fieldsCount = fields.length;
        for (int i = fields.length - 1; i >= 0; --i) {
            if (fields[i].displayName.startsWith(ClassPresentation.editable_prefix) || fields[i].fieldState != FieldState.ENABLED) {
                --fieldsCount;
            }
        }

        Option[] positionOptions = new Option[fieldsCount + 1];
        positionOptions[0] = new Option("-1").addElement(new StringElement(Messages.getMessage(Messages.LABEL_NONE, pageContext)));
        for (int position = 1; position < positionOptions.length; position++) {
            String positionString = new Integer(position).toString();
            String positionIdString = new Integer(position - 1).toString();
            positionOptions[position] = new Option(positionIdString).addElement(positionString);
        }
        return positionOptions;
    }

    protected Option[] createSortModeOptions() {
        Option[] sortingModesOptions = {
                new Option(TableViewSetupForm.ASC_SORTING_MODE).addElement(Messages.getMessage(Messages.LABEL_ASC, pageContext)),
                new Option(TableViewSetupForm.DSC_SORTING_MODE).addElement(Messages.getMessage(Messages.LABEL_DESC, pageContext)) };
        return sortingModesOptions;
    }

    protected TR getHeaderRow() {
        TR tr = new TR();
        String[] headerNames = { Messages.getMessage(Messages.LABEL_FIELD_NAMES, pageContext),
                Messages.getMessage(Messages.LABEL_DISPLAY_POSITION, pageContext), Messages.getMessage(Messages.LABEL_SORTING_TYPE, pageContext),
                Messages.getMessage(Messages.LABEL_SORTING_POSITION, pageContext), Messages.getMessage(Messages.LABEL_GROUPING, pageContext),
                Messages.getMessage(Messages.LABEL_FILTER_CRITERIA, pageContext) };
        for (int i = 0; i < headerNames.length; i++) {
            tr.addElement(new TH(headerNames[i]).setClass(Resources.CLASS_VIEW_SETUP_TH));
        }
        return tr;
    }

    @Override
    public String getAction() {
        return Commons.getActionUrl(TableViewSetupFormAction.ACTION_PATH, pageContext, PortletUrl.Action);
    }
}
