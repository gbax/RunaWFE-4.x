package ru.runa.gpd.lang.model;

import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.ui.services.GraphitiUi;

import ru.runa.gpd.editor.graphiti.UIContainer;

public class StartTextDecoration extends TextDecorationNode {

    @Override
    public StartState getTarget() {
        return (StartState) target;
    }

    public class StartDefinitionUI implements UIContainer {

        private PictogramElement owner;
        private Rectangle box;
        private Text name;
        private Text swimlane;

        public StartDefinitionUI(PictogramElement owner, Rectangle box, Text name, Text swimlane) {
            this.owner = owner;
            this.box = box;
            this.name = name;
            this.swimlane = swimlane;
            pack();
        }

        @Override
        public void pack() {
            IGaService gaService = Graphiti.getGaService();
            StartState node = (StartState) target;
            String labelName = node.getName();
            String labelSwimline = new String();
            if (node.getSwimlaneLabel() != null) {
                labelSwimline = node.getSwimlaneLabel();
            }

            IDimension swimlineDim = GraphitiUi.getUiLayoutService().calculateTextSize(labelSwimline, swimlane.getFont());
            int maxRectWidth = swimlineDim.getWidth();
            swimlane.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);

            IDimension nameDim = GraphitiUi.getUiLayoutService().calculateTextSize(labelName, name.getFont());
            maxRectWidth = Math.max(nameDim.getWidth(), maxRectWidth);
            name.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
            gaService.setLocationAndSize(name, 0, swimlineDim.getHeight(), maxRectWidth, nameDim.getHeight());

            // place swimline text
            gaService.setLocationAndSize(swimlane, 0, 0, maxRectWidth, swimlineDim.getHeight());

            // set container size as sum of image and texts dimensions
            int totalTextHeight = swimlineDim.getHeight() + nameDim.getHeight();
            gaService.setSize(box, maxRectWidth, totalTextHeight);
        }

        @Override
        public void update() {
            StartState node = (StartState) target;
            name.setValue(node.getName());
            swimlane.setValue(node.getSwimlaneLabel());

            // fit definition size for new labels
            int oldWidth = box.getWidth();
            IDimension swimlineDim = GraphitiUi.getUiLayoutService().calculateTextSize(node.getSwimlaneLabel(), swimlane.getFont());
            IDimension nameDim = GraphitiUi.getUiLayoutService().calculateTextSize(node.getName(), name.getFont());
            int maxWidth = Math.max(swimlineDim.getWidth(), nameDim.getWidth());
            box.setWidth(maxWidth);
            box.setX(box.getX() + (oldWidth - maxWidth) / 2);
            name.setWidth(maxWidth);
            swimlane.setWidth(maxWidth);

            getConstraint().setWidth(box.getWidth());
            getConstraint().setX(box.getX());
        }

        @Override
        public PictogramElement getOwner() {
            return owner;
        }

        public String getName() {
            return name.getValue();
        }

        public String getSwimlaneName() {
            return swimlane.getValue();
        }
    }

}
