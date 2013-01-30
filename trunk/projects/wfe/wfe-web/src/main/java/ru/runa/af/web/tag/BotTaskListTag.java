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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.ecs.html.A;
import org.apache.ecs.html.Form;
import org.apache.ecs.html.Input;
import org.apache.ecs.html.Option;
import org.apache.ecs.html.Select;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.TH;
import org.apache.ecs.html.TR;

import ru.runa.af.web.Native2AsciiHelper;
import ru.runa.af.web.action.BotTaskConfigurationDownloadAction;
import ru.runa.af.web.action.UpdateBotTaskConfigurationAction;
import ru.runa.af.web.action.UpdateBotTasksAction;
import ru.runa.af.web.form.BotTasksForm;
import ru.runa.af.web.system.TaskHandlerClassesInformation;
import ru.runa.common.web.Commons;
import ru.runa.common.web.Messages;
import ru.runa.common.web.Resources;
import ru.runa.common.web.form.IdsForm;
import ru.runa.common.web.html.HeaderBuilder;
import ru.runa.common.web.html.RowBuilder;
import ru.runa.common.web.html.TableBuilder;
import ru.runa.common.web.tag.TitledFormTag;
import ru.runa.service.af.AuthorizationService;
import ru.runa.service.delegate.Delegates;
import ru.runa.service.wf.BotService;
import ru.runa.wfe.bot.BotStation;
import ru.runa.wfe.bot.BotStationPermission;
import ru.runa.wfe.bot.BotTask;
import ru.runa.wfe.commons.web.PortletUrlType;
import ru.runa.wfe.commons.xml.XmlUtils;

/**
 * @author petrmikheev
 * @jsp.tag name = "botTaskListTag" body-content = "JSP"
 */
public class BotTaskListTag extends TitledFormTag {
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
    public boolean isFormButtonEnabled() throws JspException {
        AuthorizationService authorizationService = Delegates.getAuthorizationService();
        return authorizationService.isAllowed(getUser(), BotStationPermission.BOT_STATION_CONFIGURE, BotStation.INSTANCE);
    }

    @Override
    protected void fillFormElement(TD tdFormElement) throws JspException {
        tdFormElement.addElement(new Input(Input.hidden, IdsForm.ID_INPUT_NAME, Long.toString(botID)));
        BotService botService = Delegates.getBotService();
        getForm().setEncType(Form.ENC_UPLOAD);
        AuthorizationService authorizationService = Delegates.getAuthorizationService();
        boolean disabled = !authorizationService.isAllowed(getUser(), BotStationPermission.BOT_STATION_CONFIGURE, BotStation.INSTANCE);
        List<BotTask> tasks = botService.getBotTasks(getUser(), botID);
        int nameSize = 1;
        for (BotTask botTask : tasks) {
            if (botTask.getName().length() > nameSize) {
                nameSize = botTask.getName().length();
            }
        }
        RowBuilder rowBuilder = new BotTaskRowBuilder(tasks, disabled, nameSize + 10, pageContext);
        HeaderBuilder headerBuilder = new BotTaskHeaderBuilder(pageContext);
        TableBuilder tableBuilder = new TableBuilder();
        tdFormElement.addElement(tableBuilder.build(headerBuilder, rowBuilder));
    }

    @Override
    protected String getTitle() {
        return Messages.getMessage(Messages.TITLE_BOT_TASK_LIST, pageContext);
    }

    @Override
    protected String getFormButtonName() {
        return Messages.getMessage(Messages.BUTTON_APPLY, pageContext);
    }

    @Override
    public String getAction() {
        return UpdateBotTasksAction.UPDATE_BOT_TASKS_ACTION_PATH;
    }

    class BotTaskHeaderBuilder implements HeaderBuilder {

        private final PageContext context;

        public BotTaskHeaderBuilder(PageContext pageContext) {
            context = pageContext;
        }

        @Override
        public TR build() {
            TR tr = new TR();
            tr.addElement(new TH().setClass(Resources.CLASS_LIST_TABLE_TH));
            tr.addElement(new TH(Messages.getMessage(Messages.LABEL_BOT_TASK_NAME, context)).setClass(Resources.CLASS_LIST_TABLE_TH));
            tr.addElement(new TH(Messages.getMessage(Messages.LABEL_BOT_TASK_HANDLER, context)).setClass(Resources.CLASS_LIST_TABLE_TH));
            tr.addElement(new TH(Messages.getMessage(Messages.LABEL_BOT_TASK_CONFIG, context)).setClass(Resources.CLASS_LIST_TABLE_TH));
            return tr;
        }
    }

    class BotTaskRowBuilder implements RowBuilder {

        private final Iterator<BotTask> iterator;

        private final boolean disabled;

        private final int nameSize;

        private final PageContext pageContext;

