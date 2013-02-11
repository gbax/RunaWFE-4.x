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
package ru.runa.wfe.extension.handler.user;

import org.springframework.beans.factory.annotation.Autowired;

import ru.runa.wfe.InternalApplicationException;
import ru.runa.wfe.extension.handler.CommonParamBasedHandler;
import ru.runa.wfe.extension.handler.HandlerData;
import ru.runa.wfe.user.Actor;
import ru.runa.wfe.user.dao.ExecutorDAO;

public class ActorNameActionHandler extends CommonParamBasedHandler {
    @Autowired
    private ExecutorDAO executorDAO;

    @Override
    protected void executeAction(HandlerData handlerData) throws Exception {
        Long actorCode = handlerData.getInputParam(Long.class, "actorCode", null);
        String actorLogin = handlerData.getInputParam(String.class, "actorLogin", null);
        String format = handlerData.getInputParam("format");
        Actor actor;
        if (actorCode != null) {
            actor = executorDAO.getActorByCode(actorCode);
        } else if (actorLogin != null) {
            actor = executorDAO.getActor(actorLogin);
        } else {
            throw new InternalApplicationException("Neither actor code and login are not defined in configuration.");
        }
        String result;
        if ("name".equals(format)) {
            result = actor.getName();
        } else if ("code".equals(format)) {
            result = String.valueOf(actor.getCode());
        } else if ("email".equals(format)) {
            result = actor.getEmail();
        } else if ("description".equals(format)) {
            result = actor.getDescription();
        } else {
            result = actor.getFullName();
        }
        handlerData.setOutputVariable("result", result);
    }

}
