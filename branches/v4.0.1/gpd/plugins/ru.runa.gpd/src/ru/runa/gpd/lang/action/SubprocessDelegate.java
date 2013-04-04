package ru.runa.gpd.lang.action;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.window.Window;

import ru.runa.gpd.lang.model.MultiSubprocess;
import ru.runa.gpd.lang.model.Subprocess;
import ru.runa.gpd.ui.dialog.MultiInstanceDialog;
import ru.runa.gpd.ui.dialog.SubprocessDialog;

public class SubprocessDelegate extends BaseModelActionDelegate {
    @Override
    public void run(IAction action) {
        Subprocess subprocess = getSelection();
        openDetails(subprocess);
    }

    public void openDetails(Subprocess subprocess) {
        if (subprocess instanceof MultiSubprocess) {
            MultiSubprocess multiSubprocess = (MultiSubprocess) subprocess;
            MultiInstanceDialog dialog = new MultiInstanceDialog(multiSubprocess);
            if (dialog.open() != Window.CANCEL) {
                multiSubprocess.setVariableMappings(dialog.getSubprocessVariables());
                multiSubprocess.setSubProcessName(dialog.getSubprocessName());
            }
        } else {
            SubprocessDialog dialog = new SubprocessDialog(subprocess);
            if (dialog.open() != Window.CANCEL) {
                subprocess.setVariableMappings(dialog.getSubprocessVariables());
                subprocess.setSubProcessName(dialog.getSubprocessName());
            }
        }
    }
}
