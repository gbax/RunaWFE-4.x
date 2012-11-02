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
package ru.runa.wf.logic.bot;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.Subject;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.runa.service.delegate.DelegateFactory;
import ru.runa.service.wf.ExecutionService;
import ru.runa.wf.logic.bot.webservice.ErrorResponseProcessingResult;
import ru.runa.wf.logic.bot.webservice.Interaction;
import ru.runa.wf.logic.bot.webservice.WebServiceTaskHandlerSettings;
import ru.runa.wf.logic.bot.webservice.WebServiceTaskHandlerXMLParser;
import ru.runa.wf.logic.bot.webservice.WebServiceTaskHandlerXSLTHelper;
import ru.runa.wfe.InternalApplicationException;
import ru.runa.wfe.commons.TypeConversionUtil;
import ru.runa.wfe.security.AuthenticationException;
import ru.runa.wfe.security.AuthorizationException;
import ru.runa.wfe.task.TaskDoesNotExistException;
import ru.runa.wfe.task.dto.WfTask;
import ru.runa.wfe.user.ExecutorDoesNotExistException;
import ru.runa.wfe.validation.impl.ValidationException;
import ru.runa.wfe.var.IVariableProvider;
import ru.runa.wfe.var.dto.WfVariable;

import com.google.common.io.NullOutputStream;

/**
 * Web service task handler. Making web requests to web services and receiving responses. Some data from WFE system can be added to requests using XSLT transformation and special
 * XSLT tags. Responses can be processed with XSLT to fill some variables, or can be stored completely in variables.
 */
public class WebServiceTaskHandler implements TaskHandler {
    /**
     * Logging support.
     */
    private static final Log log = LogFactory.getLog(WebServiceTaskHandler.class);
    /**
     * XSLT transformation applied in bot thread, so {@link WebServiceTaskHandlerXSLTHelper} to process tag is stored in {@link ThreadLocal}.
     */
    private static ThreadLocal<WebServiceTaskHandlerXSLTHelper> xsltHelper = new ThreadLocal<WebServiceTaskHandlerXSLTHelper>();

    /**
     * Web service bot settings.
     */
    private WebServiceTaskHandlerSettings settings;

    @Override
    public void configure(String configurationName) throws TaskHandlerException {
        settings = WebServiceTaskHandlerXMLParser.read(getClass().getResourceAsStream(configurationName));
    }

    @Override
    public void configure(byte[] configuration) throws TaskHandlerException {
        settings = WebServiceTaskHandlerXMLParser.read(new ByteArrayInputStream(configuration));
    }

    @Override
    public void handle(Subject subject, IVariableProvider variableProvider, WfTask taskStub) throws TaskHandlerException {
        try {
            xsltHelper.set(new WebServiceTaskHandlerXSLTHelper(taskStub, subject));
            URL url = getWebServiceUrl(subject, taskStub);
            for (int index = getStartInteraction(subject, taskStub); index < settings.interactions.size(); ++index) {
                Interaction interaction = settings.interactions.get(index);
                byte[] soapData = prepareRequest(taskStub, interaction);
                HttpURLConnection connection = sendRequest(url, soapData);
                if (connection.getResponseCode() < 200 || connection.getResponseCode() >= 300) {
                    // Something goes wrong
                    if (!onErrorResponse(subject, taskStub, connection, interaction)) {
                        return;
                    }
                } else {
                    onResponse(taskStub, connection, interaction);
                }
            }
            completteTask(subject, taskStub);
        } catch (MalformedURLException e) {
            throw new InternalApplicationException("Can't get url for webservice call.", e);
        } catch (Exception e) {
            throw new InternalApplicationException(e);
        }
    }

    /**
     * WFE XSLT tag support: variable with specified name for current task.
     * 
     * @param name
     *            Variable name
     * @return Variable value.
     */
    public static String getVariable(String name) throws Exception {
        return xsltHelper.get().getVariable(name);
    }

    /**
     * WFE XSLT tag support: Read process instance id from variable and returns process instance graph for this process instance encoded in {@link Base64}.
     * 
     * @param processIdVariable
     *            Variable name to read process instance id.
     * @return Process instance graph for this process instance encoded in {@link Base64}.
     */
    public static String getProcessGraph(String processIdVariable) throws Exception {
        return xsltHelper.get().getProcessGraph(processIdVariable);
    }

    /**
     * Add variable to internal storage.
     * 
     * @param name
     *            Variable name.
     * @param value
     *            Variable value.
     */
    public static void setNewVariable(String name, String value) {
        xsltHelper.get().setNewVariable(name, value);
    }

