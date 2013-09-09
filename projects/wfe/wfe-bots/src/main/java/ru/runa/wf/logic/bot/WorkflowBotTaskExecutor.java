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
package ru.runa.wf.logic.bot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.runa.wfe.bot.Bot;
import ru.runa.wfe.bot.BotTask;
import ru.runa.wfe.commons.CalendarInterval;
import ru.runa.wfe.commons.ClassLoaderUtil;
import ru.runa.wfe.execution.logic.ProcessExecutionErrors;
import ru.runa.wfe.execution.logic.ProcessExecutionException;
import ru.runa.wfe.extension.TaskHandler;
import ru.runa.wfe.service.client.DelegateProcessVariableProvider;
import ru.runa.wfe.service.delegate.Delegates;
import ru.runa.wfe.task.TaskDoesNotExistException;
import ru.runa.wfe.task.dto.WfTask;
import ru.runa.wfe.user.User;
import ru.runa.wfe.var.IVariableProvider;

import com.google.common.base.Objects;
import com.google.common.base.Throwables;

/**
 * Execute task handlers for particular bot.
 * 
 * Configures and executes task handler in same method.
 * 
 * @author Dofs
 * @since 4.0
 */
public class WorkflowBotTaskExecutor implements Runnable {
    private static final Log log = LogFactory.getLog(WorkflowBotTaskExecutor.class);
    /**
     * first wait is 30 sec. Next wait is 2*wait, but no more
     * failedTasksMaxDelayPeriod
     */
    private final static int FAILED_EXECUTION_INITIAL_DELAY_SECONDS = 30;
    /**
     * 2 hours
     */
    private final static int FAILED_EXECUTION_MAX_DELAY_SECONDS = 7200;

    private final WorkflowBotExecutor botExecutor;
    private final WfTask task;
    private WorkflowBotTaskExecutionStatus executionStatus = WorkflowBotTaskExecutionStatus.SCHEDULED;
    private Calendar started = Calendar.getInstance();
    /**
     * Next wait is 2*wait, but no more FAILED_EXECUTION_MAX_DELAY_SECONDS
     */
    private int failedDelaySeconds = FAILED_EXECUTION_INITIAL_DELAY_SECONDS;
    private Thread executionThread = null;
    private boolean threadInterrupting = false;

    public WorkflowBotTaskExecutor(WorkflowBotExecutor botExecutor, WfTask task) {
        this.botExecutor = botExecutor;
        this.task = task;
    }

    public WorkflowBotTaskExecutionStatus getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(WorkflowBotTaskExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
    }

    public WfTask getTask() {
        return task;
    }

    public boolean interruptExecution() {
        if (executionThread == null) {
            return false;
        }
        executionStatus = WorkflowBotTaskExecutionStatus.FAILED;
        if (!threadInterrupting) {
            // Try to stop thread soft
            log.warn(this + " seems to be stuck (not completted at " + getExecutionInSeconds() + " sec). Interrupt signal will be send.");
            executionThread.interrupt();
            return false;
        } else {
            log.error(this + " seems to be stuck (not completted at " + getExecutionInSeconds() + " sec). Will be terminated.");
            executionThread.stop();
            return true;
        }
    }

    public int getExecutionInSeconds() {
        return new CalendarInterval(started, Calendar.getInstance()).getLengthInSeconds();
    }

    public boolean isReadyToAttemptExecuteFailedTask() {
        return getExecutionStatus() == WorkflowBotTaskExecutionStatus.FAILED && started.before(Calendar.getInstance());
    }

