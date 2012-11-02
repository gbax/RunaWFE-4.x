package ru.runa.wf.web.ftl;

import javax.security.auth.Subject;
import javax.servlet.jsp.PageContext;

import ru.runa.wf.web.FormProcessingException;
import ru.runa.wf.web.html.StartFormBuilder;
import ru.runa.wfe.InternalApplicationException;
import ru.runa.wfe.commons.ftl.FormHashModel;
import ru.runa.wfe.form.Interaction;
import ru.runa.wfe.security.AuthenticationException;
import ru.runa.wfe.var.MapDelegableVariableProvider;

public class FreemarkerStartFormBuilder extends BaseTaskFormBuilder implements StartFormBuilder {

    @Override
    public String build(Subject subject, Long definitionId, PageContext pageContext, Interaction interaction) throws AuthenticationException,
            FormProcessingException {
        try {
            FormHashModel model = new FormHashModel(subject, pageContext, new MapDelegableVariableProvider(interaction.getDefaultVariableValues(),
                    null));
            return build(interaction, model, definitionId);
        } catch (Exception e) {
            throw new InternalApplicationException(e);
        }
    }

}
