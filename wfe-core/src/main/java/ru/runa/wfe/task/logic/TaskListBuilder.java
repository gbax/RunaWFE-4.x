package ru.runa.wfe.task.logic;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ru.runa.wfe.definition.dao.IProcessDefinitionLoader;
import ru.runa.wfe.execution.ExecutionContext;
import ru.runa.wfe.execution.IExecutorContextFactory;
import ru.runa.wfe.lang.ProcessDefinition;
import ru.runa.wfe.presentation.BatchPresentation;
import ru.runa.wfe.presentation.hibernate.BatchPresentationHibernateCompiler;
import ru.runa.wfe.ss.Substitution;
import ru.runa.wfe.ss.SubstitutionCriteria;
import ru.runa.wfe.ss.TerminatorSubstitution;
import ru.runa.wfe.ss.logic.ISubstitutionLogic;
import ru.runa.wfe.task.Task;
import ru.runa.wfe.task.cache.TaskCache;
import ru.runa.wfe.task.dao.TaskDAO;
import ru.runa.wfe.task.dto.IWfTaskFactory;
import ru.runa.wfe.task.dto.WfTask;
import ru.runa.wfe.user.Actor;
import ru.runa.wfe.user.Executor;
import ru.runa.wfe.user.Group;
import ru.runa.wfe.user.dao.ExecutorDAO;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Task list builder component.
 * 
 * @author Dofs
 * @since 4.0
 */
public class TaskListBuilder implements ITaskListBuilder {

    protected static final int CAN_I_SUBSTITUTE = 1;
    protected static final int SUBSTITUTION_APPLIES = 0x10;

    private static final Log log = LogFactory.getLog(TaskListBuilder.class);

    private final TaskCache taskCache;
    @Autowired
    private IWfTaskFactory taskObjectFactory;
    @Autowired
    private ExecutorDAO executorDAO;
    @Autowired
    private ISubstitutionLogic substitutionLogic;
    @Autowired
    private IProcessDefinitionLoader processDefinitionLoader;
    @Autowired
    private TaskDAO taskDAO;
    @Autowired
    private IExecutorContextFactory executorContextFactory;

    public TaskListBuilder(TaskCache cache) {
        taskCache = cache;
    }

    @Override
    public List<WfTask> getTasks(Actor actor, BatchPresentation batchPresentation) {
        Preconditions.checkNotNull(batchPresentation, "batchPresentation");
        List<WfTask> result = taskCache.getTasks(actor.getId(), batchPresentation);
        if (result != null) {
            return result;
        }
        result = Lists.newArrayList();
        Set<Executor> executorsToGetTasksByMembership = getExecutorsToGetTasks(actor, false);
        Set<Executor> executorsToGetTasks = Sets.newHashSet(executorsToGetTasksByMembership);
        getSubstituteExecutorsToGetTasks(actor, executorsToGetTasks);
        List<Task> tasks = new BatchPresentationHibernateCompiler(batchPresentation).getBatch(executorsToGetTasks, "executor", false);
        for (Task task : tasks) {
            try {
                WfTask acceptable = getAcceptableTask(task, actor, batchPresentation, executorsToGetTasksByMembership);
                if (acceptable == null) {
                    continue;
                }
                result.add(acceptable);
            } catch (Exception e) {
                if (taskDAO.get(task.getId()) == null) {
                    log.debug(String.format("getTasks: task: %s has been completed", task, e));
                    continue;
                }
                log.error(String.format("getTasks: task: %s unable to build ", task), e);
            }
        }
        taskCache.setTasks(taskCache.getCacheVersion(), actor.getId(), batchPresentation, result);
        return result;
    }

    protected WfTask getAcceptableTask(Task task, Actor actor, BatchPresentation batchPresentation, Set<Executor> executorsToGetTasksByMembership) {
        Executor taskExecutor = task.getExecutor();
        ProcessDefinition processDefinition = processDefinitionLoader.getDefinition(task.getProcess());
        if (executorsToGetTasksByMembership.contains(taskExecutor)) {
            log.debug(String.format("getAcceptableTask: task: %s is acquired by membership rules", task));
            return taskObjectFactory.create(task, actor, false, batchPresentation.getDynamicFieldsToDisplay(true));
        }
        if (processDefinition.ignoreSubsitutionRulesForTask(task)) {
            log.debug(String.format("getAcceptableTask: task: %s is ignored due to ignore subsitution rule", task));
            return null;
        }
        return getAcceptableTask(task, actor, batchPresentation, executorContextFactory.createExecutionContext(processDefinition, task));
    }

