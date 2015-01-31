package ru.runa.gpd.formeditor.ftl.parameter;

import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import ru.runa.gpd.formeditor.ftl.ui.VariableListDialog;

public class VariableListPropertyDescriptor extends PropertyDescriptor {
    private final List<String> variableNames;

    public VariableListPropertyDescriptor(Object id, String displayName, List<String> variableNames) {
        super(id, displayName);
        this.variableNames = variableNames;
    }

    @Override
    public CellEditor createPropertyEditor(Composite parent) {
        return new VariableListCellEditor(parent);
    }

    private class VariableListCellEditor extends DialogCellEditor {

        public VariableListCellEditor(Composite parent) {
            super(parent, SWT.NONE);
        }

        @Override
        protected Object openDialogBox(Control cellEditorWindow) {
            List<String> value = (List<String>) doGetValue();
            VariableListDialog dialog = new VariableListDialog(variableNames, value);
            return dialog.openDialog();
        }

    }

}
