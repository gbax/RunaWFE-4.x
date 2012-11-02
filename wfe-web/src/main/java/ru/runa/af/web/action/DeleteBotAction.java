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
package ru.runa.af.web.action;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.runa.af.web.SubjectHttpSessionHelper;
import ru.runa.common.web.form.IdsForm;
import ru.runa.service.delegate.DelegateFactory;
import ru.runa.service.wf.BotsService;
import ru.runa.wfe.bot.Bot;

/**
 * @author petrmikheev
 * 
 * @struts:action path="/delete_bot" name="idsForm" validate="true" input = "/WEB-INF/wf/bot_station.jsp"
 */
public class DeleteBotAction extends Action {
    public static final String DELETE_BOT_ACTION_PATH = "/delete_bot";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        IdsForm idsForm = (IdsForm) form;
        Long[] botToDeleteIds = idsForm.getIds();
        if (botToDeleteIds.length == 0) {
            return new ActionForward("/bot_station.do?botStationID=" + idsForm.getId());
        }
        Subject subject = SubjectHttpSessionHelper.getActorSubject(request.getSession());
        BotsService botsService = DelegateFactory.getBotsService();
        Bot botForDelete;
        for (Long botId : botToDeleteIds) {
            botForDelete = new Bot();
            botForDelete.setId(botId);
            botForDelete = botsService.getBot(subject, botForDelete);
            botsService.remove(subject, botForDelete);
        }
        return new ActionForward("/bot_station.do?botStationID=" + idsForm.getId());
    }
}
