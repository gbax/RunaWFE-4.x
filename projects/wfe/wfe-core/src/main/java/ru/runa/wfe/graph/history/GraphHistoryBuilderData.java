package ru.runa.wfe.graph.history;

import java.util.HashMap;
import java.util.List;

import ru.runa.wfe.audit.NodeEnterLog;
import ru.runa.wfe.audit.NodeLog;
import ru.runa.wfe.audit.ProcessLog;
import ru.runa.wfe.audit.TaskLog;
import ru.runa.wfe.audit.TransitionLog;
import ru.runa.wfe.execution.Process;
import ru.runa.wfe.lang.Node;
import ru.runa.wfe.lang.NodeType;
import ru.runa.wfe.lang.ProcessDefinition;
import ru.runa.wfe.lang.SubprocessDefinition;
import ru.runa.wfe.user.Executor;

import com.google.common.collect.Lists;

/**
 * Model to parse and store data, required to build history from logs.
 */
public class GraphHistoryBuilderData {

    /**
     * Maps executor name to executor.
     */
    private final HashMap<String, Executor> executors = new HashMap<String, Executor>();
    /**
     * Model with process definition data.
     */
    private final ProcessInstanceData processInstanceData;
    /**
     * Model with passed transitions.
     */
    private final TransitionLogData transitions;
    /**
     * All process logs to build history. (Without embedded subprocess or
     * embedded subprocess only).
     */
    private final List<ProcessLog> processLogs;
    /**
     * All instances of {@link NodeLog} to build history.
     */
    private final List<NodeLog> nodeLogs = Lists.newArrayList();
    /**
     * All instances of {@link TaskLog} to build history.
     */
    private final List<TaskLog> taskLogs = Lists.newArrayList();
    /**
     * Component to parse logs into main process and subprocesses logs.
     */
    private final EmbeddedSubprocessLogsData embeddedLogsParser;

    /**
     * Creates model to parse and store data, required to build history from
     * logs.
     * 
     * @param executors
     *            Executors, defined in system.
     * @param processDefinition
     *            Process definition.
     * @param fullProcessLogs
     *            All logs for process instance.
     * @param subProcessId
     *            Subprocess name, if history for embedded subprocess is
     *            required.
     */
    public GraphHistoryBuilderData(List<Executor> executors, Process processInstance, ProcessDefinition processDefinition,
            List<ProcessLog> fullProcessLogs, String subProcessId) {
        processInstanceData = new ProcessInstanceData(processInstance, processDefinition);
        transitions = new TransitionLogData(fullProcessLogs);
        for (Executor executor : executors) {
            this.executors.put(executor.getName(), executor);
        }
        embeddedLogsParser = new EmbeddedSubprocessLogsData(fullProcessLogs, transitions, getProcessInstanceData());
        processLogs = prepareLogs(fullProcessLogs, subProcessId);
    }

    /**
     * Parse process instance logs and fill model data.
     * 
     * @param fullProcessLogs
     *            All logs for process instance.
     * @param subProcessId
     *            Subprocess name, if history for embedded subprocess is
     *            required.
     * @return Returns logs to build history.
     */
    private List<ProcessLog> prepareLogs(List<ProcessLog> fullProcessLogs, String subProcessId) {
        final boolean isForEmbeddedSubprocess = subProcessId != null && !"null".equals(subProcessId);
        List<ProcessLog> processLogForProcessing = embeddedLogsParser.getProcessLogs(subProcessId);
        for (ProcessLog processLog : processLogForProcessing) {
            if (processLog instanceof NodeLog) {
                if (isForEmbeddedSubprocess && processLog instanceof NodeEnterLog && NodeType.START_EVENT == ((NodeLog) processLog).getNodeType()) {
                    continue;
                }
                nodeLogs.add((NodeLog) processLog);
            } else if (processLog instanceof TaskLog) {
                taskLogs.add((TaskLog) processLog);
            }
        }
        return processLogForProcessing;
    }

    /**
     * Get {@link Node} by node id.
     * 
     * @param nodeId
     *            Node id.
     * @return Returns {@link Node} or null, if node is not found.
     */
    public Node getNode(String nodeId) {
        return getProcessInstanceData().getNode(nodeId);
    }

    /**
     * Get executor by it name. May return null if executor with such name is
     * not exists.
     * 
     * @param executorName
     *            Executor name.
     * @return Returns executor with given name or null.
     */
    public Executor getExecutorByName(String executorName) {
        return executors.get(executorName);
    }

    /**
     * Get process logs to build history. Embedded subprocess logs is omitted
     * (or only embedded subprocess log is returned)
     * 
     * @return Returns process logs to build history.
     */
    public List<ProcessLog> getProcessLogs() {
        return processLogs;
    }

    /**
     * Get all {@link NodeLog} instances to build history.
     * 
     * @return Returns all {@link NodeLog} instances.
     */
    public List<NodeLog> getNodeLogs() {
        return nodeLogs;
    }

    /**
     * Get all {@link TaskLog} instances to build history.
     * 
     * @return Returns all {@link TaskLog} instances.
     */
    public List<TaskLog> getTaskLogs() {
        return taskLogs;
    }

    /**
     * Get embedded subprocess definition for given name.
     * 
     * @param subProcessName
     *            Subprocess definition name.
     * @return Returns subprocess definition.
     */
    public SubprocessDefinition getEmbeddedSubprocess(String subProcessName) {
        return getProcessInstanceData().getEmbeddedSubprocess(subProcessName);
    }

    /**
     * Search first transition log from given node, which happens after given
     * log entry.
     * 
     * @param log
     *            Log entry, after which transition log must be searched.
     * @param nodeId
     *            Node id, from which transition must be moved.
     * @return Returns transition log or null, if not found.
     */
    public TransitionLog findNextTransitionLog(NodeLog log, String nodeId) {
        return transitions.findNextTransitionLog(log, nodeId);
    }

    public ProcessInstanceData getProcessInstanceData() {
        return processInstanceData;
    }
}
