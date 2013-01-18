/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package ru.runa.wfe.execution;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ru.runa.wfe.audit.ProcessLog;
import ru.runa.wfe.audit.VariableDeleteLog;
import ru.runa.wfe.audit.dao.ProcessLogDAO;
import ru.runa.wfe.commons.ApplicationContextFactory;
import ru.runa.wfe.commons.TypeConversionUtil;
import ru.runa.wfe.execution.dao.NodeProcessDAO;
import ru.runa.wfe.lang.Node;
import ru.runa.wfe.lang.ProcessDefinition;
import ru.runa.wfe.lang.SwimlaneDefinition;
import ru.runa.wfe.task.Task;
import ru.runa.wfe.user.Executor;
import ru.runa.wfe.var.IVariableProvider;
import ru.runa.wfe.var.Variable;
import ru.runa.wfe.var.VariableCreator;
import ru.runa.wfe.var.dao.VariableDAO;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ExecutionContext {
    private static Log log = LogFactory.getLog(ExecutionContext.class);
    private final ProcessDefinition processDefinition;
    private final Token token;
    private final Map<String, Object> transientVariables = Maps.newHashMap();
    @Autowired
    private VariableCreator variableCreator;
    @Autowired
    private NodeProcessDAO nodeProcessDAO;
    @Autowired
    private ProcessLogDAO processLogDAO;
    @Autowired
    private VariableDAO variableDAO;

    public ExecutionContext(ProcessDefinition processDefinition, Token token) {
        this.processDefinition = processDefinition;
        this.token = token;
        Preconditions.checkNotNull(token, "token");
        ApplicationContextFactory.getContext().getAutowireCapableBeanFactory().autowireBean(this);
    }

    public ExecutionContext(ProcessDefinition processDefinition, Process process) {
        this(processDefinition, process.getRootToken());
    }

    public ExecutionContext(ProcessDefinition processDefinition, Task task) {
        this(processDefinition, task.getToken());
    }

    /**
     * retrieves the transient variable for the given name.
     */
    public Object getTransientVariable(String name) {
        return transientVariables.get(name);
    }

    /**
     * sets the transient variable for the given name to the given value.
     */
    public void setTransientVariable(String name, Object value) {
        transientVariables.put(name, value);
    }

    public Node getNode() {
        return getToken().getNode(getProcessDefinition());
    }

    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }

    public Process getProcess() {
        Process process = getToken().getProcess();
        Preconditions.checkNotNull(process, "process");
        return process;
    }

    public Token getToken() {
        return token;
    }

    public Task getTask() {
        return getProcess().getTask(getToken().getNodeId());
    }

    public Process getParentProcess(Process subProcess) {
        NodeProcess nodeProcess = nodeProcessDAO.getNodeProcessByChild(subProcess.getId());
        if (nodeProcess != null) {
            return nodeProcess.getProcess();
        }
        return null;
    }

    public NodeProcess getParentNodeProcess() {
        return nodeProcessDAO.getNodeProcessByChild(getProcess().getId());
    }

    public List<Process> getChildProcesses() {
        String nodeId = getNode().getNodeId();
        List<NodeProcess> nodeProcesses = nodeProcessDAO.getNodeProcesses(getProcess().getId());
        List<Process> result = Lists.newArrayListWithExpectedSize(nodeProcesses.size());
        for (NodeProcess nodeProcess : nodeProcesses) {
            if (nodeId.equals(nodeProcess.getNodeId())) {
                result.add(nodeProcess.getSubProcess());
            }
        }
        return result;
    }

    /**
     * @return the variable value with the given name.
     */
    public Object getVariable(String name) {
        Variable<?> variable = variableDAO.get(getProcess(), name);
        if (variable != null) {
            return variable.getValue();
        }
        Swimlane swimlane = getProcess().getSwimlane(name);
        if (swimlane != null) {
            return swimlane.getExecutor();
        }
        return null;
    }

    public void setVariable(String name, Object value) {
        Preconditions.checkNotNull(name, "name is null");
        // backwardCompatibilityVariablesMode
        SwimlaneDefinition swimlaneDefinition = getProcessDefinition().getSwimlane(name);
        if (swimlaneDefinition != null) {
            Swimlane swimlane = getProcess().getSwimlaneNotNull(swimlaneDefinition);
            swimlane.assignExecutor(this, TypeConversionUtil.convertTo(value, Executor.class), true);
            return;
        }
        Variable<?> variable = variableDAO.get(getProcess(), name);
        // if there is already a variable and it doesn't support the current
        // type
        if (variable != null && !variable.supports(value)) {
            // delete the old variable
            log.debug("variable type change. deleting '" + name + "' from '" + this + "'");
            variableDAO.delete(variable);
            addLog(new VariableDeleteLog(variable));
            variable = null;
        }
        if (variable == null) {
            if (value != null) {
                variable = variableCreator.create(this, name, value);
                variableDAO.create(variable);
            }
        } else {
            if (Objects.equal(variable.getValue(), value)) {
                return;
            }
            log.debug("update variable '" + name + "' in '" + this + "' to value '" + value + "'");
            variable.setValue(this, value);
            // TODO merge variableDAO.update(variable);
        }
    }

    /**
     * Adds all the given variables. It doesn't remove any existing variables
     * unless they are overwritten by the given variables.
     */
    public void setVariables(Map<String, Object> variables) {
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            setVariable(entry.getKey(), entry.getValue());
        }
    }

    /**
     * @return variable provider for this process.
     */
    public IVariableProvider getVariableProvider() {
        return new ExecutionVariableProvider(this);
    }

    public void addLog(ProcessLog processLog) {
        processLog.setProcessId(getProcess().getId());
        processLog.setTokenId(getToken().getId());
        processLog.setDate(new Date());
        processLogDAO.create(processLog);
    }

    @Override
    public String toString() {
        return processDefinition + "|" + getProcess().getId();
    }

}
