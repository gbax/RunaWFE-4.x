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
package ru.runa.wfe.os.func;

import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

import ru.runa.wfe.os.OrgFunctionException;
import ru.runa.wfe.user.Actor;
import ru.runa.wfe.user.Group;

import com.google.common.collect.Lists;

/**
 * 
 * Created on 19.05.2005
 */
public class DemoChiefFunction extends ActorOrgFunctionBase {

    @Override
    protected List<Long> getActorCodes(Long actorCode) {
        Actor actor = executorDAO.getActorByCode(actorCode);
        Enumeration<String> patternsEnumeration = DemoChiefResources.getPatterns();
        String chiefName = null;
        while (patternsEnumeration.hasMoreElements()) {
            String pattern = patternsEnumeration.nextElement();
            if (Pattern.matches(pattern, actor.getName())) {
                chiefName = DemoChiefResources.getChiefName(pattern);
                break;
            }
            if (executorDAO.isExecutorExist(pattern)) {
                Group group = executorDAO.getGroup(pattern);
                if (executorDAO.isExecutorInGroup(actor, group)) {
                    chiefName = DemoChiefResources.getChiefName(pattern);
                    break;
                }
            }
        }
        if (chiefName == null) {
            throw new OrgFunctionException("Wrong parameter: '" + actorCode + "' (Chief cannot be determined)");
        }
        return Lists.newArrayList(executorDAO.getActor(chiefName).getCode());
    }

}
