package ru.runa.gpd.lang.model;

import java.util.Arrays;
import java.util.List;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import ru.runa.gpd.Localization;

public class ActionImpl extends Action {
    private String eventType;

    @Override
    public void setParent(GraphElement parent) {
        super.setParent(parent);
        if (parent instanceof Transition) {
            eventType = Event.TRANSITION;
        } else if (parent instanceof StartState) {
            eventType = Event.NODE_LEAVE;
        } else if (parent instanceof EndState) {
            eventType = Event.NODE_ENTER;
        } else if (parent instanceof TaskState) {
            eventType = Event.TASK_ASSIGN;
        } else if (parent instanceof Node) {
            eventType = Event.NODE_LEAVE;
        }
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        if (eventType != null && canSetEventType(eventType)) {
            String old = this.getEventType();
            this.eventType = eventType;
            firePropertyChange(PROPERTY_EVENT_TYPE, old, this.getEventType());
        }
    }

    private boolean canSetEventType(String eventType) {
        return getAllowedEventTypes().contains(eventType);
    }

    private static final String[] TRANSITION_EVENT_TYPES = { Event.TRANSITION };
    private static final String[] TASK_EVENT_TYPES = { Event.TASK_CREATE, Event.TASK_ASSIGN, Event.TASK_END };
    private static final String[] START_STATE_EVENT_TYPES = { Event.NODE_LEAVE };
    private static final String[] END_STATE_EVENT_TYPES = { Event.NODE_ENTER };
    private static final String[] NODE_EVENT_TYPES = { Event.NODE_ENTER, Event.NODE_ACTION, Event.NODE_LEAVE };

    private List<String> getAllowedEventTypes() {
        GraphElement parent = getParent();
        if (parent instanceof Transition) {
            return Arrays.asList(TRANSITION_EVENT_TYPES);
        } else if (parent instanceof TaskState) {
            return Arrays.asList(TASK_EVENT_TYPES);
        } else if (parent instanceof StartState) {
            return Arrays.asList(START_STATE_EVENT_TYPES);
        } else if (parent instanceof EndState) {
            return Arrays.asList(END_STATE_EVENT_TYPES);
        } else if (parent instanceof Node) {
            return Arrays.asList(NODE_EVENT_TYPES);
        } else {
            throw new IllegalArgumentException("Unknown action element " + parent);
        }
    }

    @Override
    public List<IPropertyDescriptor> getCustomPropertyDescriptors() {
        List<String> allowedEventTypes = getAllowedEventTypes();
        String[] eventTypes = new String[allowedEventTypes.size()];
        int i = 0;
        for (String string : allowedEventTypes) {
            eventTypes[i++] = Localization.getString(string);
        }
        List<IPropertyDescriptor> list = super.getCustomPropertyDescriptors();
        list.add(new ComboBoxPropertyDescriptor(PROPERTY_EVENT_TYPE, Localization.getString("Action.property.eventType"), eventTypes));
        return list;
    }

    @Override
    public Object getPropertyValue(Object id) {
        if (PROPERTY_EVENT_TYPE.equals(id)) {
            String eventType = getEventType();
            if (eventType == null) {
                return new Integer(-1);
            }
            List<String> eventTypes = getAllowedEventTypes();
            return new Integer(eventTypes.indexOf(eventType));
        }
        return super.getPropertyValue(id);
    }

    @Override
    public void setPropertyValue(Object id, Object value) {
        if (PROPERTY_EVENT_TYPE.equals(id)) {
            int index = ((Integer) value).intValue();
            setEventType(getAllowedEventTypes().get(index));
        } else {
            super.setPropertyValue(id, value);
        }
    }

}
