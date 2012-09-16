package ru.runa.bpm.ui.properties;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import ru.runa.bpm.ui.dialog.ChooseHandlerClassDialog;

public class DelegableClassPropertyDescriptor extends PropertyDescriptor {
    private String type;

    public DelegableClassPropertyDescriptor(Object id, String displayName, String type) {
        super(id, displayName);
        this.type = type;
        setLabelProvider(new MappingLabelProvider());
    }

    @Override
    public CellEditor createPropertyEditor(Composite parent) {
        return new ChooseClassDialogCellEditor(parent);
    }

    private class ChooseClassDialogCellEditor extends DialogCellEditor {

        public ChooseClassDialogCellEditor(Composite parent) {
            super(parent, SWT.NONE);
        }

        @Override
        protected Object openDialogBox(Control cellEditorWindow) {
            ChooseHandlerClassDialog dialog = new ChooseHandlerClassDialog(type);
            return dialog.openDialog();
        }

        @Override
        protected void updateContents(Object value) {
            if (getDefaultLabel() != null) {
                getDefaultLabel().setText(getLabelProvider().getText(value));
            }
        }
    }
}