    /**
     * Completes current task with variables, stored in xsltHelper.
     * 
     * @param subject
     *            Current bot subject
     * @param wfTask
     *            Current processing task.
     */
    private void completteTask(Subject subject, WfTask wfTask) throws TaskDoesNotExistException, AuthorizationException, AuthenticationException,
            ExecutorDoesNotExistException, ValidationException {
        Map<String, Object> variables = new HashMap<String, Object>();
        xsltHelper.get().MergeVariablesIn(variables);
        ExecutionService executionService = DelegateFactory.getExecutionService();
        executionService.completeTask(subject, wfTask.getId(), variables);
    }

    /**
     * Get web service URL. Tries to create URL from settings (URL parameter). If URL creation failed, then read variable with name from URL parameter and create URL with variable
     * value.
     * 
     * @param subject
     *            Current bot subject.
     * @param taskStub
     *            Current task instance to be processed.
     * @return URL of web service to send requests.
     */
    private URL getWebServiceUrl(Subject subject, WfTask taskStub) throws TaskDoesNotExistException, AuthorizationException, MalformedURLException {
        try {
            return new URL(settings.url);
        } catch (MalformedURLException e) {
            ExecutionService executionService = DelegateFactory.getExecutionService();
            WfVariable var = executionService.getVariable(subject, taskStub.getProcessId(), settings.url);
            return new URL(var.getValue() != null ? var.getValue().toString() : "");
        }
    }

    /**
     * Prepare web request to send. Applies XSLT to replace WFE tags with actual values.
     * 
     * @param taskStub
     *            Current task instance to be processed.
     * @param interaction
     *            Current interaction with web service.
     * @return Prepared web service request.
     */
    private byte[] prepareRequest(WfTask taskStub, Interaction interaction) throws TransformerException, TransformerConfigurationException,
            TransformerFactoryConfigurationError, UnsupportedEncodingException {
        ByteArrayOutputStream res2 = new ByteArrayOutputStream();
        Transformer transformer = TransformerFactory.newInstance().newTransformer(
                new StreamSource(getClass().getResourceAsStream("/bot/webServiceTaskHandlerRequest.xslt")));
        transformer.transform(new StreamSource(new ByteArrayInputStream(interaction.requestXML.getBytes(settings.encoding))), new StreamResult(res2));
        byte[] soapData = res2.toByteArray();
        if (settings.isLoggingEnable && log.isDebugEnabled()) {
            log.debug("Web service bot request for task " + taskStub.getId() + " is:\n" + new String(soapData, settings.encoding));
        }
        return soapData;
    }

