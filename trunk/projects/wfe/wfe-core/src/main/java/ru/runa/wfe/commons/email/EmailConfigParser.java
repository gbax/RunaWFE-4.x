package ru.runa.wfe.commons.email;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.dom4j.Element;

import ru.runa.wfe.commons.ClassLoaderUtil;
import ru.runa.wfe.commons.xml.XmlUtils;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;

@SuppressWarnings("unchecked")
public class EmailConfigParser {

    private static final String VALUE_ATTR = "value";
    private static final String NAME_ATTR = "name";
    private static final String PARAM_ELEMENT = "param";

    public static boolean canParse(String configuration) {
        return configuration.contains("<email-config>");
    }

    public static String toString(byte[] bs) {
        return new String(bs, Charsets.UTF_8);
    }

    public static EmailConfig parse(byte[] bs) {
        return parse(toString(bs));
    }

    public static EmailConfig parse(String configuration) {
        return parse(configuration, true);
    }

    public static EmailConfig parse(String configuration, boolean loadPropertiesFromBaseFile) {
        try {
            EmailConfig config = new EmailConfig();
            Element root = XmlUtils.parseWithoutValidation(configuration).getRootElement();
            Element commonElement = root.element("common");
            if (commonElement != null) {
                List<Element> commonParamElements = commonElement.elements(PARAM_ELEMENT);
                for (Element element : commonParamElements) {
                    String name = element.attributeValue(NAME_ATTR);
                    String value = element.attributeValue(VALUE_ATTR);
                    config.getCommonProperties().put(name, value);
                }
            }
            if (loadPropertiesFromBaseFile && config.getCommonProperties().containsKey(EmailConfig.BASE_PROPERTY_FILE_NAME)) {
                EmailConfig baseConfig = parseFromFile(config.getCommonProperties().get(EmailConfig.BASE_PROPERTY_FILE_NAME));
                config.getConnectionProperties().putAll(baseConfig.getConnectionProperties());
                config.getHeaderProperties().putAll(baseConfig.getHeaderProperties());
            }
            Element connElement = root.element("connection");
            if (connElement != null) {
                List<Element> paramElements = connElement.elements(PARAM_ELEMENT);
                for (Element element : paramElements) {
                    String name = element.attributeValue(NAME_ATTR);
                    String value = element.attributeValue(VALUE_ATTR);
                    config.getConnectionProperties().put(name, value);
                }
            }
            Element headersElement = root.element("headers");
            if (headersElement != null) {
                List<Element> paramElements = headersElement.elements(PARAM_ELEMENT);
                for (Element element : paramElements) {
                    String name = element.attributeValue(NAME_ATTR);
                    String value = element.attributeValue(VALUE_ATTR);
                    config.getHeaderProperties().put(name, value);
                }
            }
            Element messageElement = root.element("message");
            if (messageElement != null) {
                Element bodyElement = messageElement.element("body");
                if (bodyElement != null) {
                    config.setMessage(bodyElement.getTextTrim());
                }
                List<Element> paramElements = messageElement.elements(PARAM_ELEMENT);
                for (Element element : paramElements) {
                    String name = element.attributeValue(NAME_ATTR);
                    String value = element.attributeValue(VALUE_ATTR);
                    config.getContentProperties().put(name, value);
                }
            }
            Element attachmentsElement = root.element("attachments");
            if (attachmentsElement != null) {
                List<Element> fileElements = attachmentsElement.elements("file");
                for (Element element : fileElements) {
                    String name = element.attributeValue(NAME_ATTR);
                    config.getAttachments().add(name);
                }
            }
            return config;
        } catch (Exception e) {
            throw new RuntimeException("Invalid XML: " + configuration, e);
        }
    }

    private static EmailConfig parseFromFile(String fileName) throws IOException {
        InputStream is = ClassLoaderUtil.getResourceAsStream(fileName, EmailConfigParser.class);
        Preconditions.checkNotNull(is, "Resource not found by name " + fileName);
        String c = new String(ByteStreams.toByteArray(is), Charsets.UTF_8);
        return parse(c);
    }

}
