package ru.runa.gpd.ui.dialog;

import java.util.Collections;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;

import ru.runa.gpd.Localization;

public class ChooseVariableNameDialog extends ChooseItemDialog {
    private final List<String> variableNames;

    public ChooseVariableNameDialog(List<String> variableNames) {
        super(Localization.getString("ChooseVariable.title"), Localization.getString("ChooseVariable.message"), true);
        this.variableNames = variableNames;
    }

    public String openDialog() {
        try {
            Collections.sort(variableNames);
            setItems(variableNames);
            if (open() != IDialogConstants.CANCEL_ID) {
                return (String) getSelectedItem();
            }
        } catch (Exception e) {
            // ignore this and return null;
        }
        return null;

    }

}
