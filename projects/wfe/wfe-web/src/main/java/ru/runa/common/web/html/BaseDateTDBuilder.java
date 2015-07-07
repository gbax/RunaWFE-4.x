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
package ru.runa.common.web.html;

import java.util.Date;

import org.apache.ecs.ConcreteElement;
import org.apache.ecs.StringElement;
import org.apache.ecs.html.A;
import org.apache.ecs.html.TD;

import ru.runa.common.web.Commons;
import ru.runa.common.web.form.IdForm;
import ru.runa.wfe.commons.CalendarUtil;
import ru.runa.wfe.commons.web.PortletUrlType;

/**
 * Created on 14.11.2005
 * 
 * @author Vitaliy S aka Yilativs
 * @author Gordienko_m
 */
public abstract class BaseDateTDBuilder<T extends Object> implements TDBuilder {

    @Override
    public TD build(Object object, Env env) {
        ConcreteElement dateElement = new StringElement("");
        Date date = getDate((T) object);
        if (date != null) {
            String url = Commons.getActionUrl(getActionMapping(), IdForm.ID_INPUT_NAME, String.valueOf(getId((T) object)), env.getPageContext(),
                    PortletUrlType.Resource);
            String dateText = getValue(object, env);
            dateElement = new A(url, dateText);
        }
        TD td = new TD(dateElement);
        td.setClass(ru.runa.common.web.Resources.CLASS_LIST_TABLE_TD);
        return td;
    }

    @Override
    public String getValue(Object object, Env env) {
        Date date = getDate((T) object);
        if (date != null) {
            return CalendarUtil.formatDateTime(date);
        }
        return "";
    }

    protected abstract Date getDate(T object);

    protected abstract Long getId(T object);

    protected abstract String getActionMapping();

    @Override
    public String[] getSeparatedValues(Object object, Env env) {
        return new String[] { getValue(object, env) };
    }

    @Override
    public int getSeparatedValuesCount(Object object, Env env) {
        return 1;
    }
}
