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

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.ecs.html.Div;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.TH;
import org.apache.ecs.html.TR;
import org.apache.ecs.html.Table;

import ru.runa.common.web.Resources;

/**
 * @jsp.tag name = "box" body-content = "JSP"
 */
public class BoxTag extends TagSupport {
    private static final long serialVersionUID = -1392091463949758729L;
    private String align;
    private String height;
    private String title;
    private String valign;
    private String width;

    public int doStartTag() throws JspException {
        JspWriter jspOut = pageContext.getOut();

        try {
            jspOut.write(new Div().createStartTag());

            Table table = new Table();

            if (width != null) {
                table.setWidth(width);
            }
            if (height != null) {
                table.setHeight(height);

            }
            table.setClass(Resources.CLASS_BOX);

            jspOut.write(table.createStartTag());

            TR trh = new TR(((TH) new TH().setClass(Resources.CLASS_BOX_TITLE)).addElement(title));
            trh.output(jspOut);

            TR trb = new TR();
            jspOut.write(trb.createStartTag());

            TD td = new TD();
            td.setClass(Resources.CLASS_BOX_BODY);
            if (align != null) {
                td.setAlign(align);
            }
            if (valign != null) {
                td.setVAlign(valign);
            }
            jspOut.write(td.createStartTag());
        } catch (IOException e) {
            throw new JspException(e);
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        JspWriter jspOut = pageContext.getOut();
        try {
            jspOut.println(new TD().createEndTag());
            jspOut.println(new TR().createEndTag());
            jspOut.println(new Table().createEndTag());
            jspOut.write(new Div().createEndTag());

        } catch (IOException e) {
            throw new JspException(e);
        }
        return EVAL_PAGE;
    }

    /**
     * @jsp.attribute required = "false"
     */
    public String getAlign() {
        return align;
    }

    /**
     * @jsp.attribute required = "false"
     */
    public String getHeight() {
        return height;
    }

    /**
     * @jsp.attribute required = "true" rtexprvalue = "true"
     */
    public String getTitle() {
        return title;
    }

    /**
     * @jsp.attribute required = "false"
     */
    public String getValign() {
        return valign;
    }

    /**
     * @jsp.attribute required = "false"
     */
    public String getWidth() {
        return width;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setValign(String valign) {
        this.valign = valign;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
