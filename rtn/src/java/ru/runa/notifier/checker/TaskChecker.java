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

package ru.runa.notifier.checker;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.runa.af.AuthenticationException;
import ru.runa.delegate.DelegateFactory;
import ru.runa.notifier.GUI;
import ru.runa.notifier.auth.LoginHelper;
import ru.runa.notifier.tray.SystemTray;
import ru.runa.notifier.util.AePlayWave;
import ru.runa.notifier.util.ResourcesManager;
import ru.runa.wf.TaskStub;
import ru.runa.wf.presentation.WFProfileStrategy;
import ru.runa.wf.service.ExecutionService;

/**
 * Created on 2006
 * 
 * @author Gritsenko_S
 */
public class TaskChecker {
    private static final Log log = LogFactory.getLog(TaskChecker.class);

    private final PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    public static final String ERROR_OCCURED_PROPERTY = "errorOccured";

    private boolean errorOccured;

    private final TasksChecker tasksChecker;
    
    private final UnreadTaskNotifier unreadTaskNotifier;

    private Date lastTasksDate = null;

    private String onNewTaskTriggerCommand = ResourcesManager.getOnNewTaskTriggerCommand();

    public TaskChecker(SystemTray systemTray) {
        this.tasksChecker = new TasksChecker(systemTray);
        this.errorOccured = true;
        this.unreadTaskNotifier = new UnreadTaskNotifier();
    }

    private Timer timer1;
    private Timer timer2;

    public void start() {
        timer1 = new Timer();
        timer1.schedule(tasksChecker, 0, ResourcesManager.getCheckTasksTimeout());
        int unreadTasksNotification = ResourcesManager.getUnreadTasksNotificationTimeout();
        if (unreadTasksNotification > 0) {
            timer2 = new Timer();
            timer2.schedule(unreadTaskNotifier, unreadTasksNotification, unreadTasksNotification);
        }
    }

    public void stop() {
        if (timer1 != null) {
            timer1.cancel();
            timer1 = null;
            tasksChecker.cancel();
        }
        if (timer2 != null) {
            timer2.cancel();
            timer2 = null;
            unreadTaskNotifier.cancel();
        }
    }

    public void setErrorOccured(boolean errorOccured) {
        boolean old = this.errorOccured;
        this.errorOccured = errorOccured;
        if (old != this.errorOccured) {
            firePropertyChange(ERROR_OCCURED_PROPERTY, Boolean.valueOf(old), Boolean.valueOf(this.errorOccured));
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        listeners.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        listeners.removePropertyChangeListener(pcl);
    }

    protected void firePropertyChange(String propName, Object old, Object newValue) {
        listeners.firePropertyChange(propName, old, newValue);
    }

    private class TasksChecker extends TimerTask {
        private final Set<TaskStub> existingTasks = new HashSet<TaskStub>();

        private final ExecutionService executionService;

        protected SystemTray systemTray;

        private int errorCount = 0;

        public TasksChecker(SystemTray systemTray) {
            this.systemTray = systemTray;
            this.executionService = DelegateFactory.getInstance().getExecutionService();
        }

        @Override
        public void run() {
            try {
                if (!LoginHelper.isLogged()) {
                    try {
                        LoginHelper.login();
                    } catch (Throwable e) {
                        log.error("unable to login", e);
                        return;
                    }
                }
                if (LoginHelper.isLogged()) {
                    Date currentChangeDate = executionService.getLastChangeDate();
                    errorCount = 0;
                    if (lastTasksDate != null && !currentChangeDate.after(lastTasksDate) && errorOccured == false) {
                        return;
                    }
                    List<TaskStub> tasks = executionService.getTasks(LoginHelper.getSubject(),
                            WFProfileStrategy.TASK_DEFAULT_BATCH_PRESENTATION_FACTORY.getDefaultBatchPresentation());
                    final int unreadTasksCount = getUnreadTasksCount(tasks);
                    final int newTasksCount = getNewTasksCount(tasks);
                    existingTasks.clear();
                    for (TaskStub task : tasks) {
                        existingTasks.add(task);
                    }
                    GUI.display.syncExec(new Runnable() {
                        public void run() {
                            setErrorOccured(false);
                            systemTray.setTasks(unreadTasksCount, newTasksCount);
                        }
                    });
                    if (newTasksCount > 0) {
                        AePlayWave.playNotification("/onNewTask.wav");
                    }
                    unreadTaskNotifier.setUnreadTasksCount(unreadTasksCount);
                    if (onNewTaskTriggerCommand != null) {
                        try {
                            Runtime.getRuntime().exec(onNewTaskTriggerCommand);
                        } catch (Throwable th) {
                            log.warn("Unable to start onNewTaskTrigger command", th);
                        }
                    }
                    lastTasksDate = currentChangeDate;
                }
            } catch (AuthenticationException e) {
                LoginHelper.reset();
            } catch (Throwable e) {
                log.warn("run (Unforseen Exception)", e);
                if (++errorCount > 10) {
                    GUI.display.syncExec(new Runnable() {
                        public void run() {
                            setErrorOccured(true);
                        }
                    });
                }
            }
        }

        private int getUnreadTasksCount(List<TaskStub> newTaskIds) {
            int result = 0;
            for (TaskStub task : newTaskIds) {
                if (task.isFirstOpen()) {
                    result++;
                }
            }
            return result;
        }

        private int getNewTasksCount(List<TaskStub> newTaskIds) {
            int result = 0;
            for (TaskStub task : newTaskIds) {
                if (!existingTasks.contains(task) && task.isFirstOpen()) {
                    result++;
                }
            }
            return result;
        }
    }
    
    private class UnreadTaskNotifier extends TimerTask {
        private int unreadTasksCount;
        
        public void setUnreadTasksCount(int unreadTasksCount) {
            this.unreadTasksCount = unreadTasksCount;
        }

        @Override
        public void run() {
            if (unreadTasksCount > 0) {
                AePlayWave.playNotification("/unreadTasksNotification.wav");
            }
        }
    }
    
}
