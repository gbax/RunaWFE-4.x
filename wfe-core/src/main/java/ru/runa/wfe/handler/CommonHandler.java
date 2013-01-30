package ru.runa.wfe.handler;

import java.util.Map;


import ru.runa.wfe.execution.ExecutionContext;
import ru.runa.wfe.handler.action.ActionHandler;
import ru.runa.wfe.handler.bot.TaskHandlerBase;
import ru.runa.wfe.task.dto.WfTask;
import ru.runa.wfe.user.User;
import ru.runa.wfe.var.IVariableProvider;

public abstract class CommonHandler extends TaskHandlerBase implements ActionHandler {

    protected abstract Map<String, Object> executeAction(IVariableProvider variableProvider) throws Exception;

    @Override
    public void execute(ExecutionContext context) throws Exception {
        Map<String, Object> result = executeAction(context.getVariableProvider());
        if (result != null) {
            context.setVariables(result);
        }
    }

    @Override
    public Map<String, Object> handle(User user, IVariableProvider variableProvider, WfTask task) throws Exception {
        return executeAction(variableProvider);
    }

}
