package ru.runa.gpd.formeditor.ftl.conv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.runa.gpd.form.FormVariableAccess;
import ru.runa.gpd.formeditor.ftl.ComponentParameter;
import ru.runa.gpd.formeditor.ftl.ComponentType;
import ru.runa.gpd.formeditor.ftl.ComponentTypeRegistry;
import ru.runa.gpd.formeditor.ftl.VariableAccess;
import ru.runa.gpd.formeditor.ftl.parameter.RichComboParameter;
import ru.runa.gpd.lang.model.ProcessDefinition;
import ru.runa.gpd.lang.model.Variable;
import ru.runa.gpd.util.VariableUtils;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import freemarker.template.SimpleHash;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class VariableSearchHashModel extends SimpleHash {
    private static final long serialVersionUID = 1L;
    private static final String VAR_VALUE_PLC = "var";
    private final Map<String, FormVariableAccess> usedVariables = Maps.newHashMap();
    private final ProcessDefinition definition;
    private boolean stageRenderingParams = false;

    public VariableSearchHashModel(ProcessDefinition definition) {
        this.definition = definition;
    }

    public Map<String, FormVariableAccess> getUsedVariables() {
        return usedVariables;
    }

    private TemplateModel wrapParameter(Variable variable) throws TemplateModelException {
        if (variable.getUserType() != null) {
            Map<String, TemplateModel> properties = new HashMap<String, TemplateModel>();
            for (Variable attribute : variable.getUserType().getAttributes()) {
                properties.put(attribute.getName(), wrapParameter(attribute));
            }
            return new SimpleHash(properties);
        }
        return wrap(VAR_VALUE_PLC);
    }

    @Override
    public TemplateModel get(String key) throws TemplateModelException {
        // add output variables / read access
        Variable variable = VariableUtils.getVariableByName(definition, key);
        if (variable != null) {
            if (!usedVariables.containsKey(key)) {
                usedVariables.put(key, FormVariableAccess.READ);
            }
            if (stageRenderingParams) {
                return wrapParameter(variable);
            }
            return new SimpleScalar("${" + variable.getName() + "}");
        }
        if (ComponentTypeRegistry.has(key)) {
            stageRenderingParams = true;
            return new ValidationMethodModel(ComponentTypeRegistry.getNotNull(key));
        }
        usedVariables.put(key, FormVariableAccess.DOUBTFUL);
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
            for (int i = 0; i < args.size(); i++) {
                String arg = (String) args.get(i);
                if (Strings.isNullOrEmpty(arg)) {
                    continue;
                }
                ComponentParameter parameter = componentType.getParameterOrLastNotNull(i);
                if (parameter.getVariableAccess() == VariableAccess.WRITE) {
                    usedVariables.put(arg, FormVariableAccess.WRITE);
                } else if (parameter.getVariableAccess() == VariableAccess.READ) {
                    if (!VAR_VALUE_PLC.equals(arg) && !usedVariables.containsKey(arg) && !arg.startsWith(RichComboParameter.VALUE_PREFIX)) {
                        usedVariables.put(arg, FormVariableAccess.READ);
                    }
                }
            }
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
