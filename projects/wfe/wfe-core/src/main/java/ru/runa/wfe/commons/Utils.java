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
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.runa.wfe.InternalApplicationException;
import ru.runa.wfe.commons.ftl.ExpressionEvaluator;
import ru.runa.wfe.var.IVariableProvider;
import ru.runa.wfe.var.VariableMapping;

import com.google.common.base.Throwables;

public class Utils {
    private static Log log = LogFactory.getLog(Utils.class);
    private static InitialContext initialContext;
    private static ConnectionFactory connectionFactory;
    private static Queue queue;

    private static InitialContext getInitialContext() throws NamingException {
        if (initialContext == null) {
            initialContext = new InitialContext();
        }
        return initialContext;
    }

    public static synchronized UserTransaction getUserTransaction() {
        String jndiName = DatabaseProperties.getUserTransactionJndiName();
        try {
            return (UserTransaction) getInitialContext().lookup(jndiName);
        } catch (NamingException e) {
            throw new InternalApplicationException("Unable to find UserTransaction by name '" + jndiName, e);
        }
    }

    private static synchronized void init() throws JMSException, NamingException {
        if (connectionFactory == null) {
            String connectionFactoryJndiName = SystemProperties.getResources().getStringProperty("jndi.jms.connection.factory", "java:/JmsXA");
            try {
                connectionFactory = (ConnectionFactory) getInitialContext().lookup(connectionFactoryJndiName);
            } catch (Exception e) {
                throw new InternalApplicationException("Unable to find JMS ConnectionFactory by name '" + connectionFactoryJndiName, e);
            }
            queue = (Queue) getInitialContext().lookup("queue/bpmMessages");
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
            log.info("message sent: " + toString(message, false));
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

    public static void rollbackTransaction(UserTransaction transaction) {
        int status = -1;
        try {
            if (transaction != null) {
                status = transaction.getStatus();
                if (status != Status.STATUS_NO_TRANSACTION && status != Status.STATUS_ROLLEDBACK) {
                    transaction.rollback();
                } else {
                    LogFactory.getLog(Utils.class).warn("Unable to rollback, status: " + status);
                }
            }
        } catch (Exception e) {
            throw new InternalApplicationException("Unable to rollback, status: " + status, e);
        }
    }

}
