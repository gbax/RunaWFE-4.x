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
package ru.runa.af.web.html;

import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.PageContext;

import org.apache.ecs.html.A;
import org.apache.ecs.html.Input;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.TH;
import org.apache.ecs.html.TR;
import org.apache.ecs.html.Table;

import ru.runa.af.web.form.BotForm;
import ru.runa.af.web.tag.ActorSelect;
import ru.runa.common.web.Commons;
import ru.runa.common.web.HTMLUtils;
import ru.runa.common.web.Messages;
import ru.runa.common.web.Resources;
import ru.runa.common.web.form.IdsForm;
import ru.runa.wfe.bot.Bot;
import ru.runa.wfe.commons.web.PortletUrlType;
import ru.runa.wfe.user.User;

/**
 * @author petrmikheev
 */
public class BotTableBuilder {

    private final PageContext pageContext;

    public BotTableBuilder(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    public Table buildBotTable(List<Bot> bots) {
        Table table = new Table();
        table.setClass(Resources.CLASS_LIST_TABLE);
        table.setWidth("100%");
        table.addElement(createTableHeaderTR());
        for (Iterator<Bot> iterator = bots.iterator(); iterator.hasNext();) {
            table.addElement(createTR(iterator.next()));
        }
        return table;
    }

    private TR createTR(Bot bot) {
        TR tr = new TR();
        tr.setClass(Resources.CLASS_LIST_TABLE_TH);
        Input input = new Input(Input.CHECKBOX, IdsForm.IDS_INPUT_NAME, Long.toString(bot.getId()));
        String path = Commons.getActionUrl("bot.do", BotForm.BOT_ID, bot.getId(), pageContext, PortletUrlType.Render);
        tr.addElement(new TD(input).setClass(Resources.CLASS_LIST_TABLE_TD));
        tr.addElement(new TD(new A(path, bot.getUsername())).setWidth("70%").setClass(Resources.CLASS_LIST_TABLE_TD));
        // tr.addElement(new
        // TD(String.valueOf(bot.getStartTimeout())).setClass(Resources.CLASS_LIST_TABLE_TD));
        return tr;
    }

    private TR createTableHeaderTR() {
        TR tr = new TR();
        tr.setClass(Resources.CLASS_LIST_TABLE_TH);
        tr.addElement(new TH("").setClass(Resources.CLASS_LIST_TABLE_TD));
        tr.addElement(new TH(Messages.getMessage(Messages.LABEL_BOT_NAME, pageContext)).setClass(Resources.CLASS_LIST_TABLE_TD));
        // tr.addElement(new TH(Messages.getMessage(Messages.LABEL_BOT_TIMEOUT,
        // pageContext)).setClass(Resources.CLASS_LIST_TABLE_TD));
        return tr;
    }

    public static Table buildBotDetailsTable(User user, PageContext pageContext, Bot bot) {
        Table table = new Table();
        table.setClass(Resources.CLASS_LIST_TABLE);
        // Input botTimeoutInput = new Input(Input.TEXT, BotForm.BOT_TIMEOUT,
        // bot != null ? String.valueOf(bot.getStartTimeout()) : "0");
        // botTimeoutInput.setStyle("width: 300px");

        ActorSelect actorSelect = new ActorSelect(user, BotForm.USER_NAME, bot != null ? bot.getUsername() : "", true);
        table.addElement(HTMLUtils.createSelectRow(Messages.getMessage(Messages.LABEL_BOT_NAME, pageContext), actorSelect, true));
        table.addElement(HTMLUtils.createInputRow(Messages.getMessage(Messages.LABEL_BOT_PASSWORD, pageContext), BotForm.PASSWORD,
                bot != null ? bot.getPassword() : "", true, false, Input.PASSWORD));
        // tr = new TR();
        // tr.addElement(new TD(Messages.getMessage(Messages.LABEL_BOT_TIMEOUT,
        // pageContext)).setClass(Resources.CLASS_LIST_TABLE_TD));
        // tr.addElement(new
        // TD(botTimeoutInput).setClass(Resources.CLASS_LIST_TABLE_TD));
        // table.addElement(tr.setClass(Resources.CLASS_LIST_TABLE_TH));
        return table;
    }
}
