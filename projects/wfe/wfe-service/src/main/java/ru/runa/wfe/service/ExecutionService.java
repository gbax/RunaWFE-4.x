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
package ru.runa.wfe.service;

import java.util.List;
import java.util.Map;

import ru.runa.wfe.audit.ProcessLogFilter;
import ru.runa.wfe.audit.ProcessLogs;
import ru.runa.wfe.audit.SystemLog;
import ru.runa.wfe.definition.DefinitionDoesNotExistException;
import ru.runa.wfe.execution.ParentProcessExistsException;
import ru.runa.wfe.execution.ProcessDoesNotExistException;
import ru.runa.wfe.execution.ProcessFilter;
import ru.runa.wfe.execution.dto.WfProcess;
import ru.runa.wfe.execution.dto.WfSwimlane;
import ru.runa.wfe.graph.view.GraphElementPresentation;
import ru.runa.wfe.presentation.BatchPresentation;
import ru.runa.wfe.task.TaskAlreadyAcceptedException;
import ru.runa.wfe.task.TaskDoesNotExistException;
import ru.runa.wfe.task.dto.WfTask;
import ru.runa.wfe.user.Executor;
import ru.runa.wfe.user.User;
import ru.runa.wfe.validation.ValidationException;
import ru.runa.wfe.var.dto.WfVariable;

/**
 * Process execution service.
 * 
 * @author Dofs
 * @since 4.0
 */
public interface ExecutionService {

    /**
     * Starts new process by definition.
     * 
     * @param user
     *            authorized user
     * @param definitionName
     *            process definition name
     * @param variables
     *            initial variable values
     * @return id of started process
     * @throws DefinitionDoesNotExistException
     * @throws ValidationException
     */
    public Long startProcess(User user, String definitionName, Map<String, Object> variables) throws DefinitionDoesNotExistException,
            ValidationException;

    /**
     * Gets process count for {@link BatchPresentation}.
     * 
     * @param user
     *            authorized user
     * @param batchPresentation
     * @return not <code>null</code>
     */
    public int getAllProcessesCount(User user, BatchPresentation batchPresentation);

    /**
     * Gets processes for {@link BatchPresentation}.
     * 
     * @param user
     *            authorized user
     * @param batchPresentation
     * @return not <code>null</code>
     */
    public List<WfProcess> getProcesses(User user, BatchPresentation batchPresentation);

    /**
     * Gets process by id.
     * 
     * @param user
     *            authorized user
     * @param processId
     *            process id process id
     * @return not <code>null</code>
     * @throws ProcessDoesNotExistException
     */
    public WfProcess getProcess(User user, Long processId) throws ProcessDoesNotExistException;

    /**
     * Gets parent process if this process will be started as subprocess.
     * 
     * @param user
     *            authorized user
     * @param processId
     *            process id process id
     * @return parent process or <code>null</code>
     * @throws ProcessDoesNotExistException
     */
    public WfProcess getParentProcess(User user, Long processId) throws ProcessDoesNotExistException;

    /**
     * Get all subprocesses (recursively) by process id.
     * 
     * @param user
     *            authorized user
     * @param processId
     *            process id process id
     * @return not <code>null</code>
     * @throws ProcessDoesNotExistException
     */
    public List<WfProcess> getSubprocessesRecursive(User user, Long processId) throws ProcessDoesNotExistException;

    /**
     * Cancels process by id.
     * 
     * @param user
     *            authorized user
     * @param processId
     *            process id process id
     * @throws ProcessDoesNotExistException
     */
    public void cancelProcess(User user, Long processId) throws ProcessDoesNotExistException;

    /**
     * Gets tasks by {@link BatchPresentation}.
     * 
     * @param user
     *            authorized user
     * @param batchPresentation
     * @return not <code>null</code>
     */
    public List<WfTask> getTasks(User user, BatchPresentation batchPresentation);

    /**
     * Gets task by id.
     * 
     * @param user
     *            authorized user
     * @param taskId
     *            task id
     * @return not <code>null</code>
     * @throws TaskDoesNotExistException
     */
    public WfTask getTask(User user, Long taskId) throws TaskDoesNotExistException;

    /**
     * Gets all process tasks.
     * 
     * @param user
     *            authorized user
     * @param processId
     *            process id process id
     * @return not <code>null</code>
     * @throws ProcessDoesNotExistException
     */
    public List<WfTask> getProcessTasks(User user, Long processId) throws ProcessDoesNotExistException;

    /**
     * Reassigns task to another executor.
     * 
     * @param previousOwner
     *            old executor (check for multi-threaded change)
     * @param newOwner
     *            new executor
     * @throws TaskAlreadyAcceptedException
     *             if previous owner differs from provided
     */
    public void assignTask(User user, Long taskId, Executor previousOwner, Executor newOwner) throws TaskAlreadyAcceptedException;

    /**
     * Completes task by id.
     * 
     * @param user
     *            authorized user
     * @param taskId
     *            task id
     * @param variables
     *            variable value
     * @param swimlaneActorId
     *            actor id who will be assigned to task swimlane, can be
     *            <code>null</code>
     * @throws TaskDoesNotExistException
     * @throws ValidationException
     */
    public void completeTask(User user, Long taskId, Map<String, Object> variables, Long swimlaneActorId) throws TaskDoesNotExistException,
            ValidationException;

