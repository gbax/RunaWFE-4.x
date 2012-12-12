package ru.runa.gpd.lang.model;

import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import ru.runa.gpd.Activator;
import ru.runa.gpd.Localization;
import ru.runa.gpd.PluginConstants;
import ru.runa.gpd.property.EscalationActionPropertyDescriptor;
import ru.runa.gpd.property.EscalationDurationPropertyDescriptor;
import ru.runa.gpd.property.TimeOutDurationPropertyDescriptor;
import ru.runa.gpd.property.TimerActionPropertyDescriptor;
import ru.runa.gpd.settings.PrefConstants;
import ru.runa.gpd.util.TimerDuration;
import ru.runa.wfe.handler.action.EscalationActionHandler;

import com.google.common.base.Objects;

public class TaskState extends State implements Synchronizable {
    private TimerAction timerAction;
    private TimerAction escalationAction;
    private boolean ignoreSubstitution;
    private boolean useEscalation;
    private TimerDuration escalationTime;
    private boolean async;

    @Override
    public boolean testAttribute(Object target, String name, String value) {
        if (super.testAttribute(target, name, value)) {
            return true;
        }
        if ("escalationEnabled".equals(name)) {
            return Objects.equal(value, String.valueOf(isUseEscalation()));
        }
        return false;
    }

    @Override
    public boolean isAsync() {
        return async;
    }

    @Override
    public void setAsync(boolean async) {
        if (this.async != async) {
            this.async = async;
            firePropertyChange(PROPERTY_ASYNC, !async, async);
        }
    }

    public TimerAction getEscalationAction() {
        return escalationAction;
    }

    public void setEscalationAction(TimerAction escalationAction) {
        this.escalationAction = escalationAction;
    }

    public TimerDuration getEscalationTime() {
        return escalationTime;
    }

    public void setEscalationTime(TimerDuration escalationTime) {
        this.escalationTime = escalationTime;
        firePropertyChange(PROPERTY_ESCALATION, null, escalationTime);
    }

    public boolean isUseEscalation() {
        return useEscalation;
    }

    public void setUseEscalation(boolean useEscalation) {
        if (escalationAction == null || !this.useEscalation) {
            escalationAction = new TimerAction(getProcessDefinition());
            escalationAction.setDelegationClassName(EscalationActionHandler.class.getName());
            String org_function = Activator.getPrefString(PrefConstants.P_ESCALATION_CONFIG);
            escalationAction.setDelegationConfiguration(org_function);
            String repeat = Activator.getPrefString(PrefConstants.P_ESCALATION_REPEAT);
            if (repeat != null && repeat != "" && (new TimerDuration(repeat).hasDuration())) {
                escalationAction.setRepeat(repeat);
            }
            String expirationTime = Activator.getPrefString(PrefConstants.P_ESCALATION_DURATION);
            if (expirationTime != null && expirationTime != "" && (new TimerDuration(expirationTime).hasDuration())) {
                escalationTime = new TimerDuration(expirationTime);
            } else {
                escalationTime = null;
            }
        }
        this.useEscalation = useEscalation;
        firePropertyChange(PROPERTY_ESCALATION, !useEscalation, useEscalation);
    }

    /**
     * @return true if there is more than one output transitions (timer
     *         transition ignored)
     */
    public boolean hasMultipleOutputTransitions() {
        int count = getLeavingTransitions().size();
        if (hasTimeoutTransition()) {
            count--;
        }
        return count > 1;
    }

    public boolean hasTimeoutTransition() {
        for (Transition transition : getLeavingTransitions()) {
            if (PluginConstants.TIMER_TRANSITION_NAME.equals(transition.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setTimerAction(TimerAction timerAction) {
        if (timerAction == TimerAction.NONE) {
            timerAction = null;
        }
        TimerAction old = this.timerAction;
        this.timerAction = timerAction;
        firePropertyChange(PROPERTY_TIMER_ACTION, old, this.timerAction);
    }

    @Override
    public TimerAction getTimerAction() {
        return timerAction;
    }

    @Override
    public TimerAction getTimeOutAction() {
        return null;
    }

    public boolean isIgnoreSubstitution() {
        return ignoreSubstitution;
    }

    public void setIgnoreSubstitution(boolean ignoreSubstitution) {
        boolean old = this.ignoreSubstitution;
        this.ignoreSubstitution = ignoreSubstitution;
        firePropertyChange(PROPERTY_IGNORE_SUBSTITUTION, old, this.ignoreSubstitution);
    }

    @Override
    public List<IPropertyDescriptor> getCustomPropertyDescriptors() {
        List<IPropertyDescriptor> list = super.getCustomPropertyDescriptors();
        if (timerExist() && !hasTimeoutTransition()) {
            list.add(new TimerActionPropertyDescriptor(PROPERTY_TIMER_ACTION, Localization.getString("Timer.action"), this));
        }
        list.add(new PropertyDescriptor(PROPERTY_IGNORE_SUBSTITUTION, Localization.getString("property.ignoreSubstitution")));
        list.add(new TimeOutDurationPropertyDescriptor(PROPERTY_TIMEOUT_DURATION, this));
        if (useEscalation) {
            list.add(new EscalationActionPropertyDescriptor(PROPERTY_ESCALATION_ACTION, Localization.getString("escalation.action"), this));
            list.add(new EscalationDurationPropertyDescriptor(PROPERTY_ESCALATION_DURATION, this));
        }
        list.add(new PropertyDescriptor(PROPERTY_ASYNC, Localization.getString("property.execution.async")));
        return list;
    }

    @Override
    public Object getPropertyValue(Object id) {
        if (PROPERTY_TIMER_ACTION.equals(id)) {
            return timerAction;
        }
        if (PROPERTY_ESCALATION_DURATION.equals(id)) {
            if (escalationTime == null || !escalationTime.hasDuration()) {
                return "";
            }
            return escalationTime;
        }
        if (PROPERTY_TIMEOUT_DURATION.equals(id)) {
            TimerDuration d = getTimeOutDuration();
            if (d == null || !d.hasDuration()) {
                return "";
            }
            return d;
        }
        if (PROPERTY_ESCALATION_ACTION.equals(id)) {
            return escalationAction;
        }
        if (PROPERTY_IGNORE_SUBSTITUTION.equals(id)) {
            return ignoreSubstitution ? Localization.getString("message.yes") : Localization.getString("message.no");
        }
        if (PROPERTY_ASYNC.equals(id)) {
            return async ? Localization.getString("message.yes") : Localization.getString("message.no");
        }
        return super.getPropertyValue(id);
    }

    @Override
    public void setPropertyValue(Object id, Object value) {
        if (PROPERTY_TIMER_ACTION.equals(id)) {
            setTimerAction((TimerAction) value);
        } else if (PROPERTY_TIMEOUT_DURATION.equals(id)) {
            if (value == null) {
                // ignore, edit was canceled
                return;
            }
            setTimeOutDuration((TimerDuration) value);
        } else if (PROPERTY_ESCALATION_ACTION.equals(id)) {
            setEscalationAction((TimerAction) value);
        } else if (PROPERTY_ESCALATION_DURATION.equals(id)) {
            setEscalationTime((TimerDuration) value);
        } else {
            super.setPropertyValue(id, value);
        }
    }
}
