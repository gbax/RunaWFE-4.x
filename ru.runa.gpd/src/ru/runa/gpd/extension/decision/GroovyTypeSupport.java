package ru.runa.gpd.extension.decision;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.runa.gpd.Localization;
import ru.runa.gpd.PluginLogger;
import ru.runa.gpd.lang.model.Variable;
import ru.runa.gpd.ui.dialog.DoubleInputDialog;
import ru.runa.gpd.ui.dialog.UserInputDialog;

public abstract class GroovyTypeSupport {
    private static final Map<String, GroovyTypeSupport> TYPES_MAP = new HashMap<String, GroovyTypeSupport>();
    static {
        TYPES_MAP.put(Object.class.getName(), new DefaultType());
        TYPES_MAP.put(String.class.getName(), new StringType());
        TYPES_MAP.put(Boolean.class.getName(), new BooleanType());
        TYPES_MAP.put(Number.class.getName(), new NumberType());
        TYPES_MAP.put(Date.class.getName(), new DateType());
    }

    public static GroovyTypeSupport get(String className) {
        if (className == null) {
            className = Object.class.getName();
        }
        GroovyTypeSupport typeSupport = TYPES_MAP.get(className);
        while (typeSupport == null) {
            try {
                Class<?> superClass = Class.forName(className).getSuperclass();
                if (superClass == null) {
                    superClass = Object.class;
                }
                className = superClass.getName();
                typeSupport = TYPES_MAP.get(className);
            } catch (Exception e) {
                PluginLogger.logInfo("Not found type support for type: " + className + ", using default (" + e + ")");
                typeSupport = TYPES_MAP.get(Object.class.getName());
            }
        }
        return typeSupport;
    }

    public boolean hasUserInputEditor() {
        return true;
    }

    public List<String> getPredefinedValues(Operation operation) {
        List<String> v = new ArrayList<String>();
        if (operation == Operation.EQ || operation == Operation.NOT_EQ) {
            v.add("null");
        }
        return v;
    }

    public UserInputDialog createUserInputDialog() {
        return new UserInputDialog();
    }

    abstract String wrap(Object value);

    public String unwrapValue(String value) {
        return value;
    }

    abstract List<Operation> getTypedOperations();

    static class DefaultType extends GroovyTypeSupport {
        @Override
        String wrap(Object value) {
            if (value instanceof Variable) {
                return ((Variable) value).getScriptingName();
            } else {
                throw new IllegalArgumentException("value class is " + value.getClass().getName());
            }
        }

        @Override
        public boolean hasUserInputEditor() {
            return false;
        }

        @Override
        List<Operation> getTypedOperations() {
            return null;
        }
    }

    static class StringType extends GroovyTypeSupport {
        @Override
        String wrap(Object value) {
            if (value instanceof Variable) {
                return ((Variable) value).getScriptingName();
            } else if (value instanceof String) {
                return "\"" + value + "\"";
            } else {
                throw new IllegalArgumentException("value class is " + value.getClass().getName());
            }
        }

        @Override
        public String unwrapValue(String value) {
            return value.substring(1, value.length() - 1);
        }

        @Override
        List<Operation> getTypedOperations() {
            List<Operation> extOperations = new ArrayList<Operation>();
            extOperations.add(new Operation(Localization.getString("Groovy.Operation.contains"), "contains") {
                @Override
                public String generateCode(Variable variable, Object lexem2) {
                    StringBuffer buffer = new StringBuffer("");
                    buffer.append(wrap(variable));
                    buffer.append(".contains(");
                    buffer.append(wrap(lexem2));
                    buffer.append(")");
                    return buffer.toString();
                }
            });
            return extOperations;
        }
    }

    static class BooleanType extends GroovyTypeSupport {
        @Override
        String wrap(Object value) {
            if (value instanceof Variable) {
                return ((Variable) value).getScriptingName() + ".booleanValue()";
            } else if (value instanceof String) {
                return (String) value;
            } else {
                throw new IllegalArgumentException("value class is " + value.getClass().getName());
            }
        }

        @Override
        public List<String> getPredefinedValues(Operation operation) {
            List<String> v = super.getPredefinedValues(operation);
            v.add("true");
            v.add("false");
            return v;
        }

        @Override
        public boolean hasUserInputEditor() {
            return false;
        }

        @Override
        List<Operation> getTypedOperations() {
            return null;
        }
    }

    static class NumberType extends GroovyTypeSupport {
        @Override
        String wrap(Object value) {
            if (value instanceof Variable) {
                return ((Variable) value).getScriptingName() + ".doubleValue()";
            } else if (value instanceof String) {
                return (String) value;
            } else {
                throw new IllegalArgumentException("value class is " + value.getClass().getName());
            }
        }

        @Override
        public UserInputDialog createUserInputDialog() {
            return new DoubleInputDialog();
        }

        @Override
        List<Operation> getTypedOperations() {
            List<Operation> extOperations = new ArrayList<Operation>();
            extOperations.add(new Operation(Localization.getString("Groovy.Operation.more"), ">"));
            extOperations.add(new Operation(Localization.getString("Groovy.Operation.less"), "<"));
            extOperations.add(new Operation(Localization.getString("Groovy.Operation.moreeq"), ">="));
            extOperations.add(new Operation(Localization.getString("Groovy.Operation.lesseq"), "<="));
            return extOperations;
        }
    }

    static class DateType extends GroovyTypeSupport {
        @Override
        String wrap(Object value) {
            if (value instanceof Variable) {
                return ((Variable) value).getScriptingName() + ".getTime()";
            } else if (value instanceof String) {
                return (String) value;
            } else {
                throw new IllegalArgumentException("value class is " + value.getClass().getName());
            }
        }

        @Override
        public boolean hasUserInputEditor() {
            return false;
        }

        @Override
        List<Operation> getTypedOperations() {
            List<Operation> extOperations = new ArrayList<Operation>();
            extOperations.add(new Operation(Localization.getString("Groovy.Operation.earlier"), "<"));
            extOperations.add(new Operation(Localization.getString("Groovy.Operation.later"), ">"));
            extOperations.add(new Operation(Localization.getString("Groovy.Operation.earliereq"), "<="));
            extOperations.add(new Operation(Localization.getString("Groovy.Operation.latereq"), ">="));
            return extOperations;
        }
    }
}
