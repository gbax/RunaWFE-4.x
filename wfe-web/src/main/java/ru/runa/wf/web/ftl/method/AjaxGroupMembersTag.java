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
package ru.runa.wf.web.ftl.method;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import ru.runa.wfe.commons.TypeConversionUtil;
import ru.runa.wfe.commons.ftl.AjaxJsonFreemarkerTag;
import ru.runa.wfe.presentation.BatchPresentationFactory;
import ru.runa.wfe.service.client.DelegateExecutorLoader;
import ru.runa.wfe.service.delegate.Delegates;
import ru.runa.wfe.user.Actor;
import ru.runa.wfe.user.Group;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import freemarker.template.TemplateModelException;

@SuppressWarnings("unchecked")
public class AjaxGroupMembersTag extends AjaxJsonFreemarkerTag {
    private static final long serialVersionUID = 1L;

    @Override
    protected String renderRequest() throws TemplateModelException {
        String groupVariableName = getParameterAs(String.class, 0);
        String groupScriptingVariableName = variableProvider.getVariableNotNull(groupVariableName).getDefinition().getScriptingName();
        String userVariableName = getParameterAs(String.class, 1);
        String userScriptingVariableName = variableProvider.getVariableNotNull(userVariableName).getDefinition().getScriptingName();
        Map<String, String> substitutions = Maps.newHashMap();
        substitutions.put("groupSelectorId", groupScriptingVariableName);
        substitutions.put("userSelectorId", userScriptingVariableName);
        StringBuffer html = new StringBuffer();
        html.append(exportScript("scripts/AjaxGroupMembersTag.js", substitutions, true));
        html.append("<span class=\"ajaxGroupMembers\">");
        html.append("<span id=\"ajaxGroupMembers_").append(groupScriptingVariableName).append("\">");
        html.append("<select id=\"").append(groupScriptingVariableName).append("\" name=\"").append(groupVariableName).append("\">");
        List<Group> groups = (List<Group>) Delegates.getExecutorService().getExecutors(user, BatchPresentationFactory.GROUPS.createNonPaged());
        Group defaultGroup = variableProvider.getValue(Group.class, groupVariableName);
        if (defaultGroup == null && groups.size() > 0) {
            defaultGroup = groups.get(0);
        }
        if (groups.size() == 0) {
            html.append("<option value=\"\">No groups</option>");
        }
        for (Group group : groups) {
            html.append("<option value=\"ID").append(group.getId()).append("\"");
            if (Objects.equal(defaultGroup, group)) {
                html.append(" selected");
            }
            html.append(">").append(group.getName()).append("</option>");
        }
        html.append("</select></span>");
        html.append("<span id=\"ajaxGroupMembers_").append(userScriptingVariableName).append("\">");
        html.append("<select id=\"").append(userScriptingVariableName).append("\" name=\"").append(userVariableName).append("\">");
        if (defaultGroup != null) {
            List<Actor> actors = Delegates.getExecutorService().getGroupActors(user, defaultGroup);
            Actor defaultActor = variableProvider.getValue(Actor.class, userVariableName);
            if (defaultActor == null && actors.size() > 0) {
                defaultActor = actors.get(0);
            }
            if (actors.size() == 0) {
                html.append("<option value=\"\">No users in this group</option>");
            } else {
                html.append("<option value=\"\">None</option>");
            }
            for (Actor actor : actors) {
                html.append("<option value=\"ID").append(actor.getId()).append("\"");
                if (Objects.equal(defaultActor, actor)) {
                    html.append(" selected");
                }
                html.append(">").append(actor.getFullName()).append("</option>");
            }
        } else {
            html.append("<option value=\"\"></option>");
        }
        html.append("</select></span>");
        html.append("</span>");
        return html.toString();
    }

    @Override
    protected JSONAware processAjaxRequest(HttpServletRequest request) throws Exception {
        JSONArray json = new JSONArray();
        Group group = (Group) TypeConversionUtil.convertToExecutor(request.getParameter("groupId"), new DelegateExecutorLoader(user));
        List<Actor> actors = Delegates.getExecutorService().getGroupActors(user, group);
        if (actors.size() == 0) {
            json.add(createJsonObject(null, "No users in this group"));
        } else {
            json.add(createJsonObject(null, "None"));
        }
        for (Actor actor : actors) {
            json.add(createJsonObject(actor.getId(), actor.getFullName()));
        }
        return json;
    }

    private JSONObject createJsonObject(Long id, String name) {
        JSONObject object = new JSONObject();
        object.put("id", id != null ? "ID" + id : "");
        object.put("name", name);
        return object;
    }

}
