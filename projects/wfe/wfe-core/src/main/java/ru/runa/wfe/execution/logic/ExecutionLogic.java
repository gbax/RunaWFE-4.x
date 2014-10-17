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
package ru.runa.wfe.execution.logic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import ru.runa.wfe.audit.ProcessLog;
import ru.runa.wfe.audit.ProcessLogs;
import ru.runa.wfe.commons.TypeConversionUtil;
import ru.runa.wfe.commons.logic.WFCommonLogic;
import ru.runa.wfe.definition.DefinitionPermission;
import ru.runa.wfe.definition.DefinitionVariableProvider;
import ru.runa.wfe.execution.ExecutionContext;
import ru.runa.wfe.execution.NodeProcess;
import ru.runa.wfe.execution.Process;
import ru.runa.wfe.execution.ProcessDoesNotExistException;
import ru.runa.wfe.execution.ProcessFactory;
import ru.runa.wfe.execution.ProcessFilter;
import ru.runa.wfe.execution.ProcessPermission;
import ru.runa.wfe.execution.Token;
import ru.runa.wfe.execution.dto.WfProcess;
import ru.runa.wfe.graph.DrawProperties;
import ru.runa.wfe.graph.history.GraphHistoryBuilder;
import ru.runa.wfe.graph.image.GraphImageBuilder;
import ru.runa.wfe.graph.view.GraphElementPresentation;
import ru.runa.wfe.graph.view.ProcessGraphInfoVisitor;
import ru.runa.wfe.lang.ProcessDefinition;
import ru.runa.wfe.lang.SwimlaneDefinition;
import ru.runa.wfe.presentation.BatchPresentation;
import ru.runa.wfe.presentation.BatchPresentationFactory;
import ru.runa.wfe.security.Permission;
import ru.runa.wfe.security.SecuredObjectType;
import ru.runa.wfe.task.Task;
import ru.runa.wfe.user.Executor;
import ru.runa.wfe.user.User;
import ru.runa.wfe.var.IVariableProvider;
import ru.runa.wfe.var.dto.WfVariable;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Process execution logic.
 * 
 * @author Dofs
 * @since 2.0
 */
public class ExecutionLogic extends WFCommonLogic {
    @Autowired
    private ProcessFactory processFactory;

    public void cancelProcess(User user, Long processId) throws ProcessDoesNotExistException {
        ProcessFilter filter = new ProcessFilter();
        Preconditions.checkArgument(processId != null);
        filter.setId(processId);
        cancelProcesses(user, filter);
    }

    public int getProcessesCount(User user, BatchPresentation batchPresentation) {
        return getPersistentObjectCount(user, batchPresentation, ProcessPermission.READ, PROCESS_EXECUTION_CLASSES);
    }

    private static final SecuredObjectType[] PROCESS_EXECUTION_CLASSES = { SecuredObjectType.PROCESS };

    public List<WfProcess> getProcesses(User user, BatchPresentation batchPresentation) {
        List<Process> list = getPersistentObjects(user, batchPresentation, ProcessPermission.READ, PROCESS_EXECUTION_CLASSES, true);
        return getProcesses(list, batchPresentation.getDynamicFieldsToDisplay(true));
    }

    public List<WfProcess> getProcesses(User user, ProcessFilter filter) {
        List<Process> process = processDAO.getProcesses(filter);
        process = filterIdentifiable(user, process, ProcessPermission.READ);
        return getProcesses(process, null);
    }

    public void deleteProcesses(User user, final ProcessFilter filter) {
        List<Process> processes = processDAO.getProcesses(filter);
        // TODO add ProcessPermission.DELETE_PROCESS
        processes = filterIdentifiable(user, processes, ProcessPermission.CANCEL_PROCESS);
        for (Process process : processes) {
            deleteProcess(user, process);
        }
    }

