package ru.runa.wfe.lang;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Objects;

import ru.runa.wfe.execution.ExecutionContext;
import ru.runa.wfe.task.Task;
import ru.runa.wfe.task.TaskCompletionBy;
import ru.runa.wfe.task.TaskFactory;

public abstract class BaseTaskNode extends InteractionNode implements Synchronizable {
    private static final long serialVersionUID = 1L;

    @Autowired
    protected TaskFactory taskFactory;

    protected boolean async;
    protected AsyncCompletionMode asyncCompletionMode = AsyncCompletionMode.NEVER;

    @Override
    public boolean isAsync() {
        return async;
    }

    @Override
    public void setAsync(boolean async) {
        this.async = async;
    }

    @Override
    public AsyncCompletionMode getCompletionMode() {
        return asyncCompletionMode;
    }

    @Override
    public void setCompletionMode(AsyncCompletionMode completionMode) {
        this.asyncCompletionMode = completionMode;
    }

    @Override
    public void leave(ExecutionContext executionContext, Transition transition) {
        if (!async) {
            for (Task task : executionContext.getToken().getTasks()) {
                if (Objects.equal(task.getNodeId(), getNodeId())) {
                    task.end(executionContext, TaskCompletionBy.TIMER, null);
                }
            }
        }
        super.leave(executionContext, transition);
    }

}
