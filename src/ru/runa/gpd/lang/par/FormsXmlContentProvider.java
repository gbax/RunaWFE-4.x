package ru.runa.gpd.lang.par;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;

import ru.runa.gpd.lang.model.FormNode;
import ru.runa.gpd.lang.model.Node;
import ru.runa.gpd.lang.model.ProcessDefinition;
import ru.runa.gpd.util.XmlUtil;

import com.google.common.base.Strings;

public class FormsXmlContentProvider extends AuxContentProvider {
    public static final String FORMS_XML_FILE_NAME = "forms.xml";
    private static final String TYPE_ATTRIBUTE_NAME = "type";
    private static final String FILE_ATTRIBUTE_NAME = "file";
    private static final String VALIDATION_FILE_ATTRIBUTE_NAME = "validationFile";
    private static final String JS_VALIDATION_ATTRIBUTE_NAME = "jsValidation";
    private static final String SCRIPT_FILE_ATTRIBUTE_NAME = "scriptFile";
    private static final String STATE_ATTRIBUTE_NAME = "state";
    private static final String FORM_ELEMENT_NAME = "form";
    private static final String FORMS_ELEMENT_NAME = "forms";

    @Override
    public void readFromFile(IFolder folder, ProcessDefinition definition) throws Exception {
        IFile formsFile = folder.getFile(FORMS_XML_FILE_NAME);
        if (!formsFile.exists()) {
            return;
        }
        Document document = XmlUtil.parseWithoutValidation(formsFile.getContents());
        List<Element> formElementsList = document.getRootElement().elements(FORM_ELEMENT_NAME);
        for (Element formElement : formElementsList) {
            String stateId = formElement.attributeValue(STATE_ATTRIBUTE_NAME);
            FormNode formNode = definition.getGraphElementByIdNotNull(stateId);
            String typeName = formElement.attributeValue(TYPE_ATTRIBUTE_NAME);
            if (!Strings.isNullOrEmpty(typeName)) {
                formNode.setFormType(typeName);
            }
            String fileName = formElement.attributeValue(FILE_ATTRIBUTE_NAME);
            if (!Strings.isNullOrEmpty(fileName)) {
                formNode.setFormFileName(fileName);
            }
            String validationFileName = formElement.attributeValue(VALIDATION_FILE_ATTRIBUTE_NAME);
            if (!Strings.isNullOrEmpty(validationFileName)) {
                formNode.setValidationFileName(validationFileName);
                boolean useJsValidation = false;
                String useJsAttr = formElement.attributeValue(JS_VALIDATION_ATTRIBUTE_NAME);
                if ((useJsAttr != null) && (useJsAttr.length() > 0)) {
                    useJsValidation = Boolean.parseBoolean(useJsAttr);
                }
                formNode.setUseJSValidation(useJsValidation);
            }
            String scriptFileName = formElement.attributeValue(SCRIPT_FILE_ATTRIBUTE_NAME);
            if (!Strings.isNullOrEmpty(scriptFileName)) {
                formNode.setScriptFileName(scriptFileName);
            }
        }
    }

    @Override
    public void saveToFile(IFolder folder, ProcessDefinition definition) throws Exception {
        Document document = XmlUtil.createDocument(FORMS_ELEMENT_NAME);
        Element root = document.getRootElement();
        for (Node node : definition.getNodes()) {
            if (node instanceof FormNode) {
                FormNode formNode = (FormNode) node;
                if (formNode.hasForm() || formNode.hasFormValidation()) {
                    Element formElement = root.addElement(FORM_ELEMENT_NAME);
                    formElement.addAttribute(STATE_ATTRIBUTE_NAME, formNode.getId());
                    if (formNode.hasForm()) {
                        formElement.addAttribute(FILE_ATTRIBUTE_NAME, formNode.getFormFileName());
                        formElement.addAttribute(TYPE_ATTRIBUTE_NAME, formNode.getFormType());
                    }
                    if (formNode.hasFormValidation()) {
                        formElement.addAttribute(VALIDATION_FILE_ATTRIBUTE_NAME, formNode.getValidationFileName());
                        formElement.addAttribute(JS_VALIDATION_ATTRIBUTE_NAME, String.valueOf(formNode.isUseJSValidation()));
                    }
                    if (formNode.hasFormScript()) {
                        formElement.addAttribute(SCRIPT_FILE_ATTRIBUTE_NAME, formNode.getScriptFileName());
                    }
                }
            }
        }
        byte[] bytes = XmlUtil.writeXml(document);
        updateFile(folder.getFile(FORMS_XML_FILE_NAME), bytes);
    }
}
