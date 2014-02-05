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
package ru.runa.wfe.service.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import ru.runa.wfe.audit.ReceiveMessageLog;
import ru.runa.wfe.commons.JMSUtil;
import ru.runa.wfe.commons.SystemProperties;
import ru.runa.wfe.commons.TypeConversionUtil;
import ru.runa.wfe.commons.ftl.ExpressionEvaluator;
import ru.runa.wfe.definition.dao.IProcessDefinitionLoader;
import ru.runa.wfe.execution.ExecutionContext;
import ru.runa.wfe.execution.Token;
import ru.runa.wfe.execution.dao.TokenDAO;
import ru.runa.wfe.lang.NodeType;
import ru.runa.wfe.lang.ProcessDefinition;
import ru.runa.wfe.lang.ReceiveMessage;
import ru.runa.wfe.service.interceptors.EjbExceptionSupport;
import ru.runa.wfe.service.interceptors.EjbTransactionSupport;
import ru.runa.wfe.service.interceptors.PerformanceObserver;
import ru.runa.wfe.var.VariableMapping;

import com.google.common.base.Objects;

@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/bpmMessages"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "useDLQ", propertyValue = "false") })
@TransactionManagement(TransactionManagementType.BEAN)
@Interceptors({ EjbExceptionSupport.class, PerformanceObserver.class, EjbTransactionSupport.class, SpringBeanAutowiringInterceptor.class })
@SuppressWarnings("unchecked")
public class ReceiveMessageBean implements MessageListener {
    private static Log log = LogFactory.getLog(ReceiveMessageBean.class);
    @Autowired
    private TokenDAO tokenDAO;
    @Autowired
    private IProcessDefinitionLoader processDefinitionLoader;

    @Override
    public void onMessage(Message message) {
        log.debug("Received " + message);
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            boolean sequenceMessages = SystemProperties.isReceiveMessageSequenceModeEnabled();
            if (sequenceMessages) {
                synchronized (ReceiveMessageBean.class) {
                    handleMessage(objectMessage);
                }
            } else {
                handleMessage(objectMessage);
            }
        } catch (JMSException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }

    private void handleMessage(ObjectMessage message) throws JMSException {
        String messageString = JMSUtil.toString(message, false);
        log.info("Handling " + messageString);
        boolean handled = false;
        List<Token> tokens = tokenDAO.findActiveTokens(NodeType.RECEIVE_MESSAGE);
        for (Token token : tokens) {
            ProcessDefinition processDefinition = processDefinitionLoader.getDefinition(token.getProcess().getDeployment().getId());
            ReceiveMessage receiveMessage = (ReceiveMessage) token.getNodeNotNull(processDefinition);
            ExecutionContext executionContext = new ExecutionContext(processDefinition, token);
            boolean suitable = true;
            for (VariableMapping mapping : receiveMessage.getVariableMappings()) {
                if (mapping.isPropertySelector()) {
                    String selectorValue = message.getStringProperty(mapping.getName());
                    String testValue = mapping.getMappedName();
                    String expectedValue;
                    if ("${currentProcessId}".equals(testValue) || "${currentInstanceId}".equals(testValue)) {
                        expectedValue = String.valueOf(token.getProcess().getId());
                    } else if ("${currentDefinitionName}".equals(testValue)) {
                        expectedValue = token.getProcess().getDeployment().getName();
                    } else if ("${currentNodeName}".equals(testValue)) {
                        expectedValue = receiveMessage.getName();
                    } else if ("${currentNodeId}".equals(testValue)) {
                        expectedValue = receiveMessage.getNodeId();
                    } else {
                        Object value = ExpressionEvaluator.evaluateVariable(executionContext.getVariableProvider(), testValue);
                        expectedValue = TypeConversionUtil.convertTo(String.class, value);
                    }
                    if (!Objects.equal(expectedValue, selectorValue)) {
                        log.debug(message + " rejected in " + token + " due to diff in " + mapping.getName() + " (" + expectedValue + "!="
                                + selectorValue + ")");
                        suitable = false;
                        break;
                    }
                }
            }
            if (suitable) {
                log.debug(message + " accepted in " + token + " for " + receiveMessage);
                executionContext.addLog(new ReceiveMessageLog(receiveMessage, JMSUtil.toString(message, true)));
                Map<String, Object> map = (Map<String, Object>) message.getObject();
                for (VariableMapping variableMapping : receiveMessage.getVariableMappings()) {
                    if (!variableMapping.isPropertySelector()) {
                        if (map.containsKey(variableMapping.getMappedName())) {
                            Object value = map.get(variableMapping.getMappedName());
                            executionContext.setVariableValue(variableMapping.getName(), value);
                        } else {
                            log.warn("Message does not contain value for '" + variableMapping.getMappedName() + "'");
                        }
                    }
                }
                receiveMessage.leave(executionContext);
                handled = true;
            }
        }
        if (!handled) {
            throw new MessagePostponedException(messageString);
        }
    }
}
