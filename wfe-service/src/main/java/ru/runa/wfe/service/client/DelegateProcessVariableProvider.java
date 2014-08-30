package ru.runa.wfe.service.client;

import ru.runa.wfe.execution.dto.WfProcess;
import ru.runa.wfe.service.delegate.Delegates;
import ru.runa.wfe.user.IExecutorLoader;
import ru.runa.wfe.user.User;
import ru.runa.wfe.var.AbstractVariableProvider;
import ru.runa.wfe.var.dto.WfVariable;

/**
 * Implementation which uses service call for each variable retrieval (through
 * RunaWFE delegates).
 * 
 * @author Dofs
 * @since 4.0
 */
public class DelegateProcessVariableProvider extends AbstractVariableProvider {
    private final User user;
    private final Long processId;

    public DelegateProcessVariableProvider(User user, Long processId) {
        this.user = user;
        this.processId = processId;
    }

    @Override
    protected IExecutorLoader getExecutorLoader() {
        return new DelegateExecutorLoader(user);
    }

    @Override
    public Long getProcessId() {
        return processId;
    }

    @Override
    public String getProcessDefinitionName() {
        WfProcess process = Delegates.getExecutionService().getProcess(user, processId);
        return process.getName();
    }

    @Override
    public Object getValue(String variableName) {
        WfVariable variable = getVariable(variableName);
        if (variable != null) {
            return variable.getValue();
        }
        return null;
    }

    @Override
    public WfVariable getVariable(String variableName) {
        return Delegates.getExecutionService().getVariable(user, processId, variableName);
    }

    @Override
    public DelegateProcessVariableProvider getSameProvider(Long processId) {
        return new DelegateProcessVariableProvider(user, processId);
    }
}
