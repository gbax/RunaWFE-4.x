package ru.runa.gpd.formeditor.ftl.validation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.runa.gpd.formeditor.ftl.Component;
import ru.runa.gpd.formeditor.ftl.ComponentType;
import ru.runa.gpd.formeditor.ftl.ComponentTypeRegistry;
import ru.runa.gpd.lang.model.ProcessDefinition;
import ru.runa.gpd.lang.model.Variable;
import ru.runa.gpd.util.VariableUtils;

import com.google.common.collect.Lists;

import freemarker.template.SimpleHash;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class ValidationHashModel extends SimpleHash {
    private static final long serialVersionUID = 1L;
    private final List<Component> components = Lists.newArrayList();
    private final ProcessDefinition definition;
    private boolean stageRenderingParams = false;

    public ValidationHashModel(ProcessDefinition definition) {
        this.definition = definition;
    }

    public List<Component> getComponents() {
        return components;
    }

    private TemplateModel wrapParameter(Variable variable) throws TemplateModelException {
        if (variable.getUserType() != null) {
            Map<String, TemplateModel> properties = new HashMap<String, TemplateModel>();
            for (Variable attribute : variable.getUserType().getAttributes()) {
                properties.put(attribute.getName(), wrapParameter(attribute));
            }
            return new SimpleHash(properties);
        }
        return wrap("var");
    }

    @Override
    public TemplateModel get(String key) throws TemplateModelException {
        // add output variables / read access
        Variable variable = VariableUtils.getVariableByName(definition, key);
        if (variable != null) {
            if (stageRenderingParams) {
                return wrapParameter(variable);
            }
            return new SimpleScalar("${" + variable.getName() + "}");
        }
        if (ComponentTypeRegistry.has(key)) {
            stageRenderingParams = true;
            return new ValidationMethodModel(ComponentTypeRegistry.getNotNull(key));
        }
        return new UndefinedMethodModel();
    }

    private class ValidationMethodModel implements TemplateMethodModel {
        private final ComponentType componentType;

        public ValidationMethodModel(ComponentType componentType) {
            this.componentType = componentType;
        }

        @Override
        public Object exec(List args) throws TemplateModelException {
            stageRenderingParams = false;
            Component component = new Component(componentType, 0);
            component.setRawParameters(args);
            components.add(component);
            return "noop";
        }
    }

    private class UndefinedMethodModel implements TemplateMethodModel {

        @Override
        public Object exec(List args) throws TemplateModelException {
            return "noop";
        }
    }

}
