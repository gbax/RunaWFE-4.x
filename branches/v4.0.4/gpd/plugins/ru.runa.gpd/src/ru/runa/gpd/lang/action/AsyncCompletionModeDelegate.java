package ru.runa.gpd.lang.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Menu;

import ru.runa.gpd.Localization;
import ru.runa.gpd.lang.model.Synchronizable;
import ru.runa.wfe.lang.AsyncCompletionMode;

import com.google.common.base.Objects;

public class AsyncCompletionModeDelegate extends BaseModelDropDownActionDelegate {
    private AsyncCompletionMode selectedMode;
    private Synchronizable synchronizable;

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        super.selectionChanged(action, selection);
        if (action.isEnabled()) {
            synchronizable = (Synchronizable) getSelection();
            action.setEnabled(synchronizable.isAsync());
            selectedMode = synchronizable.getAsyncCompletionMode();
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
        for (AsyncCompletionMode mode : AsyncCompletionMode.values()) {
            SetModeAction action = new SetModeAction(mode);
            if (Objects.equal(selectedMode, mode)) {
                action.setChecked(true);
            }
            ActionContributionItem item = new ActionContributionItem(action);
            item.fill(menu, -1);
        }
    }

    public class SetModeAction extends Action {
        private final AsyncCompletionMode mode;

        public SetModeAction(AsyncCompletionMode mode) {
            this.mode = mode;
            setText(Localization.getString("label.action.asyncCompletionMode." + mode.name()));
        }

        @Override
        public void run() {
            synchronizable.setAsyncCompletionMode(mode);
        }
    }
}
