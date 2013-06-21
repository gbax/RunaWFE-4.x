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
package ru.runa.af.web.tag;

import java.util.ArrayList;
import java.util.List;

import org.apache.ecs.html.Input;
import org.apache.ecs.html.TD;

import ru.runa.af.web.action.RemoveRelationAction;
import ru.runa.common.WebResources;
import ru.runa.common.web.Messages;
import ru.runa.common.web.form.IdForm;
import ru.runa.common.web.html.HeaderBuilder;
import ru.runa.common.web.html.IdentifiableCheckboxTDBuilder;
import ru.runa.common.web.html.ReflectionRowBuilder;
import ru.runa.common.web.html.RowBuilder;
import ru.runa.common.web.html.StringsHeaderBuilder;
import ru.runa.common.web.html.TDBuilder;
import ru.runa.common.web.html.TableBuilder;
import ru.runa.common.web.tag.TitledFormTag;
import ru.runa.wfe.presentation.BatchPresentation;
import ru.runa.wfe.presentation.BatchPresentationFactory;
import ru.runa.wfe.presentation.FieldDescriptor;
import ru.runa.wfe.relation.Relation;
import ru.runa.wfe.relation.RelationPair;
import ru.runa.wfe.relation.RelationPermission;
import ru.runa.wfe.security.Identifiable;
import ru.runa.wfe.security.Permission;
import ru.runa.wfe.service.delegate.Delegates;
import ru.runa.wfe.user.Executor;

/**
 * @jsp.tag name = "listExecutorLeftRelationMembersForm" body-content = "JSP"
 */
public class ListExecutorLeftRelationMembersFormTag extends TitledFormTag {

    private static final long serialVersionUID = 1L;

    private String relationName;

    boolean isFormButtonVisible;

    Long executorId;

    private String returnAction;

    @Override
    protected void fillFormElement(TD tdFormElement) {
        Relation currentRelation = Delegates.getRelationService().getRelationByName(getUser(), getRelationName());
        isFormButtonVisible = Delegates.getAuthorizationService().isAllowed(getUser(), RelationPermission.UPDATE_RELATION, currentRelation);
        List<Executor> executors = new ArrayList<Executor>();
        Executor executor = Delegates.getExecutorService().getExecutor(getUser(), executorId);
        executors.add(executor);
        BatchPresentation groupsBatchPresentation = BatchPresentationFactory.GROUPS.createNonPaged();
        executors.addAll(Delegates.getExecutorService().getExecutorGroups(getUser(), executor, groupsBatchPresentation, false));
        List<RelationPair> relationPairs = Delegates.getRelationService().getExecutorsRelationPairsLeft(getUser(), null, executors);

        TableBuilder tableBuilder = new TableBuilder();
        TDBuilder checkboxBuilder = new IdentifiableCheckboxTDBuilder(RelationPermission.UPDATE_RELATION) {

            @Override
            protected boolean isEnabled(Object object, Env env) {
                return isFormButtonVisible;
            }
        };

        BatchPresentation batchPresentation = BatchPresentationFactory.RELATION_PAIRS.createDefault();
        TDBuilder[] builders = getBuilders(new TDBuilder[] { checkboxBuilder }, batchPresentation, new TDBuilder[] {});

        RowBuilder rowBuilder = new ReflectionRowBuilder(relationPairs, batchPresentation, pageContext, WebResources.ACTION_MAPPING_UPDATE_EXECUTOR,
                getReturnAction(), IdForm.ID_INPUT_NAME, builders);
        HeaderBuilder headerBuilder = new StringsHeaderBuilder(getNames(batchPresentation));

        tdFormElement.addElement(tableBuilder.build(headerBuilder, rowBuilder));
        tdFormElement.addElement(new Input(Input.HIDDEN, "relationName", getRelationName()));
        tdFormElement.addElement(new Input(Input.HIDDEN, "executorId", Long.toString(executorId)));
        tdFormElement.addElement(new Input(Input.HIDDEN, "success", "/manage_executor_relation_left.do"));
        tdFormElement.addElement(new Input(Input.HIDDEN, "failure", "/manage_executor_relation_left.do"));
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    /**
     * @jsp.attribute required = "true" rtexprvalue = "true"
     */
    public String getRelationName() {
        return relationName;
    }

    public void setExecutorId(Long executorId) {
        this.executorId = executorId;
    }

    /**
     * @jsp.attribute required = "true" rtexprvalue = "true"
     */
    public Long getExecutorId() {
        return executorId;
    }

    public String getReturnAction() {
        return returnAction;
    }

    /**
     * @jsp.attribute required = "false" rtexprvalue = "true"
     */
    public void setReturnAction(String returnAction) {
        this.returnAction = returnAction;
    }

    @Override
    protected String getTitle() {
        return relationName;
    }

    @Override
    protected boolean isFormButtonEnabled() {
        return isFormButtonVisible;
    }

    @Override
    protected boolean isFormButtonEnabled(Identifiable identifiable, Permission permission) {
        return isFormButtonVisible;
    }

    @Override
    protected boolean isFormButtonVisible() {
        return isFormButtonVisible;
    }

    @Override
    protected boolean isMultipleSubmit() {
        return false;
    }

    @Override
    public String getAction() {
        return RemoveRelationAction.ACTION_PATH;
    }

    @Override
    protected String getFormButtonName() {
        return Messages.getMessage(Messages.BUTTON_REMOVE, pageContext);
    }

    protected String[] getNames(BatchPresentation batchPresentation) {
        FieldDescriptor[] fields = batchPresentation.getDisplayFields();
        String[] result = new String[fields.length + 1];
        result[0] = "";
        for (int i = 0; i < fields.length; ++i) {
            result[i + 1] = Messages.getMessage(fields[i].displayName, pageContext);
        }
        return result;
    }
}
