package ru.runa.gpd.editor.graphiti.layout;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.services.Graphiti;

import ru.runa.gpd.editor.graphiti.GaProperty;
import ru.runa.gpd.editor.graphiti.PropertyUtil;
import ru.runa.gpd.lang.model.Node;
import ru.runa.gpd.lang.model.Synchronizable;

public class LayoutStateNodeFeature extends ElementLayoutFeature {
    public static final String MAIN_RECT = "mainRect";
    public static final String BORDER_RECT = "borderRect";

    @Override
    public boolean layout(ILayoutContext context) {
        ContainerShape containerShape = (ContainerShape) context.getPictogramElement();
        GraphicsAlgorithm ga = containerShape.getGraphicsAlgorithm();

        Node node = (Node) getBusinessObjectForPictogramElement(containerShape);
        if (node.isMinimizedView()) {
            GraphicsAlgorithm mainRectangle = PropertyUtil.findGaRecursiveByName(ga, MAIN_RECT);
            Graphiti.getGaService().setSize(mainRectangle, 3 * GRID_SIZE, 3 * GRID_SIZE);
            GraphicsAlgorithm borderRectangle = PropertyUtil.findGaRecursiveByName(ga, BORDER_RECT);
            Graphiti.getGaService().setLocationAndSize(borderRectangle, 0, 0, 3 * GRID_SIZE, 3 * GRID_SIZE);
            GraphicsAlgorithm swimlaneText = PropertyUtil.findGaRecursiveByName(ga, GaProperty.SWIMLANE_NAME);
            if (swimlaneText != null) {
                Graphiti.getGaService().setLocationAndSize(swimlaneText, 0, 0, 0, 0);
            }
            GraphicsAlgorithm nameText = PropertyUtil.findGaRecursiveByName(ga, GaProperty.NAME);
            if (nameText != null) {
                Graphiti.getGaService().setLocationAndSize(nameText, 0, 0, 0, 0);
            }
            GraphicsAlgorithm scriptImage = PropertyUtil.findGaRecursiveByName(ga, GaProperty.SCRIPT);
            if (scriptImage != null) {
                Graphiti.getGaService().setLocationAndSize(scriptImage, 10, 10, 16, 16);
            }
            return true;
        }

        Dimension bounds = adjustBounds(context);
        int borderWidth = bounds.width - GRID_SIZE;
        int borderHeight = bounds.height - GRID_SIZE;
        GraphicsAlgorithm mainRectangle = PropertyUtil.findGaRecursiveByName(ga, MAIN_RECT);
        Graphiti.getGaService().setSize(mainRectangle, bounds.width, bounds.height);
        GraphicsAlgorithm borderRectangle = PropertyUtil.findGaRecursiveByName(ga, BORDER_RECT);
        Graphiti.getGaService().setLocationAndSize(borderRectangle, GRID_SIZE / 2, GRID_SIZE / 2, borderWidth, borderHeight);
        GraphicsAlgorithm swimlaneText = PropertyUtil.findGaRecursiveByName(ga, GaProperty.SWIMLANE_NAME);
        if (swimlaneText != null) {
            Graphiti.getGaService().setLocationAndSize(swimlaneText, 0, 0, borderWidth, 2 * GRID_SIZE);
        }
        GraphicsAlgorithm nameText = PropertyUtil.findGaRecursiveByName(ga, GaProperty.NAME);
        Graphiti.getGaService().setLocationAndSize(nameText, 0, 2 * GRID_SIZE, borderWidth, borderHeight - 4 * GRID_SIZE);
        GraphicsAlgorithm subProcessImage = PropertyUtil.findGaRecursiveByName(ga, GaProperty.SUBPROCESS);
        if (subProcessImage != null) {
            Graphiti.getGaService().setLocationAndSize(subProcessImage, bounds.width / 2 - 7, bounds.height - 2 * GRID_SIZE, 14, 14);
        }
        GraphicsAlgorithm multiProcessImage = PropertyUtil.findGaRecursiveByName(ga, GaProperty.MULTIPROCESS);
        if (multiProcessImage != null) {
            Graphiti.getGaService().setLocationAndSize(multiProcessImage, bounds.width / 2 - 8, bounds.height - 2 * GRID_SIZE, 16, 12);
        }
        GraphicsAlgorithm scriptImage = PropertyUtil.findGaRecursiveByName(ga, GaProperty.SCRIPT);
        if (scriptImage != null) {
            Graphiti.getGaService().setLocationAndSize(scriptImage, GRID_SIZE, GRID_SIZE, 16, 16);
        }
        GraphicsAlgorithm asyncImage = PropertyUtil.findGaRecursiveByName(ga, GaProperty.ASYNC);
        if (asyncImage != null) {
            int size = ((Synchronizable) node).isAsync() ? 14 : 1;
            Graphiti.getGaService().setLocationAndSize(asyncImage, bounds.width - 2 * GRID_SIZE, bounds.height - 2 * GRID_SIZE, size, size);
        }
        return true;
    }
}
