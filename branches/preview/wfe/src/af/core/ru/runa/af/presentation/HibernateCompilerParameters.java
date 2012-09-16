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
package ru.runa.af.presentation;

import java.util.Collection;
import java.util.List;

import ru.runa.InternalApplicationException;
import ru.runa.af.Permission;
import ru.runa.af.SecuredObject;
import ru.runa.bpm.taskmgmt.exe.TaskInstance;

/**
 * Parameter object.
 * Parameters, used to build HQL/SQL query. 
 */
public class HibernateCompilerParameters {

    /**
     * Collection of owners id (Long for example). If this collection and ownersDBPath is set, when 
     * only objects, which has specified owners will be queried.  
     */
    private final Collection owners;

    /**
     * HQL path from root object to calculate object owner (actorId for {@link TaskInstance} for example).
     */
    private final String ownersDBPath;

    /**
     * Flag, equals true, if paging must be used in query; false otherwise.
     */
    private final boolean enablePaging;

    /**
     * Flag, equals true, if only objects count must be queried.  
     */
    private final boolean isCountQuery;

    /**
     * Executors, which must has permission on queried objects.
     * Queries only objects with condition: at least one executor from executorIds must have 'permission' with 'securedObjectTypes'
     */
    private final List<Long> executorIds;

    /**
     * Permission, which at least one executors must has on queried objects.
     * Queries only objects with condition: at least one executor from executorIds must have 'permission' with 'securedObjectTypes'
     */
    private final Permission permission;

    /**
     * Type of secured object for queried objects. 
     * Queries only objects with condition: at least one executor from executorIds must have 'permission' with 'securedObjectTypes'
     */
    private final int[] securedObjectTypes;

    /**
     * Subclass of root persisted object, to be queried.
     * May be null, if root persistent object and all it's subclasses must be queried. 
     */
    private final Class requestedClass;

    /**
     * Restrictions, applied to object identity. Must be HQL query string or null.
     * If set, added to query in form 'object id in (idRestriction)'.   
     */
    private final String[] idRestriction;

    /**
     * Flag, equals true, if only identity of objects must be loaded; false to load entire object. 
     * <br/><b>Loaded object must have id property.</b>
     */
    private final boolean onlyIdentityLoad;

    /**
     * Creates parameter object for building HQL query using other {@linkplain HibernateCompilerParameters} as source.
     * Copy all parameters from source {@linkplain HibernateCompilerParameters}, except isCountQuery flag.
     * @param src {@linkplain HibernateCompilerParameters} to copy parameters from.
     * @param isCountQuery Flag, equals true, if only objects count must be queried.  
     */
    public HibernateCompilerParameters(HibernateCompilerParameters src, boolean isCountQuery) {
        this.owners = src.owners;
        this.ownersDBPath = src.ownersDBPath;
        this.enablePaging = isCountQuery ? false : src.enablePaging;
        this.isCountQuery = isCountQuery;
        this.executorIds = src.executorIds;
        this.permission = src.permission;
        this.securedObjectTypes = src.securedObjectTypes;
        this.requestedClass = src.requestedClass;
        this.idRestriction = src.idRestriction;
        onlyIdentityLoad = false;
    }

    /**
     * Creates parameter object for building HQL query with permission check.
     * @param owners Collection of owners id (Long for example).
     * @param ownersDBPath HQL path from root object to calculate object owner (actorId for {@link TaskInstance} for example).
     * @param enablePaging Flag, equals true, if paging must be used in request; false otherwise.
     * @param isCountQuery Flag, equals true, if only objects count must be queried.  
     * @param executorIds Executors, which must has permission on queried objects.
     * @param permission Permission, which at least one executors must has on queried objects.
     * @param securedObjectTypes Type of secured object for queried objects.
     * @param requestedClass Subclass of root persisted object, to be queried.
     * @param idRestriction Restrictions, applied to object identity. Must be HQL query string or null. 
     */
    public HibernateCompilerParameters(Collection owners, String ownersDBPath, boolean enablePaging, boolean isCountQuery, List<Long> executorIds,
            Permission permission, int[] securedObjectTypes, Class requestedClass, String[] idRestriction) {
        this.owners = owners;
        this.ownersDBPath = ownersDBPath;
        if (ownersDBPath != null && owners == null) {
            throw new InternalApplicationException("No owners supplied to query with owner restrictions.");
        }
        this.enablePaging = enablePaging;
        this.isCountQuery = isCountQuery;
        this.executorIds = executorIds;
        this.permission = permission;
        this.securedObjectTypes = securedObjectTypes;
        if (executorIds == null || permission == null || securedObjectTypes == null) {
            throw new InternalApplicationException("Can't build query with permission check. No secured parametes specified.");
        }
        this.requestedClass = requestedClass;
        this.idRestriction = idRestriction;
        onlyIdentityLoad = false;
    }

