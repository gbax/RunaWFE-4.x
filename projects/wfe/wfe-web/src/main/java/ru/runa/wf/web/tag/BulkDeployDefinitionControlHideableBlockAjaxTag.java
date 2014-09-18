package ru.runa.wf.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.ecs.Entities;
import org.apache.ecs.html.A;
import org.apache.ecs.html.IMG;
import org.apache.ecs.html.Script;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.TR;
import org.apache.ecs.html.Table;

import ru.runa.common.WebResources;
import ru.runa.common.web.BatchPresentationsVisibility;
import ru.runa.common.web.Commons;
import ru.runa.common.web.Messages;
import ru.runa.common.web.Resources;
import ru.runa.common.web.action.HideableBlockAction;
import ru.runa.common.web.tag.AbstractReturningTag;
import ru.runa.wfe.commons.web.PortletUrlType;

/**
 * Created 23.05.2014
 * 
 * @jsp.tag name = "bulkDeployDefinitionControlHideableBlock" body-content = "JSP"
 */
public class BulkDeployDefinitionControlHideableBlockAjaxTag extends AbstractReturningTag {
    private static final long serialVersionUID = -4644961109658379700L;

    private String hideableBlockId;

    public void setHideableBlockId(String id) {
        hideableBlockId = id;
    }

    public String getHideableBlockId() {
        return hideableBlockId;
    }

    public String getHideTitle() {
        return Messages.getMessage(Messages.LABEL_HIDE_DEPLOY_DEFINITION_CONTROLS, pageContext);
    }

    public String getShowTitle() {
        return Messages.getMessage(Messages.LABEL_SHOW_DEPLOY_DEFINITION_CONTROLS, pageContext);
    }

    @Override
    public String getAction() {
        return HideableBlockAction.ACTION_PATH;
    }

    @Override
    public int doStartTag() throws JspException {
        if (WebResources.isBulkDeploymentElements()) {
            JspWriter jspOut = pageContext.getOut();
            try {
                jspOut.println(new Table().createStartTag());
                TR tr = new TR();
                TD headerTD = new TD();
                tr.addElement(headerTD);
                headerTD.setClass(Resources.CLASS_HIDEABLEBLOCK);

                boolean contentVisible = BatchPresentationsVisibility.get(pageContext.getSession()).isBlockVisible(hideableBlockId);
                headerTD.addElement(Entities.NBSP);
                headerTD.addElement(new Script("visibleBlock = " + contentVisible + ";"));

                String id = getHideableBlockId() + "Controls";

                A link = new A("javascript:viewBlock('" + getHideableBlockId() + "');");
                headerTD.addElement(link);
                link.setID(id + "Link");
                link.setClass(Resources.CLASS_HIDEABLEBLOCK);
                String imgLink = contentVisible ? Resources.VISIBLE_IMAGE : Resources.HIDDEN_IMAGE;
                IMG img = new IMG(Commons.getUrl(imgLink, pageContext, PortletUrlType.Resource));
                img.setID(id + "Img");
                link.addElement(img);
                img.setAlt(contentVisible ? Resources.VISIBLE_ALT : Resources.HIDDEN_ALT);
                img.setClass(Resources.CLASS_HIDEABLEBLOCK);
                link.addElement(contentVisible ? getHideTitle() : getShowTitle());

                headerTD.addElement(Entities.NBSP);

                tr.output(jspOut);
                String style = "";
                if (!contentVisible) {
                    style = "style=\"display: none;\"";
                }
                jspOut.println("<tr><td id=\"" + id + "\" " + style + ">");
                return EVAL_BODY_INCLUDE;
            } catch (IOException e) {
                throw new JspException(e);
            }
        }
        return 0;
    }

    @Override
    public int doEndTag() throws JspException {
        if (WebResources.isBulkDeploymentElements()) {
            JspWriter jspOut = pageContext.getOut();
            try {
                jspOut.println("</td></tr>");
                jspOut.println(new Table().createEndTag());
            } catch (IOException e) {
                throw new JspException(e);
            }
            return EVAL_PAGE;
        }
        return 0;
    }
}
