package ru.runa.wfe.execution.logic;

import java.util.ArrayList;
import java.util.List;

import ru.runa.wfe.commons.ApplicationContextFactory;
import ru.runa.wfe.commons.TypeConversionUtil;
import ru.runa.wfe.commons.ftl.ExpressionEvaluator;
import ru.runa.wfe.extension.OrgFunction;
import ru.runa.wfe.extension.orgfunction.NullOrgFunction;
import ru.runa.wfe.user.Executor;
import ru.runa.wfe.var.IVariableProvider;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

public class OrgFunctionSwimlaneInitializer extends SwimlaneInitializer {
    private String orgFunctionClassName;
    private String[] parameterNames;

    @Override
    public void parse(String swimlaneConfiguration) {
        if (Strings.isNullOrEmpty(swimlaneConfiguration)) {
            orgFunctionClassName = NullOrgFunction.class.getName();
            parameterNames = new String[0];
            return;
        }
        int leftBracketIndex = swimlaneConfiguration.indexOf(LEFT_BRACKET);
        int rightBracketIndex = swimlaneConfiguration.indexOf(RIGHT_BRACKET);
        orgFunctionClassName = swimlaneConfiguration.substring(0, leftBracketIndex);
        String parametersString = swimlaneConfiguration.substring(leftBracketIndex + 1, rightBracketIndex);
        parameterNames = parametersString.split(",", -1);
    }

    public String getOrgFunctionClassName() {
        return orgFunctionClassName;
    }

    public String[] getParameterNames() {
        return parameterNames;
    }

    @Override
    public List<? extends Executor> evaluate(IVariableProvider variableProvider) {
        OrgFunction orgFunction = ApplicationContextFactory.createAutowiredBean(orgFunctionClassName);
        List<Object> params = new ArrayList<Object>();
        for (String name : parameterNames) {
            params.addAll(TypeConversionUtil.convertTo(List.class, ExpressionEvaluator.evaluateVariableNotNull(variableProvider, name)));
        }
        Object[] parameters = params.toArray(new Object[params.size()]);
        return orgFunction.getExecutors(parameters);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("orgFunctionClassName", orgFunctionClassName).add("parameters", parameterNames).toString();
    }

}
