package ru.runa.gpd.lang.action;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;

import ru.runa.gpd.Localization;
import ru.runa.gpd.PluginLogger;
import ru.runa.gpd.lang.model.FormNode;
import ru.runa.gpd.ui.custom.Dialogs;
import ru.runa.gpd.util.IOUtils;
import ru.runa.gpd.util.ValidationUtil;
import ru.runa.gpd.validation.ValidatorConfig;

public class OpenFormValidationDelegate extends FormDelegate {
    @Override
    public void run(IAction action) {
        try {
            FormNode formNode = getSelection();
            if (!formNode.hasFormValidation()) {
                if (!Dialogs.confirm(Localization.getString("OpenFormValidationDelegate.CreateEmptyValidation"))) {
                    return;
                }
                String fileName = formNode.getId() + "." + FormNode.VALIDATION_SUFFIX;
                IFile file = ValidationUtil.rewriteValidation(getDefinitionFile(), fileName, new HashMap<String, Map<String, ValidatorConfig>>());
                setNewValidationFormFile(formNode, file.getName());
            }
            IFile validationFile = IOUtils.getAdjacentFile(getDefinitionFile(), formNode.getValidationFileName());
            if (!validationFile.exists()) {
                ValidationUtil.createEmptyValidation(getDefinitionFile(), formNode.getValidationFileName());
            }
            openValidationFile(formNode, validationFile);
        } catch (Exception e) {
            PluginLogger.logError(e);
        }
    }

}
