package ru.runa.wfe.extension.handler;

import java.util.Map;

import ru.runa.wfe.commons.TimeMeasurer;
import ru.runa.wfe.execution.ExecutionContext;
import ru.runa.wfe.extension.ActionHandler;
import ru.runa.wfe.task.dto.WfTask;
import ru.runa.wfe.user.User;
import ru.runa.wfe.var.IVariableProvider;

import com.google.common.base.Throwables;

/**
 * Base class for standard XML parameter-based configuration.
 * 
 * @author dofs[197@gmail.com]
 */
public abstract class CommonParamBasedHandler extends TaskHandlerBase implements ActionHandler {
    private ParamsDef paramsDef;

    @Override
    public void setConfiguration(String configuration) throws Exception {
        paramsDef = ParamsDef.parse(configuration);
    }

    protected abstract void executeAction(HandlerData handlerData) throws Exception;

    @Override
    public void execute(ExecutionContext context) throws Exception {
        final HandlerData handlerData = new HandlerData(paramsDef, context);
        TimeMeasurer timeMeasurer = new TimeMeasurer(log);
        try {
            timeMeasurer.jobStarted();
            executeAction(handlerData);
            context.setVariableValues(handlerData.getOutputVariables());
            timeMeasurer.jobEnded(handlerData.getTaskName());
        } catch (Throwable th) {
            if (handlerData.isFailOnError()) {
                throw Throwables.propagate(th);
            }
            log.error("action handler execution error.", th);
        }
    }

    @Override
    public Map<String, Object> handle(final User user, final IVariableProvider variableProvider, final WfTask task) throws Exception {
        HandlerData handlerData = new HandlerData(paramsDef, variableProvider, task);
        TimeMeasurer timeMeasurer = new TimeMeasurer(log);
        try {
            timeMeasurer.jobStarted();
            executeAction(handlerData);
            timeMeasurer.jobEnded("Execution of " + handlerData.getTaskName());
        } catch (Throwable th) {
            if (handlerData.isFailOnError()) {
                throw Throwables.propagate(th);
            }
            log.error("task handler execution error.", th);
        }
        return handlerData.getOutputVariables();
    }

}
