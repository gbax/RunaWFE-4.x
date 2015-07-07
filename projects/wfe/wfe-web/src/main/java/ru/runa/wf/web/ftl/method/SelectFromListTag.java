package ru.runa.wf.web.ftl.method;

import java.util.List;

import ru.runa.wfe.commons.ftl.FreemarkerTag;
import ru.runa.wfe.commons.ftl.FtlTagVariableSubmissionPostProcessor;
import ru.runa.wfe.user.Executor;
import ru.runa.wfe.var.ISelectable;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

@SuppressWarnings("unchecked")
public class SelectFromListTag extends FreemarkerTag implements FtlTagVariableSubmissionPostProcessor {
    private static final long serialVersionUID = 1L;

    @Override
    protected Object executeTag() {
        String variableName = getParameterAsString(0);
        List<Object> list = getParameterVariableValue(List.class, 1, null);
        if (list == null) {
            list = Lists.newArrayList();
        }
        if (list.size() > 0 && list.get(0) instanceof ISelectable) {
            registerVariablePostProcessor(variableName);
        }
        Object selectedValue = variableProvider.getValue(Object.class, variableName);
        StringBuffer html = new StringBuffer();
        html.append("<select name=\"").append(variableName).append("\">");
        html.append("<option value=\"\"> ------------------------- </option>");
        for (Object option : list) {
            String optionValue;
            String optionLabel;
            if (option instanceof ISelectable) {
                ISelectable selectable = (ISelectable) option;
                optionValue = selectable.getValue();
                optionLabel = selectable.getLabel();
            } else if (option instanceof Executor) {
                Executor executor = (Executor) option;
                optionValue = "ID" + executor.getId();
                optionLabel = executor.getLabel();
            } else {
                optionValue = String.valueOf(option);
                optionLabel = String.valueOf(option);
            }
            html.append("<option value=\"").append(optionValue).append("\"");
            if (Objects.equal(selectedValue, option)) {
                html.append(" selected=\"true\"");
            }
            html.append(">").append(optionLabel).append("</option>");
        }
        html.append("</select>");
        return html;
    }

    @Override
    public Object postProcessValue(Object source) {
        if (source instanceof String) {
            String value = (String) source;
            List<ISelectable> list = getParameterVariableValueNotNull(List.class, 1);
            for (ISelectable option : list) {
                if (Objects.equal(value, option.getValue())) {
                    return option;
                }
            }
        }
        return source;
    }
}
