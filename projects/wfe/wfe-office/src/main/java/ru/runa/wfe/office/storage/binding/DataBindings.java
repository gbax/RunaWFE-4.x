package ru.runa.wfe.office.storage.binding;

import java.util.ArrayList;
import java.util.List;

import ru.runa.wfe.InternalApplicationException;
import ru.runa.wfe.commons.ftl.ExpressionEvaluator;
import ru.runa.wfe.office.shared.FilesSupplierConfig;
import ru.runa.wfe.var.IVariableProvider;
import ru.runa.wfe.var.file.IFileVariable;

public class DataBindings extends FilesSupplierConfig {

    private final List<DataBinding> bindings = new ArrayList<DataBinding>();

    private QueryType queryType;

    private String condition;

    @Override
    protected String getContentType() {
        if (isFileNameBelongsToXLSX(getOutputFileName(), false)) {
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        } else {
            return "application/ms-excel";
        }
    }

    public boolean isFileNameBelongsToXLSX(String fileName, boolean defaultValue) {
        if (fileName == null) {
            return defaultValue;
        }
        return fileName.endsWith("xlsx");
    }

    public boolean isInputFileXLSX(IVariableProvider variableProvider, boolean defaultValue) {
        if (inputFileVariableName != null) {
            Object value = variableProvider.getValue(inputFileVariableName);
            if (value instanceof IFileVariable) {
                IFileVariable fileVariable = (IFileVariable) value;
                return isFileNameBelongsToXLSX(fileVariable.getName(), defaultValue);
            }
            throw new InternalApplicationException("Variable '" + inputFileVariableName + "' should contains a file");
        }
        if (inputFilePath != null) {
            String path = (String) ExpressionEvaluator.evaluateVariableNotNull(variableProvider, inputFilePath);
            return isFileNameBelongsToXLSX(path, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public String getDefaultOutputFileName() {
        return "spreadsheet.xls";
    }

    public List<DataBinding> getBindings() {
        return bindings;
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public QueryType getQueryType() {
        return queryType;
    }

    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
