package ru.runa.wfe.audit;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import ru.runa.wfe.task.Task;

/**
 * Logging task completion by timer.
 * 
 * @author Dofs
 */
@Entity
@DiscriminatorValue(value = "O")
public class TaskCancelledLog extends TaskEndLog {
    private static final long serialVersionUID = 1L;

    public TaskCancelledLog() {
    }

    public TaskCancelledLog(Task task) {
        super(task, null);
    }

    @Override
    @Transient
    public String getActorName() {
        return "";
    }

    @Override
    @Transient
    public Object[] getPatternArguments() {
        return new Object[] { getTaskName() };
    }

}
