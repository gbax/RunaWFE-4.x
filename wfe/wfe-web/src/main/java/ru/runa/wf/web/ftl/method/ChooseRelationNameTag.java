package ru.runa.wf.web.ftl.method;

import java.util.List;

import ru.runa.wfe.commons.ftl.FreemarkerTag;
import ru.runa.wfe.presentation.BatchPresentation;
import ru.runa.wfe.presentation.BatchPresentationFactory;
import ru.runa.wfe.relation.Relation;
import ru.runa.wfe.service.delegate.Delegates;

import com.google.common.base.Objects;

public class ChooseRelationNameTag extends FreemarkerTag {
    private static final long serialVersionUID = 1L;

    @Override
    protected Object executeTag() {
        String variableName = getParameterAsString(0);
        Object value = variableProvider.getValue(variableName);
        BatchPresentation batchPresentation = BatchPresentationFactory.RELATIONS.createNonPaged();
        List<Relation> relations = Delegates.getRelationService().getRelations(user, batchPresentation);
        String html = "<select name=\"" + variableName + "\">";
        html += "<option value=\"\"> ------------------------- </option>";
        for (Relation relation : relations) {
            html += "<option";
            if (Objects.equal(relation.getName(), value)) {
                html += " selected";
            }
            html += ">" + relation.getName() + "</option>";
        }
        html += "</select>";
        return html;
    }

}
