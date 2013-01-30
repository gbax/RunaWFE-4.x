package ru.runa.af.web.action;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.runa.af.web.Native2AsciiHelper;
import ru.runa.common.web.HTMLUtils;
import ru.runa.common.web.action.ActionBase;
import ru.runa.common.web.form.IdForm;
import ru.runa.service.delegate.Delegates;
import ru.runa.service.wf.BotService;
import ru.runa.wfe.bot.BotTask;
import ru.runa.wfe.commons.xml.XmlUtils;

import com.google.common.base.Charsets;

/**
 * User: stan79 Date: 24.01.2009 Time: 13:24:46
 * 
 * @struts:action path="/download_bot_task_configuration" name="idForm" input =
 *                "/WEB-INF/wf/bot.jsp"
 */
public class BotTaskConfigurationDownloadAction extends ActionBase {
    public static final String DOWNLOAD_BOT_TASK_CONFIGURATION_ACTION_PATH = "/download_bot_task_configuration";
    private static final Log log = LogFactory.getLog(BotTaskConfigurationDownloadAction.class);

    @Override
    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
        try {
            IdForm form = (IdForm) actionForm;
            boolean editAction = request.getParameter("edit") != null;
            BotService botService = Delegates.getBotService();
            BotTask botTask = botService.getBotTask(getLoggedUser(request), form.getId());
            String fileName = botTask.getName() + "_" + botTask.getId() + ".xml";
            byte[] configuration = botTask.getConfiguration();
            boolean configurationIsXml = true;
            try {
                XmlUtils.parseWithoutValidation(configuration);
            } catch (Exception e) {
                configurationIsXml = false;
            }
            String tempConfiguration = new String(configuration, Charsets.UTF_8);
            if (editAction && !configurationIsXml && Native2AsciiHelper.isNeedConvert(tempConfiguration)) {
                configuration = Native2AsciiHelper.asciiToNative(tempConfiguration).getBytes(Charsets.UTF_8);
                fileName = botTask.getName() + "_" + botTask.getId() + ".properties";
            }
            response.setContentType("text/xml");
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "max-age=0");
            String encodedFileName = HTMLUtils.encodeFileName(fileName, request.getHeader("User-Agent"));
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");
            OutputStream os = response.getOutputStream();
            os.write(configuration);
            os.flush();
        } catch (Throwable e) {
            log.error("download bot config", e);
        }
        return null;
    }
}
