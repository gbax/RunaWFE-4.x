package ru.runa.wfe.office.shared;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import ru.runa.wfe.InternalApplicationException;
import ru.runa.wfe.commons.ClassLoaderUtil;
import ru.runa.wfe.commons.ftl.ExpressionEvaluator;
import ru.runa.wfe.definition.IFileDataProvider;
import ru.runa.wfe.var.IVariableProvider;
import ru.runa.wfe.var.file.FileVariable;
import ru.runa.wfe.var.file.IFileVariable;

import com.google.common.io.Files;

public abstract class FilesSupplierConfig {
    protected String inputFilePath;
    protected String inputFileVariableName;
    private String outputDirPath;
    private String outputFileName;
    private String outputFileVariableName;

    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public void setInputFileVariableName(String inputFileVariableName) {
        this.inputFileVariableName = inputFileVariableName;
    }

    public void setOutputDirPath(String outputDirPath) {
        this.outputDirPath = outputDirPath;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public void setOutputFileVariableName(String outputFileVariableName) {
        this.outputFileVariableName = outputFileVariableName;
    }

    protected abstract String getContentType();

    public abstract String getDefaultOutputFileName();

    public String getOutputFileName() {
        if (outputFileName == null) {
            return getDefaultOutputFileName();
        }
        return outputFileName;
    }

    public InputStream getFileInputStream(IVariableProvider variableProvider, IFileDataProvider fileDataProvider, boolean required) {
        if (inputFileVariableName != null) {
            Object value = variableProvider.getValue(inputFileVariableName);
            if (value instanceof IFileVariable) {
                IFileVariable fileVariable = (IFileVariable) value;
                return new ByteArrayInputStream(fileVariable.getData());
            }
            if (value instanceof byte[]) {
                return new ByteArrayInputStream((byte[]) value);
            }
            throw new InternalApplicationException("Variable '" + inputFileVariableName + "' should contains a file");
        }
        if (inputFilePath != null) {
            if (inputFilePath.startsWith(IFileDataProvider.PROCESS_FILE_PROTOCOL)
                    || inputFilePath.startsWith(IFileDataProvider.BOT_TASK_FILE_PROTOCOL)) {

                String inputFilePathWithoutProtocol = inputFilePath;
                if (inputFilePath.startsWith(IFileDataProvider.PROCESS_FILE_PROTOCOL)) {
                    inputFilePathWithoutProtocol = inputFilePath.substring(IFileDataProvider.PROCESS_FILE_PROTOCOL.length());
                }
                if (inputFilePath.startsWith(IFileDataProvider.BOT_TASK_FILE_PROTOCOL)) {
                    inputFilePathWithoutProtocol = inputFilePath.substring(IFileDataProvider.BOT_TASK_FILE_PROTOCOL.length());
                }

                byte[] data = fileDataProvider.getFileDataNotNull(inputFilePathWithoutProtocol);
                return new ByteArrayInputStream(data);
            }
            String path = (String) ExpressionEvaluator.evaluateVariableNotNull(variableProvider, inputFilePath);
            File file = new File(path);
            if (file.exists() && !file.isDirectory()) {
                try {
                    return Files.newInputStreamSupplier(file).getInput();
                } catch (IOException e) {
                    throw new InternalApplicationException("Unable to read input file from location '" + path + "'");
                }
            }
            return ClassLoaderUtil.getAsStreamNotNull(path, getClass());
        }
        if (required) {
            throw new InternalApplicationException("No input file defined in configuration");
        }
        return null;
    }

    public OutputStream getFileOutputStream(Map<String, Object> outputVariables, boolean required) {
        if (outputFileVariableName != null) {
            FileVariable fileVariable = new FileVariable(getOutputFileName(), getContentType());
            outputVariables.put(outputFileVariableName, fileVariable);
            return new FileVariableOutputStream(fileVariable);
        }
        if (outputDirPath != null) {
            File dir = new File(outputDirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (!dir.exists() || !dir.isDirectory()) {
                throw new InternalApplicationException("Unable to locate output directory '" + outputDirPath + "'");
            }
            File file = new File(dir, getOutputFileName());
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new InternalApplicationException("Unable to create new output file in location '" + file.getAbsolutePath() + "'", e);
                }
            }
            try {
                return Files.newOutputStreamSupplier(file).getOutput();
            } catch (IOException e) {
                throw new InternalApplicationException("Unable to write output file to location '" + file.getAbsolutePath() + "'", e);
            }
        }
        if (required) {
            throw new InternalApplicationException("No output file defined in configuration");
        }
        return null;
    }

    public static class FileVariableOutputStream extends ByteArrayOutputStream {
        private final FileVariable fileVariable;

        public FileVariableOutputStream(FileVariable fileVariable) {
            this.fileVariable = fileVariable;
        }

        @Override
        public void close() throws IOException {
            super.close();
            fileVariable.setData(toByteArray());
        }
    }
}
