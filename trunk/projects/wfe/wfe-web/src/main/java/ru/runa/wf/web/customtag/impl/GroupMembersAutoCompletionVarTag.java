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
package ru.runa.wf.web.customtag.impl;

import java.util.List;

import javax.security.auth.Subject;

import ru.runa.service.delegate.Delegates;
import ru.runa.wfe.presentation.BatchPresentation;
import ru.runa.wfe.presentation.BatchPresentationFactory;
import ru.runa.wfe.user.Actor;
import ru.runa.wfe.user.Executor;
import ru.runa.wfe.user.Group;

import com.google.common.collect.Lists;

public class GroupMembersAutoCompletionVarTag extends AbstractAutoCompletionComboBoxVarTag {

    @Override
    public List<Actor> getActors(Subject subject, String varName, Object varValue) {
        BatchPresentation batchPresentation = BatchPresentationFactory.ACTORS.createNonPaged();
        int[] sortIds = { 1 };
        boolean[] sortOrder = { true };
        batchPresentation.setFieldsToSort(sortIds, sortOrder);

        Group group = Delegates.getExecutorService().getExecutor(subject, varName);
        List<Executor> executors = Delegates.getExecutorService().getGroupChildren(subject, group, batchPresentation, false);
        List<Actor> actors = Lists.newArrayList();
        for (Executor executor : executors) {
            actors.add((Actor) executor);
        }
        return actors;
    }
}
