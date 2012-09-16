package ru.runa.bp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.runa.Messages;
import ru.runa.alfresco.AlfSession;
import ru.runa.alfresco.AlfSessionWrapper;
import ru.runa.alfresco.ConnectionException;
import ru.runa.wf.web.ftl.AjaxFreemarkerTag;
import freemarker.template.TemplateModelException;

/**
 * Base class for RunaWFE ajax freemarker tag.
 * @author dofs
 */
public abstract class AlfAjaxTag extends AjaxFreemarkerTag {
    protected Log log = LogFactory.getLog(getClass());

    protected abstract String renderRequest(AlfSession session) throws Exception;
    
    protected void processAjaxRequest(AlfSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //
    }

    @Override
    protected String renderRequest() throws TemplateModelException {
        try {
            return new AlfSessionWrapper<String>() {
                @Override
                protected String code() throws Exception {
                    return renderRequest(session);
                }
            }.runInSession();
        } catch (Exception e) {
            return renderErrorResponse(e);
        }
    }
    
    protected String renderErrorResponse(Exception e) {
        StringBuffer html = new StringBuffer("<div style=\"background-color: #ffb0b0; border: 1px solid red; padding: 3px;\">");
        if (e instanceof ConnectionException) {
            html.append(Messages.getMessage("error.alfresco.unavailable"));
        } else {
            log.error("", e);
            html.append(Messages.getMessage("error.alfresco")).append(": ").append(e.getMessage());
        }
        html.append("</div>");
        return html.toString();
    }

    @Override
    public void processAjaxRequest(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        new AlfSessionWrapper<String>() {
            @Override
            protected String code() throws Exception {
                processAjaxRequest(session, request, response);
                return null;
            }
        }.runInSession();
    }

    protected boolean isValid(String varValue) {
        return varValue != null && varValue.length() > 0;
    }

}
