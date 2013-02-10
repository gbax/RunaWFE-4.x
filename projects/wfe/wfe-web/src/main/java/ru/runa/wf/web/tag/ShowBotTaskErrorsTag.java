package ru.runa.wf.web.tag;

import java.util.List;
import java.util.Map;

import org.apache.ecs.ConcreteElement;
import org.apache.ecs.Element;
import org.apache.ecs.StringElement;
import org.apache.ecs.html.A;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.TH;
import org.apache.ecs.html.TR;

import ru.runa.common.web.Commons;
import ru.runa.common.web.Messages;
import ru.runa.common.web.Resources;
import ru.runa.common.web.html.HeaderBuilder;
import ru.runa.common.web.html.RowBuilder;
import ru.runa.common.web.html.TRRowBuilder;
import ru.runa.common.web.html.TableBuilder;
import ru.runa.common.web.tag.VisibleTag;
import ru.runa.service.delegate.Delegates;
import ru.runa.wfe.bot.Bot;
import ru.runa.wfe.bot.BotStation;
import ru.runa.wfe.commons.web.PortletUrlType;
import ru.runa.wfe.execution.logic.ProcessExecutionErrors;
import ru.runa.wfe.execution.logic.ProcessExecutionErrors.BotTaskIdentifier;
import ru.runa.wfe.security.Permission;

import com.google.common.collect.Lists;

public class ShowBotTaskErrorsTag extends VisibleTag {
    private static final long serialVersionUID = 1L;

    @Override
    protected ConcreteElement getStartElement() {
        return new StringElement();
    }

    @Override
    protected ConcreteElement getEndElement() {
        List<TR> rows = Lists.newArrayList();
        for (Map.Entry<BotTaskIdentifier, Throwable> entry : ProcessExecutionErrors.getBotTaskConfigurationErrors().entrySet()) {
            TR tr = new TR();
            Element botNameElement;
            Bot bot = entry.getKey().getBot();
            if (Delegates.getAuthorizationService().isAllowed(getUser(), Permission.READ, BotStation.INSTANCE)) {
                botNameElement = new A(Commons.getUrl("/bot.do?botId=" + bot.getId(), pageContext, PortletUrlType.Render), bot.getUsername());
            } else {
                botNameElement = new StringElement(bot.getUsername());
            }
            tr.addElement(new TD(botNameElement).setClass(Resources.CLASS_LIST_TABLE_TD));
            tr.addElement(new TD(entry.getKey().getBotTaskName()).setClass(Resources.CLASS_LIST_TABLE_TD));
            String url = "javascript:showBotTaskConfigurationError(" + bot.getId() + ", '" + entry.getKey().getBotTaskName() + "')";
            tr.addElement(new TD(new A(url, entry.getValue().getLocalizedMessage())).setClass(Resources.CLASS_LIST_TABLE_TD));
            rows.add(tr);
        }
        ErrorsHeaderBuilder tasksHistoryHeaderBuilder = new ErrorsHeaderBuilder();
        RowBuilder rowBuilder = new TRRowBuilder(rows);
        TableBuilder tableBuilder = new TableBuilder();
        return tableBuilder.build(tasksHistoryHeaderBuilder, rowBuilder);
    }

    private class ErrorsHeaderBuilder implements HeaderBuilder {

        @Override
        public TR build() {
            TR tr = new TR();
            tr.addElement(new TH(Messages.getMessage("errors.bot.name", pageContext)).setClass(Resources.CLASS_LIST_TABLE_TH));
            tr.addElement(new TH(Messages.getMessage("errors.bottask.name", pageContext)).setClass(Resources.CLASS_LIST_TABLE_TH));
            tr.addElement(new TH(Messages.getMessage("errors.error", pageContext)).setClass(Resources.CLASS_LIST_TABLE_TH));
            return tr;
        }
    }

}
