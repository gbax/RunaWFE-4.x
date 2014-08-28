package ru.runa.gpd.lang.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import ru.runa.gpd.Localization;
import ru.runa.gpd.PluginLogger;
import ru.runa.gpd.form.FormType;
import ru.runa.gpd.form.FormTypeProvider;
import ru.runa.gpd.form.FormVariableAccess;
import ru.runa.gpd.lang.ValidationError;
import ru.runa.gpd.property.FormFilesPropertyDescriptor;
import ru.runa.gpd.util.IOUtils;
import ru.runa.gpd.util.VariableUtils;
import ru.runa.gpd.validation.ValidatorConfig;
import ru.runa.gpd.validation.ValidatorParser;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

public abstract class FormNode extends SwimlanedNode {
    public static final String EMPTY = "";
    public static final String VALIDATION_SUFFIX = "validation.xml";
    public static final String SCRIPT_SUFFIX = "js";
    private String formFileName;
    private String formType;
    private String validationFileName;
    private boolean useJSValidation;
    private String scriptFileName;
    private String templateFileName;

    @Override
    public boolean testAttribute(Object target, String name, String value) {
        if (super.testAttribute(target, name, value)) {
            return true;
        }
        if ("formExists".equals(name)) {
            return Objects.equal(value, String.valueOf(hasForm()));
        }
        return false;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String type) {
        this.formType = type;
    }

    public boolean hasForm() {
        return formFileName != null && formFileName.length() > 0;
    }

    public boolean hasFormValidation() {
        return validationFileName != null && validationFileName.length() > 0;
    }

    public boolean hasFormScript() {
        return scriptFileName != null && scriptFileName.length() > 0;
    }

    public String getFormFileName() {
        return formFileName;
    }

    public void setFormFileName(String formFile) {
        String old = this.formFileName;
        this.formFileName = formFile.trim();
        firePropertyChange(PROPERTY_FORM_FILE, old, this.formFileName);
    }

    public String getValidationFileName() {
        return validationFileName;
    }

    public void setValidationFileName(String validationFile) {
        String old = this.validationFileName;
        this.validationFileName = validationFile.trim();
        firePropertyChange(PROPERTY_FORM_VALIDATION_FILE, old, this.validationFileName);
    }

    public boolean isUseJSValidation() {
        return useJSValidation;
    }

    public void setUseJSValidation(boolean useJSValidation) {
        boolean old = this.useJSValidation;
        this.useJSValidation = useJSValidation;
        firePropertyChange(PROPERTY_FORM_JS_VALIDATION, old, this.useJSValidation);
    }

    public String getScriptFileName() {
        return scriptFileName;
    }

    public void setScriptFileName(String scriptFile) {
        String old = this.scriptFileName;
        this.scriptFileName = scriptFile;
        firePropertyChange(PROPERTY_FORM_SCRIPT_FILE, old, this.scriptFileName);
    }

    public String getTemplateFileName() {
        return templateFileName;
    }

    public void setTemplateFileName(String templateFileName) {
        String old = this.templateFileName;
        this.templateFileName = templateFileName;
        firePropertyChange(PROPERTY_FORM_TEMPLATE_FILE, old, this.templateFileName);
    }

    public boolean hasFormTemplate() {
        return templateFileName != null && templateFileName.length() > 0;
    }

    @Override
    protected List<IPropertyDescriptor> getCustomPropertyDescriptors() {
        List<IPropertyDescriptor> list = super.getCustomPropertyDescriptors();
        list.add(new FormFilesPropertyDescriptor("formFiles", Localization.getString("FormNode.property.formFiles"), this));
        return list;
    }

    @Override
    public Object getPropertyValue(Object id) {
        if ("formFiles".equals(id)) {
            return this;
        }
        return super.getPropertyValue(id);
    }

