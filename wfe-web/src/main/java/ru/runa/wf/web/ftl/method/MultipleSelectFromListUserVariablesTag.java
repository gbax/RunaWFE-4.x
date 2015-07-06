package ru.runa.wf.web.ftl.method;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import ru.runa.wfe.commons.ftl.FtlTagVariableSubmissionHandler;
import ru.runa.wfe.var.ComplexVariable;
import ru.runa.wfe.var.VariableDefinition;
import ru.runa.wfe.var.VariableUserType;
import ru.runa.wfe.var.file.IFileVariable;
import ru.runa.wfe.var.format.FileFormat;
import ru.runa.wfe.var.format.FormatCommons;
import ru.runa.wfe.var.format.TextFormat;
import ru.runa.wfe.var.format.VariableFormat;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class MultipleSelectFromListUserVariablesTag extends AbstractListUserVariables implements FtlTagVariableSubmissionHandler {
    private static final long serialVersionUID = 1L;

    @Override
    protected Object executeTag() throws Exception {
        initFields();

        registerVariableHandler(dectVariableName);

        return ViewUtil.getUserTypeListTable(user, webHelper, variableProvider.getVariableNotNull(variableName),
                variableProvider.getVariableNotNull(dectVariableName), variableProvider.getProcessId(), sortField,
                displayMode == DisplayMode.MULTI_DIMENTIONAL_TABLE);
    }

    @Override
    public Map<String, ? extends Object> extractVariables(VariableDefinition variableDefinition, Map<String, ? extends Object> userInput,
            Map<String, String> formatErrorsForFields) throws Exception {
        Map<String, Object> result = Maps.newHashMap();
        if (!variableDefinition.getName().equals(dectVariableName) || !userInput.containsKey(dectVariableName)) {
            return result;
        }
        Object raw = userInput.get(dectVariableName);
        String json = null;
        VariableFormat format = FormatCommons.create(variableDefinition);
        if (!(raw instanceof String[])) {
            json = (String) raw;
        } else {
            json = Arrays.toString((String[]) raw);
        }
        if (variable == null) {
            try {
                result.put(variableDefinition.getName(), format.parse(json));
            } catch (ClassCastException e) {
                /*
                 * FIXME: bad handle executor and file type variables in json
                 */
                log.error(String.format("%s", e));
                throw e;
            }
            return result;
        }
        try {
            List<ComplexVariable> selected = Lists.newArrayList();
            JSONArray input = (JSONArray) JSONValue.parse(json);
            for (Object o : input) {
                for (ComplexVariable var : variable) {
                    if (!compareByValue(var, (JSONObject) o)) {
                        continue;
                    }
                    if (!selected.contains(var)) {
                        selected.add(var);
                    }
                    break;
                }
            }
            result.put(variableDefinition.getName(), selected);
        } catch (Exception e) {
            log.error(String.format("%s", e));
            throw e;
        }
        return result;
    }

    private final boolean compareByValue(ComplexVariable who, JSONObject with) {
        boolean result = true;
        VariableUserType type = who.getUserType();
        for (Object keyName : with.keySet()) {
            VariableDefinition def = type.getAttribute((String) keyName);
            VariableFormat format = FormatCommons.create(def);
            String toCompare = null;
            if (format instanceof FileFormat) {
                IFileVariable file = (IFileVariable) who.get(keyName);
                if (file != null) {
                    toCompare = file.getName();
                }
            } else {
                toCompare = format.format(who.get(keyName));
            }
            if (toCompare == null) {
                toCompare = "";
            }
            if (format instanceof TextFormat) {
                String[] words1 = toCompare.split("\\s+");
                String[] words2 = ((String) with.get(keyName)).split("\\s+");
                if (Arrays.equals(words1, words2)) {
                    continue;
                }
            } else if (toCompare.equals(with.get(keyName))) {
                continue;
            }
            result = false;
            break;
        }
        return result;
    }
}