package ru.runa.gpd.editor.graphiti.add;

import org.eclipse.graphiti.features.context.IAddContext;

import ru.runa.gpd.lang.model.Node;
import ru.runa.gpd.lang.model.ProcessDefinition;
import ru.runa.gpd.lang.model.Swimlane;

public abstract class AddNodeFeature extends AddElementFeature {
    @Override
    public boolean canAdd(IAddContext context) {
        if (context.getNewObject() instanceof Node) {
            Object parentObject = getBusinessObjectForPictogramElement(context.getTargetContainer());
            return (parentObject instanceof ProcessDefinition || parentObject instanceof Swimlane);
        }
        return false;
    }
}
