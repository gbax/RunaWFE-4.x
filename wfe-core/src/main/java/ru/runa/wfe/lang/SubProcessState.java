package ru.runa.wfe.lang;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import ru.runa.wfe.InternalApplicationException;
import ru.runa.wfe.audit.SubprocessEndLog;
import ru.runa.wfe.definition.dao.IProcessDefinitionLoader;
import ru.runa.wfe.execution.ExecutionContext;
import ru.runa.wfe.execution.Process;
import ru.runa.wfe.execution.ProcessFactory;
import ru.runa.wfe.var.VariableMapping;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

public class SubProcessState extends VariableContainerNode {
    private static final long serialVersionUID = 1L;

    private String subProcessName;
    private boolean embedded;
    @Autowired
    private IProcessDefinitionLoader processDefinitionLoader;
    @Autowired
    private ProcessFactory processFactory;

    @Override
    public NodeType getNodeType() {
        return NodeType.SUBPROCESS;
    }

    @Override
    public void validate() {
        super.validate();
        Preconditions.checkNotNull(subProcessName, "subProcessName in " + this);
        if (isEmbedded()) {
            if (getArrivingTransitions().size() != 1) {
                throw new InternalApplicationException("Subprocess state for embedded subprocess should have 1 arriving transition");
            }
            if (getLeavingTransitions().size() != 1) {
                throw new InternalApplicationException("Subprocess state for embedded subprocess should have 1 leaving transition");
            }
        }
    }

    public String getSubProcessName() {
        return subProcessName;
    }

    public void setSubProcessName(String subProcessName) {
        this.subProcessName = subProcessName;
    }

    public boolean isEmbedded() {
        return embedded;
    }

    public void setEmbedded(boolean embedded) {
        this.embedded = embedded;
    }

    protected ProcessDefinition getSubProcessDefinition() {
        return processDefinitionLoader.getDefinition(subProcessName);
    }

    @Override
    public void execute(ExecutionContext executionContext) {
        if (isEmbedded()) {
            throw new InternalApplicationException("it's not intended for execution");
        }
        // create the subprocess
        Map<String, Object> variables = Maps.newHashMap();
        for (VariableMapping variableMapping : variableMappings) {
            // if this variable mapping is readable
            if (variableMapping.isReadable()) {
                // the variable is copied from the super process variable
                // name to the sub process mapped name
                String variableName = variableMapping.getName();
                Object value = executionContext.getVariable(variableName);
                if (value != null) {
                    String mappedName = variableMapping.getMappedName();
                    log.debug("copying super process var '" + variableName + "' to sub process var '" + mappedName + "': " + value);
                    variables.put(mappedName, value);
                }
            }
        }
        ProcessDefinition subProcessDefinition = getSubProcessDefinition();
        Process subProcess = processFactory.createSubprocess(executionContext, subProcessDefinition, variables);
        processFactory.startSubprocess(executionContext, new ExecutionContext(subProcessDefinition, subProcess));
    }

    @Override
    public void leave(ExecutionContext executionContext, Transition transition) {
        performLeave(executionContext);
        super.leave(executionContext, transition);
    }

    protected void performLeave(ExecutionContext executionContext) {
        List<Process> subprocesses = executionContext.getAllSubprocesses();
        if (subprocesses.size() == 0) {
            throw new InternalApplicationException("SubProcessState has 0 subprocesses at leave stage!");
        }
        Process subProcess = subprocesses.get(subprocesses.size() - 1);
        ExecutionContext subExecutionContext = new ExecutionContext(getSubProcessDefinition(), subProcess);
        for (VariableMapping variableMapping : variableMappings) {
            // if this variable access is writable
            if (variableMapping.isWritable()) {
                // the variable is copied from the sub process mapped name
                // to the super process variable name
                String mappedName = variableMapping.getMappedName();
                Object value = subExecutionContext.getVariable(mappedName);
                if (value != null) {
                    String variableName = variableMapping.getName();
                    log.debug("copying sub process var '" + mappedName + "' to super process var '" + variableName + "': " + value);
                    executionContext.setVariable(variableName, value);
                }
            }
        }
        executionContext.addLog(new SubprocessEndLog(this, executionContext.getToken(), subProcess));
    }

}
