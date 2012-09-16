package ru.runa.jbpm.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import ru.runa.bpm.ui.util.ProjectFinder;
import ru.runa.bpm.ui.util.WorkspaceOperations;

public class ImportAction extends BaseActionDelegate {

    public void run(IAction action) {
        WorkspaceOperations.importProcessDefinition(getStructuredSelection());
    }

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        action.setEnabled(ProjectFinder.getAllProjects().length > 0);
    }

}
