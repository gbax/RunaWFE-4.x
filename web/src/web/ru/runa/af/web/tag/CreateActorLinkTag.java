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

import ru.runa.common.web.Commons;
import ru.runa.common.web.Commons.PortletUrl;
import ru.runa.common.web.Messages;

/**
 * Created on 03.09.2004
 * 
 * @jsp.tag name = "createActorLink" body-content = "empty"
 */
public class CreateActorLinkTag extends CreateExecutorLinkTag {

    private static final long serialVersionUID = -7064081489072327132L;

    protected String getLinkText() {
        return Messages.getMessage(Messages.BUTTON_CREATE_ACTOR, pageContext);
    }

    public static final String FORWARD = "create_actor";

    protected String getHref() {
        return Commons.getForwardUrl(FORWARD, pageContext, PortletUrl.Render);
    }
}
