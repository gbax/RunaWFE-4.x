package ru.runa.gpd;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import ru.runa.gpd.ui.custom.Dialogs;

public class PluginLogger {

    public static void logInfo(String message) {
        log(createStatus(IStatus.INFO, IStatus.OK, message, null));
    }

    public static void logError(Throwable exception) {
        StringBuffer messageBuffer = new StringBuffer();
        messageBuffer.append(exception.getMessage());
        messageBuffer.append(" (").append(exception.getClass().getName()).append(")");
        logError(messageBuffer.toString(), exception);
    }

    public static void logError(String message, Throwable exception) {
        logErrorWithoutDialog(message, exception);
        if (exception instanceof CoreException) {
            IStatus status = ((CoreException) exception).getStatus();
            // org.eclipse.core.resources
            if ("org.eclipse.core.filesystem".equals(status.getPlugin())) {
                message += "\n" + Localization.getString("error.org.eclipse.core.filesystem");
            }
        }
        Dialogs.error(message, exception);
    }

    public static void logErrorWithoutDialog(String message, Throwable exception) {
        log(createStatus(IStatus.ERROR, IStatus.OK, message, exception));
    }

    private static IStatus createStatus(int severity, int code, String message, Throwable exception) {
        return new Status(severity, Activator.getDefault().getBundle().getSymbolicName(), code, message, exception);
    }

    private static void log(IStatus status) {
        Activator.getDefault().getLog().log(status);
    }

    public static IStatus createStatus(Throwable exception) {
        return createStatus(IStatus.ERROR, IStatus.OK, exception.getMessage(), exception);
    }

}
