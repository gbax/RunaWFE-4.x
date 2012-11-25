package ru.runa.wfe.commons.ftl;

import java.io.Serializable;
import java.util.List;

import javax.security.auth.Subject;
import javax.servlet.jsp.PageContext;

import ru.runa.wfe.commons.TypeConversionUtil;
import ru.runa.wfe.commons.web.WebHelper;
import ru.runa.wfe.var.IVariableProvider;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

@SuppressWarnings("unchecked")
public abstract class FreemarkerTag implements TemplateMethodModelEx, Serializable {
    private static final long serialVersionUID = 1L;
    protected Subject subject;
    protected PageContext pageContext;
    protected IVariableProvider variableProvider;
    protected WebHelper webHelper;
    private List<TemplateModel> arguments;

    public void init(Subject subject, PageContext pageContext, WebHelper webHelper, IVariableProvider variableProvider) {
        this.subject = subject;
        this.pageContext = pageContext;
        this.webHelper = webHelper;
        this.variableProvider = variableProvider;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public final Object exec(List arguments) throws TemplateModelException {
        this.arguments = arguments;
        return executeTag();
    }

    protected void registerVariableHandler(String variableName) {
        pageContext.getSession().setAttribute(FtlTagVariableHandler.HANDLER_KEY_PREFIX + variableName, this);
    }

    protected abstract Object executeTag() throws TemplateModelException;

    protected <T> T getParameterAs(Class<T> clazz, int i) throws TemplateModelException {
        Object paramValue = null;
        if (i < arguments.size()) {
            paramValue = BeansWrapper.getDefaultInstance().unwrap(arguments.get(i));
        }
        return TypeConversionUtil.convertTo(paramValue, clazz);
    }

}
