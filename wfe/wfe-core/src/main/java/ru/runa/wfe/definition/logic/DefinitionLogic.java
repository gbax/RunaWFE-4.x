/*
 * This file is part of the RUNA WFE project.
 * 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation; version 2.1 
 * of the License. 
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU Lesser General Public License for more details. 
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this program; if not, write to the Free Software 
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package ru.runa.wfe.definition.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import ru.runa.wfe.audit.ProcessDefinitionDeleteLog;
import ru.runa.wfe.audit.dao.SystemLogDAO;
import ru.runa.wfe.commons.logic.WFCommonLogic;
import ru.runa.wfe.definition.DefinitionAlreadyExistException;
import ru.runa.wfe.definition.DefinitionArchiveFormatException;
import ru.runa.wfe.definition.DefinitionDoesNotExistException;
import ru.runa.wfe.definition.DefinitionNameMismatchException;
import ru.runa.wfe.definition.DefinitionPermission;
import ru.runa.wfe.definition.Deployment;
import ru.runa.wfe.definition.Language;
import ru.runa.wfe.definition.WorkflowSystemPermission;
import ru.runa.wfe.definition.dto.WfDefinition;
import ru.runa.wfe.definition.par.ProcessArchive;
import ru.runa.wfe.execution.ParentProcessExistsException;
import ru.runa.wfe.execution.Process;
import ru.runa.wfe.execution.ProcessFilter;
import ru.runa.wfe.form.Interaction;
import ru.runa.wfe.graph.image.SubprocessPermissionVisitor;
import ru.runa.wfe.graph.view.GraphElementPresentation;
import ru.runa.wfe.lang.ProcessDefinition;
import ru.runa.wfe.lang.SwimlaneDefinition;
import ru.runa.wfe.lang.Transition;
import ru.runa.wfe.presentation.BatchPresentation;
import ru.runa.wfe.presentation.hibernate.BatchPresentationHibernateCompiler;
import ru.runa.wfe.security.ASystem;
import ru.runa.wfe.security.Permission;
import ru.runa.wfe.task.Task;
import ru.runa.wfe.user.User;
import ru.runa.wfe.var.VariableDefinition;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * Created on 15.03.2005
 */
public class DefinitionLogic extends WFCommonLogic {
    @Autowired
    private SystemLogDAO systemLogDAO;

    public WfDefinition deployProcessDefinition(User user, byte[] processArchiveBytes, List<String> processType) {
        checkPermissionAllowed(user, ASystem.INSTANCE, WorkflowSystemPermission.DEPLOY_DEFINITION);
        ProcessDefinition definition = null;
        try {
            definition = parseProcessDefinition(processArchiveBytes);
        } catch (Exception e) {
            throw new DefinitionArchiveFormatException(e);
        }
        try {
            getLatestDefinition(definition.getName());
            throw new DefinitionAlreadyExistException(definition.getName());
        } catch (DefinitionDoesNotExistException e) {
            // expected
        }
        definition.getDeployment().setCategories(processType);
        deploymentDAO.deploy(definition.getDeployment(), null);
        Collection<Permission> allPermissions = new DefinitionPermission().getAllPermissions();
        permissionDAO.setPermissions(user.getActor(), allPermissions, definition.getDeployment());
        log.debug("Deployed process definition " + definition);
        return new WfDefinition(definition);
    }

    public WfDefinition redeployProcessDefinition(User user, Long definitionId, byte[] processArchiveBytes, List<String> processType) {
        Deployment oldDeployment = deploymentDAO.getNotNull(definitionId);
        checkPermissionAllowed(user, oldDeployment, DefinitionPermission.REDEPLOY_DEFINITION);
        if (processArchiveBytes == null) {
            Preconditions.checkNotNull(processType, "In mode 'update only categories' process type is required");
            oldDeployment.setCategories(processType);
            return getProcessDefinition(user, definitionId);
        }
        ProcessDefinition definition;
        try {
            definition = parseProcessDefinition(processArchiveBytes);
        } catch (Exception e) {
            throw new DefinitionArchiveFormatException(e);
        }
        if (!oldDeployment.getName().equals(definition.getName())) {
            throw new DefinitionNameMismatchException("Expected definition name " + oldDeployment.getName(), definition.getName(),
                    oldDeployment.getName());
        }
        if (processType != null) {
            definition.getDeployment().setCategories(processType);
        } else {
            definition.getDeployment().setCategory(oldDeployment.getCategory());
        }
        deploymentDAO.deploy(definition.getDeployment(), oldDeployment);
        // for (Executor executor :
        // permissionDAO.getExecutorsWithPermission(deployment)) {
        // Map<Permission, Boolean> permissions =
        // permissionDAO.getOwnPermissions(executor, deployment);
        // permissionDAO.setPermissions(executor, permissions.keySet(),
        // definition);
        // }
        log.debug("Process definition " + oldDeployment + " was successfully redeployed");
        return new WfDefinition(definition);
    }

