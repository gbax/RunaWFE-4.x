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
package ru.runa.common.web.tag;

import javax.servlet.jsp.JspException;

import org.apache.ecs.ConcreteElement;
import org.apache.ecs.StringElement;
import org.apache.ecs.html.A;

import ru.runa.common.web.Commons;
import ru.runa.common.web.Commons.PortletUrl;
import ru.runa.common.web.Resources;

/**
 * Created on 02.09.2004
 * 
 * @jsp.tag name = "link" body-content = "empty"
 */
public class LinkTag extends VisibleTag {

    private static final long serialVersionUID = -6333366313026520201L;

    /**
     * @return true if link must be enabled
     * @throws JspException
     */
    protected boolean isLinkEnabled() throws JspException {
        return true;
    }

    protected ConcreteElement getEndElement() throws JspException {
        ConcreteElement concreteElement;
        if (isLinkEnabled()) {
            A link = new A(getHref(), getLinkText());
            link.setClass(Resources.CLASS_LINK);
            concreteElement = link;
        } else {
            //TODO Mihail insists on hiding links instead disabling. Disabled links now will be hiden
            //			concreteElement = new Span(getLinkText());
            //			concreteElement.setClass(Resources.CLASS_DISABLED_LINK);
            concreteElement = new StringElement();
        }
        concreteElement.setClass(Resources.CLASS_LINK);
        return concreteElement;
    }

    protected String href = "";

    protected String linkText = "";

    protected String getLinkText() {
        return linkText;
    }

    protected String getHref() throws JspException {
        return href;
    }

    /**
     * @jsp.attribute required = "false" rtexprvalue = "true"
     */
    public void setForward(String forward) throws JspException {
        href = Commons.getForwardUrl(forward, pageContext, PortletUrl.Action);
    }

    /**
     * @jsp.attribute required = "false" rtexprvalue = "true"
     */
    public void setHref(String href) throws JspException {
        this.href = Commons.getActionUrl(href, pageContext, PortletUrl.Render);
    }

    /**
     * @jsp.attribute required = "false" rtexprvalue = "true"
     */
    public void setLinkText(String linkText) {
        this.linkText = linkText;
    }

    protected ConcreteElement getStartElement() throws JspException {
        return new StringElement();
    }
}
