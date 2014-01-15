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
package ru.runa.wfe.commons.ftl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.LogFactory;

import ru.runa.wfe.commons.ClassLoaderUtil;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;

import freemarker.template.TemplateModelException;

public abstract class AjaxFreemarkerTag extends FreemarkerTag {
    private static final long serialVersionUID = 1L;
    public static final String TAG_SESSION_PREFIX = "ftltag_";

    /**
     * Used only if multiple tags of the same type used in same form.
     * 
     * @return qualifier, usually variable name
     */
    public String getQualifier() {
        try {
            return getParameterAsString(0);
        } catch (TemplateModelException e) {
            return null;
        }
    }

    @Override
    protected final Object executeTag() throws Exception {
        return renderRequest();
    }

    /**
     * Is invoked on first (static) page rendering
     * 
     * @return tag html
     */
    protected abstract String renderRequest() throws Exception;

    /**
     * Invoked on ajax request
     */
    public void processAjaxRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    }

    protected String exportExternalScript(String src) {
        if (webHelper == null || webHelper.getPageContext().getAttribute(src) != null) {
            return "";
        }
        webHelper.getPageContext().setAttribute(src, Boolean.TRUE);
        String url = webHelper.getUrl(src);
        return "<script type=\"text/javascript\" src=\"" + url + "\"></script>";
    }

    protected String exportScriptCode(String javascript) {
        return "<script type=\"text/javascript\">" + javascript + "</script>";
    }

    protected String exportScript(String path, Map<String, String> substitutions, boolean uniqueScript) {
        try {
            if (webHelper == null) {
                return "";
            }
            if (uniqueScript && webHelper.getPageContext().getAttribute(path) != null) {
                return "";
            }
            InputStream is = ClassLoaderUtil.getAsStreamNotNull(path, getClass());
            byte[] data = ByteStreams.toByteArray(is);
            String jsCode = new String(data, Charsets.UTF_8);

            substitutions.put("jsonUrl", webHelper.getUrl("/form.fp?json=true"));
            for (String sKey : substitutions.keySet()) {
                String v = substitutions.get(sKey);
                jsCode = jsCode.replaceAll(Pattern.quote(sKey), Matcher.quoteReplacement(v));
            }
            webHelper.getPageContext().setAttribute(path, Boolean.TRUE);
            return "<script type=\"text/javascript\">" + jsCode + "</script>";
        } catch (IOException e) {
            LogFactory.getLog(getClass()).error("Tag execution error", e);
            return "<p style='color: red;'>Tag error: Script not found at </p><b>" + e.getMessage() + "</b>";
        }
    }
}