    public void cancelProcesses(User user, final ProcessFilter filter) {
        List<Process> processes = processDAO.getProcesses(filter);
        processes = filterIdentifiable(user, processes, ProcessPermission.CANCEL_PROCESS);
        for (Process process : processes) {
            ProcessDefinition processDefinition = getDefinition(process);
            ExecutionContext executionContext = new ExecutionContext(processDefinition, process);
            process.end(executionContext, user.getActor());
            log.info(process + " was cancelled by " + user);
        }
    }

    public WfProcess getProcess(User user, Long id) throws ProcessDoesNotExistException {
        Process process = processDAO.getNotNull(id);
        checkPermissionAllowed(user, process, Permission.READ);
        return new WfProcess(process);
    }

    public WfProcess getParentProcess(User user, Long id) throws ProcessDoesNotExistException {
        NodeProcess nodeProcess = nodeProcessDAO.getNodeProcessByChild(id);
        if (nodeProcess == null) {
            return null;
        }
        return new WfProcess(nodeProcess.getProcess());
    }

    public List<WfProcess> getSubprocessesRecursive(User user, Long id, boolean recursive) throws ProcessDoesNotExistException {
        Process process = processDAO.getNotNull(id);
        List<Process> subprocesses;
        if (recursive) {
            subprocesses = nodeProcessDAO.getSubprocessesRecursive(process);
        } else {
            subprocesses = nodeProcessDAO.getSubprocesses(process);
        }
        subprocesses = filterIdentifiable(user, subprocesses, ProcessPermission.READ);
        return getProcesses(subprocesses, null);
    }

    private List<WfProcess> getProcesses(List<Process> processes, List<String> variableNamesToInclude) {
        List<WfProcess> result = Lists.newArrayListWithExpectedSize(processes.size());
        for (Process process : processes) {
            WfProcess wfProcess = new WfProcess(process);
            if (variableNamesToInclude != null) {
                ProcessDefinition processDefinition = getDefinition(process);
                ExecutionContext executionContext = new ExecutionContext(processDefinition, process);
                for (String variableName : variableNamesToInclude) {
                    try {
                        WfVariable variable = executionContext.getVariableProvider().getVariable(variableName);
                        if (variable != null) {
                            wfProcess.addVariable(variable);
                        }
                    } catch (Exception e) {
                        log.error("Unable to get '" + variableName + "' in " + process, e);
                    }
                }
            }
            result.add(wfProcess);
        }
        return result;
    }

    public Long startProcess(User user, String definitionName, Map<String, Object> variables) {
        if (variables == null) {
            variables = Maps.newHashMap();
        }
        ProcessDefinition processDefinition = getLatestDefinition(definitionName);
        checkPermissionAllowed(user, processDefinition.getDeployment(), DefinitionPermission.START_PROCESS);
        Map<String, Object> defaultValues = processDefinition.getDefaultVariableValues();
        for (Map.Entry<String, Object> entry : defaultValues.entrySet()) {
            if (!variables.containsKey(entry.getKey())) {
                variables.put(entry.getKey(), entry.getValue());
            }
        }
        IVariableProvider variableProvider = new DefinitionVariableProvider(processDefinition);
        validateVariables(user, processDefinition, processDefinition.getStartStateNotNull().getNodeId(), variables, variableProvider);
        String transitionName = (String) variables.remove(WfProcess.SELECTED_TRANSITION_KEY);
        Process process = processFactory.startProcess(processDefinition, variables, user.getActor(), transitionName);
        SwimlaneDefinition startTaskSwimlaneDefinition = processDefinition.getStartStateNotNull().getFirstTaskNotNull().getSwimlane();
        Object predefinedProcessStarterObject = variables.get(startTaskSwimlaneDefinition.getName());
        if (predefinedProcessStarterObject != null) {
            Executor predefinedProcessStarter = TypeConversionUtil.convertTo(Executor.class, predefinedProcessStarterObject);
            ExecutionContext executionContext = new ExecutionContext(processDefinition, process);
            process.getSwimlaneNotNull(startTaskSwimlaneDefinition).assignExecutor(executionContext, predefinedProcessStarter, true);
        }
        log.info(process + " was successfully started by " + user);
        return process.getId();
    }

