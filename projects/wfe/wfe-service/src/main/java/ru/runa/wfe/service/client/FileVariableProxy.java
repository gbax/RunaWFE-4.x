package ru.runa.wfe.service.client;

import ru.runa.wfe.service.delegate.Delegates;
import ru.runa.wfe.user.User;
import ru.runa.wfe.var.file.IFileVariable;

/**
 * This class eliminates byte[] data transferring without usage.
 * 
 * @author dofs
 * @since 4.0
 */
public class FileVariableProxy implements IFileVariable {
    private static final long serialVersionUID = 1L;
    private User user;
    private Long processId;
    private String variableName;
    private String name;
    private String contentType;
    private byte[] data;
    private String stringValue;

    public FileVariableProxy() {
    }

    public FileVariableProxy(User user, Long processId, String variableName, IFileVariable fileVariable) {
        this.name = fileVariable.getName();
        this.contentType = fileVariable.getContentType();
        this.user = user;
        this.processId = processId;
        this.variableName = variableName;
        this.stringValue = fileVariable.getStringValue();
    }

    public String getVariableName() {
        return variableName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public byte[] getData() {
        if (data == null) {
            IFileVariable fileVariable = Delegates.getExecutionService().getFileVariableValue(user, processId, variableName);
            data = fileVariable != null ? fileVariable.getData() : new byte[0];
        }
        return data;
    }

    @Override
    public String getStringValue() {
        return stringValue;
    }
}