        public BotTaskRowBuilder(List<BotTask> tasks, boolean disabled, int nameSize, PageContext pageContext) {
            this.disabled = disabled;
            iterator = tasks.iterator();
            this.nameSize = nameSize;
            this.pageContext = pageContext;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public TR buildNext() {
            TR tr = new TR();
            BotTask task = iterator.next();
            tr.addElement(buildSelectTD(task));
            tr.addElement(buildNameTD(task));
            tr.addElement(buildHandlerTD(task));
            tr.addElement(buildConfigurationUploadTD(task));
            return tr;
        }

        private TD buildNameTD(BotTask task) {
            TD resTD = new TD();
            resTD.setClass(Resources.CLASS_LIST_TABLE_TD);
            Input input = new Input(Input.TEXT, BotTasksForm.BOT_TASK_INPUT_NAME_PREFIX + task.getId() + BotTasksForm.NAME_INPUT_NAME, task.getName());
            input.setDisabled(disabled);
            input.setSize(nameSize);
            resTD.addElement(input);
            return resTD;
        }

        private TD buildHandlerTD(BotTask task) {
            TD resTD = new TD();
            resTD.setClass(Resources.CLASS_LIST_TABLE_TD);
            Select select = new Select();
            select.setName(BotTasksForm.BOT_TASK_INPUT_NAME_PREFIX + task.getId() + BotTasksForm.HANDLER_INPUT_NAME);
            select.setDisabled(disabled);
            String taskHandlerClazz = task.getTaskHandlerClassName();
            boolean isHandlerPresent = false;
            for (String className : TaskHandlerClassesInformation.getClassNames()) {
                Option option = new Option();
                if (className.equalsIgnoreCase(taskHandlerClazz)) {
                    option.setSelected(true);
                    isHandlerPresent = true;
                }
                option.setValue(className);
                option.addElement(className);
                select.addElement(option);
            }
            if (!isHandlerPresent) {
                Option option = new Option();
                option.setSelected(true);
                String handlerName = Messages.getMessage(Messages.LABEL_UNKNOWN_BOT_HANDLER, pageContext) + ": " + taskHandlerClazz;
                option.setValue(handlerName);
                option.addElement(handlerName);
                select.addElement(option);
            }
            resTD.addElement(select);
            return resTD;
        }

        private TD buildSelectTD(BotTask task) {
            TD checkboxTD = new TD();
            Input checkBoxInput = new Input(Input.CHECKBOX, IdsForm.IDS_INPUT_NAME, String.valueOf(task.getId()));
            checkBoxInput.setChecked(true);
            checkBoxInput.setDisabled(disabled);
            checkboxTD.setClass(Resources.CLASS_LIST_TABLE_TD);
            checkboxTD.addElement(checkBoxInput);
            return checkboxTD;
        }
    }

    private TD buildConfigurationUploadTD(BotTask task) {
        TD fileUploadTD = new TD();
        fileUploadTD.setClass(Resources.CLASS_LIST_TABLE_TD);
        Input fileUploadInput = new Input(Input.FILE, BotTasksForm.BOT_TASK_INPUT_NAME_PREFIX + task.getId() + BotTasksForm.CONFIG_FILE_INPUT_NAME);
        fileUploadTD.addElement(fileUploadInput);
        if (task.getConfiguration() != null && task.getConfiguration().length > 0) {
            A link = new A(Commons.getActionUrl(BotTaskConfigurationDownloadAction.DOWNLOAD_BOT_TASK_CONFIGURATION_ACTION_PATH, "id", task.getId(),
                    pageContext, PortletUrlType.Action), Messages.getMessage(Messages.LABEL_BOT_TASK_CONFIG_DOWNLOAD, pageContext));
            link.setClass(Resources.CLASS_LINK);
            fileUploadTD.addElement(link);

            StringBuffer str = new StringBuffer();
            str.append("&nbsp;");
            str.append(Messages.getMessage(Messages.LABEL_BOT_TASK_CONFIG_EDIT, pageContext));
            boolean configurationIsXml = true;
            try {
                XmlUtils.parseWithoutValidation(task.getConfiguration());
            } catch (Exception e) {
                configurationIsXml = false;
            }
            if (!configurationIsXml && !Native2AsciiHelper.isNeedConvert(new String(task.getConfiguration()))) {
                str.append("*");
            }
            StringBuffer jsLink = new StringBuffer("javascript:");
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            parameterMap.put("id", task.getId());
            parameterMap.put("edit", "true");
            jsLink.append("openDocumentEditor('");
            jsLink.append(Commons.getActionUrl(BotTaskConfigurationDownloadAction.DOWNLOAD_BOT_TASK_CONFIGURATION_ACTION_PATH, parameterMap,
                    pageContext, PortletUrlType.Action));
            jsLink.append("','");
            jsLink.append(Commons.getActionUrl(UpdateBotTaskConfigurationAction.UPDATE_TASK_HANDLER_CONF_ACTION_PATH, "id", task.getId(),
                    pageContext, PortletUrlType.Action));
            jsLink.append("','");
            jsLink.append(Messages.getMessage(Messages.BUTTON_SAVE, pageContext));
            jsLink.append("','");
            jsLink.append(Messages.getMessage(Messages.BUTTON_CANCEL, pageContext));
            jsLink.append("');");

            A editLink = new A(jsLink.toString(), str.toString());
            editLink.setClass(Resources.CLASS_LINK);
            fileUploadTD.addElement(editLink);
        }
        return fileUploadTD;
    }
}
