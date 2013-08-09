package ru.runa.wfe.audit;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.apache.commons.logging.LogFactory;

import ru.runa.wfe.commons.SafeIndefiniteLoop;
import ru.runa.wfe.lang.NodeType;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProcessLogs implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<ProcessLog> logs = Lists.newArrayList();
    private final HashMap<Long, Long> subprocessToProcessIds = Maps.newHashMap();

    public ProcessLogs() {
    }

    public ProcessLogs(Long processId) {
        subprocessToProcessIds.put(processId, null);
    }

    public void addLogs(List<ProcessLog> processLogs, boolean withSubprocesses) {
        logs.addAll(processLogs);
        if (withSubprocesses) {
            for (ProcessLog log : processLogs) {
                if (log instanceof SubprocessStartLog) {
                    Long subprocessId = ((SubprocessStartLog) log).getSubprocessId();
                    subprocessToProcessIds.put(subprocessId, log.getProcessId());
                }
            }
        }
        Collections.sort(logs);
    }

    public int getMaxSubprocessLevel() {
        final Map<Long, Long> tmpIds = Maps.newHashMap(subprocessToProcessIds);
        final Map<Long, Integer> levels = Maps.newHashMap();
        new SafeIndefiniteLoop(100) {

            @Override
            protected boolean continueLoop() {
                return !tmpIds.isEmpty();
            }

            @Override
            protected void doOp() {
                for (Map.Entry<Long, Long> entry : tmpIds.entrySet()) {
                    if (levels.containsKey(entry.getKey())) {
                        continue;
                    }
                    if (entry.getValue() == null) {
                        levels.put(entry.getKey(), 0);
                        tmpIds.remove(entry.getKey());
                        break;
                    }
                    if (levels.containsKey(entry.getValue())) {
                        levels.put(entry.getKey(), levels.get(entry.getValue()) + 1);
                        tmpIds.remove(entry.getKey());
                        break;
                    }
                }
            }

        }.doLoop();
        int level = 0;
        for (Integer l : levels.values()) {
            if (l > level) {
                level = l;
            }
        }
        return level;
    }

    public List<Long> getSubprocessIds(ProcessLog processLog) {
        List<Long> result = Lists.newArrayList();
        Long processId = processLog.getProcessId();
        while (subprocessToProcessIds.get(processId) != null) {
            result.add(processId);
            processId = subprocessToProcessIds.get(processId);
        }
        Collections.reverse(result);
        return result;
    }

    public List<ProcessLog> getLogs() {
        return logs;
    }

    public <T extends ProcessLog> T getFirstOrNull(Class<T> logClass) {
        for (ProcessLog log : logs) {
            if (log.getClass() == logClass) {
                return (T) log;
            }
        }
        return null;
    }

    public <T extends ProcessLog> List<T> getLogs(Class<T> logClass) {
        List<T> list = Lists.newArrayList();
        for (ProcessLog log : logs) {
            if (log.getClass() == logClass) {
                list.add((T) log);
            }
        }
        return list;
    }

    public Map<TaskCreateLog, TaskEndLog> getTaskLogs() {
        Map<String, TaskCreateLog> tmpByTaskName = Maps.newHashMap();
        Map<String, TaskCreateLog> tmpByNodeId = Maps.newHashMap();
        Map<TaskCreateLog, TaskEndLog> result = Maps.newHashMap();
        boolean compatibilityMode = false;
        for (ProcessLog log : logs) {
            if (log instanceof TaskCreateLog) {
                TaskCreateLog taskCreateLog = (TaskCreateLog) log;
                String key = log.getProcessId() + taskCreateLog.getTaskName();
                tmpByTaskName.put(key, taskCreateLog);
                key = log.getProcessId() + taskCreateLog.getNodeId();
                tmpByNodeId.put(key, taskCreateLog);
            }
            if (log instanceof TaskEndLog) {
                TaskEndLog taskEndLog = (TaskEndLog) log;
                TaskCreateLog taskCreateLog;
                if (taskEndLog.getNodeId() != null) {
                    String key = log.getProcessId() + taskEndLog.getNodeId();
                    taskCreateLog = tmpByNodeId.remove(key);
                    if (taskCreateLog != null) {
                        tmpByTaskName.remove(log.getProcessId() + taskCreateLog.getTaskName());
                    }
                } else {
                    String key = log.getProcessId() + taskEndLog.getTaskName();
                    taskCreateLog = tmpByTaskName.remove(key);
                    compatibilityMode = true;
                }
                if (taskCreateLog == null) {
                    // incorrect algorithm execution caused by:
                    // TODO cycle execution
                    // TODO multi tasks
                    LogFactory.getLog(getClass()).error("No TaskCreateLog for " + log);
                    continue;
                }
                result.put(taskCreateLog, taskEndLog);
            }
            if (log instanceof NodeLeaveLog) {
                NodeLeaveLog nodeLeaveLog = (NodeLeaveLog) log;
                if (NodeType.START_EVENT == nodeLeaveLog.getNodeType()) {
                    ProcessStartLog processStartLog = getFirstOrNull(ProcessStartLog.class);
                    if (processStartLog == null) {
                        continue;
                    }
                    TaskCreateLog taskCreateLog = new TaskCreateLog();
                    taskCreateLog.setId(processStartLog.getId());
                    taskCreateLog.setDate(nodeLeaveLog.getDate());
                    taskCreateLog.setProcessId(nodeLeaveLog.getProcessId());
                    taskCreateLog.setSeverity(nodeLeaveLog.getSeverity());
                    taskCreateLog.setTokenId(nodeLeaveLog.getTokenId());
                    taskCreateLog.addAttribute(IAttributes.ATTR_TASK_NAME, nodeLeaveLog.getNodeName());
                    TaskEndLog taskEndLog = new TaskEndLog();
                    taskEndLog.setId(processStartLog.getId());
                    taskEndLog.setDate(nodeLeaveLog.getDate());
                    taskEndLog.setProcessId(nodeLeaveLog.getProcessId());
                    taskEndLog.setSeverity(nodeLeaveLog.getSeverity());
                    taskEndLog.setTokenId(nodeLeaveLog.getTokenId());
                    taskEndLog.addAttribute(IAttributes.ATTR_TASK_NAME, nodeLeaveLog.getNodeName());
                    taskEndLog.addAttribute(IAttributes.ATTR_ACTOR_NAME, processStartLog.getActorName());
                    result.put(taskCreateLog, taskEndLog);
                }
            }
        }
        // unfinished tasks
        for (TaskCreateLog taskCreateLog : compatibilityMode ? tmpByTaskName.values() : tmpByNodeId.values()) {
            result.put(taskCreateLog, null);
        }
        return result;
    }
}
