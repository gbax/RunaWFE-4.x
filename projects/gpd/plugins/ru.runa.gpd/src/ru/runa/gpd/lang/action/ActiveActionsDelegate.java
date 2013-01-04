package ru.runa.gpd.lang.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import ru.runa.gpd.Localization;
import ru.runa.gpd.editor.gef.command.AddActionCommand;
import ru.runa.gpd.handler.DelegableProvider;
import ru.runa.gpd.handler.HandlerRegistry;
import ru.runa.gpd.lang.model.Active;

public class ActiveActionsDelegate extends BaseModelDropDownActionDelegate {
    private Active active;

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        super.selectionChanged(action, selection);
        active = (Active) getSelection();
    }

    /**
     * Fills the menu with applicable launch shortcuts
     * 
     * @param menu
     *            The menu to fill
     */
    @Override
    protected void fillMenu(Menu menu) {
        boolean createSeparator = false;
        for (ru.runa.gpd.lang.model.Action action : active.getActions()) {
            Action menuAction = new ShowAction(action);
            menuAction.setText(action.getLabel());
            ActionContributionItem item = new ActionContributionItem(menuAction);
            item.fill(menu, -1);
            createSeparator = true;
        }
        if (createSeparator) {
            new MenuItem(menu, SWT.SEPARATOR);
        }
        Action menuAction = new AddActionAction();
        ActionContributionItem item = new ActionContributionItem(menuAction);
        item.fill(menu, -1);
    }

    public class AddActionAction extends Action {
        public AddActionAction() {
            setText(Localization.getString("button.create"));
        }

        @Override
        public void run() {
            AddActionCommand command = new AddActionCommand();
            command.setTarget(active);
            executeCommand(command);
            setFocus(command.getAction());
        }
    }

    private void setFocus(ru.runa.gpd.lang.model.Action action) {
        getActiveDesignerEditor().select(action);
    }

    public class ShowAction extends Action {
        private ru.runa.gpd.lang.model.Action action;

        public ShowAction(ru.runa.gpd.lang.model.Action action) {
            this.action = action;
        }

        @Override
        public void run() {
            setFocus(action);
            DelegableProvider provider = HandlerRegistry.getProvider(action.getDelegationClassName());
            String newConfig = provider.showConfigurationDialog(action);
            if (newConfig != null) {
                action.setDelegationConfiguration(newConfig);
            }
        }
    }
}
