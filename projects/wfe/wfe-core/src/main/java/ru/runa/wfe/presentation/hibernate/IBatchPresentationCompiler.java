package ru.runa.wfe.presentation.hibernate;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;

import ru.runa.wfe.presentation.BatchPresentation;
import ru.runa.wfe.security.Permission;
import ru.runa.wfe.security.SecuredObjectType;
import ru.runa.wfe.task.Task;
import ru.runa.wfe.user.User;

public interface IBatchPresentationCompiler<T> {

    /**
     * Creates query to load data according to {@link BatchPresentation}.
     * 
     * @return {@link Query} to load data.
     */
    public List<T> getBatch();

    /**
     * Creates query to load data according to {@link BatchPresentation}.
     * 
     * @param enablePaging
     *            Flag, equals true, if paging must be used in query; false
     *            otherwise.
     * @return {@link Query} to load data.
     */
    public List<T> getBatch(boolean enablePaging);

    /**
     * Creates query to load data according to {@link BatchPresentation}.
     * Restrictions may not be set (if null).
     * 
     * @param owners
     *            Collection of owners id (Long for example).
     * @param ownersDBPath
     *            HQL path from root object to calculate object owner (actorId
     *            for {@link Task} for example).
     * @param enablePaging
     *            Flag, equals true, if paging must be used in query; false
     *            otherwise.
     * @return {@link Query} to load data.
     */
    public List<T> getBatch(Collection<?> owners, String ownersDBPath, boolean enablePaging);

    /**
     * Creates query to load data according to {@link BatchPresentation} with
     * owners and permission restriction.
     * 
     * @param enablePaging
     *            Flag, equals true, if paging must be used in query; false
     *            otherwise.
     * @param user
     *            User which must has permission on queried objects.
     * @param permission
     *            Permission, which at least one executors must has on queried
     *            objects.
     * @param securedObjectTypes
     *            Type of secured object for queried objects.
     * @return {@link Query} to load data.
     */
    public List<T> getBatch(boolean enablePaging, User user, Permission permission, SecuredObjectType[] securedObjectTypes);

    /**
     * Creates query to load data according to {@link BatchPresentation}.
     * Restrictions may not be set (if null).
     * 
     * @param concretteClass
     *            Subclass of root persistent class to be loaded by query.
     * @param enablePaging
     *            Flag, equals true, if paging must be used in query; false
     *            otherwise.
     * @return {@link Query} to load data.
     */
    public List<T> getBatch(Class<T> concretteClass, boolean enablePaging);

}
