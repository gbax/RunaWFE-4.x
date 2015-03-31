package ru.runa.wfe.task;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import ru.runa.wfe.commons.ClassLoaderUtil;
import ru.runa.wfe.commons.email.EmailConfig;
import ru.runa.wfe.commons.email.EmailConfigParser;
import ru.runa.wfe.commons.email.EmailUtils;
import ru.runa.wfe.form.Interaction;
import ru.runa.wfe.lang.ProcessDefinition;
import ru.runa.wfe.security.auth.UserHolder;
import ru.runa.wfe.task.logic.ITaskNotifier;
import ru.runa.wfe.user.Actor;
import ru.runa.wfe.user.Executor;
import ru.runa.wfe.user.Group;
import ru.runa.wfe.user.dao.ExecutorDAO;
import ru.runa.wfe.var.ComplexVariable;
import ru.runa.wfe.var.DelegableVariableProvider;
import ru.runa.wfe.var.IVariableProvider;
import ru.runa.wfe.var.MapDelegableVariableProvider;
import ru.runa.wfe.var.ScriptingComplexVariable;
import ru.runa.wfe.var.VariableDefinition;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.ByteStreams;

public class EmailTaskNotifier implements ITaskNotifier {
    private static final Log log = LogFactory.getLog(EmailTaskNotifier.class);
    @Autowired
    private ExecutorDAO executorDAO;
    private boolean enabled = true;
    private boolean onlyIfTaskActorEmailDefined = false;
    private byte[] configBytes;

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setOnlyIfTaskActorEmailDefined(boolean onlyIfTaskActorEmailDefined) {
        this.onlyIfTaskActorEmailDefined = onlyIfTaskActorEmailDefined;
    }

    @Required
    public void setConfigLocation(String path) {
        try {
            InputStream in = ClassLoaderUtil.getAsStreamNotNull(path, getClass());
            configBytes = ByteStreams.toByteArray(in);
        } catch (Exception e) {
            log.error("Configuration error: " + e);
        }
    }

    @Override
    public void onTaskAssigned(ProcessDefinition processDefinition, IVariableProvider variableProvider, Task task, Executor previousExecutor) {
        if (!enabled || configBytes == null) {
            return;
        }
        try {
            log.debug("About " + task + " assigned to " + task.getExecutor() + ", previous: " + previousExecutor);
            EmailConfig config = EmailConfigParser.parse(configBytes);
            List<String> emailsToSend = getEmails(task.getExecutor());
            List<String> emailsWereSent = getEmails(previousExecutor);
            emailsToSend.removeAll(emailsWereSent);
            if (onlyIfTaskActorEmailDefined && emailsToSend.size() == 0) {
                log.debug("Ignored due to empty emails, previously emails were sent: " + emailsWereSent);
                return;
            }
            String emails = Joiner.on(", ").join(emailsToSend);
            Interaction interaction = processDefinition.getInteractionNotNull(task.getNodeId());
            Map<String, Object> map = Maps.newHashMap();
            map.put("interaction", interaction);
            map.put("task", task);
            map.put("emails", emails);
            ScriptingVariableProvider scriptingVariableProvider = new ScriptingVariableProvider(processDefinition, variableProvider);
            IVariableProvider emailVariableProvider = new MapDelegableVariableProvider(map, scriptingVariableProvider);
            EmailUtils.prepareTaskMessage(UserHolder.get(), config, interaction, emailVariableProvider);
            EmailUtils.sendMessageRequest(config);
        } catch (Exception e) {
            log.warn("", e);
        }
    }

    private List<String> getEmails(Executor executor) {
        List<String> emails = Lists.newArrayList();
        if (executor instanceof Actor) {
            Actor actor = (Actor) executor;
            if (actor.getEmail() != null && actor.getEmail().trim().length() > 0) {
                emails.add(actor.getEmail().trim());
            }
        } else if (executor instanceof Group) {
            Collection<Actor> actors = executorDAO.getGroupActors((Group) executor);
            for (Actor actor : actors) {
                if (actor.getEmail() != null && actor.getEmail().trim().length() > 0) {
                    emails.add(actor.getEmail().trim());
                }
            }
        }
        return emails;
    }

    public static class ScriptingVariableProvider extends DelegableVariableProvider {
        private final ProcessDefinition processDefinition;

        public ScriptingVariableProvider(ProcessDefinition processDefinition, IVariableProvider variableProvider) {
            super(variableProvider);
            this.processDefinition = processDefinition;
        }

        @Override
        public Object getValue(String variableName) {
            Object object = super.getValue(variableName);
            if (object == null) {
                for (VariableDefinition definition : processDefinition.getVariables()) {
                    if (Objects.equal(variableName, definition.getScriptingName())) {
                        object = super.getValue(definition.getName());
                        break;
                    }
                }
            }
            if (object instanceof ComplexVariable) {
                object = new ScriptingComplexVariable((ComplexVariable) object);
            }
            return object;
        }
    }
}
