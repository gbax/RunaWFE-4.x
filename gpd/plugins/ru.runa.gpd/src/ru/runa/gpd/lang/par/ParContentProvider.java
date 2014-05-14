package ru.runa.gpd.lang.par;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;

import ru.runa.gpd.PluginLogger;
import ru.runa.gpd.lang.model.FormNode;
import ru.runa.gpd.lang.model.ProcessDefinition;
import ru.runa.gpd.lang.model.SubprocessDefinition;
import ru.runa.gpd.util.IOUtils;
import ru.runa.gpd.util.XmlUtil;
import ru.runa.gpd.validation.ValidatorConfig;
import ru.runa.gpd.validation.ValidatorParser;

public class ParContentProvider {
    public static final String PROCESS_DEFINITION_FILE_NAME = "processdefinition.xml";
    public static final String SUBPROCESS_DEFINITION_PREFIX = "sub";
    public static final String PROCESS_DEFINITION_DESCRIPTION_FILE_NAME = "index.html";
    public static final String FORM_CSS_FILE_NAME = "form.css";
    public static final String PROCESS_IMAGE_FILE_NAME = "processimage.jpg";
    public static final String PROCESS_INSTANCE_START_IMAGE_FILE_NAME = "start.png";
    private static final List<AuxContentProvider> contentProviders = new ArrayList<AuxContentProvider>();
    static {
        contentProviders.add(new VariablesXmlContentProvider());
        contentProviders.add(new FormsXmlContentProvider());
        contentProviders.add(new GpdXmlContentProvider());
        contentProviders.add(new SwimlaneGUIContentProvider());
        contentProviders.add(new ActionDescriptionContentProvider());
        contentProviders.add(new SubstitutionExceptionsXmlContentProvider());
        contentProviders.add(new BotsXmlContentProvider());
    }

    public static void readAuxInfo(IFile definitionFile, ProcessDefinition definition) throws Exception {
        IFolder folder = (IFolder) definitionFile.getParent();
        for (AuxContentProvider contentProvider : contentProviders) {
            String fileName = contentProvider.getFileName();
            if (definition instanceof SubprocessDefinition) {
                if (!contentProvider.isSupportedForEmbeddedSubprocess()) {
                    continue;
                }
                fileName = definition.getId() + "." + fileName;
            }
            IFile file = folder.getFile(fileName);
            if (!file.exists()) {
                continue;
            }
            Document document = XmlUtil.parseWithoutValidation(file.getContents());
            contentProvider.read(document, definition);
        }
    }

    public static void saveAuxInfo(IFile definitionFile, ProcessDefinition definition) {
        try {
            IFolder folder = (IFolder) definitionFile.getParent();
            for (AuxContentProvider contentProvider : contentProviders) {
                String fileName = contentProvider.getFileName();
                if (definition instanceof SubprocessDefinition) {
                    if (!contentProvider.isSupportedForEmbeddedSubprocess()) {
                        continue;
                    }
                    fileName = definition.getId() + "." + fileName;
                }
                IFile file = folder.getFile(fileName);
                Document document = contentProvider.save(definition);
                if (document != null) {
                    byte[] contentBytes;
                    if (contentProvider instanceof BotsXmlContentProvider) {
                        // TODO why this is reall yneed?
                        contentBytes = XmlUtil.writeXml(document, OutputFormat.createPrettyPrint());
                    } else {
                        contentBytes = XmlUtil.writeXml(document);
                    }
                    InputStream content = new ByteArrayInputStream(contentBytes);
                    IOUtils.createOrUpdateFile(file, content);
                } else {
                    if (file.exists()) {
                        file.delete(true, null);
                    }
                }
            }
        } catch (Exception e) {
            PluginLogger.logError(e);
        }
    }

    public static List<FormNode> getFormsWhereVariableUsed(IFile definitionFile, ProcessDefinition definition, String variableName) {
        List<FormNode> result = new ArrayList<FormNode>();
        List<FormNode> allNodes = definition.getChildren(FormNode.class);
        for (FormNode formNode : allNodes) {
            if (formNode.hasFormValidation()) {
                IFile validationFile = IOUtils.getAdjacentFile(definitionFile, formNode.getValidationFileName());
                Map<String, Map<String, ValidatorConfig>> config = ValidatorParser.parseValidatorConfigs(validationFile);
                if (config.containsKey(variableName)) {
                    result.add(formNode);
                }
            }
        }
        return result;
    }

    public static void rewriteFormValidationsRemoveVariable(IFile definitionFile, List<FormNode> formNodes, String variableName) {
        for (FormNode formNode : formNodes) {
            IFile validationFile = IOUtils.getAdjacentFile(definitionFile, formNode.getValidationFileName());
            Map<String, Map<String, ValidatorConfig>> fieldConfigs = ValidatorParser.parseValidatorConfigs(validationFile);
            fieldConfigs.remove(variableName);
            ValidatorParser.writeValidatorXml(validationFile, fieldConfigs);
        }
    }
}
