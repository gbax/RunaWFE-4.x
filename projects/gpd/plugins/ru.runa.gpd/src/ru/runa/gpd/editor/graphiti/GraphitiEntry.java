package ru.runa.gpd.editor.graphiti;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IFeature;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IUpdateFeature;

import ru.runa.gpd.editor.Entry;
import ru.runa.gpd.editor.graphiti.add.AddElementFeature;
import ru.runa.gpd.editor.graphiti.add.AddTransitionFeature;
import ru.runa.gpd.editor.graphiti.create.CreateElementFeature;
import ru.runa.gpd.editor.graphiti.create.CreateTransitionFeature;
import ru.runa.gpd.editor.graphiti.layout.ElementLayoutFeature;
import ru.runa.gpd.editor.graphiti.update.UpdateFeature;
import ru.runa.gpd.lang.NodeTypeDefinition;

public class GraphitiEntry extends Entry {

    public GraphitiEntry(NodeTypeDefinition nodeTypeDefinition, IConfigurationElement element) {
        super(nodeTypeDefinition, element);
    }

    public IFeature createCreateFeature(DiagramFeatureProvider provider) {
        IFeature feature = createExecutableExtension("create");
        if (feature instanceof CreateElementFeature) {
            CreateElementFeature createElementFeature = (CreateElementFeature) feature;
            createElementFeature.setFeatureProvider(provider);
            createElementFeature.setNodeDefinition(nodeTypeDefinition);
        }
        if (feature instanceof CreateTransitionFeature) {
            ((CreateTransitionFeature) feature).setFeatureProvider(provider);
        }
        return feature;
    }

    public IAddFeature createAddFeature(DiagramFeatureProvider provider) {
        IAddFeature feature = createExecutableExtension("add");
        if (feature instanceof AddElementFeature) {
            ((AddElementFeature) feature).setFeatureProvider(provider);
        }
        if (feature instanceof AddTransitionFeature) {
            ((AddTransitionFeature) feature).setFeatureProvider(provider);
        }
        return feature;
    }

    public IUpdateFeature createUpdateFeature(DiagramFeatureProvider provider) {
        IUpdateFeature feature = createExecutableExtension("update");
        if (feature instanceof UpdateFeature) {
            UpdateFeature updateFeature = (UpdateFeature) feature;
            updateFeature.setFeatureProvider(provider);
        }
        return feature;
    }

    public ILayoutFeature createLayoutFeature(DiagramFeatureProvider provider) {
        ILayoutFeature feature = createExecutableExtension("layout");
        if (feature instanceof ElementLayoutFeature) {
            ((ElementLayoutFeature) feature).setFeatureProvider(provider);
        }
        return feature;
    }

}
