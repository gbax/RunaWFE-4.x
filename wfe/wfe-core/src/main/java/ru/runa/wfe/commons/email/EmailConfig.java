package ru.runa.wfe.commons.email;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.runa.wfe.commons.ftl.ExpressionEvaluator;
import ru.runa.wfe.var.IVariableProvider;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class EmailConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Log log = LogFactory.getLog(EmailConfig.class);

    public static final String THROW_ERROR_ON_FAILURE = "throwErrorOnFailure";
    public static final String BASE_PROPERTY_FILE_NAME = "basePropertiesFileName";
    public static final String USE_MESSAGE_FROM_TASK_FORM = "bodyInlined";
    public static final String MESSAGE_TYPE = "bodyType";

    private final Map<String, String> commonProperties = new HashMap<String, String>();
    private final Map<String, String> connectionProperties = new HashMap<String, String>();
    private final Map<String, String> headerProperties = new HashMap<String, String>();
    private final Map<String, String> contentProperties = new HashMap<String, String>();
    private final List<String> attachmentVariableNames = Lists.newArrayList();
    private final List<Attachment> attachments = Lists.newArrayList();
    private String messageId;
    private String message;

    public Map<String, String> getCommonProperties() {
        return commonProperties;
    }

    public Map<String, String> getConnectionProperties() {
        return connectionProperties;
    }

    public Map<String, String> getHeaderProperties() {
        return headerProperties;
    }

    public Map<String, String> getContentProperties() {
        return contentProperties;
    }

    public List<String> getAttachmentVariableNames() {
        return attachmentVariableNames;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isThrowErrorOnFailure() {
        if (getCommonProperties().containsKey(THROW_ERROR_ON_FAILURE)) {
            return Boolean.valueOf(getCommonProperties().get(THROW_ERROR_ON_FAILURE));
        }
        return Boolean.TRUE;
    }

    public boolean isUseMessageFromTaskForm() {
        if (getContentProperties().containsKey(USE_MESSAGE_FROM_TASK_FORM)) {
            return Boolean.valueOf(getContentProperties().get(USE_MESSAGE_FROM_TASK_FORM));
        }
        return Boolean.FALSE;
    }

    public String getMessageType() {
        if (getContentProperties().containsKey(MESSAGE_TYPE)) {
            return getContentProperties().get(MESSAGE_TYPE);
        }
        return "html";
    }

    public void checkValid() throws Exception {
        Preconditions.checkNotNull(message, "Message is null");
        if (connectionProperties.size() == 0) {
            throw new Exception("connectionProperties.size() == 0");
        }
        if (headerProperties.size() == 0) {
            throw new Exception("headerProperties.size() == 0");
        }
    }

    public void applySubstitutions(IVariableProvider variableProvider) {
        Map<String, String> tmpConnectionProperties = new HashMap<String, String>(connectionProperties);
        Map<String, String> tmpHeaderProperties = new HashMap<String, String>(headerProperties);
        connectionProperties.clear();
        headerProperties.clear();
        for (String propName : tmpConnectionProperties.keySet()) {
            String propValue = tmpConnectionProperties.get(propName);
            String substitutedValue = ExpressionEvaluator.process(null, propValue, variableProvider, null);
            if (!substitutedValue.equals(propValue)) {
                log.debug("Substituted " + propName + ": " + propValue + " -> " + substitutedValue);
            }
            connectionProperties.put(propName, substitutedValue);
        }
        for (Map.Entry<String, String> propEntry : tmpHeaderProperties.entrySet()) {
            String propValue = propEntry.getValue();
            String substitutedValue = ExpressionEvaluator.process(null, propValue, variableProvider, null);
            if (!substitutedValue.equals(propValue)) {
                log.debug("Substituted " + propEntry.getKey() + ": " + propValue + " -> " + substitutedValue);
            }
            headerProperties.put(propEntry.getKey(), substitutedValue);
        }
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\nConnection settings: ").append(connectionProperties);
        buffer.append("\nHeaders: ").append(headerProperties);
        buffer.append("\nMessage: ").append(message);
        return buffer.toString();
    }

    public static class Attachment {
        public boolean inlined;
        public String fileName;
        public byte[] content;
    }

}
