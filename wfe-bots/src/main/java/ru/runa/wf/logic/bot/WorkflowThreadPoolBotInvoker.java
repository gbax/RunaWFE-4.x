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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.runa.wfe.bot.Bot;
import ru.runa.wfe.bot.BotStation;
import ru.runa.wfe.bot.BotTask;
import ru.runa.wfe.bot.invoker.BotInvoker;
import ru.runa.wfe.execution.logic.ProcessExecutionErrors;
import ru.runa.wfe.security.AuthenticationException;
import ru.runa.wfe.service.delegate.Delegates;
import ru.runa.wfe.task.dto.WfTask;
import ru.runa.wfe.user.User;

import com.google.common.collect.Maps;

public class WorkflowThreadPoolBotInvoker implements BotInvoker, Runnable {
    private final Log log = LogFactory.getLog(WorkflowThreadPoolBotInvoker.class);
    private ScheduledExecutorService executor = null;
    private long configurationVersion = -1;
    private final Map<Bot, WorkflowBotExecutor> botExecutors = Maps.newHashMap();
    private Future<?> botInvokerInvocation = null;
    private final Map<WorkflowBotTaskExecutor, ScheduledFuture<?>> scheduledTasks = Maps.newConcurrentMap();

    private final long STUCK_TIMEOUT_SECONDS = 300;
    private BotStation botStation;

    /**
     * Checking botInvokerInvocation.isDone() leads to run() method called only
     * once per moment.
     */
    @Override
    public synchronized void invokeBots(BotStation botStation) {
        this.botStation = botStation;
        if (botInvokerInvocation != null && !botInvokerInvocation.isDone()) {
            log.debug("botInvokerInvocation != null && !botInvokerInvocation.isDone()");
            return;
        }
        if (executor == null) {
            log.debug("Creating new executor(ScheduledExecutorService)");
            executor = new ScheduledThreadPoolExecutor(BotStationResources.getThreadPoolSize());
        }
        checkStuckBots();
        botInvokerInvocation = executor.schedule(this, 1000, TimeUnit.MILLISECONDS);
        logBotsActivites();
    }

    private void checkStuckBots() {
        try {
            for (Iterator<Entry<WorkflowBotTaskExecutor, ScheduledFuture<?>>> iter = scheduledTasks.entrySet().iterator(); iter.hasNext();) {
                Entry<WorkflowBotTaskExecutor, ScheduledFuture<?>> entry = iter.next();
                if (entry.getValue().isDone()) {
                    iter.remove();
                    continue;
                }
                WorkflowBotTaskExecutor executor = entry.getKey();
                if (executor.getExecutionStatus() == WorkflowBotTaskExecutionStatus.STARTED
                        && executor.getExecutionInSeconds() > STUCK_TIMEOUT_SECONDS) {
                    if (executor.interruptExecution()) {
                        iter.remove();
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Stuck threads search/stop is throwing exception.", e);
        }
    }

    private void configure() {
        try {
            if (botStation.getVersion() != configurationVersion) {
                log.info("Will update bots configuration.");
                String username = BotStationResources.getSystemUsername();
                String password = BotStationResources.getSystemPassword();
                User botStationUser = Delegates.getAuthenticationService().authenticateByLoginPassword(username, password);
                Map<Bot, WorkflowBotExecutor> existingBotExecutors = Maps.newHashMap(botExecutors);
                botExecutors.clear();
                List<Bot> bots = Delegates.getBotService().getBots(botStationUser, botStation.getId());
                for (Bot bot : bots) {
                    try {
                        log.info("Configuring " + bot.getUsername());
                        User user = Delegates.getAuthenticationService().authenticateByLoginPassword(bot.getUsername(), bot.getPassword());
                        List<BotTask> tasks = Delegates.getBotService().getBotTasks(user, bot.getId());
                        if (existingBotExecutors.containsKey(bot)) {
                            WorkflowBotExecutor botExecutor = existingBotExecutors.get(bot);
                            botExecutor.reinitialize(tasks);
                            botExecutors.put(bot, botExecutor);
                        } else {
                            botExecutors.put(bot, new WorkflowBotExecutor(user, bot, tasks));
                        }
                        ProcessExecutionErrors.removeBotTaskConfigurationError(bot, null);
                    } catch (Exception e) {
                        log.error("Unable to configure " + bot);
                        ProcessExecutionErrors.addBotTaskConfigurationError(bot, null, e);
                    }
                }
                configurationVersion = botStation.getVersion();
            } else {
                log.debug("bots configuration is up to date, version = " + botStation.getVersion());
            }
        } catch (Throwable th) {
            log.error("Botstation configuration error. ", th);
        }
    }

    /**
     * Checking botInvokerInvocation.isDone() in synchronized invokeBots method
     * leads to run() method called only once per moment.
     */
    @Override
    public void run() {
        configure();
        if (executor == null) {
            log.warn("executor(ScheduledExecutorService) == null");
            return;
        }
        for (WorkflowBotExecutor botExecutor : botExecutors.values()) {
            try {
                Set<WfTask> tasks = botExecutor.getNewTasks();
                for (WfTask task : tasks) {
                    WorkflowBotTaskExecutor botTaskExecutor = botExecutor.createBotTaskExecutor(task);
                    ScheduledFuture<?> future = executor.schedule(botTaskExecutor, 200, TimeUnit.MILLISECONDS);
                    scheduledTasks.put(botTaskExecutor, future);
                }
            } catch (AuthenticationException e) {
                configurationVersion = -1;
                log.error("BotRunner execution failed. Will recreate botstation settings and bots.", e);
            } catch (Exception e) {
                log.error("BotRunner execution failed.", e);
            }
        }
    }

    private void logBotsActivites() {
        BotLogger botLogger = BotStationResources.createBotLogger();
        if (botLogger == null) {
            return;
        }
        botLogger.logActivity();
    }
}
