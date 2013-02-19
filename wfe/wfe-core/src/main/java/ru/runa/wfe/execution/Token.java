/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package ru.runa.wfe.execution;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;

import ru.runa.wfe.lang.Event;
import ru.runa.wfe.lang.Node;
import ru.runa.wfe.lang.NodeType;
import ru.runa.wfe.lang.ProcessDefinition;
import ru.runa.wfe.lang.StartState;
import ru.runa.wfe.lang.Transition;
import ru.runa.wfe.task.Task;
import ru.runa.wfe.user.Actor;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

/**
 * represents one path of execution and maintains a pointer to a node in the
 * {@link ru.runa.wfe.lang.ProcessDefinition}.
 */
@Entity
@Table(name = "BPM_TOKEN")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Token implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long version;
    private String name;
    private Date startDate;
    private Date endDate;
    private Process process;
    private Token parent;
    private Set<Token> children;
    private boolean ableToReactivateParent;

    private String nodeId;
    private NodeType nodeType;
    private String transitionId;

    public Token() {
    }

    /**
     * creates a root token.
     */
    public Token(ProcessDefinition processDefinition, Process process) {
        setStartDate(new Date());
        setProcess(process);
        StartState startState = processDefinition.getStartStateNotNull();
        setNodeId(startState.getNodeId());
        setNodeType(startState.getNodeType());
        setAbleToReactivateParent(true);
        setName("");
        setChildren(new HashSet<Token>());
    }

    /**
     * creates a child token.
     */
    public Token(Token parent, String name) {
        setStartDate(new Date());
        setProcess(parent.getProcess());
        setName(parent.getName() + "/" + name);
        setNodeId(parent.getNodeId());
        setNodeType(parent.getNodeType());
        setTransitionId(parent.getTransitionId());
        setAbleToReactivateParent(true);
        setChildren(new HashSet<Token>());
        setParent(parent);
        parent.addChild(this);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "SEQ_BPM_TOKEN")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    @Version
    @Column(name = "VERSION")
    protected Long getVersion() {
        return version;
    }

    protected void setVersion(Long version) {
        this.version = version;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "NODE_ID")
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    @Column(name = "NODE_TYPE")
    @Enumerated(EnumType.STRING)
    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    @Column(name = "TRANSITION_ID")
    public String getTransitionId() {
        return transitionId;
    }

    public void setTransitionId(String transitionId) {
        this.transitionId = transitionId;
    }

    @Column(name = "START_DATE")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date start) {
        startDate = start;
    }

    @Column(name = "END_DATE")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date end) {
        endDate = end;
    }

    @ManyToOne(targetEntity = Process.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "PROCESS_ID")
    @ForeignKey(name = "FK_TOKEN_PROCESS")
    @Index(name = "IX_TOKEN_PROCESS")
    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    @ManyToOne(targetEntity = Token.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    @ForeignKey(name = "FK_TOKEN_PARENT")
    @Index(name = "IX_TOKEN_PARENT")
    public Token getParent() {
        return parent;
    }

    public void setParent(Token parent) {
        this.parent = parent;
    }

    @OneToMany(targetEntity = Token.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    @Cascade({ CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public Set<Token> getChildren() {
        return children;
    }

    public void setChildren(Set<Token> children) {
        this.children = children;
    }

    @Column(name = "REACTIVATE_PARENT")
    public boolean isAbleToReactivateParent() {
        return ableToReactivateParent;
    }

    public void setAbleToReactivateParent(boolean ableToReactivateParent) {
        this.ableToReactivateParent = ableToReactivateParent;
    }

    public Node getNode(ProcessDefinition processDefinition) {
        return processDefinition.getNodeNotNull(nodeId);
    }

    @Transient
    public List<Task> getActiveTasks() {
        return getProcess().getActiveTasks(this);
    }

    private void addChild(Token token) {
        getChildren().add(token);
    }

    public void signal(ExecutionContext executionContext) {
        signal(executionContext, null);
    }

    public void signal(ExecutionContext executionContext, Transition transition) {
        if (!hasEnded()) {
            Node node = getNode(executionContext.getProcessDefinition());
            // fire the event before-signal
            node.fireEvent(executionContext, Event.EVENTTYPE_BEFORE_SIGNAL);
            node.leave(executionContext, transition);
            // fire the event after-signal
            node.fireEvent(executionContext, Event.EVENTTYPE_AFTER_SIGNAL);
        }
    }

    /**
     * ends this token and all of its children (if any).
     * 
     * @param canceller
     *            actor who cancels process (if any), can be <code>null</code>
     */
    public void end(ExecutionContext executionContext, Actor canceller) {
        // if not already ended
        if (endDate == null) {
            // ended tokens cannot reactivate parents
            // ableToReactivateParent = false;
            // set the end date
            // the end date is also the flag that indicates that this token has
            // ended.
            endDate = new Date();
            // end all this token's children
            for (Token child : getChildren()) {
                if (!child.hasEnded()) {
                    child.end(executionContext, canceller);
                }
            }
            for (Process subProcess : executionContext.getChildProcesses()) {
                subProcess.end(executionContext, canceller);
            }
        }
    }

    public boolean hasEnded() {
        return endDate != null;
    }

    @Transient
    public List<Token> getActiveChildren() {
        List<Token> activeChildren = Lists.newArrayList();
        for (Token child : getChildren()) {
            if (!child.hasEnded()) {
                activeChildren.add(child);
            }
        }
        return activeChildren;
    }

    // public void reactivateIfAllChildrenArePassive(ExecutionContext
    // executionContext, Node node) {
    // boolean reactivateParent = true;
    // for (Token token : children) {
    // if (!token.hasEnded() && token.isAbleToReactivateParent()) {
    // reactivateParent = false;
    // break;
    // }
    // }
    // if (reactivateParent) {
    // // write to all child tokens that the parent is already
    // // reactivated
    // for (Token concurrentToken : children) {
    // concurrentToken.setAbleToReactivateParent(false);
    // }
    // // write to all child tokens that the parent is already
    // // reactivated
    // node.leave(new ExecutionContext(executionContext.getProcessDefinition(),
    // this));
    // }
    // }

    // TODO: token death example
    // public void checkImplicitTermination() {
    // if (terminationImplicit && node.hasNoLeavingTransitions()) {
    // end();
    // if (process.isTerminatedImplicitly()) {
    // process.end();
    // }
    // }
    // }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("process", getProcess()).add("name", name).toString();
    }

}
