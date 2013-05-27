package ru.runa.gpd.editor.gef.part.graph;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import ru.runa.gpd.editor.gef.figure.NodeFigure;
import ru.runa.gpd.lang.model.GraphElement;
import ru.runa.gpd.lang.model.PropertyNames;

import com.google.common.collect.Lists;

public abstract class ElementGraphicalEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener, PropertyNames {
    private final List<String> propertyNamesToCauseFigureUpdate = Lists.newArrayList();
    private final List<String> propertyNamesToCauseFigureResize = Lists.newArrayList();

    public ElementGraphicalEditPart() {
        fillFigureUpdatePropertyNames(propertyNamesToCauseFigureUpdate);
        fillFigureResizePropertyNames(propertyNamesToCauseFigureResize);
    }

    protected void fillFigureUpdatePropertyNames(List<String> list) {
    }

    protected void fillFigureResizePropertyNames(List<String> list) {
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (propertyNamesToCauseFigureResize.contains(event.getPropertyName())) {
            figure.setBounds(figure.getBounds());
        }
        if (propertyNamesToCauseFigureUpdate.contains(event.getPropertyName())) {
            IFigure figure = getFigure();
            if (figure instanceof NodeFigure) {
                ((NodeFigure) figure).update();
            }
        }
    }

    @Override
    public GraphElement getModel() {
        return (GraphElement) super.getModel();
    }

    @Override
    protected IFigure createFigure() {
        IFigure figure = getModel().getTypeDefinition().getGefEntry().createFigure(getModel().getProcessDefinition());
        if (figure instanceof NodeFigure) {
            NodeFigure nodeFigure = (NodeFigure) figure;
            nodeFigure.setModel(getModel());
            nodeFigure.update();
        }
        return figure;
    }

    @Override
    public void activate() {
        if (!isActive()) {
            getModel().addPropertyChangeListener(this);
            super.activate();
        }
    }

    @Override
    public void deactivate() {
        if (isActive()) {
            getModel().removePropertyChangeListener(this);
            super.deactivate();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getAdapter(Class key) {
        if (GraphElement.class.isAssignableFrom(key)) {
            GraphElement element = getModel();
            if (key.isAssignableFrom(element.getClass())) {
                return element;
            }
        }
        return super.getAdapter(key);
    }

    public String getAssociatedViewId() {
        return null; // PropertiesView.ID;
    }
}
