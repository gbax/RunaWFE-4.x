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
package ru.runa.wfe.definition.cache;

import java.util.List;

import ru.runa.wfe.definition.DefinitionDoesNotExistException;
import ru.runa.wfe.definition.dao.DefinitionDAO;
import ru.runa.wfe.lang.ProcessDefinition;

/**
 * Interface for process definition cache components.
 */
public interface ProcessDefinitionCache {
    /**
     * Returns {@link ProcessDefinition} with specified identity.
     * 
     * @param definitionDAO
     *            {@link DefinitionDAO}, which will be used to load {@link ProcessDefinition} from database if it's not in cache.
     * @param definitionId
     *            {@link ProcessDefinition} identity.
     * @return {@link ProcessDefinition} with specified identity.
     * @throws DefinitionDoesNotExistException
     *             {@link ProcessDefinition} with specified identity doesn't exists.
     */
    public ProcessDefinition getDefinition(DefinitionDAO definitionDAO, Long definitionId) throws DefinitionDoesNotExistException;

    /**
     * Returns {@link ProcessDefinition} with specified name and latest version.
     * 
     * @param definitionDAO
     *            {@link DefinitionDAO}, which will be used to load {@link ProcessDefinition} from database if it's not in cache.
     * @param definitionName
     *            Name of {@link ProcessDefinition}
     * @return {@link ProcessDefinition} with specified name and latest version.
     * @throws DefinitionDoesNotExistException
     *             {@link ProcessDefinition} with specified name doesn't exists.
     */
    public ProcessDefinition getLatestDefinition(DefinitionDAO definitionDAO, String definitionName)
            throws DefinitionDoesNotExistException;

    /**
     * Returns all {@link ProcessDefinition} with latest versions.
     * 
     * @param jbpmContext
     *            {@link JbpmContext}, which will be used to load {@link ProcessDefinition} from database if it's not in cache.
     * @return All {@link ProcessDefinition} with latest versions.
     */
    public List<ProcessDefinition> getLatestProcessDefinitions(DefinitionDAO definitionDAO);

}