    public WfDefinition getLatestProcessDefinition(User user, String definitionName) {
        ProcessDefinition definition = getLatestDefinition(definitionName);
        checkPermissionAllowed(user, definition.getDeployment(), Permission.READ);
        return new WfDefinition(definition);
    }

    public WfDefinition getProcessDefinition(User user, Long definitionId) {
        try {
            ProcessDefinition processDefinition = getDefinition(definitionId);
            checkPermissionAllowed(user, processDefinition.getDeployment(), Permission.READ);
            return new WfDefinition(processDefinition);
        } catch (Exception e) {
            Deployment deployment = deploymentDAO.getNotNull(definitionId);
            checkPermissionAllowed(user, deployment, Permission.READ);
            return new WfDefinition(deployment);
        }
    }

    /**
     * Loads graph presentation elements for process definition and set readable
     * flag.
     * 
     * @param user
     *            Current user.
     * @param definitionId
     *            Identity of process definition, which presentation elements
     *            must be loaded.
     * @return List of graph presentation elements.
     */
    public List<GraphElementPresentation> getProcessDefinitionGraphElements(User user, Long definitionId) {
        ProcessDefinition definition = getDefinition(definitionId);
        checkPermissionAllowed(user, definition.getDeployment(), DefinitionPermission.READ);
        SubprocessPermissionVisitor operation = new SubprocessPermissionVisitor(user, definition, processDefinitionLoader);
        return getDefinitionGraphElements(user, definition, operation);
    }

    public List<WfDefinition> getLatestProcessDefinitions(User user, BatchPresentation batchPresentation) {
        List<Number> deploymentIds = new BatchPresentationHibernateCompiler(batchPresentation).getIdentities(false);
        List<WfDefinition> result = Lists.newArrayListWithExpectedSize(deploymentIds.size());
        for (Number definitionId : deploymentIds) {
            try {
                ProcessDefinition processDefinition = getDefinition(definitionId.longValue());
                result.add(new WfDefinition(processDefinition));
            } catch (Exception e) {
                Deployment deployment = deploymentDAO.get(definitionId.longValue());
                result.add(new WfDefinition(deployment));
            }
        }
        return filterIdentifiable(user, result, Permission.READ);
    }

    public List<WfDefinition> getProcessDefinitionHistory(User user, String name) {
        List<Deployment> deploymentVersions = deploymentDAO.findAllDeploymentVersions(name);
        List<WfDefinition> result = Lists.newArrayListWithExpectedSize(deploymentVersions.size());
        for (Deployment deployment : deploymentVersions) {
            if (isPermissionAllowed(user, deployment, Permission.READ)) {
                result.add(new WfDefinition(deployment));
            }
        }
        return result;
    }

    public void undeployProcessDefinition(User user, String definitionName) {
        Preconditions.checkNotNull(definitionName, "definitionName must be specified.");
        Deployment deployment = deploymentDAO.findLatestDeployment(definitionName);
        checkPermissionAllowed(user, deployment, DefinitionPermission.UNDEPLOY_DEFINITION);
        permissionDAO.deleteAllPermissions(deployment);
        ProcessFilter filter = new ProcessFilter();
        filter.setDefinitionName(definitionName);
        List<Process> processes = processDAO.getProcesses(filter);
        for (Process process : processes) {
            if (nodeProcessDAO.getNodeProcessByChild(process.getId()) != null) {
                throw new ParentProcessExistsException(definitionName, nodeProcessDAO.getNodeProcessByChild(process.getId()).getProcess()
                        .getDeployment().getName());
            }
        }
        deleteProcessDefinitionsByName(user, definitionName);
        log.debug("Process definition " + deployment + " was undeployed");
    }

