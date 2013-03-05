package ru.runa.wfe.var;

import ru.runa.wfe.var.dto.WfVariable;

public class EmptyVariableProvider extends AbstractVariableProvider {

    @Override
    public Long getProcessId() {
        return null;
    }

    @Override
    public Object getValue(String variableName) {
        return null;
    }

    @Override
    public WfVariable getVariable(String variableName) {
        return null;
    }

}
