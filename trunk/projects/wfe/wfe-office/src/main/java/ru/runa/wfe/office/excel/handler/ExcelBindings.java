package ru.runa.wfe.office.excel.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.runa.wfe.InternalApplicationException;
import ru.runa.wfe.office.shared.FilesSupplierConfig;
import ru.runa.wfe.var.FileVariable;
import ru.runa.wfe.var.IVariableProvider;

public class ExcelBindings extends FilesSupplierConfig {
    private final List<ExcelBinding> bindings = new ArrayList<ExcelBinding>();

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
            if (value instanceof FileVariable) {
                FileVariable fileVariable = (FileVariable) value;
                return isFileNameBelongsToXLSX(fileVariable.getName(), defaultValue);
            }
            throw new InternalApplicationException("Variable '" + inputFileVariableName + "' should contains a file");
        }
        if (inputFilePath != null) {
            return isFileNameBelongsToXLSX(inputFilePath, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public String getDefaultOutputFileName() {
        return "spreadsheet.xls";
    }

    public List<ExcelBinding> getBindings() {
        return bindings;
    }

    public static class FileVariableOutputStream extends ByteArrayOutputStream {
        private final FileVariable fileVariable;

        public FileVariableOutputStream(FileVariable fileVariable) {
            this.fileVariable = fileVariable;
        }

        @Override
        public void close() throws IOException {
            super.close();
            fileVariable.setData(buf);
        }
    }
}