    /**
     * Deletes all Process Definitions from the database
     */
    private void deleteProcessDefinitionsByName(User user, String definitionName) {
        List<Deployment> deployments = deploymentDAO.findAllDeploymentVersions(definitionName);
        for (Deployment deployment : deployments) {
            // delete all the processes of this definition
            List<Process> processes = processDAO.findAllProcesses(deployment.getId());
            for (Process process : processes) {
                deleteProcess(process);
            }
            deploymentDAO.delete(deployment);
            systemLogDAO.create(new ProcessDefinitionDeleteLog(user.getActor().getId(), definitionName, deployment.getVersion()));
        }
    }

    public Interaction getInteraction(User user, Long taskId) {
        Task task = taskDAO.getNotNull(taskId);
        ProcessDefinition definition = getDefinition(task);
        if (!isPermissionAllowed(user, definition.getDeployment(), DefinitionPermission.READ)) {
            checkCanParticipate(user.getActor(), task);
        }
        return definition.getInteractionNotNull(task.getNodeId());
    }

    public List<String> getOutputTransitionNames(User user, Long definitionId, Long taskId, boolean withTimerTransitions) {
        List<Transition> transitions;
        if (definitionId != null) {
            ProcessDefinition processDefinition = getDefinition(definitionId);
            transitions = processDefinition.getStartStateNotNull().getLeavingTransitions();
        } else {
            Task task = taskDAO.getNotNull(taskId);
            ProcessDefinition processDefinition = getDefinition(task);
            transitions = processDefinition.getNodeNotNull(task.getNodeId()).getLeavingTransitions();
        }
        List<String> result = new ArrayList<String>();
        for (Transition transition : transitions) {
            if (withTimerTransitions || !transition.isTimerTransition()) {
                result.add(transition.getName());
            }
        }
        return result;
    }

    public byte[] getFile(User user, Long definitionId, String fileName) {
        ProcessDefinition definition = getDefinition(definitionId);
        if (!ProcessArchive.UNSECURED_FILE_NAMES.contains(fileName)) {
            checkPermissionAllowed(user, definition.getDeployment(), DefinitionPermission.READ);
        }
        if (ProcessArchive.PAR_FILE.equals(fileName)) {
            return definition.getDeployment().getContent();
        }
        return definition.getFileData(fileName);
    }

    public Interaction getStartInteraction(User user, Long definitionId) {
        ProcessDefinition definition = getDefinition(definitionId);
        checkPermissionAllowed(user, definition.getDeployment(), DefinitionPermission.READ);
        Interaction interaction = definition.getInteractionNotNull(definition.getStartStateNotNull().getNodeId());
        Map<String, Object> defaultValues = definition.getDefaultVariableValues();
        for (Map.Entry<String, Object> entry : defaultValues.entrySet()) {
            interaction.getDefaultVariableValues().put(entry.getKey(), entry.getValue());
        }
        return interaction;
    }

    public List<SwimlaneDefinition> getSwimlanes(User user, Long definitionId) {
        ProcessDefinition definition = processDefinitionLoader.getDefinition(definitionId);
        checkPermissionAllowed(user, definition.getDeployment(), DefinitionPermission.READ);
        return Lists.newArrayList(definition.getSwimlanes().values());
    }

    public List<VariableDefinition> getProcessDefinitionVariables(User user, Long definitionId) {
        ProcessDefinition definition = getDefinition(definitionId);
        checkPermissionAllowed(user, definition.getDeployment(), DefinitionPermission.READ);
        return definition.getVariables();
    }

    private ProcessDefinition parseProcessDefinition(byte[] data) {
        Deployment deployment = new Deployment();
        deployment.setDeployedDate(new Date());
        deployment.setLanguage(Language.JPDL);
        deployment.setContent(data);
        ProcessArchive archive = new ProcessArchive(deployment);
        return archive.parseProcessDefinition();
    }

}
