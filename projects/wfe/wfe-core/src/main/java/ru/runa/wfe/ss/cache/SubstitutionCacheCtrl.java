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
package ru.runa.wfe.ss.cache;

import java.util.Set;
import java.util.TreeMap;

import ru.runa.wfe.InternalApplicationException;
import ru.runa.wfe.commons.cache.BaseCacheCtrl;
import ru.runa.wfe.commons.cache.CachingLogic;
import ru.runa.wfe.commons.cache.ChangedObjectParameter;
import ru.runa.wfe.commons.cache.SubstitutionChangeListener;
import ru.runa.wfe.ss.Substitution;
import ru.runa.wfe.ss.SubstitutionCriteria;
import ru.runa.wfe.user.Actor;

import com.google.common.base.Objects;

public class SubstitutionCacheCtrl extends BaseCacheCtrl<SubstitutionCacheImpl> implements SubstitutionChangeListener, SubstitutionCache {

    private static final SubstitutionCacheCtrl instance = new SubstitutionCacheCtrl();

    private SubstitutionCacheCtrl() {
        CachingLogic.registerChangeListener(this);
    }

    public static SubstitutionCacheCtrl getInstance() {
        return instance;
    }

    @Override
    public SubstitutionCacheImpl buildCache() {
        return new SubstitutionCacheImpl();
    }

    @Override
    public TreeMap<Substitution, Set<Long>> getSubstitutors(Actor actor, boolean loadIfRequired) {
        SubstitutionCacheImpl cache = CachingLogic.getCacheImpl(this);
        return cache.getSubstitutors(actor, loadIfRequired);
    }

    @Override
    public Set<Long> getSubstituted(Actor actor) {
        SubstitutionCacheImpl cache = CachingLogic.getCacheImpl(this);
        return cache.getSubstituted(actor);
    }

    /**
     * Try to get substitutors for actor. If cache is not initialized or substitutors not found this
     * method will not query database - it returns null instead.
     * @param actor Actor, to get substitutors.
     * @return Substitutors for actor or null, if substitutors not initialized for actor.
     */
    public TreeMap<Substitution, Set<Long>> tryToGetSubstitutors(Actor actor) {
        SubstitutionCacheImpl cache = getCache();
        if (cache == null) {
            return null;
        }
        return cache.getSubstitutors(actor, false);
    }

    @Override
    public void doOnChange(ChangedObjectParameter changedObject) {
        SubstitutionCacheImpl cache = getCache();
        if (cache == null) {
            return;
        }
        if (changedObject.object instanceof Substitution || changedObject.object instanceof SubstitutionCriteria) {
            uninitialize(changedObject);
            // TODO: refresh cache for target actors
            // ExecutorCacheImpl executorCache =
            // ExecutorCacheCtrl.getInstance().getCache();
            // if (executorCache == null) {
            // uninitialize(object, change);
            // return;
            // }
            // Substitution substitution = (Substitution) object;
            // Actor actor = (Actor)
            // executorCache.getExecutor(substitution.getActorId());
            // cache.onActorStatusChange(actor, change);
            return;
        }
        if (changedObject.object instanceof SubstitutionCriteria) {
            uninitialize(changedObject);
            return;
        }
        if (changedObject.object instanceof Actor) {
            int activePropertyIndex = changedObject.getPropertyIndex("active");
            int namePropertyIndex = changedObject.getPropertyIndex("name");
            if (changedObject.previousState != null && !Objects.equal(changedObject.previousState[activePropertyIndex], changedObject.currentState[activePropertyIndex])) {
                cache.onActorStatusChange((Actor) changedObject.object, changedObject.changeType);
            }
            if (changedObject.previousState != null && !Objects.equal(changedObject.previousState[namePropertyIndex], changedObject.currentState[namePropertyIndex])) {
                // this event interested due to Actor.hashCode() implementation
                // cache.onActorNameChange((Actor) object, change);
            }
            return;
        }
        throw new InternalApplicationException("Unexpected object " + changedObject.object);
    }

}
