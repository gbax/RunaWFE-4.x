package ru.runa.bpm.ui.common.command;

import org.eclipse.gef.commands.Command;
import ru.runa.bpm.ui.common.model.Action;
import ru.runa.bpm.ui.common.model.Active;

public class ActionDeleteCommand extends Command {
    private Action action;
    private Active parent;
    private int index;

    @Override
    public void execute() {
        parent = (Active) action.getParent();
        index = parent.removeAction(action);
    }

    @Override
    public void undo() {
        parent.addAction(action, index);
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
