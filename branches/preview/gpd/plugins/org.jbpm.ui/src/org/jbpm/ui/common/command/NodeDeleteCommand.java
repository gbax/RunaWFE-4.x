package ru.runa.bpm.ui.common.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.commands.Command;
import ru.runa.bpm.ui.common.model.Node;
import ru.runa.bpm.ui.common.model.ProcessDefinition;
import ru.runa.bpm.ui.common.model.Transition;

public class NodeDeleteCommand extends Command {

    private Node node;

    private ProcessDefinition definition;

    private Map<Transition, Node> transitionSources;

    public void setNode(Node node) {
        this.node = node;
    }

    private void detachTransitions(List<Transition> transitions) {
    	for (Transition transition : transitions) {
            detachTransition(transition);
		}
    }

    private void reattachTransitions() {
        if (transitionSources != null) {
            for (Transition transition : transitionSources.keySet()) {
                reattachTransition(transition);
            }
        }
    }

    private void detachTransition(Transition transition) {
        transitionSources.put(transition, transition.getSource());
        transition.getSource().removeLeavingTransition(transition);
    }

    private void reattachTransition(Transition transition) {
        Node source = transitionSources.get(transition);
        source.addLeavingTransition(transition);
    }

    @Override
    public void execute() {
        definition = node.getProcessDefinition();
        transitionSources = new HashMap<Transition, Node>();
        detachTransitions(node.getLeavingTransitions());
        detachTransitions(node.getArrivingTransitions());
        definition.removeChild(node);
    }

    @Override
    public void undo() {
        definition.addChild(node);
        reattachTransitions();
    }

}
