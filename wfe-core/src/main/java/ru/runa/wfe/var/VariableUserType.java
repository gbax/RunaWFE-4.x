package ru.runa.wfe.var;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

@XmlAccessorType(XmlAccessType.FIELD)
public class VariableUserType implements Serializable {
    public static final String DELIM = ".";
    private String name;
    private final List<VariableDefinition> attributes = Lists.newArrayList();

    public VariableUserType() {
    }

    public VariableUserType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<VariableDefinition> getAttributes() {
        return attributes;
    }

    public VariableDefinition getAttribute(String name) {
        for (VariableDefinition definition : attributes) {
            if (Objects.equal(name, definition.getName())) {
                return definition;
            }
        }
        return null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, attributes);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VariableUserType)) {
            return false;
        }
        VariableUserType type = (VariableUserType) obj;
        return Objects.equal(name, type.name) && Objects.equal(attributes, type.attributes);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(getClass()).add("name", name).add("attributes", attributes).toString();
    }

}