    @Override
    public void validate(List<ValidationError> errors, IFile definitionFile) {
        super.validate(errors, definitionFile);
        if (hasFormValidation()) {
            IFile validationFile = IOUtils.getAdjacentFile(definitionFile, this.validationFileName);
            if (!validationFile.exists()) {
                errors.add(ValidationError.createLocalizedError(this, "formNode.validationFileNotFound", this.validationFileName));
                return;
            }
        }
        if (hasForm()) {
            try {
                IFile formFile = IOUtils.getAdjacentFile(definitionFile, this.formFileName);
                FormType formType = FormTypeProvider.getFormType(this.formType);
                Map<String, FormVariableAccess> formVars = formType.getFormVariableNames(formFile, this);
                List<String> allVariableNames = getProcessDefinition().getVariableNames(true);
                Set<String> validationVariables = getValidationConfigs((IFolder) formFile.getParent()).keySet();
                for (String formVarName : formVars.keySet()) {
                    if (formVars.get(formVarName) == FormVariableAccess.DOUBTFUL) {
                        errors.add(ValidationError.createLocalizedWarning(this, "formNode.formVariableTagUnknown", formVarName));
                    } else if (!validationVariables.contains(formVarName) && formVars.get(formVarName) == FormVariableAccess.WRITE) {
                        errors.add(ValidationError.createLocalizedWarning(this, "formNode.formVariableOutOfValidation", formVarName));
                    } else if (!allVariableNames.contains(formVarName)) {
                        errors.add(ValidationError.createLocalizedWarning(this, "formNode.formVariableDoesNotExist", formVarName));
                    }
                }
                for (String validationVarName : validationVariables) {
                    if (ValidatorConfig.GLOBAL_FIELD_ID.equals(validationVarName)) {
                        continue;
                    }
                    if (!formVars.keySet().contains(validationVarName)) {
                        errors.add(ValidationError.createLocalizedWarning(this, "formNode.validationVariableOutOfForm", validationVarName));
                    }
                    if (!allVariableNames.contains(validationVarName)) {
                        errors.add(ValidationError.createLocalizedError(this, "formNode.validationVariableDoesNotExist", validationVarName));
                    }
                }
                formType.validate(formFile, this, errors);
            } catch (Exception e) {
                PluginLogger.logErrorWithoutDialog("Error validating form: '" + getName() + "'", e);
                errors.add(ValidationError.createLocalizedWarning(this, "formNode.validationUnknownError", e));
            }
        }
    }

    public Map<String, Map<String, ValidatorConfig>> getValidationConfigs(IFolder processFolder) throws Exception {
        if (!hasFormValidation()) {
            return Maps.newHashMap();
        }
        IFile validationFile = IOUtils.getFile(processFolder, this.validationFileName);
        return ValidatorParser.parseValidatorConfigs(validationFile);
    }

    public Map<String, FormVariableAccess> getFormVariables(IFolder processFolder) throws Exception {
        if (!hasForm()) {
            return Maps.newHashMap();
        }
        FormType formType = FormTypeProvider.getFormType(this.formType);
        IFile formFile = IOUtils.getFile(processFolder, this.formFileName);
        return formType.getFormVariableNames(formFile, this);
    }

    @Override
    public boolean isExclusive() {
        return true;
    }

    @Override
    public FormNode getCopy(GraphElement parent) {
        FormNode copy = (FormNode) super.getCopy(parent);
        copy.setFormType(getFormType());
        if (hasForm()) {
            copy.setFormFileName(copy.getId() + "." + getFormType());
        }
        if (hasFormValidation()) {
            copy.setValidationFileName(copy.getId() + "." + FormNode.VALIDATION_SUFFIX);
        }
        if (hasFormScript()) {
            copy.setScriptFileName(copy.getId() + "." + FormNode.SCRIPT_SUFFIX);
        }
        if (hasFormTemplate()) {
            copy.setTemplateFileName(getTemplateFileName());
        }
        copy.setUseJSValidation(isUseJSValidation());
        return copy;
    }

    @Override
    public List<Variable> getUsedVariables(IFolder processFolder) {
        List<Variable> result = super.getUsedVariables(processFolder);
        try {
            Map<String, FormVariableAccess> variables = getFormVariables(processFolder);
            for (String variableName : variables.keySet()) {
                Variable variable = VariableUtils.getVariableByName(getProcessDefinition(), variableName);
                if (variable != null) {
                    result.add(variable);
                }
            }
        } catch (Exception e) {
            PluginLogger.logError(e);
        }
        return result;
    }
}