    /**
     * Creates parameter object for building HQL query without permission check.
     * @param owners Collection of owners id (Long for example).
     * @param ownersDBPath HQL path from root object to calculate object owner (actorId for {@link TaskInstance} for example).
     * @param enablePaging Flag, equals true, if paging must be used in request; false otherwise.
     * @param isCountQuery Flag, equals true, if only objects count must be queried.  
     * @param requestedClass Subclass of root persisted object, to be queried.
     */
    public HibernateCompilerParameters(Collection owners, String ownersDBPath, boolean enablePaging, boolean isCountQuery, Class requestedClass) {
        this.owners = owners;
        this.ownersDBPath = ownersDBPath;
        if (ownersDBPath != null && owners == null) {
            throw new InternalApplicationException("No owners supplied to query with owner restrictions.");
        }
        this.enablePaging = enablePaging;
        this.isCountQuery = isCountQuery;
        executorIds = null;
        permission = null;
        securedObjectTypes = null;
        this.requestedClass = requestedClass;
        idRestriction = null;
        onlyIdentityLoad = false;
    }

    /**
     * Creates parameter object for building HQL query without permission check.
     * @param owners Collection of owners id (Long for example).
     * @param ownersDBPath HQL path from root object to calculate object owner (actorId for {@link TaskInstance} for example).
     * @param enablePaging Flag, equals true, if paging must be used in request; false otherwise.
     * @param isCountQuery Flag, equals true, if only objects count must be queried.  
     * @param requestedClass Subclass of root persisted object, to be queried.
     */
    public HibernateCompilerParameters(Collection owners, String ownersDBPath, boolean enablePaging, boolean isCountQuery, Class requestedClass,
        boolean onlyIdentityLoad) {
        this.owners = owners;
        this.ownersDBPath = ownersDBPath;
        if (ownersDBPath != null && owners == null) {
            throw new InternalApplicationException("No owners supplied to query with owner restrictions.");
        }
        this.enablePaging = enablePaging;
        this.isCountQuery = isCountQuery;
        executorIds = null;
        permission = null;
        securedObjectTypes = null;
        this.requestedClass = requestedClass;
        idRestriction = null;
        this.onlyIdentityLoad = onlyIdentityLoad;
    }

    /**
     * Check, if HQL/SQL query must has permission check. 
     * @return true, is query must check executors permission.
     */
    public boolean isSequresQuery() {
        return executorIds != null;
    }

    /**
     * Check, if HQL/SQL query must return only objects count.
     * @return true, if HQL/SQL query must return only objects count; false, if list of objects must be returned.
     */
    public boolean isCountQuery() {
        return isCountQuery;
    }

    /**
     * Check, if paging must be used in request.
     * @return true, if paging must be used in request; false otherwise.
     */
    public boolean isPagingEnabled() {
        return enablePaging;
    }

    /**
     * Check, if query must have owners restrictions.
     * @return true, if query must have owners restrictions and false otherwise.
     */
    public boolean hasOwners() {
        return ownersDBPath != null;
    }

    /**
     * Subclass of root persisted object, to be queried.
     * May be null, if root persistent object and all it's subclasses must be queried. 
     * @return Subclass of root persisted object, to be queried.
     */
    public Class getQueriedClass() {
        return requestedClass;
    }

    /**
     * Collection of owners id (Long for example).
     * @return Collection of owners id. 
     */
    public Collection getOwners() {
        return owners;
    }

    /**
     * HQL path from root object to calculate object owner (actorId for {@link TaskInstance} for example).
     * @return HQL path from root object to calculate object owner.
     */
    public String getOwnerDBPath() {
        return ownersDBPath;
    }

    /**
     * Executors, which must has permission on queried objects.
     * @return Executors id's, to check permission.
     */
    public List<Long> getExecutorIds() {
        return executorIds;
    }

    /**
     * Permission, which at least one executors must has on queried objects.
     * @return Permission, which executor must have on object.
     */
    public Permission getPermission() {
        return permission;
    }

    /**
     * Type of secured object for queried objects. 
     * @return {@link SecuredObject} types.
     */
    public int[] getSecuredObjectTypes() {
        return securedObjectTypes;
    }

    /**
     * Restrictions, applied to object identity. Must be HQL query string or null.
     * If set, added to query in form 'object id in (idRestriction)'.   
     * @return Return array of restrictions, applied to object identity.
     */
    public String[] getIdRestriction() {
        return idRestriction;
    }

    /**
     * Flag, equals true, if only identity of objects must be loaded; false to load entire object.
     * <br/><b>Loaded object must have id property.</b>
     * @return true, if only identity must be loaded.
     */
    public boolean isOnlyIdentityLoad() {
        return onlyIdentityLoad;
    }
}
