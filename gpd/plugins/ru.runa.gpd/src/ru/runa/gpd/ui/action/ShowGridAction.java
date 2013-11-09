package ru.runa.gpd.ui.action;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;

import ru.runa.gpd.editor.ProcessEditorBase;
import ru.runa.gpd.lang.model.SubprocessDefinition;

public class ShowGridAction extends BaseActionDelegate {
    @Override
    public void run(IAction action) {
        ProcessEditorBase editor = getActiveDesignerEditor();
        editor.getDefinition().setShowGrid(!editor.getDefinition().isShowGrid());
    }

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        ProcessEditorBase editor = getActiveDesignerEditor();
        action.setChecked(editor != null && editor.getDefinition().isShowGrid());
        action.setEnabled(editor != null && !(editor.getDefinition() instanceof SubprocessDefinition));
    }
}
