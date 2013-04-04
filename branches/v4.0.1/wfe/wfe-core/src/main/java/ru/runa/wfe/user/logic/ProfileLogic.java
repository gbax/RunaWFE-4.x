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
package ru.runa.wfe.user.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ru.runa.wfe.InternalApplicationException;
import ru.runa.wfe.commons.logic.CommonLogic;
import ru.runa.wfe.presentation.BatchPresentation;
import ru.runa.wfe.presentation.BatchPresentationConsts;
import ru.runa.wfe.presentation.dao.BatchPresentationDAO;
import ru.runa.wfe.security.Permission;
import ru.runa.wfe.user.Actor;
import ru.runa.wfe.user.ActorPermission;
import ru.runa.wfe.user.ExecutorDoesNotExistException;
import ru.runa.wfe.user.ExecutorPermission;
import ru.runa.wfe.user.Profile;
import ru.runa.wfe.user.User;
import ru.runa.wfe.user.dao.ProfileDAO;

import com.google.common.collect.Lists;

/**
 * Actor's profile management.
 * 
 * @author Dofs
 * @since 1.0
 */
public class ProfileLogic extends CommonLogic {
    @Autowired
    private ProfileDAO profileDAO;
    @Autowired
    private BatchPresentationDAO batchPresentationDAO;

    public void updateProfiles(User user, List<Profile> profiles) {
        for (Profile profile : profiles) {
            checkPermissionAllowed(user, profile.getActor(), ExecutorPermission.UPDATE);
            profileDAO.update(profile);
        }
    }

    private Profile getProfileNotNull(Actor actor) {
        Profile profile = profileDAO.get(actor);
        if (profile == null) {
            profile = new Profile();
            profile.setActor(actor);
            profileDAO.create(profile);
        }
        return profile;
    }

    public List<Profile> getProfiles(User user, List<Long> actorIds) throws ExecutorDoesNotExistException {
        List<Profile> result = Lists.newArrayListWithCapacity(actorIds.size());
        for (Long actorId : actorIds) {
            Actor actor = executorDAO.getActor(actorId);
            checkPermissionAllowed(user, actor, Permission.READ);
            result.add(getProfileNotNull(actor));
        }
        return result;
    }

    public Profile getProfile(User user, Long actorId) {
        Actor actor = executorDAO.getActor(actorId);
        checkPermissionAllowed(user, actor, Permission.READ);
        return getProfileNotNull(actor);
    }

    public void deleteActorProfile(User user, Long actorId) {
        Actor actor = executorDAO.getActor(actorId);
        checkPermissionAllowed(user, actor, ActorPermission.UPDATE);
        profileDAO.delete(actor);
    }

    public Profile changeActiveBatchPresentation(User user, String batchPresentationId, String newActiveBatchName) {
        Profile profile = profileDAO.get(user.getActor());
        profile.setActiveBatchPresentation(batchPresentationId, newActiveBatchName);
        // batchPresentationDAO.flushPendingChanges();
        return profileDAO.get(user.getActor());
    }

    public Profile deleteBatchPresentation(User user, BatchPresentation batchPresentation) {
        Profile profile = profileDAO.get(user.getActor());
        profile.deleteBatchPresentation(batchPresentation);
        // batchPresentationDAO.delete(batchPresentation);
        // batchPresentationDAO.flushPendingChanges();
        return profile;
    }

    public Profile createBatchPresentation(User user, BatchPresentation batchPresentation) {
        Profile profile = profileDAO.get(user.getActor());
        batchPresentation.serializeFields();
        profile.addBatchPresentation(batchPresentation);
        profile.setActiveBatchPresentation(batchPresentation.getCategory(), batchPresentation.getName());
        return profile;
    }

    public Profile saveBatchPresentation(User user, BatchPresentation batchPresentation) {
        if (BatchPresentationConsts.DEFAULT_NAME.equals(batchPresentation.getName())) {
            throw new InternalApplicationException("default batch presentation cannot be changed");
        }
        batchPresentation.serializeFields();
        batchPresentation = batchPresentationDAO.update(batchPresentation);
        batchPresentationDAO.flushPendingChanges();
        return profileDAO.get(user.getActor());
    }
}
