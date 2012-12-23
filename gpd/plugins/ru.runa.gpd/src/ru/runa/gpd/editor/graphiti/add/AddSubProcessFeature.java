package ru.runa.gpd.editor.graphiti.add;

import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.services.Graphiti;

import ru.runa.gpd.editor.graphiti.GaProperty;
import ru.runa.gpd.lang.model.State;

public class AddSubProcessFeature extends AddStateNodeFeature {
    @Override
    protected void addCustomGraphics(State state, IAddContext context, GraphicsAlgorithmContainer container) {
        Image image = Graphiti.getGaService().createImage(container, "graph/subprocess.png");
        image.getProperties().add(new GaProperty(GaProperty.ID, GaProperty.SUBPROCESS));
        Graphiti.getGaService().setLocation(image, state.getConstraint().width / 2 - 7, state.getConstraint().height - 2 * GRID_SIZE);
    }
}
