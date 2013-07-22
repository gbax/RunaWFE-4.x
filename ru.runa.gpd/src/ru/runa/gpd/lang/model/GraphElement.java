package ru.runa.gpd.lang.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IActionFilter;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import ru.runa.gpd.Localization;
import ru.runa.gpd.PluginConstants;
import ru.runa.gpd.extension.DelegableProvider;
import ru.runa.gpd.extension.HandlerRegistry;
import ru.runa.gpd.lang.Language;
import ru.runa.gpd.lang.NodeRegistry;
import ru.runa.gpd.lang.NodeTypeDefinition;
import ru.runa.gpd.property.DelegableClassPropertyDescriptor;
import ru.runa.gpd.property.DelegableConfPropertyDescriptor;
import ru.runa.gpd.property.DurationPropertyDescriptor;
import ru.runa.gpd.property.TimerActionPropertyDescriptor;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

@SuppressWarnings("unchecked")
public abstract class GraphElement implements IPropertySource, PropertyNames, IActionFilter {
    protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);
    private PropertyChangeListener delegatedListener;
    private GraphElement parent;
    private GraphElement parentContainer;
    private final List<GraphElement> childs = new ArrayList<GraphElement>();
    private Rectangle constraint;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String nodeId) {
        this.id = nodeId;
    }

    public GraphElement getParentContainer() {
        return parentContainer;
    }

    public void setParentContainer(GraphElement parentContainer) {
        this.parentContainer = parentContainer;
    }

    @Override
    public boolean testAttribute(Object target, String name, String value) {
        if ("language".equals(name)) {
            return Objects.equal(value, getProcessDefinition().getLanguage().name().toLowerCase());
        }
        if ("delegable".equals(name)) {
            return Objects.equal(value, String.valueOf(isDelegable()));
        }
        return false;
    }

    public void setDelegatedListener(PropertyChangeListener delegatedListener) {
        this.delegatedListener = delegatedListener;
        if (delegatedListener != null) {
            addPropertyChangeListener(delegatedListener);
            for (GraphElement child : getChildren(GraphElement.class)) {
                child.setDelegatedListener(delegatedListener);
            }
        }
    }

    public void unsetDelegatedListener(PropertyChangeListener delegatedListener) {
        if (delegatedListener != null) {
            removePropertyChangeListener(delegatedListener);
            for (GraphElement child : getChildren(GraphElement.class)) {
                child.unsetDelegatedListener(delegatedListener);
            }
        }
        this.delegatedListener = null;
    }

    public Rectangle getConstraint() {
        return constraint;
    }

    public void setDirty() {
        ProcessDefinition pd = getProcessDefinition();
        if (pd != null) {
            pd.setDirty(true);
        }
    }

    public void setConstraint(Rectangle newConstraint) {
        if (!Objects.equal(this.constraint, newConstraint)) {
            Rectangle oldConstraint = this.constraint;
            this.constraint = newConstraint;
            firePropertyChange(NODE_BOUNDS_RESIZED, oldConstraint, newConstraint);
        }
    }

    public ProcessDefinition getProcessDefinition() {
        if (parent == null) {
            return this instanceof ProcessDefinition ? (ProcessDefinition) this : null;
        }
        return parent.getProcessDefinition();
    }

    protected void validate() {
        if (isDelegable()) {
            Delegable d = (Delegable) this;
            DelegableProvider provider = HandlerRegistry.getProvider(delegationClassName);
            if (delegationClassName == null || delegationClassName.length() == 0) {
                addError("delegationClassName.empty");
            } else if (!HandlerRegistry.getInstance().isArtifactRegistered(d.getDelegationType(), delegationClassName)) {
                addWarning("delegationClassName.classNotFound");
            } else if (!provider.validateValue(d)) {
                addError("delegable.invalidConfiguration");
            }
        }
    }

    public void addError(String messageKey, Object... params) {
        getProcessDefinition().addError(this, messageKey, params);
    }

    public void addWarning(String messageKey, Object... params) {
        getProcessDefinition().addWarning(this, messageKey, params);
    }

    public NodeTypeDefinition getTypeDefinition() {
        return NodeRegistry.getNodeTypeDefinition(getClass());
    }

    public GraphElement getParent() {
        return parent;
    }

    public void setParent(GraphElement parent) {
        this.parent = parent;
    }

    public void removeChild(GraphElement child) {
        childs.remove(child);
        firePropertyChange(NODE_REMOVED, child, null);
        firePropertyChange(NODE_CHILDS_CHANGED, null, null);
        if (child.delegatedListener != null) {
            child.removePropertyChangeListener(child.delegatedListener);
        }
    }

    public void addChild(GraphElement child) {
        addChild(child, childs.size());
    }

    public void addChild(GraphElement child, int index) {
        childs.add(index, child);
        child.setParent(this);
        child.setDelegatedListener(delegatedListener);
        firePropertyChange(NODE_CHILDS_CHANGED, null, 1);
        try {
            String nodeId = child.getId();
            if (nodeId == null) {
                nodeId = String.valueOf(getProcessDefinition().getNextNodeId());
                child.setId("ID" + nodeId);
            } else {
                nodeId = nodeId.substring(2);
                int nodeIdInt = Integer.parseInt(nodeId);
                getProcessDefinition().setNextNodeIdIfApplicable(nodeIdInt);
            }
            if (child instanceof NamedGraphElement) {
                NamedGraphElement namedGraphElement = (NamedGraphElement) child;
                if (Strings.isNullOrEmpty(namedGraphElement.getName())) {
                    namedGraphElement.setName(child.getTypeDefinition().getLabel() + " " + nodeId);
                }
            }
        } catch (StringIndexOutOfBoundsException e) {
        } catch (NumberFormatException e) {
        }
    }

    public void swapChilds(GraphElement child1, GraphElement child2) {
        Collections.swap(childs, childs.indexOf(child1), childs.indexOf(child2));
        firePropertyChange(PropertyNames.NODE_CHILDS_CHANGED, null, null);
    }

    public void changeChildIndex(GraphElement child, GraphElement insertBefore) {
        if (insertBefore != null && child != null) {
            childs.remove(child);
            childs.add(childs.indexOf(insertBefore), child);
            firePropertyChange(PropertyNames.NODE_CHILDS_CHANGED, null, null);
        }
    }

    public <T extends GraphElement> List<T> getChildren(Class<T> type) {
        List<T> items = new ArrayList<T>();
        for (GraphElement element : childs) {
            if (type.isAssignableFrom(element.getClass())) {
                items.add((T) element);
            }
        }
        return items;
    }

    public List<Node> getNodes() {
        return getChildren(Node.class);
    }

    public List<GraphElement> getElements() {
        return getChildren(GraphElement.class);
    }

    public <T extends GraphElement> List<T> getChildrenRecursive(Class<T> type) {
        List<T> items = new ArrayList<T>();
        for (GraphElement element : childs) {
            if (type.isAssignableFrom(element.getClass())) {
                items.add((T) element);
            }
            items.addAll(element.getChildrenRecursive(type));
        }
        return items;
    }

    public <T extends GraphElement> T getFirstChild(Class<T> type) {
        for (GraphElement element : childs) {
            if (type.isAssignableFrom(element.getClass())) {
                return (T) element;
            }
        }
        return null;
    }

    // Active implementation
    public void addAction(Action action, int index) {
        if (!(this instanceof Active)) {
            throw new IllegalStateException("It's not Active class ... " + this.getClass());
        }
        if (index == -1) {
            addChild(action);
        } else {
            addChild(action, index);
        }
    }

    public int removeAction(Action action) {
        int index = childs.indexOf(action);
        removeChild(action);
        return index;
    }

    public List<Action> getActions() {
        return getChildren(Action.class);
    }

    // Describable
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        String old = this.description;
        this.description = description;
        firePropertyChange(PROPERTY_DESCRIPTION, old, this.getDescription());
    }

    // Delegable
    private String delegationClassName;
    private String delegationConfiguration = "";

    public String getDelegationClassName() {
        return delegationClassName;
    }

    public void setDelegationClassName(String delegationClassName) {
        String old = getDelegationClassName();
        this.delegationClassName = delegationClassName;
        firePropertyChange(PropertyNames.PROPERTY_CLASS, old, this.delegationClassName);
    }

    public String getDelegationConfiguration() {
        return delegationConfiguration;
    }

    public void setDelegationConfiguration(String delegationConfiguration) {
        if (delegationConfiguration == null) {
            delegationConfiguration = "";
        }
        if (!this.delegationConfiguration.equals(delegationConfiguration)) {
            String old = this.delegationConfiguration;
            this.delegationConfiguration = delegationConfiguration;
            firePropertyChange(PROPERTY_CONFIGURATION, old, this.delegationConfiguration);
        }
    }

    // IPropertySource
    protected void firePropertyChange(String propName, Object old, Object newValue) {
        if (!PluginConstants.NON_GUI_THREAD_NAME.equals(Thread.currentThread().getName())) {
            listeners.firePropertyChange(propName, old, newValue);
        }
        if (!PROPERTY_DIRTY.equals(propName)) {
            setDirty();
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        listeners.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        listeners.removePropertyChangeListener(pcl);
    }

    protected void removeAllPropertyChangeListeners() {
        listeners = new PropertyChangeSupport(this);
    }

    @Override
    public Object getEditableValue() {
        return this;
    }

    @Override
    public boolean isPropertySet(Object id) {
        return false;
    }

    @Override
    public void resetPropertyValue(Object id) {
    }

    public boolean isDelegable() {
        return this instanceof Delegable;
    }

    @Override
    public final IPropertyDescriptor[] getPropertyDescriptors() {
        List<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
        if (this instanceof NamedGraphElement) {
            descriptors.add(new PropertyDescriptor(PROPERTY_ID, Localization.getString("Node.property.id")));
            if (((NamedGraphElement) this).canNameBeSetFromProperties()) {
                descriptors.add(new TextPropertyDescriptor(PROPERTY_NAME, Localization.getString("property.name")));
            } else {
                descriptors.add(new PropertyDescriptor(PROPERTY_NAME, Localization.getString("property.name")));
            }
        }
        // if (this instanceof Describable) {
        descriptors.add(new TextPropertyDescriptor(PROPERTY_DESCRIPTION, Localization.getString("property.description")));
        // }
        if (isDelegable()) {
            String type = ((Delegable) this).getDelegationType();
            descriptors.add(new DelegableClassPropertyDescriptor(PROPERTY_CLASS, Localization.getString("property.delegation.class"), type));
            descriptors.add(new DelegableConfPropertyDescriptor(PROPERTY_CONFIGURATION, (Delegable) this, Localization.getString("property.delegation.configuration")));
        }
        if (this instanceof ITimed && getProcessDefinition().getLanguage() == Language.JPDL) {
            Timer timer = ((ITimed) this).getTimer();
            if (timer != null) {
                descriptors.add(new DurationPropertyDescriptor(PROPERTY_TIMER_DELAY, timer.getProcessDefinition(), timer.getDelay(), Localization.getString("property.duration")));
                descriptors.add(new TimerActionPropertyDescriptor(PROPERTY_TIMER_ACTION, Localization.getString("Timer.action"), timer));
            }
        }
        descriptors.addAll(getCustomPropertyDescriptors());
        return descriptors.toArray(new IPropertyDescriptor[descriptors.size()]);
    }

    protected List<IPropertyDescriptor> getCustomPropertyDescriptors() {
        return new ArrayList<IPropertyDescriptor>();
    }

    protected String safeStringValue(String canBeNull) {
        return canBeNull == null ? "" : canBeNull;
    }

    @Override
    public Object getPropertyValue(Object id) {
        if (PROPERTY_ID.equals(id)) {
            return getId();
        } else if (PROPERTY_CLASS.equals(id)) {
            return safeStringValue(getDelegationClassName());
        } else if (PROPERTY_CONFIGURATION.equals(id)) {
            return safeStringValue(getDelegationConfiguration());
        } else if (PROPERTY_DESCRIPTION.equals(id)) {
            return safeStringValue(getDescription());
        }
        return null;
    }

    @Override
    public void setPropertyValue(Object id, Object value) {
        if (PROPERTY_CLASS.equals(id)) {
            setDelegationClassName((String) value);
        } else if (PROPERTY_CONFIGURATION.equals(id)) {
            setDelegationConfiguration((String) value);
        } else if (PROPERTY_DESCRIPTION.equals(id)) {
            setDescription((String) value);
        }
    }

    @Override
    public String toString() {
        return getClass().getName() + " (" + getId() + ")";
    }

    public Image getEntryImage() {
        return getTypeDefinition().getImage(getProcessDefinition().getLanguage().getNotation());
    }
}
