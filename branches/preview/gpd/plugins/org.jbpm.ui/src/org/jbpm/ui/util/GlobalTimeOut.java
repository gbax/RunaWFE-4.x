package ru.runa.bpm.ui.util;

import ru.runa.bpm.ui.DesignerPlugin;
import ru.runa.bpm.ui.common.model.TimerAction;
import ru.runa.bpm.ui.pref.PrefConstants;

public class GlobalTimeOut implements PrefConstants {

    //private final TimerDuration timeOutDuration;
    //private final TimerAction timeOutAction;

    public GlobalTimeOut() {
/*        timeOutDuration = new TimerDuration(TimerDuration.EMPTY);
        String stringDuration = DesignerPlugin.getPrefString(P_TASKS_TIMEOUT_DURATION);
        if (stringDuration != null && !stringDuration.isEmpty()) {
            timeOutDuration.setDuration(stringDuration);
        }
        timeOutAction = new TimerAction(null);
        String stringActionClass = DesignerPlugin.getPrefString(P_TASKS_TIMEOUT_ACTION_CLASS);
        if (stringActionClass != null && !stringActionClass.isEmpty()) {
            timeOutAction.setDelegationClassName(stringActionClass);
        }
        String stringActionConfig = DesignerPlugin.getPrefString(P_TASKS_TIMEOUT_ACTION_CONFIG);
        if (stringActionConfig != null && !stringActionConfig.isEmpty()) {
            timeOutAction.setDelegationConfiguration(stringActionConfig);
        }
        String stringActionRepeat = DesignerPlugin.getPrefString(P_TASKS_TIMEOUT_ACTION_REPEAT);
        if (stringActionRepeat != null && !stringActionRepeat.isEmpty()) {
            timeOutAction.setRepeat(stringActionRepeat);
        }*/
    }

    public boolean timeOutExist() {
        //return (timeOutDuration != null && timeOutDuration.hasDuration());
    	return false;
    }

    public TimerAction getTimeOutAction() {
        //return timeOutAction;
    	return null;
    }

    public TimerDuration getTimeOutDuration() {
        //return timeOutDuration;
    	return null;
    }

}
