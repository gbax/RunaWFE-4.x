package ru.runa.gpd.editor.gef.command;

import org.eclipse.gef.commands.Command;

import ru.runa.gpd.lang.model.Node;
import ru.runa.gpd.lang.model.PropertyNames;
import ru.runa.gpd.lang.model.Transition;

public class TransitionDeleteCommand extends Command {
    private Transition transition;

    private Node source;

    public void setTransition(Transition transition) {
        this.transition = transition;
    }

    @Override
    public boolean canExecute() {
        return transition != null;
    }

    @Override
    public void execute() {
        source = transition.getSource();
        source.removeLeavingTransition(transition);
    }

    @Override
    public void undo() {
        if (!source.getLeavingTransitions().contains(transition)) {
            source.addLeavingTransition(transition);
        }
        // to refresh visuals in
        // NodeGraphicalEditPart.propertyChange(PropertyChangeEvent)
        transition.getTarget().firePropertyChange(PropertyNames.NODE_ARRIVING_TRANSITION_ADDED, null, transition);
    }
}
