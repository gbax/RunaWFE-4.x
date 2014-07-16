package ru.runa.common.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import ru.runa.wfe.commons.web.PortletUrlType;
import ru.runa.wfe.commons.web.WebHelper;

import com.google.common.collect.Maps;

public class StrutsWebHelper implements WebHelper {
    private final PageContext pageContext;

    public StrutsWebHelper(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    @Override
    public String getMessage(String key) {
        return Commons.getMessage(key, pageContext);
    }

    @Override
    public HttpServletRequest getRequest() {
        return (HttpServletRequest) pageContext.getRequest();
    }

    @Override
    public String getUrl(String relativeUrl) {
        return Commons.getUrl(relativeUrl, pageContext, PortletUrlType.Resource);
    }

    @Override
    public String getActionUrl(String relativeUrl, Map<String, ? extends Object> params) {
        if (ACTION_DOWNLOAD_PROCESS_FILE.equals(relativeUrl)) {
            return Commons.getActionUrl("/variableDownloader", params, pageContext, PortletUrlType.Render);
        }
        if (ACTION_DOWNLOAD_LOG_FILE.equals(relativeUrl)) {
            Map<String, Object> adjusted = Maps.newHashMap();
            adjusted.put("logId", params.remove(PARAM_ID));
            return Commons.getActionUrl("/variableDownloader", adjusted, pageContext, PortletUrlType.Render);
        }
        return Commons.getActionUrl(relativeUrl, params, pageContext, PortletUrlType.Render);
    }

}
