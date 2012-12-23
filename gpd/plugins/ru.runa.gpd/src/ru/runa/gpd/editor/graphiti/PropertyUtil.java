package ru.runa.gpd.editor.graphiti;

import org.eclipse.graphiti.mm.Property;
import org.eclipse.graphiti.mm.PropertyContainer;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import com.google.common.base.Objects;

public class PropertyUtil {
    public static String getPropertyValue(PropertyContainer ga, String propertyName) {
        for (Property property : ga.getProperties()) {
            if (Objects.equal(propertyName, property.getKey())) {
                return property.getValue();
            }
        }
        return null;
    }

    public static String findTextValueRecursive(PictogramElement pe, String name) {
        GraphicsAlgorithm ga = findGaRecursiveByName(pe, name);
        if (ga instanceof Text) {
            return ((Text) ga).getValue();
        }
        if (ga instanceof MultiText) {
            return ((MultiText) ga).getValue();
        }
        return null;
    }

    public static <T extends GraphicsAlgorithm> T findGaRecursiveByName(PictogramElement pe, String name) {
        GraphicsAlgorithm ga = pe.getGraphicsAlgorithm();
        for (Property property : pe.getProperties()) {
            if (Objects.equal(GaProperty.ID, property.getKey()) && Objects.equal(name, property.getValue())) {
                return (T) ga;
            }
        }
        GraphicsAlgorithm result = findGaRecursiveByName(ga, name);
        if (result != null) {
            return (T) result;
        }
        if (pe instanceof ContainerShape) {
            for (Shape shape : ((ContainerShape) pe).getChildren()) {
                result = findGaRecursiveByName(shape, name);
                if (result != null) {
                    return (T) result;
                }
            }
        }
        if (pe instanceof Connection) {
            Connection connection = (Connection) pe;
            for (ConnectionDecorator connectionDecorator : connection.getConnectionDecorators()) {
                result = findGaRecursiveByName(connectionDecorator, name);
                if (result != null) {
                    return (T) result;
                }
            }
        }
        return null;
    }

    public static <T extends GraphicsAlgorithm> T findGaRecursiveByName(GraphicsAlgorithm ga, String name) {
        for (Property property : ga.getProperties()) {
            if (Objects.equal(GaProperty.ID, property.getKey()) && Objects.equal(name, property.getValue())) {
                return (T) ga;
            }
        }
        for (GraphicsAlgorithm childGa : ga.getGraphicsAlgorithmChildren()) {
            GraphicsAlgorithm result = findGaRecursiveByName(childGa, name);
            if (result != null) {
                return (T) result;
            }
        }
        return null;
    }

    public static void setProperty(PictogramElement pe, String name, String value) {
        GraphicsAlgorithm ga = findGaRecursiveByName(pe, name);
        if (ga instanceof Text) {
            ((Text) ga).setValue(value);
        }
        if (ga instanceof MultiText) {
            ((MultiText) ga).setValue(value);
        }
    }
}
