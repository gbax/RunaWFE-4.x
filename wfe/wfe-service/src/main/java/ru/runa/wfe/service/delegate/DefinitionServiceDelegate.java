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
package ru.runa.wfe.service.delegate;

import java.util.List;

import ru.runa.wfe.definition.DefinitionDoesNotExistException;
import ru.runa.wfe.definition.dto.WfDefinition;
import ru.runa.wfe.form.Interaction;
import ru.runa.wfe.graph.view.GraphElementPresentation;
import ru.runa.wfe.lang.ProcessDefinition;
import ru.runa.wfe.lang.SwimlaneDefinition;
import ru.runa.wfe.presentation.BatchPresentation;
import ru.runa.wfe.service.DefinitionService;
import ru.runa.wfe.user.User;
import ru.runa.wfe.var.VariableDefinition;

/**
 * Provides simplified access to local ProcessDefinition. Created on 28.09.2004
 */
public class DefinitionServiceDelegate extends EJB3Delegate implements DefinitionService {

    public DefinitionServiceDelegate() {
        super(DefinitionService.class);
    }

    private DefinitionService getDefinitionService() {
        return getService();
    }

    @Override
    public WfDefinition deployProcessDefinition(User user, byte[] process, List<String> processType) {
        try {
            return getDefinitionService().deployProcessDefinition(user, process, processType);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public WfDefinition redeployProcessDefinition(User user, Long processId, byte[] processArchive, List<String> processType) {
        try {
            return getDefinitionService().redeployProcessDefinition(user, processId, processArchive, processType);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public List<WfDefinition> getLatestProcessDefinitions(User user, BatchPresentation batchPresentation) {
        try {
            return getDefinitionService().getLatestProcessDefinitions(user, batchPresentation);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public WfDefinition getLatestProcessDefinition(User user, String definitionName) {
        try {
            return getDefinitionService().getLatestProcessDefinition(user, definitionName);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public WfDefinition getProcessDefinition(User user, Long definitionId) {
        try {
            return getDefinitionService().getProcessDefinition(user, definitionId);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public ProcessDefinition getParsedProcessDefinition(User user, Long definitionId) throws DefinitionDoesNotExistException {
        try {
            return getDefinitionService().getParsedProcessDefinition(user, definitionId);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public void undeployProcessDefinition(User user, String definitionName, Long version) {
        try {
            getDefinitionService().undeployProcessDefinition(user, definitionName, version);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public List<String> getOutputTransitionNames(User user, Long definitionId, Long taskId, boolean withTimerTransitions) {
        try {
            return getDefinitionService().getOutputTransitionNames(user, definitionId, taskId, withTimerTransitions);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public Interaction getStartInteraction(User user, Long definitionId) {
        try {
            return getDefinitionService().getStartInteraction(user, definitionId);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public Interaction getTaskInteraction(User user, Long taskId) {
        try {
            return getDefinitionService().getTaskInteraction(user, taskId);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public Interaction getTaskNodeInteraction(User user, Long definitionId, String nodeId) {
        try {
            return getDefinitionService().getTaskNodeInteraction(user, definitionId, nodeId);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public byte[] getProcessDefinitionFile(User user, Long definitionId, String fileName) {
        try {
            return getDefinitionService().getProcessDefinitionFile(user, definitionId, fileName);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public byte[] getProcessDefinitionGraph(User user, Long definitionId, String subprocessId) throws DefinitionDoesNotExistException {
        try {
            return getDefinitionService().getProcessDefinitionGraph(user, definitionId, subprocessId);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public List<SwimlaneDefinition> getSwimlaneDefinitions(User user, Long definitionId) {
        try {
            return getDefinitionService().getSwimlaneDefinitions(user, definitionId);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public List<VariableDefinition> getVariableDefinitions(User user, Long definitionId) {
        try {
            return getDefinitionService().getVariableDefinitions(user, definitionId);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public VariableDefinition getVariableDefinition(User user, Long definitionId, String variableName) throws DefinitionDoesNotExistException {
        try {
            return getDefinitionService().getVariableDefinition(user, definitionId, variableName);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public List<GraphElementPresentation> getProcessDefinitionGraphElements(User user, Long definitionId, String subprocessId) {
        try {
            return getDefinitionService().getProcessDefinitionGraphElements(user, definitionId, subprocessId);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @Override
    public List<WfDefinition> getProcessDefinitionHistory(User user, String name) {
        try {
            return getDefinitionService().getProcessDefinitionHistory(user, name);
        } catch (Exception e) {
            throw handleException(e);
        }
    }
}
