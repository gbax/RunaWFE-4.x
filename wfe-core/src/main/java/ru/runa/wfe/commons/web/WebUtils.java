package ru.runa.wfe.commons.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.io.ByteStreams;

public class WebUtils {

    public static String getScript(String javascript) {
        return "<script type=\"text/javascript\">" + javascript + "</script>";
    }

    public static String getFreemarkerTagScript(WebHelper webHelper, String javascript, Map<String, String> substitutions) {
        if (webHelper == null) {
            return "";
        }
        substitutions.put("jsonUrl", webHelper.getUrl("/form.fp?json=true"));
        for (String sKey : substitutions.keySet()) {
            String v = substitutions.get(sKey);
            javascript = javascript.replaceAll(Pattern.quote(sKey), Matcher.quoteReplacement(v));
        }
        return getScript(javascript);
    }

    public static String getFreemarkerTagScript(WebHelper webHelper, InputStream javascriptStream, Map<String, String> substitutions) {
        Preconditions.checkNotNull(javascriptStream);
        try {
            String javascript = new String(ByteStreams.toByteArray(javascriptStream), Charsets.UTF_8);
            return getFreemarkerTagScript(webHelper, javascript, substitutions);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    public static String getExternalScript(WebHelper webHelper, String src) {
        if (webHelper == null || webHelper.getPageContext().getAttribute(src) != null) {
            return "";
        }
        webHelper.getPageContext().setAttribute(src, Boolean.TRUE);
        String url = webHelper.getUrl(src);
        return "<script type=\"text/javascript\" src=\"" + url + "\"></script>";
    }

}
