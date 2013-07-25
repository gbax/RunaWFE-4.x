/*
 * This file is part of the RUNA WFE project.
 * 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation; version 2.1 
 * of the License. 
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU Lesser General Public License for more details. 
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this program; if not, write to the Free Software 
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package ru.runa.wfe.service.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import ru.runa.wfe.audit.ProcessLogFilter;
import ru.runa.wfe.audit.ProcessLogs;
import ru.runa.wfe.audit.SystemLog;
import ru.runa.wfe.audit.logic.AuditLogic;
import ru.runa.wfe.execution.ProcessFilter;
import ru.runa.wfe.execution.dto.WfProcess;
import ru.runa.wfe.execution.dto.WfSwimlane;
import ru.runa.wfe.execution.logic.ExecutionLogic;
import ru.runa.wfe.graph.view.GraphElementPresentation;
import ru.runa.wfe.presentation.BatchPresentation;
import ru.runa.wfe.presentation.BatchPresentationFactory;
import ru.runa.wfe.service.client.FileVariableProxy;
import ru.runa.wfe.service.decl.ExecutionServiceLocal;
import ru.runa.wfe.service.decl.ExecutionServiceRemote;
import ru.runa.wfe.service.decl.ExecutionServiceRemoteWS;
import ru.runa.wfe.service.interceptors.EjbExceptionSupport;
import ru.runa.wfe.service.interceptors.EjbTransactionSupport;
import ru.runa.wfe.service.interceptors.PerformanceObserver;
import ru.runa.wfe.task.dto.WfTask;
import ru.runa.wfe.task.logic.TaskLogic;
import ru.runa.wfe.user.Executor;
import ru.runa.wfe.user.User;
import ru.runa.wfe.var.FileVariable;
import ru.runa.wfe.var.converter.SerializableToByteArrayConverter;
import ru.runa.wfe.var.dto.WfVariable;
import ru.runa.wfe.var.jaxb.VariableAdapter;
import ru.runa.wfe.var.logic.VariableLogic;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Stateless(name = "ExecutionServiceBean")
@TransactionManagement(TransactionManagementType.BEAN)
@Interceptors({ EjbExceptionSupport.class, PerformanceObserver.class, EjbTransactionSupport.class, SpringBeanAutowiringInterceptor.class })
@WebService(name = "ExecutionAPI", serviceName = "ExecutionWebService")
@SOAPBinding
public class ExecutionServiceBean implements ExecutionServiceLocal, ExecutionServiceRemote, ExecutionServiceRemoteWS {
    private static final Log log = LogFactory.getLog(ExecutionServiceBean.class);
    @Autowired
    private ExecutionLogic executionLogic;
    @Autowired
    private TaskLogic taskLogic;
    @Autowired
    private VariableLogic variableLogic;
    @Autowired
    private AuditLogic auditLogic;

    @WebMethod(exclude = true)
    @Override
    public Long startProcess(User user, String definitionName, Map<String, Object> variables) {
        Preconditions.checkArgument(user != null);
        Preconditions.checkArgument(definitionName != null && !definitionName.isEmpty());
        return executionLogic.startProcess(user, definitionName, variables);
    }

    @Override
    public int getAllProcessesCount(@WebParam(name = "user") User user, @WebParam(name = "batchPresentation") BatchPresentation batchPresentation) {
        Preconditions.checkArgument(user != null);
        if (batchPresentation == null) {
            batchPresentation = BatchPresentationFactory.PROCESSES.createDefault();
        }
        return executionLogic.getAllProcessesCount(user, batchPresentation);
    }

    @Override
    public List<WfProcess> getProcesses(@WebParam(name = "user") User user, @WebParam(name = "batchPresentation") BatchPresentation batchPresentation) {
        Preconditions.checkArgument(user != null);
        if (batchPresentation == null) {
            batchPresentation = BatchPresentationFactory.PROCESSES.createDefault();
        }
        return executionLogic.getProcesses(user, batchPresentation);
    }

    @Override
    public WfProcess getProcess(@WebParam(name = "user") User user, @WebParam(name = "id") Long id) {
        Preconditions.checkArgument(user != null);
        return executionLogic.getProcess(user, id);
    }

    @Override
    public WfProcess getParentProcess(@WebParam(name = "user") User user, @WebParam(name = "id") Long id) {
        Preconditions.checkArgument(user != null);
        return executionLogic.getParentProcess(user, id);
    }

    @Override
    public List<WfProcess> getSubprocessesRecursive(@WebParam(name = "user") User user, @WebParam(name = "id") Long id) {
        Preconditions.checkArgument(user != null);
        return executionLogic.getSubprocessesRecursive(user, id);
    }

    @Override
    public List<WfTask> getTasks(@WebParam(name = "user") User user, @WebParam(name = "batchPresentation") BatchPresentation batchPresentation) {
        Preconditions.checkArgument(user != null);
        if (batchPresentation == null) {
            batchPresentation = BatchPresentationFactory.TASKS.createDefault();
        }
        return taskLogic.getTasks(user, batchPresentation);
    }

    @Override
    public WfTask getTask(@WebParam(name = "user") User user, @WebParam(name = "taskId") Long taskId) {
        Preconditions.checkArgument(user != null);
        return taskLogic.getTask(user, taskId);
    }

    @WebMethod(exclude = true)
    @Override
    public List<WfVariable> getVariables(User user, Long processId) {
        Preconditions.checkArgument(user != null);
        List<WfVariable> list = variableLogic.getVariables(user, processId);
        for (WfVariable variable : list) {
            convertValueToProxy(user, processId, variable);
        }
        return list;
    }

    @WebMethod(exclude = true)
    @Override
    public Map<Long, WfVariable> getVariablesFromProcesses(User user, List<Long> processIds, String variableName) {
        Preconditions.checkArgument(user != null);
        Map<Long, WfVariable> map = variableLogic.getVariableValueFromProcesses(user, processIds, variableName);
        for (Map.Entry<Long, WfVariable> entry : map.entrySet()) {
            convertValueToProxy(user, entry.getKey(), entry.getValue());
        }
        return map;
    }

    @Override
    public WfVariable getVariable(@WebParam(name = "user") User user, @WebParam(name = "processId") Long processId,
            @WebParam(name = "variableName") String variableName) {
        Preconditions.checkArgument(user != null);
        return variableLogic.getVariable(user, processId, variableName);
    }

    @WebMethod(exclude = true)
    @Override
    public void updateVariables(User user, Long processId, Map<String, Object> variables) {
        Preconditions.checkArgument(user != null);
        variableLogic.updateVariables(user, processId, variables);
    }

    @WebMethod(exclude = true)
    @Override
    public void completeTask(User user, Long taskId, @WebParam(name = "variables") Map<String, Object> variables,
            @WebParam(name = "swimlaneActorId") Long swimlaneActorId) {
        Preconditions.checkArgument(user != null);
        taskLogic.completeTask(user, taskId, variables, swimlaneActorId);
    }

    @Override
    public void cancelProcess(@WebParam(name = "user") User user, @WebParam(name = "processId") Long processId) {
        Preconditions.checkArgument(user != null);
        executionLogic.cancelProcess(user, processId);
    }

    @Override
    public List<WfSwimlane> getSwimlanes(@WebParam(name = "user") User user, @WebParam(name = "processId") Long processId) {
        Preconditions.checkArgument(user != null);
        return taskLogic.getSwimlanes(user, processId);
    }

    @Override
    public List<WfTask> getProcessTasks(@WebParam(name = "user") User user, @WebParam(name = "processId") Long processId) {
        Preconditions.checkArgument(user != null);
        return taskLogic.getTasks(user, processId);
    }

    @Override
    public byte[] getProcessDiagram(@WebParam(name = "user") User user, @WebParam(name = "processId") Long processId,
            @WebParam(name = "taskId") Long taskId, @WebParam(name = "childProcessId") Long childProcessId) {
        Preconditions.checkArgument(user != null);
        return executionLogic.getProcessDiagram(user, processId, taskId, childProcessId);
    }

    @Override
    public byte[] getProcessHistoryDiagram(@WebParam(name = "user") User user, @WebParam(name = "processId") Long processId,
            @WebParam(name = "taskId") Long taskId) {
        Preconditions.checkArgument(user != null);
        return executionLogic.getProcessHistoryDiagram(user, processId, taskId);
    }

    @Override
    public List<GraphElementPresentation> getProcessUIHistoryData(@WebParam(name = "user") User user, @WebParam(name = "processId") Long processId,
            @WebParam(name = "taskId") Long taskId) {
        Preconditions.checkArgument(user != null);
        return executionLogic.getProcessUIHistoryData(user, processId, taskId);
    }

    @Override
    public List<GraphElementPresentation> getProcessGraphElements(@WebParam(name = "user") User user, @WebParam(name = "processId") Long processId) {
        Preconditions.checkArgument(user != null);
        return executionLogic.getProcessGraphElements(user, processId);
    }

    @Override
    public void assignSwimlane(@WebParam(name = "user") User user, @WebParam(name = "processId") Long processId,
            @WebParam(name = "swimlaneName") String swimlaneName, @WebParam(name = "executor") Executor executor) {
        Preconditions.checkArgument(user != null);
        taskLogic.assignSwimlane(user, processId, swimlaneName, executor);
    }

    @Override
    public void assignTask(@WebParam(name = "user") User user, @WebParam(name = "taskId") Long taskId,
            @WebParam(name = "previousOwner") Executor previousOwner, @WebParam(name = "newExecutor") Executor newExecutor) {
        Preconditions.checkArgument(user != null);
        taskLogic.assignTask(user, taskId, previousOwner, newExecutor);
    }

    @Override
    public ProcessLogs getProcessLogs(@WebParam(name = "user") User user, @WebParam(name = "filter") ProcessLogFilter filter) {
        Preconditions.checkArgument(user != null);
        return auditLogic.getProcessLogs(user, filter);
    }

    @Override
    public Object getProcessLogValue(@WebParam(name = "user") User user, @WebParam(name = "logId") Long logId) {
        return auditLogic.getProcessLogValue(user, logId);
    }

    @Override
    public void markTaskOpened(@WebParam(name = "user") User user, @WebParam(name = "taskId") Long taskId) {
        Preconditions.checkArgument(user != null);
        taskLogic.markTaskOpened(user, taskId);
    }

    @Override
    public void removeProcesses(@WebParam(name = "user") User user, @WebParam(name = "filter") ProcessFilter filter) {
        Preconditions.checkArgument(user != null);
        Preconditions.checkArgument(filter != null);
        executionLogic.deleteProcesses(user, filter);
    }

    @Override
    public List<SystemLog> getSystemLogs(@WebParam(name = "user") User user, @WebParam(name = "batchPresentation") BatchPresentation batchPresentation) {
        Preconditions.checkArgument(user != null);
        if (batchPresentation == null) {
            batchPresentation = BatchPresentationFactory.SYSTEM_LOGS.createDefault();
        }
        return auditLogic.getSystemLogs(user, batchPresentation);
    }

    @Override
    public int getSystemLogsCount(@WebParam(name = "user") User user, @WebParam(name = "batchPresentation") BatchPresentation batchPresentation) {
        Preconditions.checkArgument(user != null);
        if (batchPresentation == null) {
            batchPresentation = BatchPresentationFactory.SYSTEM_LOGS.createDefault();
        }
        return auditLogic.getSystemLogsCount(user, batchPresentation);
    }

    private boolean convertValueToProxy(User user, Long processId, WfVariable variable) {
        if (variable.getValue() instanceof FileVariable) {
            FileVariable fileVariable = (FileVariable) variable.getValue();
            FileVariableProxy proxy = new FileVariableProxy(user, processId, variable.getDefinition().getName(), fileVariable);
            variable.setValue(proxy);
            return true;
        }
        return false;
    }

    @Override
    public List<ru.runa.wfe.var.jaxb.WfVariable> getVariablesWS(@WebParam(name = "user") User user, @WebParam(name = "processId") Long processId) {
        List<WfVariable> list = getVariables(user, processId);
        List<ru.runa.wfe.var.jaxb.WfVariable> result = Lists.newArrayListWithExpectedSize(list.size());
        VariableAdapter adapter = new VariableAdapter();
        for (WfVariable variable : list) {
            result.add(adapter.marshal(variable));
        }
        return result;
    }

    @Override
    public Long startProcessWS(@WebParam(name = "user") User user, @WebParam(name = "definitionName") String definitionName,
            @WebParam(name = "variables") List<ru.runa.wfe.var.jaxb.WfVariable> variables) {
        return startProcess(user, definitionName, toVariablesMap(variables));
    }

    @Override
    public void completeTaskWS(@WebParam(name = "user") User user, @WebParam(name = "taskId") Long taskId,
            @WebParam(name = "variables") List<ru.runa.wfe.var.jaxb.WfVariable> variables, @WebParam(name = "swimlaneActorId") Long swimlaneActorId) {
        completeTask(user, taskId, toVariablesMap(variables), swimlaneActorId);
    }

    @Override
    public void updateVariablesWS(@WebParam(name = "user") User user, @WebParam(name = "processId") Long processId,
            @WebParam(name = "variables") List<ru.runa.wfe.var.jaxb.WfVariable> variables) {
        updateVariables(user, processId, toVariablesMap(variables));
    }

    private Map<String, Object> toVariablesMap(List<ru.runa.wfe.var.jaxb.WfVariable> variables) {
        Map<String, Object> map = Maps.newHashMap();
        if (variables != null) {
            for (ru.runa.wfe.var.jaxb.WfVariable wsVariable : variables) {
                VariableAdapter adapter = new VariableAdapter();
                WfVariable wfVariable = adapter.unmarshal(wsVariable);
                Preconditions.checkNotNull(wfVariable.getDefinition(), "variable.definition");
                Object value = wfVariable.getValue();
                if (value instanceof byte[]) {
                    log.debug("Variable '" + wfVariable.getDefinition().getName() + "': reverting from bytes");
                    value = new SerializableToByteArrayConverter().revert(value);
                }
                if (wfVariable.getDefinition().getFormatClassName() != null) {
                    try {
                        if (value == null) {
                            log.debug("Variable '" + wfVariable.getDefinition().getName() + "' value is null");
                        } else {
                            log.debug("Variable '" + wfVariable.getDefinition().getName() + "' value is type of "
                                    + (value != null ? value.getClass() : "null"));
                            value = wfVariable.getFormatNotNull().parse(new String[] { value.toString() });
                        }
                    } catch (Exception e) {
                        throw Throwables.propagate(e);
                    }
                }
                map.put(wfVariable.getDefinition().getName(), value);
            }
        }
        return map;
    }

}
