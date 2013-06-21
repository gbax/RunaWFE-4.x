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
package ru.runa.wf.web.html;

import org.apache.ecs.html.A;
import org.apache.ecs.html.TD;

import ru.runa.af.web.ExecutorNameConverter;
import ru.runa.common.WebResources;
import ru.runa.common.web.Commons;
import ru.runa.common.web.form.IdForm;
import ru.runa.common.web.html.TDBuilder;
import ru.runa.wfe.commons.web.PortletUrlType;
import ru.runa.wfe.task.dto.WfTask;
import ru.runa.wfe.user.Executor;

/**
 * Created on 24.07.2007
 * 
 * @author Konstantinov A.
 */
public class TaskOwnerTDBuilder implements TDBuilder {

    public TaskOwnerTDBuilder() {
    }

    private Executor getOwner(Object object) {
        WfTask task = (WfTask) object;
        if (task.isAcquiredBySubstitution()) {
            return task.getTargetActor();
        } else {
            return task.getOwner();
        }
    }

    @Override
    public TD build(Object object, Env env) {
        Executor owner = getOwner(object);
        String actorName = ExecutorNameConverter.getName(owner, env.getPageContext());
        String url = Commons.getActionUrl(WebResources.ACTION_MAPPING_UPDATE_EXECUTOR, IdForm.ID_INPUT_NAME, owner.getId(), env.getPageContext(),
                PortletUrlType.Render);
        A link = new A(url, actorName);
        TD td = new TD(link);
        td.setClass(ru.runa.common.web.Resources.CLASS_LIST_TABLE_TD);
        return td;
    }

    @Override
    public String getValue(Object object, Env env) {
        Executor owner = getOwner(object);
        return ExecutorNameConverter.getName(owner, env.getPageContext());
    }

    @Override
    public String[] getSeparatedValues(Object object, Env env) {
        return new String[] { getValue(object, env) };
    }

    @Override
    public int getSeparatedValuesCount(Object object, Env env) {
        return 1;
    }
}
