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
package ru.runa.wfe.commons;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.runa.wfe.commons.ftl.ExpressionEvaluator;
import ru.runa.wfe.var.IVariableProvider;
import ru.runa.wfe.var.VariableMapping;

import com.google.common.base.Throwables;

public class JMSUtil {
    private static Log log = LogFactory.getLog(JMSUtil.class);

    private static ConnectionFactory connectionFactory;
    private static Queue queue;

    private static synchronized void init() throws JMSException, NamingException {
        if (connectionFactory == null) {
            InitialContext iniCtx = new InitialContext();
            connectionFactory = (ConnectionFactory) iniCtx.lookup("java:/JmsXA");
            queue = (Queue) iniCtx.lookup("queue/bpmMessages");
        }
    }

    public static ObjectMessage sendMessage(List<VariableMapping> data, IVariableProvider variableProvider, long ttl) {
        Connection connection = null;
        Session session = null;
        MessageProducer sender = null;
        try {
            init();
            connection = connectionFactory.createConnection();
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
            sender = session.createProducer(queue);

            HashMap<String, Object> map = new HashMap<String, Object>();
            for (VariableMapping variableMapping : data) {
                if (!variableMapping.isPropertySelector()) {
                    map.put(variableMapping.getMappedName(), variableProvider.getValue(variableMapping.getName()));
                }
            }
            ObjectMessage message = session.createObjectMessage(map);
            for (VariableMapping variableMapping : data) {
                if (variableMapping.isPropertySelector()) {
                    Object value = ExpressionEvaluator.evaluateVariableNotNull(variableProvider, variableMapping.getMappedName());
                    String stringValue = TypeConversionUtil.convertTo(String.class, value);
                    message.setStringProperty(variableMapping.getName(), stringValue);
                }
            }
            sender.send(message, Message.DEFAULT_DELIVERY_MODE, Message.DEFAULT_PRIORITY, ttl);
            sender.close();
            log.info("message sent: " + JMSUtil.toString(message, false));
            return message;
        } catch (Exception e) {
            throw Throwables.propagate(e);
        } finally {
            if (sender != null) {
                try {
                    sender.close();
                } catch (Exception ignore) {
                }
            }
            if (session != null) {
                try {
                    session.close();
                } catch (Exception ignore) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignore) {
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static String toString(ObjectMessage message, boolean html) {
        try {
            StringBuffer buffer = new StringBuffer();
            buffer.append(message.toString());
            buffer.append(html ? "<br>" : "\n");
            if (message.getJMSExpiration() != 0) {
                buffer.append("{JMSExpiration=").append(CalendarUtil.formatDateTime(new Date(message.getJMSExpiration()))).append("}");
                buffer.append(html ? "<br>" : "\n");
            }
            Enumeration<String> propertyNames = message.getPropertyNames();
            Map<String, String> properties = new HashMap<String, String>();
            while (propertyNames.hasMoreElements()) {
                String propertyName = propertyNames.nextElement();
                String propertyValue = message.getStringProperty(propertyName);
                properties.put(propertyName, propertyValue);
            }
            buffer.append(properties);
            buffer.append(html ? "<br>" : "\n");
            if (message.getObject() instanceof Map) {
                buffer.append(TypeConversionUtil.toStringMap((Map<? extends Object, ? extends Object>) message.getObject()));
            } else {
                buffer.append(message.getObject());
            }
            return buffer.toString();
        } catch (JMSException e) {
            throw Throwables.propagate(e);
        }
    }

}