    protected WfTask getAcceptableTask(Task task, Actor actor, BatchPresentation batchPresentation, ExecutionContext executionContext) {
        log.debug(String.format("getAcceptableTask: whether task: %s should be acquired by substitution rules?", task));
        boolean firstOpen = !task.getOpenedByExecutorIds().contains(actor.getId());
        Executor taskExecutor = task.getExecutor();
        if (taskExecutor instanceof Actor) {
            if (isTaskAcceptableBySubstitutionRules(executionContext, task, (Actor) taskExecutor, actor)) {
                log.debug(String.format("getAcceptableTask: task: %s is acquired by substitution rules [by actor]", task));
                return taskObjectFactory.create(task, (Actor) taskExecutor, true, batchPresentation.getDynamicFieldsToDisplay(true), firstOpen);
            }
        } else {
            for (Actor groupActor : executorDAO.getGroupActors((Group) taskExecutor)) {
                if (!isTaskAcceptableBySubstitutionRules(executionContext, task, groupActor, actor)) {
                    continue;
                }
                log.debug(String.format("getAcceptableTask: task: %s is acquired by substitution rules [by group]", task));
                return taskObjectFactory.create(task, groupActor, true, batchPresentation.getDynamicFieldsToDisplay(true), firstOpen);
            }
        }
        return null;
    }

    protected void getSubstituteExecutorsToGetTasks(Actor actor, Set<Executor> out) {
        Set<Long> substitutedActors = substitutionLogic.getSubstituted(actor);
        log.debug(String.format("getExecutorsToGetTasks: building tasklist for: %s with substituted: %s", actor, substitutedActors));
        for (Long substitutedActor : substitutedActors) {
            out.addAll(getExecutorsToGetTasks(executorDAO.getActor(substitutedActor), true));
        }
    }

    protected Set<Executor> getExecutorsToGetTasks(Actor actor, boolean addOnlyInactiveGroups) {
        Set<Executor> executors = new HashSet<Executor>();
        executors.add(actor);
        Set<Group> upperGroups = executorDAO.getExecutorParentsAll(actor);
        if (addOnlyInactiveGroups) {
            for (Group group : upperGroups) {
                if (!hasActiveActorInGroup(group)) {
                    executors.add(group);
                }
            }
        } else {
            executors.addAll(upperGroups);
        }
        return executors;
    }

    protected boolean isTaskAcceptableBySubstitutionRules(ExecutionContext executionContext, Task task, Actor assignedActor, Actor substitutorActor) {
        TreeMap<Substitution, Set<Long>> mapOfSubstitionRule = substitutionLogic.getSubstitutors(assignedActor);
        for (Map.Entry<Substitution, Set<Long>> substitutionRule : mapOfSubstitionRule.entrySet()) {
            Substitution substitution = substitutionRule.getKey();
            SubstitutionCriteria criteria = substitution.getCriteria();
            if (substitution instanceof TerminatorSubstitution) {
                if (criteriaIsSatisfied(criteria, executionContext, task, assignedActor, substitutorActor)) {
                    log.debug(String.format("isTaskAcceptableBySubstitutionRules: task: %s is ignored due to acceptable terminator rule", task));
                    return false;
                }
                continue;
            }
            int substitutionRules = checkSubstitutionRules(criteria, substitutionRule.getValue(), executionContext, task, assignedActor,
                    substitutorActor);
            if ((substitutionRules & SUBSTITUTION_APPLIES) == 0) {
                continue;
            }
            return (substitutionRules & CAN_I_SUBSTITUTE) != 0;
        }
        log.debug(String.format("isTaskAcceptableBySubstitutionRules:  task: %s is ignored due to no subsitution rule applies: %s", task,
                mapOfSubstitionRule));
        return false;
    }

    protected int checkSubstitutionRules(SubstitutionCriteria criteria, Set<Long> ids, ExecutionContext executionContext, Task task,
            Actor assignedActor, Actor substitutorActor) {
        int result = 0;
        for (Long actorId : ids) {
            Actor actor = executorDAO.getActor(actorId);
            if (actor.isActive() && criteriaIsSatisfied(criteria, executionContext, task, assignedActor, actor)) {
                log.debug(String.format("checkSubstitutionCriteriaRules: to task: %s is applied %s", task, criteria));
                result |= SUBSTITUTION_APPLIES;
            }
            if (Objects.equal(actor, substitutorActor)) {
                result |= CAN_I_SUBSTITUTE;
            }
        }
        return result;
    }

    protected boolean criteriaIsSatisfied(SubstitutionCriteria criteria, ExecutionContext executionContext, Task task, Actor asActor,
            Actor substitutorActor) {
        return (criteria == null || criteria.isSatisfied(executionContext, task, asActor, substitutorActor));
    }

    protected boolean hasActiveActorInGroup(Group group) {
        for (Actor actor : executorDAO.getGroupActors(group)) {
            if (actor.isActive()) {
                return true;
            }
        }
        return false;
    }

}
