package ru.runa.gpd.editor.graphiti.update;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.impl.DeleteContext;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;

import ru.runa.gpd.editor.graphiti.HasTextDecorator;
import ru.runa.gpd.lang.model.GraphElement;
import ru.runa.gpd.lang.model.Node;
import ru.runa.gpd.lang.model.Transition;

public class DeleteElementFeature extends DefaultDeleteFeature {
    public DeleteElementFeature(IFeatureProvider provider) {
        super(provider);
    }

    @Override
    protected boolean getUserDecision(IDeleteContext context) {
        return true;
    }

    @Override
    protected void deleteBusinessObject(Object bo) {
        GraphElement element = (GraphElement) bo;
        if (element instanceof HasTextDecorator) {
            HasTextDecorator withDefinition = (HasTextDecorator) element;
            IDeleteContext delContext = new DeleteContext(withDefinition.getTextDecoratorEmulation().getDefinition().getUiContainer().getOwner());
            delete(delContext);
        }
        if (element instanceof Node) {
            Node node = (Node) element;
            for (Transition transition : node.getLeavingTransitions()) {
                transition.getSource().removeLeavingTransition(transition);
            }
            for (Transition transition : node.getArrivingTransitions()) {
                transition.getSource().removeLeavingTransition(transition);
            }
        }
        if (element instanceof Transition) {
            Transition transition = (Transition) element;
            transition.getSource().removeLeavingTransition(transition);
        } else {
            element.getParent().removeChild(element);
        }
    }
}
