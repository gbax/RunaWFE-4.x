package ru.runa.wfe.extension.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.runa.wfe.extension.Configurable;
import ru.runa.wfe.extension.TaskHandler;
import ru.runa.wfe.task.dto.WfTask;
import ru.runa.wfe.user.User;
import ru.runa.wfe.var.IVariableProvider;

import com.google.common.base.Charsets;

public abstract class TaskHandlerBase implements TaskHandler, Configurable {
    protected Log log = LogFactory.getLog(getClass());
    private String configuration;

    @Override
    public final void setConfiguration(byte[] config) throws Exception {
        if (config != null) {
            configuration = new String(config, Charsets.UTF_8);
        }
        setConfiguration(configuration);
    }

    @Override
    public String getConfiguration() {
        return configuration;
    }

    @Override
    public void onRollback(User user, IVariableProvider variableProvider, WfTask task) throws Exception {
    }
}
