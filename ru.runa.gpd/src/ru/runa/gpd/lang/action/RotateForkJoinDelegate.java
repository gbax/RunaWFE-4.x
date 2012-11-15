package ru.runa.gpd.lang.action;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.action.IAction;

import ru.runa.gpd.lang.model.Node;

public class RotateForkJoinDelegate extends BaseActionDelegate {

    public void run(IAction action) {
        Node node = (Node) selectedPart.getModel();
        Rectangle oldConstraint = node.getConstraint();
        Rectangle newConstraint = oldConstraint.getCopy();
        newConstraint.width = oldConstraint.height;
        newConstraint.height = oldConstraint.width;
        node.setConstraint(newConstraint);
    }

}
