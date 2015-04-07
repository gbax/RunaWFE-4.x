package ru.runa.gpd.formeditor.ftl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import ru.runa.gpd.util.EventSupport;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class Component extends EventSupport implements IPropertySource {
    private final int id;
    private final ComponentType type;
    private final List<Object> parameterValues = Lists.newArrayList();

    public Component(ComponentType type, int id) {
        this.type = type;
        this.id = id;
        for (ComponentParameter componentParameter : type.getParameters()) {
            parameterValues.add(componentParameter.getType().isMultiple() ? new ArrayList<String>() : "");
        }
    }

    public Component(Component component) {
        this.type = component.type;
        this.id = component.id;
        parameterValues.addAll(component.parameterValues);
    }

    public ComponentType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public Object getParameterValue(ComponentParameter parameter) {
        int index = type.getParameters().indexOf(parameter);
        if (index == -1) {
            throw new RuntimeException("No parameter found:" + parameter + " in " + type.getParameters());
        }
        return parameterValues.get(index);
    }

    public Object setParameterValue(ComponentParameter parameter, Object value) {
        int index = type.getParameters().indexOf(parameter);
        if (index == -1) {
            throw new RuntimeException("No parameter found:" + parameter + " in " + type.getParameters());
        }
        if (value instanceof String) {
            value = StringEscapeUtils.unescapeXml((String) value);
        }
        return parameterValues.set(index, value);
    }

    public List<String> getRawParameters() {
        List<String> list = Lists.newArrayList();
        for (Object value : parameterValues) {
            if (value instanceof List) {
                list.addAll((List<String>) value);
            } else {
                list.add((String) value);
            }
        }
        return list;
    }

    public void setRawParameters(List args) {
        for (int i = 0; i < args.size(); i++) {
            String value = args.get(i).toString();
            ComponentParameter parameter = type.getParameterOrLastMultiple(i);
            if (parameter == null) {
                continue;
            }
            if (parameter.getType().isMultiple()) {
                ((List<String>) getParameterValue(parameter)).add(value);
            } else {
                setParameterValue(parameter, value);
            }
        }
    }

    @Override
    public Object getEditableValue() {
        return null;
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        IPropertyDescriptor[] descriptors = new IPropertyDescriptor[type.getParameters().size()];
        for (int i = 0; i < descriptors.length; i++) {
            ComponentParameter parameter = type.getParameters().get(i);
            PropertyDescriptor descriptor = parameter.getType().createPropertyDescriptor(parameter, i);
            descriptor.setDescription(parameter.getDescription());
            descriptors[i] = descriptor;
        }
        return descriptors;
    }

    @Override
    public Object getPropertyValue(Object propertyId) {
        ComponentParameter parameter = type.getParameters().get((Integer) propertyId);
        Object value = getParameterValue(parameter);
        return parameter.getType().toPropertyDescriptorValue(parameter, value);
    }

    @Override
    public boolean isPropertySet(Object propertyId) {
        return true;
    }

    @Override
    public void resetPropertyValue(Object propertyId) {
        ComponentParameter parameter = type.getParameters().get((Integer) propertyId);
        Object old = setParameterValue(parameter, null);
        firePropertyChange(propertyId.toString(), old, null);
    }

    @Override
    public void setPropertyValue(Object propertyId, Object editorValue) {
        ComponentParameter parameter = type.getParameters().get((Integer) propertyId);
        Object value = parameter.getType().fromPropertyDescriptorValue(parameter, editorValue);
        Object old = setParameterValue(parameter, value);
        firePropertyChange(propertyId.toString(), old, value);
    }

    private String stringQuotation(Object obj) {
    	String result = obj.toString();
    	if (result.contains("\"") && !result.contains("'")) {
    		return String.format("'%s'", obj);
    	} 
    	else if (!result.contains("\"") && result.contains("'")) {
    		return String.format("\"%s\"", obj);
    	}
    	return String.format("\"%s\"", result.replaceAll("\"", "\\\""));
    }
    
    @Override
    public String toString() {
        StringBuffer ftl = new StringBuffer();
        ftl.append("${").append(type.getId()).append("(");
        boolean first = true;
        for (ComponentParameter parameter : type.getParameters()) {
            if (!first) {
                ftl.append(", ");
            } else {
                first = false;
            }
            boolean surroundWithBrackets = parameter.getType().isSurroundBrackets();
            Object obj = null;
            if (parameter.getType().isMultiple()) {
                List<String> list = (List<String>) getParameterValue(parameter);
                String delim = surroundWithBrackets ? "\", \"" : ", ";
                obj = Joiner.on(delim).join(list);
            } else {
            	obj = getParameterValue(parameter);
            }
            if (surroundWithBrackets) {
                ftl.append(stringQuotation(obj));
            } else {
            	ftl.append(obj);
            }
        }
        ftl.append(")}");
        return ftl.toString();
    }
}