    public byte[] getProcessDiagram(User user, Long processId, Long taskId, Long childProcessId, String subprocessId) {
        try {
            Process process = processDAO.getNotNull(processId);
            checkPermissionAllowed(user, process, ProcessPermission.READ);
            ProcessDefinition processDefinition = getDefinition(process);
            Token highlightedToken = null;
            if (taskId != null) {
                Task task = taskDAO.get(taskId);
                if (task != null) {
                    log.debug("Task id='" + taskId + "' is null due to completion and graph auto-refresh?");
                    highlightedToken = task.getToken();
                }
            }
            if (childProcessId != null) {
                highlightedToken = nodeProcessDAO.getNodeProcessByChild(childProcessId).getParentToken();
            }
            if (subprocessId != null) {
                processDefinition = processDefinition.getEmbeddedSubprocessByIdNotNull(subprocessId);
            }
            ProcessLogs processLogs = new ProcessLogs(processId);
            processLogs.addLogs(processLogDAO.get(processId, processDefinition), false);
            GraphImageBuilder builder = new GraphImageBuilder(processDefinition);
            builder.setHighlightedToken(highlightedToken);
            return builder.createDiagram(process, processLogs);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    public List<GraphElementPresentation> getProcessDiagramElements(User user, Long processId, String subprocessId) {
        Process process = processDAO.getNotNull(processId);
        ProcessDefinition definition = getDefinition(process.getDeployment().getId());
        if (subprocessId != null) {
            definition = definition.getEmbeddedSubprocessByIdNotNull(subprocessId);
        }
        List<NodeProcess> nodeProcesses = nodeProcessDAO.getNodeProcesses(process, null, null, null);
        ProcessLogs processLogs = null;
        if (DrawProperties.isLogsInGraphEnabled()) {
            processLogs = new ProcessLogs(process.getId());
            processLogs.addLogs(processLogDAO.getAll(processId), false);
        }
        ProcessGraphInfoVisitor visitor = new ProcessGraphInfoVisitor(user, definition, process, processLogs, nodeProcesses);
        return getDefinitionGraphElements(user, definition, visitor);
    }

    public byte[] getProcessHistoryDiagram(User user, Long processId, Long taskId, String subprocessId) throws ProcessDoesNotExistException {
        try {
            Process process = processDAO.getNotNull(processId);
            checkPermissionAllowed(user, process, ProcessPermission.READ);
            ProcessDefinition processDefinition = getDefinition(process);
            List<ProcessLog> logs = processLogDAO.getAll(processId);
            List<Executor> executors = executorDAO.getAllExecutors(BatchPresentationFactory.EXECUTORS.createNonPaged());
            GraphHistoryBuilder converter = new GraphHistoryBuilder(executors, processDefinition, logs, subprocessId);
            return converter.createDiagram(process, processLogDAO.getPassedTransitions(processDefinition, process));
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    public List<GraphElementPresentation> getProcessHistoryDiagramElements(User user, Long processId, Long taskId, String subprocessId)
            throws ProcessDoesNotExistException {
        try {
            Process process = processDAO.getNotNull(processId);
            checkPermissionAllowed(user, process, ProcessPermission.READ);
            ProcessDefinition processDefinition = getDefinition(process);
            List<ProcessLog> logs = processLogDAO.getAll(processId);
            List<Executor> executors = executorDAO.getAllExecutors(BatchPresentationFactory.EXECUTORS.createNonPaged());
            GraphHistoryBuilder converter = new GraphHistoryBuilder(executors, processDefinition, logs, "" + subprocessId);
            converter.createDiagram(process, processLogDAO.getPassedTransitions(processDefinition, process));
            return converter.getLogElements();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

}
