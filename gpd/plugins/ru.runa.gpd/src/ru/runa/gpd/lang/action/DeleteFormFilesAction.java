package ru.runa.gpd.lang.action;

import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelection;

import ru.runa.gpd.Localization;
import ru.runa.gpd.lang.model.FormNode;
import ru.runa.gpd.ui.dialog.MultipleSelectionDialog;
import ru.runa.gpd.util.SelectionItem;

import com.google.common.collect.Lists;

public class DeleteFormFilesAction extends BaseModelActionDelegate {

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        super.selectionChanged(action, selection);
        FormNode formNode = getSelection();
        if (formNode != null) {
            action.setEnabled(formNode.hasForm() || formNode.hasFormValidation() || formNode.hasFormScript());
        }
    }

    @Override
    public void run(IAction action) {
        FormNode formNode = getSelection();
        List<SelectionItem> items = Lists.newArrayList();
        SelectionItem deleteFormFile = null;
        if (formNode.hasForm()) {
            deleteFormFile = new SelectionItem(true, Localization.getString("form.file"));
            items.add(deleteFormFile);
        }
        SelectionItem deleteValidationFile = null;
        if (formNode.hasFormValidation()) {
            deleteValidationFile = new SelectionItem(false, Localization.getString("form.validationFile"));
            items.add(deleteValidationFile);
        }
        SelectionItem deleteScriptFile = null;
        if (formNode.hasFormScript()) {
            deleteScriptFile = new SelectionItem(true, Localization.getString("form.scriptFile"));
            items.add(deleteScriptFile);
        }
        MultipleSelectionDialog dialog = new MultipleSelectionDialog(
                Localization.getString("DeleteFormFilesAction.title"), items);
        if (dialog.open() == IDialogConstants.OK_ID) {
            if (deleteFormFile != null && deleteFormFile.isEnabled()) {
                formNode.setFormFileName(FormNode.EMPTY);
            }
            if (deleteValidationFile != null && deleteValidationFile.isEnabled()) {
                formNode.setValidationFileName(FormNode.EMPTY);
            }
            if (deleteScriptFile != null && deleteScriptFile.isEnabled()) {
                formNode.setScriptFileName(FormNode.EMPTY);
            }
        }
    }
}
