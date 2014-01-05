package ru.runa.gpd.lang.model;

import java.util.List;

import org.eclipse.core.resources.IFile;

import ru.runa.gpd.lang.ValidationError;
import ru.runa.gpd.util.VariableMapping;

public class MultiSubprocess extends Subprocess implements IMultiInstancesContainer {
    @Override
    public void validate(List<ValidationError> errors, IFile definitionFile) {
        super.validate(errors, definitionFile);
        boolean readMultiinstanceLinkExists = false;
        boolean needTest = true;
        for (VariableMapping variableMapping : variableMappings) {
            if (VariableMapping.USAGE_MULTIINSTANCE_VARS.equals(variableMapping.getUsage()) && variableMapping.getProcessVariableName().equals("typeMultiInstance")) {
                needTest = false;
            }
            if (variableMapping.getUsage().contains(VariableMapping.USAGE_MULTIINSTANCE_LINK) && variableMapping.getUsage().contains(VariableMapping.USAGE_READ)
                    && !variableMapping.getUsage().contains(VariableMapping.USAGE_WRITE)) {
                readMultiinstanceLinkExists = true;
            }
        }
        if (needTest && !readMultiinstanceLinkExists) {
            errors.add(ValidationError.createLocalizedError(this, "multiinstance.noMultiinstanceLink"));
            return;
        }
    }

    @Override
    protected boolean isCompatibleVariables(Variable variable1, Variable variable2) {
        if (List.class.getName().equals(variable1.getJavaClassName())) {
            return true;
        }
        return super.isCompatibleVariables(variable1, variable2);
    }
}
