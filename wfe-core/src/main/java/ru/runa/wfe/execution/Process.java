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

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import ru.runa.wfe.InternalApplicationException;
import ru.runa.wfe.audit.ProcessCancelLog;
import ru.runa.wfe.audit.ProcessEndLog;
import ru.runa.wfe.commons.ApplicationContextFactory;
import ru.runa.wfe.definition.Deployment;
import ru.runa.wfe.handler.assign.AssignmentHandler;
import ru.runa.wfe.job.dao.JobDAO;
import ru.runa.wfe.lang.Event;
import ru.runa.wfe.lang.ProcessDefinition;
import ru.runa.wfe.lang.SwimlaneDefinition;
import ru.runa.wfe.security.Identifiable;
import ru.runa.wfe.security.SecuredObjectType;
import ru.runa.wfe.task.Task;
import ru.runa.wfe.user.Actor;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * Is one execution of a {@link ru.runa.wfe.lang.ProcessDefinition}.
 */
@Entity
@Table(name = "BPM_PROCESS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Process implements Identifiable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long version;
    private Date startDate;
    private Date endDate;
    private Token rootToken;
    private String hierarchySubProcess;
    private Deployment definition;

    private Set<Swimlane> swimlanes;
    private Set<Task> tasks;

    public Process() {
    }

    @Transient
    @Override
    public SecuredObjectType getSecuredObjectType() {
        return SecuredObjectType.EXECUTION;
    }

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "SEQ_BPM_PROCESS")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    // TODO (if possible) delete TREE_PATH
    @Column(name = "TREE_PATH")
    public String getHierarchySubProcess() {
        return hierarchySubProcess;
    }

    public void setHierarchySubProcess(String hierarchySubProcess) {
        this.hierarchySubProcess = hierarchySubProcess;
    }

    @Column(name = "START_DATE")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date start) {
        this.startDate = start;
    }

    @Column(name = "END_DATE")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date end) {
        this.endDate = end;
    }

    @ManyToOne(targetEntity = Deployment.class)
    @JoinColumn(name = "DEFINITION_ID", nullable = false)
    @ForeignKey(name = "FK_PROCIN_PROCDEF")
    @Index(name = "IDX_PROCIN_PROCDEF")
    public Deployment getDefinition() {
        return definition;
    }

    public void setDefinition(Deployment processDeployment) {
        this.definition = processDeployment;
    }

    @ManyToOne(targetEntity = Token.class, cascade = { javax.persistence.CascadeType.ALL })
    @JoinColumn(name = "ROOT_TOKEN_ID", nullable = false)
    @ForeignKey(name = "FK_PROCIN_ROOTTKN")
    @Index(name = "IDX_PROCIN_ROOTTK")
    public Token getRootToken() {
        return rootToken;
    }

    public void setRootToken(Token rootToken) {
        this.rootToken = rootToken;
    }

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Swimlane.class)
    @JoinColumn(name = "PROCESS_ID")
    @Cascade({ CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public Set<Swimlane> getSwimlanes() {
        return swimlanes;
    }

    public void setSwimlanes(Set<Swimlane> swimlanes) {
        this.swimlanes = swimlanes;
    }

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Task.class)
    @JoinColumn(name = "PROCESS_ID")
    @Cascade({ CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Swimlane getSwimlane(String swimlaneName) {
        for (Swimlane existing : swimlanes) {
            if (swimlaneName.equals(existing.getName())) {
                return existing;
            }
        }
        return null;
    }

    public Swimlane getSwimlaneNotNull(SwimlaneDefinition swimlaneDefinition) {
        Swimlane swimlane = getSwimlane(swimlaneDefinition.getName());
        if (swimlane == null) {
            swimlane = new Swimlane(swimlaneDefinition.getName());
            swimlane.setProcess(this);
            swimlanes.add(swimlane);
        }
        return swimlane;
    }

    public Swimlane getInitializedSwimlaneNotNull(ExecutionContext executionContext, SwimlaneDefinition swimlaneDefinition, boolean reassign) {
        Swimlane swimlane = getSwimlaneNotNull(swimlaneDefinition);
        if (reassign || swimlane.getExecutor() == null) {
            try {
                // assign the swimlane
                AssignmentHandler assignmentHandler = swimlaneDefinition.getDelegation().getInstance();
                assignmentHandler.assign(executionContext, swimlane);
            } catch (Exception e) {
                throw new InternalApplicationException(swimlaneDefinition.getName(), e);
            }
        }
        return swimlane;
    }

    public Task getTask(String taskName) {
        for (Task task : tasks) {
            if (taskName.equals(task.getName())) {
                return task;
            }
        }
        return null;
    }

    /**
     * @return collection of active {@link Task}s for the given token.
     * @param token
     *            can be <code>null</code>
     */
    public List<Task> getActiveTasks(Token token) {
        List<Task> result = Lists.newArrayList();
        for (Task task : tasks) {
            if (task.isActive() && (token == null || token.equals(task.getToken()))) {
                result.add(task);
            }
        }
        return result;
    }

    /**
     * removes all active tasks related to the given token.
     */
    public void removeActiveTasks(Token token) {
        Preconditions.checkNotNull(token, "token");
        for (Task task : tasks) {
            if (token.equals(task.getToken())) {
                task.setEndDate(new Date());
            }
        }
    }

    /**
     * instructs the main path of execution to continue by taking the default transition on the current node.
     * 
     * @throws IllegalStateException
     *             if the token is not active.
     */
    public void signal(ExecutionContext executionContext) {
        Preconditions.checkState(endDate == null, "couldn't signal token : token has ended");
        rootToken.signal(executionContext);
    }

    /**
     * Cancels this process and all the tokens in it.
     */
    public void cancel(ExecutionContext executionContext, Actor actor) {
        if (!hasEnded()) {
            endInternal(executionContext);
            executionContext.addLog(new ProcessCancelLog(actor));
        }
    }

    /**
     * ends this process and all the tokens in it.
     */
    public void end(ExecutionContext executionContext) {
        if (!hasEnded()) {
            endInternal(executionContext);
            executionContext.addLog(new ProcessEndLog());
        }
    }

    private void endInternal(ExecutionContext executionContext) {
        // end the main path of execution
        rootToken.end(executionContext);
        // mark this process as ended
        setEndDate(new Date());
        // fire the process-end event
        // OLD stuff ExecutionContext executionContext = new
        // ExecutionContext(rootToken);
        executionContext.getProcessDefinition().fireEvent(executionContext, Event.EVENTTYPE_PROCESS_END);

        // check if this process was started as a subprocess of a super process
        NodeProcess parentNodeProcess = executionContext.getParentNodeProcess();
        if (parentNodeProcess != null && !parentNodeProcess.getParentToken().hasEnded()) {
            Long superDefinitionId = parentNodeProcess.getProcess().getDefinition().getId();
            ProcessDefinition superDefinition = ApplicationContextFactory.getProcessDefinitionLoader().getDefinition(superDefinitionId);
            ExecutionContext superExecutionContext = new ExecutionContext(superDefinition, parentNodeProcess.getParentToken());
            parentNodeProcess.getParentToken().signal(superExecutionContext);
        }

        // make sure all the timers for this process are canceled
        // after the process end updates are posted to the database
        JobDAO jobDAO = ApplicationContextFactory.getJobDAO();
        jobDAO.deleteJobs(this);
    }

    /**
     * Tells if this process is still active or not.
     */
    public boolean hasEnded() {
        return endDate != null;
    }

    @Override
    public String toString() {
        return getDefinition().getName() + ": " + id;
    }

}
