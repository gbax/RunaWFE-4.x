package ru.runa.common.web.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import ru.runa.common.web.Resources;
import ru.runa.common.web.form.AdminScriptForm;
import ru.runa.service.client.AdminScriptClient;
import ru.runa.service.client.AdminScriptClient.Handler;
import ru.runa.wfe.WfException;
import ru.runa.wfe.commons.IOCommons;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;

public class AdminkitScriptsAction extends ActionBase {
    private static final Log log = LogFactory.getLog(AdminkitScriptsAction.class);
    public static final String PATH = "/admin_scripts";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
        AdminScriptForm form = (AdminScriptForm) actionForm;
        boolean ajaxRequest = form.isAjax();
        ActionMessages errors = new ActionMessages();
        try {
            String action = form.getAction();
            String fileName = form.getFileName();
            if ("get".equals(action)) {
                log.info("Getting script " + fileName);
                if (!Strings.isNullOrEmpty(fileName)) {
                    File file = new File(IOCommons.getAdminkitScriptsDirPath() + fileName);
                    byte[] script = FileUtils.readFileToByteArray(file);
                    writeResponse(response, script);
                }
            } else if ("execute".equals(action)) {
                log.info("Executing script");
                final List<String> scriptErrors = new ArrayList<String>();
                AdminScriptClient.run(getLoggedUser(request), getScript(form), new Handler() {

                    @Override
                    public void onTransactionException(Exception e) {
                        scriptErrors.add(e.getMessage());
                    }
                });
                if (ajaxRequest) {
                    writeResponse(response, String.valueOf(scriptErrors.size()).getBytes());
                } else {
                    if (scriptErrors.size() == 0) {
                        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("adminkit.script.execution.success"));
                    } else {
                        errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("adminkit.script.execution.failed"));
                    }
                }
            } else if ("save".equals(action)) {
                if (Strings.isNullOrEmpty(fileName)) {
                    throw new Exception("File name is required");
                }
                log.debug("Saving script " + fileName);
                File file = new File(IOCommons.getAdminkitScriptsDirPath() + fileName);
                FileUtils.writeByteArrayToFile(file, getScript(form));
                log.info("Saved script " + fileName);
                if (!ajaxRequest) {
                    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("adminkit.script.save.success"));
                }
            } else if ("delete".equals(action)) {
                log.debug("Deleting script " + fileName);
                File file = new File(IOCommons.getAdminkitScriptsDirPath() + fileName);
                if (file.exists()) {
                    if (file.delete()) {
                        log.info("Deleted script " + fileName);
                    } else {
                        log.warn("Script does not deleted " + fileName);
                    }
                } else {
                    log.warn("Script does not exist " + fileName);
                }
                if (!ajaxRequest) {
                    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("adminkit.script.delete.success"));
                }
            } else {
                log.error("Unknown action: " + action);
            }
        } catch (Throwable th) {
            log.error("admin scripts action", th);
            setErrors(ajaxRequest, errors, request, response, th.getMessage());
        }
        if (ajaxRequest) {
            return null;
        }
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
        }
        return mapping.findForward(Resources.FORWARD_SUCCESS);
    }

    private byte[] getScript(AdminScriptForm form) throws IOException {
        if (!Strings.isNullOrEmpty(form.getScript())) {
            return form.getScript().getBytes(Charsets.UTF_8);
        }
        if (form.getUploadFile() != null) {
            return form.getUploadFile().getFileData();
        }
        throw new WfException("No script parameter found");
    }

    private void setErrors(boolean ajaxRequest, ActionMessages errors, HttpServletRequest request, HttpServletResponse response, String text) {
        if (ajaxRequest) {
            writeResponse(response, text.getBytes(Charsets.UTF_8));
        } else {
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(text, false));
        }
    }

    private void writeResponse(HttpServletResponse response, byte[] data) {
        try {
            response.setContentType("text/xml");
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "max-age=0");
            OutputStream os = response.getOutputStream();
            os.write(data);
            os.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
