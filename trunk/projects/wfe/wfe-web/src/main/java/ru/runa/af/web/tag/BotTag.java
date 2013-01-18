package ru.runa.af.web.tag;

import javax.servlet.jsp.JspException;

import org.apache.ecs.html.Input;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.TR;
import org.apache.ecs.html.Table;

import ru.runa.af.web.action.UpdateBotAction;
import ru.runa.af.web.form.BotForm;
import ru.runa.common.web.Messages;
import ru.runa.common.web.tag.TitledFormTag;
import ru.runa.service.af.AuthorizationService;
import ru.runa.service.delegate.Delegates;
import ru.runa.wfe.bot.Bot;
import ru.runa.wfe.bot.BotStation;
import ru.runa.wfe.bot.BotStationPermission;
import ru.runa.wfe.security.AuthenticationException;
import ru.runa.wfe.security.AuthorizationException;

/**
 * @author petrmikheev
 * @jsp.tag name = "botTag" body-content = "JSP"
 */
public class BotTag extends TitledFormTag {
    private static final long serialVersionUID = 1L;

    private Long botID;

    public void setBotID(Long botID) {
        this.botID = botID;
    }

    /**
     * @jsp.attribute required = "false" rtexprvalue = "true"
     */
    public Long getBotID() {
        return botID;
    }

    @Override
    protected void fillFormElement(TD tdFormElement) throws JspException {
        Bot bot = findBot();
        if (bot == null) {
            throw new JspException();
        }
        Table table = new Table();
        ActorSelectTD actorSelect = new ActorSelectTD(getSubject(), BotForm.USER_NAME, bot.getUsername());
        Input botPasswordInput = new Input(Input.PASSWORD, BotForm.PASSWORD, bot.getPassword());
        Input botTimeoutInput = new Input(Input.TEXT, BotForm.BOT_TIMEOUT, String.valueOf(bot.getStartTimeout()));

        Input hiddenBotStationID = new Input(Input.HIDDEN, BotForm.BOT_STATION_ID, bot.getBotStation().getId().intValue());
        Input hiddenBotID = new Input(Input.HIDDEN, BotForm.BOT_ID, String.valueOf(botID));
        tdFormElement.addElement(hiddenBotStationID);
        tdFormElement.addElement(hiddenBotID);

        TR tr = new TR();
        tr.addElement(new TD(Messages.getMessage(Messages.LABEL_BOT_NAME, pageContext)));
        tr.addElement(actorSelect);
        table.addElement(tr);
        tr = new TR();
        tr.addElement(new TD(Messages.getMessage(Messages.LABEL_BOT_PASSWORD, pageContext)));
        tr.addElement(new TD(botPasswordInput));
        table.addElement(tr);
        tr = new TR();
        tr.addElement(new TD(Messages.getMessage(Messages.LABEL_BOT_TIMEOUT, pageContext)));
        tr.addElement(new TD(botTimeoutInput));
        table.addElement(tr);

        tdFormElement.addElement(table);
    }

    private Bot findBot() {
        return Delegates.getBotService().getBot(getSubject(), botID);
    }

    @Override
    protected String getTitle() {
        return Messages.getMessage(Messages.TITLE_BOT_DETAILS, pageContext);
    }

    @Override
    protected String getFormButtonName() {
        return Messages.getMessage(Messages.BUTTON_APPLY, pageContext);
    }

    @Override
    public String getButtonAlignment() {
        return "right";
    }

    @Override
    public String getAction() {
        return UpdateBotAction.UPDATE_BOT_ACTION_PATH;
    }

    @Override
    public boolean isFormButtonEnabled() throws JspException {
        boolean result = false;
        try {
            AuthorizationService authorizationService = Delegates.getAuthorizationService();
            result = authorizationService.isAllowed(getSubject(), BotStationPermission.BOT_STATION_CONFIGURE, BotStation.INSTANCE);
        } catch (AuthorizationException e) {
            throw new JspException(e);
        } catch (AuthenticationException e) {
            throw new JspException(e);
        }
        return result;
    }
}
