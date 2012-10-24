package org.jbpm.ui.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jbpm.ui.resource.Messages;
import org.jbpm.ui.util.VariableMapping;

public class MessageVariableDialog extends Dialog {

    private final List<String> processVariables;
    private final boolean usageSelector;
    private final VariableMapping oldMapping;
    private String variable = "";
    private String alias = "";

    private Text aliasText;

    protected MessageVariableDialog(List<String> processVariables, boolean usageSelector, VariableMapping oldMapping) {
        super(Display.getCurrent().getActiveShell());
        this.processVariables = processVariables;
        this.usageSelector = usageSelector;
        this.oldMapping = oldMapping;
        if (oldMapping != null) {
            this.variable = oldMapping.getProcessVariable();
            this.alias = oldMapping.getSubprocessVariable();
        }
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        String message = oldMapping != null ? Messages.getString("button.update") : Messages.getString("button.create");
        newShell.setText(message);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite area = (Composite) super.createDialogArea(parent);
        GridLayout layout = new GridLayout(1, false);
        area.setLayout(layout);

        final Composite composite = new Composite(area, SWT.NONE);
        final GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        composite.setLayout(gridLayout);
        composite.setLayoutData(new GridData());

        Label labelProcessVariable = new Label(composite, SWT.NONE);
        labelProcessVariable.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
        labelProcessVariable.setText(Messages.getString(usageSelector ? "property.name" : "MessageNodeDialog.VariableName") + ":");
        if (usageSelector) {
            final Text variableText = new Text(composite, SWT.BORDER);
            GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
            gridData.minimumWidth = 200;
            variableText.setLayoutData(gridData);
            variableText.setText(getVariable());

            variableText.addModifyListener(new ModifyListener() {

                public void modifyText(ModifyEvent e) {
                    variable = variableText.getText();
                }

            });
        } else {
            final Combo variableCombo = new Combo(composite, SWT.BORDER);
            GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
            gridData.minimumWidth = 200;
            variableCombo.setItems(processVariables.toArray(new String[processVariables.size()]));
            variableCombo.setLayoutData(gridData);
            variableCombo.setText(getVariable());
            variableCombo.addModifyListener(new ModifyListener() {

                public void modifyText(ModifyEvent e) {
                    variable = variableCombo.getText();
                    aliasText.setText(variable);
                }
            });
        }

        Label labelSubprocessVariable = new Label(composite, SWT.NONE);
        labelSubprocessVariable.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
        labelSubprocessVariable.setText(Messages.getString(usageSelector ? "property.value" : "MessageNodeDialog.Alias") + ":");
        aliasText = new Text(composite, SWT.BORDER);
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.minimumWidth = 200;
        aliasText.setLayoutData(gridData);
        aliasText.setText(getAlias());

        aliasText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                alias = aliasText.getText();
            }
        });

        return area;
    }

    public String getVariable() {
        return variable;
    }

    public String getAlias() {
        return alias;
    }
}
