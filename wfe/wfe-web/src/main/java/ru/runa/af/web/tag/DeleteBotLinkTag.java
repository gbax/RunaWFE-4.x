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
package ru.runa.af.web.tag;

import ru.runa.common.web.Messages;
import ru.runa.common.web.tag.LinkTag;
import ru.runa.service.af.AuthorizationService;
import ru.runa.wfe.bot.BotStation;
import ru.runa.wfe.bot.BotStationPermission;

/**
 * @author stan79
 * @jsp.tag name = "deleteBotLink" body-content = "empty"
 */
public class DeleteBotLinkTag extends LinkTag {

    private static final long serialVersionUID = -8445857392805848169L;

    @Override
    protected boolean isLinkEnabled() {
        try {
            AuthorizationService authorizationService = ru.runa.service.delegate.Delegates.getAuthorizationService();
            return authorizationService.isAllowed(getUser(), BotStationPermission.BOT_STATION_CONFIGURE, BotStation.INSTANCE);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected String getLinkText() {
        return Messages.getMessage(Messages.BUTTON_DELETE_BOT, pageContext);
    }
}