    /**
     * Opens HTTP connection to web service and setup required connection parameters. Send request to web service.
     * 
     * @param url
     *            URL to open connection.
     * @param length
     *            SOAP data length.
     * @return HTTP connection to communicate with web service.
     */
    private HttpURLConnection sendRequest(URL url, byte[] soapData) throws IOException, ProtocolException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "text/xml; charset=" + settings.encoding);
        if (settings.authBase != null) {
            String auth = Base64.encodeBase64String(settings.authBase.getBytes());
            connection.setRequestProperty("Authorization", "Basic " + auth);
        }
        String soapAction = settings.soapAction == null ? "" : settings.soapAction;
        connection.setRequestProperty("SOAPAction", "\"" + soapAction + "\"");
        if (settings.requestMethod != null) {
            connection.setRequestMethod(settings.requestMethod);
        }
        connection.setFixedLengthStreamingMode(soapData.length);
        connection.setDoOutput(true);
        OutputStream os = connection.getOutputStream();
        os.write(soapData);
        os.flush();
        return connection;
    }

    /**
     * Called to process response from web service with code not from [200; 299] (This codes indicates error).
     * 
     * @param subject
     *            Current bot subject.
     * @param taskStub
     *            Current task instance to be processed.
     * @param connection
     *            HTTP connection to communicate with web service.
     * @param interaction
     *            Current processing interaction.
     * @return true, if next interaction must be processed and false if required to stop interaction processing.
     */
    private boolean onErrorResponse(Subject subject, WfTask taskStub, HttpURLConnection connection, Interaction interaction) throws IOException,
            TaskHandlerException, InternalApplicationException, AuthorizationException, AuthenticationException, TaskDoesNotExistException {
        log.debug("Web service bot got error response with code " + connection.getResponseCode() + " for task " + taskStub.getId());
        if (interaction.responseVariable != null) {
            xsltHelper.get().setNewVariable(interaction.responseVariable, "Got error response: '" + connection.getResponseMessage() + "'");
        }
        ErrorResponseProcessingResult errorAction = (interaction.errorAction == null ? settings.errorAction : interaction.errorAction);
        if (errorAction == ErrorResponseProcessingResult.BREAK || errorAction == null) {
            throw new TaskHandlerException("Interaction with we service failed with behavior BREAK.");
        }
        if (errorAction == ErrorResponseProcessingResult.IGNORE) {
            return true;
        }
        saveExecutionState(subject, taskStub, interaction);
        return false;
    }

    /**
     * Saves current bot execution state.
     */
    private void saveExecutionState(Subject subject, WfTask task, Interaction interaction) throws AuthorizationException, AuthenticationException,
            TaskDoesNotExistException {
        ExecutionService executionService = DelegateFactory.getExecutionService();
        Map<String, Object> variables = new HashMap<String, Object>();
        xsltHelper.get().MergeVariablesIn(variables);
        variables.put("WS_ITERATION_" + task.getId(), settings.interactions.indexOf(interaction));
        executionService.updateVariables(subject, task.getProcessId(), variables);
    }

    /**
     * Called to process response from web service with code from [200; 299] (This codes indicates successful request processing).
     * 
     * @param taskStub
     *            Current task instance to be processed.
     * @param connection
     *            HTTP connection to communicate with web service.
     * @param interaction
     *            Current processing interaction.
     */
    private void onResponse(WfTask taskStub, HttpURLConnection connection, Interaction interaction) throws UnsupportedEncodingException, IOException,
            TransformerFactoryConfigurationError, TransformerException {
        if (interaction.responseXSLT == null && interaction.responseVariable == null) {
            return;
        }
        String response = logResponseAndSetVariable(taskStub, connection, interaction);
        InputStream inputStream = response == null ? connection.getInputStream() : new ByteArrayInputStream(response.getBytes(settings.encoding));
        if (interaction.responseXSLT != null) {
            Transformer transformer = TransformerFactory.newInstance().newTransformer(
                    new StreamSource(new ByteArrayInputStream(interaction.responseXSLT.getBytes(settings.encoding))));
            transformer.transform(new StreamSource(inputStream), new StreamResult(new NullOutputStream()));
        }
    }

    /**
     * Reads response from connection, log it and set it into response variable.
     * 
     * @param taskStub
     *            Current task instance to be processed.
     * @param connection
     *            HTTP connection to communicate with web service.
     * @param interaction
     *            Current processing interaction.
     * @return Response as string or null, if response wasn't read from stream.
     */
    private String logResponseAndSetVariable(WfTask taskStub, HttpURLConnection connection, Interaction interaction) throws IOException {
        if (interaction.responseVariable == null && (!settings.isLoggingEnable || !log.isDebugEnabled())) {
            return null;
        }
        String response = readStringFromStream(connection.getInputStream(), settings.encoding, interaction.maxResponseLength);
        if (settings.isLoggingEnable) {
            log.debug("Web service bot got response for task " + taskStub.getId() + ":\n" + response);
        }
        if (interaction.responseVariable != null) {
            xsltHelper.get().setNewVariable(interaction.responseVariable, response);
        }
        return response;
    }

    /**
     * Read string from input stream.
     * 
     * @param stream
     *            Stream with data, to read from.
     * @param encoding
     *            Characters encoding, used to convert bytes in stream to characters.
     * @param maxLength
     *            Maximum data length.
     * @return String, readed from stream.
     * @throws IllegalArgumentException
     *             if data length is exceeded maxLength value.
     */
    private String readStringFromStream(InputStream stream, String encoding, int maxLength) throws IOException {
        Writer stringWriter = new StringWriter(stream.available() > maxLength ? maxLength : stream.available());
        InputStreamReader inputStreamReader = new InputStreamReader(stream, encoding);
        Reader reader = new BufferedReader(inputStreamReader);
        try {
            char[] buffer = new char[4096];
            int totalReaded = 0;
            int readCount = 0;
            while (totalReaded != maxLength
                    && (readCount = reader.read(buffer, 0, maxLength - totalReaded > 4096 ? 4096 : maxLength - totalReaded)) != -1) {
                stringWriter.write(buffer, 0, readCount);
                totalReaded += readCount;
            }
            if (reader.read(buffer, 0, 1) != -1) {
                throw new IllegalArgumentException("WebServiceTaskHandler got to large response.");
            }
            return stringWriter.toString();
        } finally {
            reader.close();
            inputStreamReader.close();
        }
    }

    /**
     * Returns index of interaction, to start execution from.
     * 
     * @param subject
     *            Current bot subject.
     * @param taskStub
     *            Current task instance to be processed.
     * @return index of interaction.
     */
    private int getStartInteraction(Subject subject, WfTask taskStub) throws TaskDoesNotExistException, AuthorizationException {
        ExecutionService executionService = DelegateFactory.getExecutionService();
        WfVariable variable = executionService.getVariable(subject, taskStub.getProcessId(), "WS_ITERATION_" + taskStub.getId());
        return TypeConversionUtil.convertTo(variable.getValue(), int.class);
    }
}
