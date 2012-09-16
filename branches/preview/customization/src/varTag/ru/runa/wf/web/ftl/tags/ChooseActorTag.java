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
package ru.runa.wf.web.ftl.tags;

import java.util.List;

import ru.runa.af.Actor;
import ru.runa.wf.web.ftl.FreemarkerTag;
import ru.runa.wf.web.html.vartag.AbstractActorComboBoxVarTag;
import ru.runa.wf.web.html.vartag.ActorComboboxVarTag;
import freemarker.template.TemplateModelException;

public class ChooseActorTag extends FreemarkerTag {

    private static final long serialVersionUID = 1L;

    @Override
    protected Object executeTag() throws TemplateModelException {
        String actorVarName = getParameterAs(String.class, 0);
        String view = getParameterAs(String.class, 1);

        try {
            AbstractActorComboBoxVarTag actorComboBoxVarTag = new ActorComboboxVarTag();
            if ("all".equals(view)) {
                return actorComboBoxVarTag.getHtml(subject, actorVarName, null, pageContext);
            } else if ("raw".equals(view)) {
                List<Actor> actors = actorComboBoxVarTag.getActors(subject, actorVarName, null);
                return actors;
            } else {
                throw new TemplateModelException("Unexpected value of VIEW parameter: " + view);
            }
        } catch (Exception e) {
            throw new TemplateModelException(e);
        }
    }

}
