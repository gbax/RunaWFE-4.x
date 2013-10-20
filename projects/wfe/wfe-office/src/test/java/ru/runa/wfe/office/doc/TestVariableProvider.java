package ru.runa.wfe.office.doc;

import java.util.List;
import java.util.Map;

import ru.runa.wfe.var.AbstractVariableProvider;
import ru.runa.wfe.var.VariableDefinition;
import ru.runa.wfe.var.dto.WfVariable;
import ru.runa.wfe.var.format.ListFormat;
import ru.runa.wfe.var.format.LongFormat;
import ru.runa.wfe.var.format.MapFormat;
import ru.runa.wfe.var.format.StringFormat;

import com.google.common.collect.Maps;

public class TestVariableProvider extends AbstractVariableProvider {
    private final Map<String, WfVariable> variables = Maps.newHashMap();

    public TestVariableProvider(Map<String, Object> values) {
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            if (entry.getValue() instanceof WfVariable) {
                this.variables.put(entry.getKey(), (WfVariable) entry.getValue());
            } else {
                VariableDefinition definition = new VariableDefinition(true, entry.getKey(), entry.getKey());
                if (entry.getValue() instanceof Long) {
                    definition.setFormat(LongFormat.class.getName());
//                } else if (entry.getValue() instanceof Map) {
//                    definition.setFormat(MapFormat.class.getName());
//                } else if (entry.getValue() instanceof List) {
//                    definition.setFormat(ListFormat.class.getName());
                } else {
                    definition.setFormat(StringFormat.class.getName());
                }
                WfVariable variable = new WfVariable(definition, entry.getValue());
                this.variables.put(entry.getKey(), variable);
            }
        }
    }

    public Long getProcessId() {
        throw new UnsupportedOperationException();
    }

    public Object getValue(String variableName) {
        WfVariable variable = getVariable(variableName);
        if (variable != null) {
            return variable.getValue();
        }
        return null;
    }

    public WfVariable getVariable(String variableName) {
        return variables.get(variableName);
    }

}
