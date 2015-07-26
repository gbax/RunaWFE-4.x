package ru.runa.wfe.audit;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import ru.runa.wfe.audit.presentation.ExecutorIdsValue;
import ru.runa.wfe.task.Task;
import ru.runa.wfe.user.Executor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;

/**
 * Logging task delegation
 *
 * @author gbax
 */
@Entity
@DiscriminatorValue(value = "D")
public class TaskDelegationLog extends TaskLog {

    private static final long serialVersionUID = 1L;

    public TaskDelegationLog() {}

    public TaskDelegationLog(Task task, List<Executor> executors) {
        super(task);
        List<Long> ids = Lists.newArrayList();
        for (Executor executor : executors) {
            ids.add(executor.getId());
        }
        addAttribute(ATTR_MESSAGE, Joiner.on(ExecutorIdsValue.DELIM).join(ids));
        setSeverity(Severity.INFO);
    }

    @Override
    @Transient
    public Object[] getPatternArguments() {
        return new Object[] { getTaskName(), new ExecutorIdsValue(getAttributeNotNull(ATTR_MESSAGE)) };
    }

    @Override
    public void processBy(ProcessLogVisitor visitor) {
        visitor.onTaskDelegaionLog(this);
    }

}
