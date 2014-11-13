package ru.runa.wfe.commons.email;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.runa.wfe.commons.SystemProperties;
import ru.runa.wfe.commons.email.EmailConfig.Attachment;
import ru.runa.wfe.commons.ftl.ExpressionEvaluator;
import ru.runa.wfe.form.Interaction;
import ru.runa.wfe.user.User;
import ru.runa.wfe.var.IVariableProvider;
import ru.runa.wfe.var.file.IFileVariable;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;

public class EmailUtils {
    private static final Log log = LogFactory.getLog(EmailConfig.class);

    private static final String MAIL_TRANSPORT = "mail.transport.protocol";
    private static final String MAIL_HOST = "mail.host";
    private static final String MAIL_USER = "mail.user";
    private static final String MAIL_PASSWORD = "mail.password";

    private static MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();

    static {
        System.setProperty("mail.mime.encodefilename", "true");
    }

    public static void sendMessage(EmailConfig config, List<Attachment> attachments) throws Exception {
        config.checkValid();
        Properties props = new Properties();
        props.putAll(config.getConnectionProperties());

        String protocol = props.getProperty(MAIL_TRANSPORT);

        String connectionTimepoutPropName = "mail." + protocol + ".connectiontimeout";
        if (!props.contains(connectionTimepoutPropName)) {
            props.put(connectionTimepoutPropName, "5000");
        }
        String timepoutPropName = "mail." + protocol + ".timeout";
        if (!props.contains(timepoutPropName)) {
            props.put(timepoutPropName, "5000");
        }

        if (config.getHeaderProperties().containsKey("Subject")) {
            String subject = config.getHeaderProperties().get("Subject");
            subject = MimeUtility.encodeText(subject, Charsets.UTF_8.name(), null);
            config.getHeaderProperties().put("Subject", subject);
        }

        PasswordAuthenticator authenticator = null;
        boolean auth = "true".equals(props.getProperty("mail." + protocol + ".auth"));
        if (auth) {
            String username = props.getProperty(MAIL_USER);
            String password = props.getProperty(MAIL_PASSWORD);
            Preconditions.checkNotNull(username, "Authenticaton enabled but property " + MAIL_USER + " is not set");
            Preconditions.checkNotNull(password, "Authenticaton enabled but property " + MAIL_PASSWORD + " is not set");
            authenticator = new PasswordAuthenticator(username, password);
            if (!config.getHeaderProperties().containsKey("From")) {
                config.getHeaderProperties().put("From", username);
            }
        }

        Session session = Session.getInstance(props, authenticator);
        MimeMessage msg = new MimeMessage(session);
        for (String headerName : config.getHeaderProperties().keySet()) {
            String headerValue = config.getHeaderProperties().get(headerName);
            msg.setHeader(headerName, headerValue);
        }
        Multipart multipart = new MimeMultipart("related");
        MimeBodyPart part = new MimeBodyPart();
        part.setText(config.getMessage(), Charsets.UTF_8.name(), config.getMessageType());
        multipart.addBodyPart(part);
        if (attachments != null) {
            for (Attachment attachment : attachments) {
                MimeBodyPart attach = new MimeBodyPart();
                attach.setDataHandler(new DataHandler(new ByteArrayDataSource(attachment.content, fileTypeMap.getContentType(attachment.fileName))));
                if (attachment.inlined) {
                    attach.setHeader("Content-ID", attachment.fileName);
                    attach.setDisposition(Part.INLINE);
                } else {
                    attach.setFileName(attachment.fileName);
                }
                multipart.addBodyPart(attach);
            }
        }
        msg.setContent(multipart);
        log.info("Connecting to [" + protocol + "]: " + props.getProperty(MAIL_HOST) + ":" + props.getProperty("mail." + protocol + ".port"));
        Transport transport = session.getTransport();
        try {
            transport.connect();
            msg.saveChanges();
            transport.sendMessage(msg, msg.getAllRecipients());
            String debugMessage = "Message sent";
            Address[] toAddresses = msg.getRecipients(RecipientType.TO);
            if (toAddresses != null) {
                debugMessage += " To:" + Arrays.asList(toAddresses);
            }
            Address[] ccAddresses = msg.getRecipients(RecipientType.CC);
            if (ccAddresses != null) {
                debugMessage += " Cc:" + Arrays.asList(ccAddresses);
            }
            log.info(debugMessage);
        } finally {
            transport.close();
        }
    }

    public static void sendTaskMessage(User user, EmailConfig config, Interaction interaction, IVariableProvider variableProvider) throws Exception {
        config.applySubstitutions(variableProvider);
        String formTemplate;
        if (config.isUseMessageFromTaskForm()) {
            if (interaction.hasForm()) {
                formTemplate = new String(interaction.getFormData(), Charsets.UTF_8);
                if (!"ftl".equals(interaction.getType())) {
                    throw new Exception("Property 'UseMessageFromTaskForm' is applicable only to free form layout form (ftl)");
                }
            } else {
                if (SystemProperties.isV3CompatibilityMode()) {
                    formTemplate = " ";
                } else {
                    throw new Exception("Property 'UseMessageFromTaskForm' is set but form does not exist");
                }
            }
        } else {
            formTemplate = config.getMessage();
        }
        String formMessage = ExpressionEvaluator.process(user, formTemplate, variableProvider, null);
        Map<String, String> replacements = new HashMap<String, String>();
        List<Attachment> attachments = new ArrayList<Attachment>();
        // List<String> images = HTMLUtils.findImages(formBytes);
        // for (String image : images) {
        // Attachment attachment = new Attachment();
        // attachment.fileName = image;
        // attachment.content =
        // fileDataProvider.getFileDataNotNull(attachment.fileName);
        // attachment.inlined = true;
        // attachments.add(attachment);
        // replacements.put(attachment.fileName, "cid:" + attachment.fileName);
        // } TODO images not supported
        for (String variableName : config.getAttachments()) {
            IFileVariable fileVariable = variableProvider.getValue(IFileVariable.class, variableName);
            if (fileVariable != null) {
                Attachment attachment = new Attachment();
                attachment.fileName = fileVariable.getName();
                attachment.content = fileVariable.getData();
                attachments.add(attachment);
            }
        }
        for (String repl : replacements.keySet()) {
            formMessage = formMessage.replaceAll(repl, replacements.get(repl));
        }
        config.setMessage(formMessage);
        sendMessage(config, attachments);
    }

    private static class PasswordAuthenticator extends Authenticator {

        private final String username;
        private final String password;

        public PasswordAuthenticator(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    }
}