    /**
     * Gets all initialized process roles.
     * 
     * @param user
     *            authorized user
     * @param processId
     *            process id process id
     * @return not <code>null</code>
     * @throws ProcessDoesNotExistException
     */
    public List<WfSwimlane> getSwimlanes(User user, Long processId) throws ProcessDoesNotExistException;

    /**
     * Assigns role by name to specified executor.
     * 
     * @param user
     *            authorized user
     * @param processId
     *            process id
     * @param swimlaneName
     *            swimlane name
     * @param executor
     *            new role executor
     * @throws ProcessDoesNotExistException
     */
    public void assignSwimlane(User user, Long processId, String swimlaneName, Executor executor) throws ProcessDoesNotExistException;

    /**
     * Gets all process variables.
     * 
     * @param user
     *            authorized user
     * @param processId
     *            process id
     * @return not <code>null</code>
     * @throws ProcessDoesNotExistException
     */
    public List<WfVariable> getVariables(User user, Long processId) throws ProcessDoesNotExistException;

    /**
     * Gets variable by name from process.
     * 
     * @param user
     *            authorized user
     * @param processId
     *            process id
     * @param variableName
     *            variable name
     * @return not <code>null</code>
     * @throws ProcessDoesNotExistException
     */
    public WfVariable getVariable(User user, Long processId, String variableName) throws ProcessDoesNotExistException;

    /**
     * Gets variables by name from different processes.
     * 
     * @param user
     *            authorized user
     * @param processId
     *            process ids
     * @param variableName
     *            variable name
     * @return not <code>null</code>
     */
    public Map<Long, WfVariable> getVariablesFromProcesses(User user, List<Long> processIds, String variableName);

    /**
     * Updates process variables without any signalling.
     * 
     * @param user
     *            authorized user
     * @param processId
     *            process id
     * @param variables
     *            variable values
     * @throws ProcessDoesNotExistException
     */
    public void updateVariables(User user, Long processId, Map<String, Object> variables) throws ProcessDoesNotExistException;

    /**
     * Gets process diagram as PNG image.
     * 
     * @param user
     *            authorized user
     * @param processId
     *            process id
     * @param taskId
     *            active task id
     * @param childProcessId
     * @return not <code>null</code>
     * @throws ProcessDoesNotExistException
     */
    public byte[] getProcessDiagram(User user, Long processId, Long taskId, Long childProcessId) throws ProcessDoesNotExistException;

    /**
     * Gets process graph elements for diagram.
     * 
     * @param user
     *            authorized user
     * @param processId
     *            process id
     * @return not <code>null</code>
     * @throws ProcessDoesNotExistException
     */
    public List<GraphElementPresentation> getProcessGraphElements(User user, Long processId) throws ProcessDoesNotExistException;

    /**
     * Gets process graph element for history diagram.
     * 
     * @param user
     *            authorized user
     * @param processId
     *            process id
     * @param taskId
     *            active task id
     * @return not <code>null</code>
     * @throws ProcessDoesNotExistException
     */
    public List<GraphElementPresentation> getProcessUIHistoryData(User user, Long processId, Long taskId) throws ProcessDoesNotExistException;

    /**
     * Gets process history graphical diagram PNG image.
     * 
     * @param user
     *            authorized user
     * @param processId
     *            process id
     * @param taskId
     *            task id
     * @return not <code>null</code>
     * @throws ProcessDoesNotExistException
     */
    public byte[] getProcessHistoryDiagram(User user, Long processId, Long taskId) throws ProcessDoesNotExistException;

    /**
     * Marks task as read.
     * 
     * @param user
     *            authorized user
     * @param taskId
     *            task id
     * @throws TaskDoesNotExistException
     */
    public void markTaskOpened(User user, Long taskId) throws TaskDoesNotExistException;

    /**
     * Gets process logs by filter.
     * 
     * @param user
     *            authorized user
     * @param filter
     *            process log filter
     * @return not <code>null</code>
     */
    public ProcessLogs getProcessLogs(User user, ProcessLogFilter filter);

    /**
     * Gets process log byte array value.
     * 
     * @param user
     *            authorized user
     * @param logId
     *            process log id
     * @return value or <code>null</code>
     */
    public Object getProcessLogValue(User user, Long logId);

    /**
     * Removes processes by filter criterias.
     */
    public void removeProcesses(User user, ProcessFilter filter) throws ProcessDoesNotExistException, ParentProcessExistsException;

    /**
     * Gets system logs for {@link BatchPresentation}.
     * 
     * @param user
     *            authorized user
     * @param batchPresentation
     * @return not <code>null</code>
     */
    public List<SystemLog> getSystemLogs(User user, BatchPresentation batchPresentation);

    /**
     * Gets system log count for {@link BatchPresentation}.
     * 
     * @param user
     *            authorized user
     * @param batchPresentation
     * @return not <code>null</code>
     */
    public int getSystemLogsCount(User user, BatchPresentation batchPresentation);
}
