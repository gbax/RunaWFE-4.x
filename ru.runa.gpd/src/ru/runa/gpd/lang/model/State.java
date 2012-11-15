package ru.runa.gpd.lang.model;

import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import ru.runa.gpd.PluginConstants;
import ru.runa.gpd.property.DurationPropertyDescriptor;
import ru.runa.gpd.util.TimerDuration;

public abstract class State extends FormNode implements Active, ITimed, ITimeOut {

	private boolean hasTimer = false;
    public void setHasTimer(boolean hasTimer) {
		this.hasTimer = hasTimer;
	}

	private TimerDuration duration;
    private TimerDuration timeOutDuration;
    private boolean reassignmentEnabled = false;
    private boolean minimizedView = false;

    public boolean isReassignmentEnabled() {
        return reassignmentEnabled;
    }

    public boolean isMinimizedView() {
        return minimizedView;
    }

    public void setMinimizedView(boolean minimazedView) {
        this.minimizedView = minimazedView;
        firePropertyChange(PROPERTY_MINIMAZED_VIEW, !reassignmentEnabled, reassignmentEnabled);
    }

    public void setReassignmentEnabled(boolean forceReassign) {
        this.reassignmentEnabled = forceReassign;
        firePropertyChange(PROPERTY_SWIMLANE_REASSIGN, !reassignmentEnabled, reassignmentEnabled);
    }

    public String getDueDate() {
    	if (duration == null) return null;
        return duration.getDuration();
    }

    public void setDuration(TimerDuration duration) {
        this.duration = duration;
        firePropertyChange(PROPERTY_TIMER_DURATION, null, null);
    }

    @Override
    public void setDueDate(String dueDate) {
        setDuration(new TimerDuration(dueDate));
    }

    @Override
    public TimerDuration getDuration() {
        return duration;
    }

    public String getTimeOutDueDate() {
    	if (timeOutDuration==null) return null;
        return timeOutDuration.getDuration();
    }

    public void setTimeOutDuration(TimerDuration timeOutDuration) {
        this.timeOutDuration = timeOutDuration;
        firePropertyChange(PROPERTY_TIMEOUT_DURATION, null, null);
    }

    public void setTimeOutDueDate(String timeOutDueDate) {
        setTimeOutDuration(new TimerDuration(timeOutDueDate));
    }

    public TimerDuration getTimeOutDuration() {
        return timeOutDuration;
    }

    @Override
    public Object getPropertyValue(Object id) {
        if (PROPERTY_TIMER_DURATION.equals(id)) {
        	if (duration==null || !duration.hasDuration()) return "";
            return duration;
        }
        /*if (PROPERTY_TIMEOUT_DURATION.equals(id)) {
            return timeOutDuration;
        }*/
        return super.getPropertyValue(id);
    }

    @Override
    public void setPropertyValue(Object id, Object value) {
        if (PROPERTY_TIMER_DURATION.equals(id)) {
            if (value == null) {
                // ignore, edit was canceled
                return;
            }
            setDuration((TimerDuration) value);
        /*} else if (PROPERTY_TIMEOUT_DURATION.equals(id)) {
            if (value == null) {
                // ignore, edit was canceled
                return;
            }
            setTimeOutDuration((TimerDuration) value);*/
        } else {
            super.setPropertyValue(id, value);
        }
    }

    @Override
    public List<IPropertyDescriptor> getCustomPropertyDescriptors() {
        List<IPropertyDescriptor> list = super.getCustomPropertyDescriptors();
        if (timerExist()) {
            list.add(new DurationPropertyDescriptor(PROPERTY_TIMER_DURATION, this));
        }// else {
         //   list.add(new TimeOutDurationPropertyDescriptor(PROPERTY_TIMEOUT_DURATION, this));
        //}
        return list;
    }

    @Override
    public String getNextTransitionName() {
        if (timerExist() && getTransitionByName(PluginConstants.TIMER_TRANSITION_NAME) == null) {
            return PluginConstants.TIMER_TRANSITION_NAME;
        }
        return super.getNextTransitionName();
    }

    public void createTimer() {
        if (!timerExist()) {
        	hasTimer = true;
            //setDueDate(TimerDuration.EMPTY);
            firePropertyChange(PROPERTY_TIMER, false, true);
            setDirty();
        }
    }

    @Override
    public boolean timerExist() {
        return hasTimer; //duration != null;
    }

/*    public boolean timeOutExist() {
        return (timeOutDuration != null && timeOutDuration.hasDuration());
    } */

    public void removeTimer() {
        if (timerExist()) {
        	hasTimer = false;
            this.duration = null;
            firePropertyChange(PROPERTY_TIMER, true, false);
            Transition timeoutTransition = getTransitionByName(PluginConstants.TIMER_TRANSITION_NAME);
            if (timeoutTransition != null) {
                removeLeavingTransition(timeoutTransition);
            }
            setDirty();
        }
    }

    @Override
    protected void validate() {
        super.validate();
        if (timerExist() && duration!=null && duration.getVariableName() != null
                && !getProcessDefinition().getVariableNames(false).contains(duration.getVariableName())) {
            addError("timerState.invalidVariable");
        }
    }

    @Override
    public void addLeavingTransition(Transition transition) {
        if (!timerExist() && PluginConstants.TIMER_TRANSITION_NAME.equals(transition.getName())) {
            transition.setName(getNextTransitionName());
        }
        super.addLeavingTransition(transition);
    }
}
