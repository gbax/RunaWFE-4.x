package ru.runa.gpd.lang.action;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import ru.runa.gpd.Localization;
import ru.runa.gpd.lang.model.MultiTaskState;
import ru.runa.gpd.lang.model.ProcessDefinition;
import ru.runa.gpd.lang.model.Variable;
import ru.runa.gpd.ui.wizard.CompactWizardDialog;
import ru.runa.gpd.ui.wizard.VariableWizard;
import ru.runa.wfe.var.format.ListFormat;

import com.google.common.base.Objects;

public class MultiTaskExecutorsActionsDelegate extends BaseModelDropDownActionDelegate {
    private String selectedVariable;
    private ProcessDefinition currentDefinition;
    private MultiTaskState multiTaskState;

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        super.selectionChanged(action, selection);
        multiTaskState = getSelection();
        if (multiTaskState != null) {
            selectedVariable = multiTaskState.getExecutorsVariableName();
            currentDefinition = multiTaskState.getProcessDefinition();
        }
    }

    /**
     * Fills the menu with applicable launch shortcuts
     * 
     * @param menu
     *            The menu to fill
     */
    @Override
    protected void fillMenu(Menu menu) {
        for (Variable variable : currentDefinition.getVariables(true, false, List.class.getName())) {
            Action action = new SetVariableAction();
            action.setText(variable.getName());
            if (Objects.equal(selectedVariable, variable.getName())) {
                action.setChecked(true);
            }
            ActionContributionItem item = new ActionContributionItem(action);
            item.fill(menu, -1);
        }
        new MenuItem(menu, SWT.SEPARATOR);
        Action action;
        ActionContributionItem item;
        {
            action = new CreateVariableAction();
            item = new ActionContributionItem(action);
            item.fill(menu, -1);
        }
        if (selectedVariable != null) {
            action = new ClearVariableAction();
            item = new ActionContributionItem(action);
            item.fill(menu, -1);
        }
    }

    private void createVariable() {
        Variable typedVariable = new Variable();
        typedVariable.setFormat(ListFormat.class.getName());
        VariableWizard wizard = new VariableWizard(currentDefinition, typedVariable, true, false);
        CompactWizardDialog dialog = new CompactWizardDialog(wizard);
        if (dialog.open() == Window.OK) {
            Variable variable = wizard.getVariable();
            currentDefinition.addChild(variable);
            setVariableName(variable.getName());
        }
    }

    private void setVariableName(String variableName) {
        multiTaskState.setExecutorsVariableName(variableName);
    }

    public class SetVariableAction extends Action {
        @Override
        public void run() {
            setVariableName(getText());
        }
    }

    public class CreateVariableAction extends Action {
        public CreateVariableAction() {
            setText(Localization.getString("label.action.createExecutorsVariable"));
        }

        @Override
        public void run() {
            createVariable();
        }
    }

    public class ClearVariableAction extends Action {
        public ClearVariableAction() {
            setText(Localization.getString("label.action.clearExecutors"));
        }

        @Override
        public void run() {
            setVariableName(null);
        }
    }
}