    private void doHandle() throws Exception {
        TaskHandler taskHandler = null;
        User user = botExecutor.getUser();
        Bot bot = botExecutor.getBot();
        BotTask botTask = null;
        IVariableProvider variableProvider = new DelegateProcessVariableProvider(user, task.getProcessId());
        try {
            String botTaskName = BotTaskConfigurationUtils.getBotTaskName(user, task);
            botTask = botExecutor.getBotTasks().get(botTaskName);
            if (botTask == null) {
                log.error("No handler for bot task " + botTaskName + " in " + bot);
                throw new ProcessExecutionException(ProcessExecutionException.BOT_TASK_MISSED, botTaskName, bot.getUsername());
            }
            taskHandler = ClassLoaderUtil.instantiate(botTask.getTaskHandlerClassName());
            try {
                if (BotTaskConfigurationUtils.isExtendedBotTaskConfiguration(botTask.getConfiguration())) {
                    if (botTask.getConfiguration() != null) {
                        byte[] configuration = BotTaskConfigurationUtils.substituteExtendedConfiguration(user, task, botTask.getConfiguration(),
                                variableProvider);
                        taskHandler.setConfiguration(configuration);
                    }
                } else if (BotTaskConfigurationUtils.isParameterizedBotTaskConfiguration(botTask.getConfiguration())) {
                    byte[] configuration = BotTaskConfigurationUtils.substituteParameterizedConfiguration(user, task, botTask.getConfiguration(),
                            variableProvider);
                    taskHandler.setConfiguration(configuration);
                } else {
                    taskHandler.setConfiguration(botTask.getConfiguration());
                }
                log.info("Configured taskHandler for " + botTask.getName());
                ProcessExecutionErrors.removeBotTaskConfigurationError(bot, botTask);
            } catch (Throwable th) {
                ProcessExecutionErrors.addBotTaskConfigurationError(bot, botTask, th);
                log.error("Can't create handler for bot " + bot + " (task is " + botTask + ")", th);
                throw new ProcessExecutionException(ProcessExecutionException.BOT_TASK_CONFIGURATION_ERROR, th, botTaskName, th.getMessage());
            }

            log.info("Starting bot task " + task + " with config \n" + taskHandler.getConfiguration());
            Map<String, Object> variables = taskHandler.handle(user, variableProvider, task);
            if (variables == null) {
                variables = new HashMap<String, Object>();
            }
            Object skipTaskCompletion = variables.remove(TaskHandler.SKIP_TASK_COMPLETION_VARIABLE_NAME);
            if (Objects.equal(Boolean.TRUE, skipTaskCompletion)) {
                log.info("Bot task " + task + " postponed (skipTaskCompletion) by task handler " + taskHandler.getClass());
            } else {
                Delegates.getExecutionService().completeTask(user, task.getId(), variables, null);
                log.debug("Handled bot task " + task + ", " + bot + " by " + taskHandler.getClass());
            }
            ProcessExecutionErrors.removeProcessError(task.getProcessId(), task.getNodeId());
        } catch (TaskDoesNotExistException e) {
            log.warn(task + " already handled");
            ProcessExecutionErrors.removeProcessError(task.getProcessId(), task.getNodeId());
        } catch (Throwable th) {
            ProcessExecutionErrors.addProcessError(task, botTask, th);
            if (taskHandler != null) {
                try {
                    taskHandler.onRollback(user, variableProvider, task);
                } catch (Exception e) {
                    log.error("onRollback failed in task handler " + taskHandler, e);
                }
            }
            throw Throwables.propagate(th);
        }
    }

    @Override
    public void run() {
        try {
            started = Calendar.getInstance();
            executionThread = Thread.currentThread();
            executionStatus = WorkflowBotTaskExecutionStatus.STARTED;
            doHandle();
            executionStatus = WorkflowBotTaskExecutionStatus.COMPLETED;
            return;
        } catch (Throwable e) {
            log.error("Error execution " + this, e);
            logBotError(task, e);
            executionStatus = WorkflowBotTaskExecutionStatus.FAILED;
            // Double delay if exists
            failedDelaySeconds *= 2;
            if (failedDelaySeconds > FAILED_EXECUTION_MAX_DELAY_SECONDS) {
                failedDelaySeconds = FAILED_EXECUTION_MAX_DELAY_SECONDS;
            }
            log.info("FailedDelaySeconds = " + failedDelaySeconds + " for " + task);
            started.add(Calendar.SECOND, failedDelaySeconds);
        }
        executionThread = null;
        return;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(task);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WorkflowBotTaskExecutor) {
            WorkflowBotTaskExecutor wbte = (WorkflowBotTaskExecutor) obj;
            return Objects.equal(task, wbte.task);
        }
        return false;
    }

    @Override
    public String toString() {
        return botExecutor.getBot() + " with task " + task + "; status: " + executionStatus;
    }

    private void logBotError(WfTask task, Throwable th) {
        BotLogger botLogger = BotStationResources.createBotLogger();
        if (botLogger == null) {
            return;
        }
        botLogger.logError(task, th);
    }
}
