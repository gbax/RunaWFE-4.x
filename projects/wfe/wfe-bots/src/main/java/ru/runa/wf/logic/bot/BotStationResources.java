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

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.runa.wfe.bot.invoker.BotInvoker;
import ru.runa.wfe.commons.ClassLoaderUtil;
import ru.runa.wfe.commons.PropertyResources;

/**
 * Bot station configuration.
 *
 * @author dofs
 * @since 4.0
 */
public class BotStationResources {
    private static final Log log = LogFactory.getLog(BotStationResources.class);
    private static final PropertyResources RESOURCES = new PropertyResources("botstation.properties");

    public static BotLogger createBotLogger() {
        String loggerClassName = RESOURCES.getStringProperty("bot.logger.class");
        if (loggerClassName == null) {
            return null;
        }
        return (BotLogger) ClassLoaderUtil.instantiate(loggerClassName);
    }

    public static int getThreadPoolSize() {
        try {
            return RESOURCES.getIntegerProperty("thread.pool.size", 1);
        } catch (Exception e) {
            return 1;
        }
    }

    public static BotInvoker createBotInvoker() {
        return (BotInvoker) ClassLoaderUtil.instantiate(RESOURCES.getStringProperty("bot.invoker.class"));
    }

    public static int getFailedExecutionInitialDelay() {
        try {
            return RESOURCES.getIntegerProperty("botstation.failedExecutionInitialDelaySeconds", 30);
        } catch (Exception e) {
            return 30;
        }
    }

    public static int getFailedExecutionMaxDelay() {
        try {
            return RESOURCES.getIntegerProperty("botstation.failedExecutionMaxDelaySeconds", 7200);
        } catch (Exception e) {
            return 7200;
        }
    }

    public static String getSystemUsername() {
        return RESOURCES.getStringPropertyNotNull("botstation.system.username");
    }

    public static String getSystemPassword() {
        return RESOURCES.getStringPropertyNotNull("botstation.system.password");
    }

    public static long getBotInvocationPeriod() {
        long periodInSeconds = RESOURCES.getLongProperty("botstation.invocation.period.seconds", 30);
        if (periodInSeconds < 1) {
            log.warn("bot_ivoker.properies invocation.period is less than 1 sec. Invocation period was set to 30 sec.");
            periodInSeconds = 30;
        } else {
            log.info("Invocation period was set to " + periodInSeconds + " sec.");
        }
        return periodInSeconds * 1000;
    }

    public static List<String> getTaskHandlerJarNames() {
        return RESOURCES.getMultipleStringProperty("taskhandler.jar.names");
    }

    public static boolean isAutoStartBotStations() {
        return RESOURCES.getBooleanProperty("botstations.autostart.enabled", true);
    }

}
