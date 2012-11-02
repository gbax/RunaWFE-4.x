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
 * aLong with this program; if not, write to the Free Software 
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package ru.runa.wfe.definition.cache;

import java.util.List;

import ru.runa.wfe.commons.cache.BaseCacheImpl;
import ru.runa.wfe.commons.cache.Cache;
import ru.runa.wfe.definition.DefinitionDoesNotExistException;
import ru.runa.wfe.definition.Deployment;
import ru.runa.wfe.definition.dao.DefinitionDAO;
import ru.runa.wfe.definition.jpdl.JpdlProcessArchive;
import ru.runa.wfe.lang.ProcessDefinition;

import com.google.common.collect.Lists;

class ProcessDefCacheImpl extends BaseCacheImpl implements ProcessDefinitionCache {

    public static final String definitionIdToDefinitionName = "ru.runa.wfe.wfe.wf.caches.definitionIdToDefinition";
    public static final String definitionNameToLatestDefinitionName = "ru.runa.wfe.wfe.wf.caches.definitionNameToLatestDefinition";
    public static final String definitionNameToSwimlaneNamesName = "ru.runa.wfe.wfe.wf.caches.definitionNameToSwimlaneNames";
    public static final String definitionNameToTypeName = "ru.runa.wfe.wfe.wf.caches.definitionNameToType";
    public static final String definitionIDtoFilesName = "ru.runa.wfe.wfe.wf.caches.definitionIDtoFiles";
    public static final String definitionIDtoFormsName = "ru.runa.wfe.wfe.wf.caches.definitionIDtoForms";
    public static final String instanceIDtoDefinitionName = "ru.runa.wfe.wfe.wf.caches.instanceIDtoDefinition";
    public static final String taskIDtoDefinitionName = "ru.runa.wfe.wfe.wf.caches.taskIDtoDefinition";

    private final Cache<Long, ProcessDefinition> definitionIdToDefinition;
    private final Cache<String, Long> definitionNameToId;
    private final List<ProcessDefinition> latestProcessDefinition = Lists.newArrayList();

    public ProcessDefCacheImpl() {
        definitionIdToDefinition = createCache(definitionIdToDefinitionName);
        definitionNameToId = createCache(definitionNameToLatestDefinitionName);
    }

    public void clear(Deployment deployment) {
        definitionIdToDefinition.remove(deployment.getId());
        definitionNameToId.remove(deployment.getName());
        latestProcessDefinition.clear();
    }

    @Override
    public ProcessDefinition getDefinition(DefinitionDAO definitionDAO, Long definitionId) throws DefinitionDoesNotExistException {
        ProcessDefinition processDefinition = null;
        synchronized (this) {
            processDefinition = definitionIdToDefinition.get(definitionId);
            if (processDefinition != null) {
                return processDefinition;
            }
        }
        Deployment processDeploymentDbImpl = definitionDAO.getDeploymentNotNull(definitionId);
        JpdlProcessArchive archive = new JpdlProcessArchive(processDeploymentDbImpl);
        processDefinition = archive.parseProcessDefinition();
        synchronized (this) {
            definitionIdToDefinition.put(definitionId, processDefinition);
        }
        return processDefinition;
    }

    @Override
    public ProcessDefinition getLatestDefinition(DefinitionDAO definitionDAO, String definitionName) {
        Long definitionId = null;
        synchronized (this) {
            definitionId = definitionNameToId.get(definitionName);
            if (definitionId != null) {
                return getDefinition(definitionDAO, definitionId);
            }
        }
        definitionId = definitionDAO.findLatestDeployment(definitionName).getId();
        synchronized (this) {
            definitionNameToId.put(definitionName, definitionId);
        }
        return getDefinition(definitionDAO, definitionId);
    }

    @Override
    public List<ProcessDefinition> getLatestProcessDefinitions(DefinitionDAO definitionDAO) {
        synchronized (this) {
            if (latestProcessDefinition.size() != 0) {
                return latestProcessDefinition;
            }
        }
        List<Deployment> result = definitionDAO.findLatestDeployments();
        synchronized (this) {
            for (Deployment processDeployment : result) {
                latestProcessDefinition.add(getDefinition(definitionDAO, processDeployment.getId()));
            }
        }
        return latestProcessDefinition;
    }

}
