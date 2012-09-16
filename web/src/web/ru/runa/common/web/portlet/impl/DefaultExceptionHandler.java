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
package ru.runa.common.web.portlet.impl;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;

import ru.runa.common.web.ActionExceptionHelper;
import ru.runa.common.web.portlet.PortletExceptionHandler;

public class DefaultExceptionHandler implements PortletExceptionHandler {
    public boolean processError(Exception exception, ServletContext servletContext, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        if (exception instanceof ru.runa.af.AuthenticationException) {
            ActionExceptionHelper.addException(getActionErrors(request), exception);
            servletContext.getRequestDispatcher("/start.do").forward(request, response);
            return true;
        }

        if (exception instanceof ru.runa.af.AuthorizationException) {
            ActionExceptionHelper.addException(getActionErrors(request), exception);
            servletContext.getRequestDispatcher("/error.do").forward(request, response);
            return true;
        }

        if (exception instanceof ru.runa.InternalApplicationException) {
            ActionExceptionHelper.addException(getActionErrors(request), exception);
            servletContext.getRequestDispatcher("/error.do").forward(request, response);
            return true;
        }

        if (exception instanceof java.lang.NullPointerException) {
            ActionExceptionHelper.addException(getActionErrors(request), exception);
            servletContext.getRequestDispatcher("/error.do").forward(request, response);
            return true;
        }

        if (exception instanceof ru.runa.common.web.InvalidSessionException) {
            ActionExceptionHelper.addException(getActionErrors(request), exception);
            servletContext.getRequestDispatcher("/start.do").forward(request, response);
            return true;
        }

        if (exception instanceof ru.runa.af.ExecutorOutOfDateException) {
            ActionExceptionHelper.addException(getActionErrors(request), exception);
            servletContext.getRequestDispatcher("/manage_executors.do").forward(request, response);
            return true;
        }

        if (exception instanceof ru.runa.wf.ProcessDefinitionDoesNotExistException) {
            ActionExceptionHelper.addException(getActionErrors(request), exception);
            servletContext.getRequestDispatcher("/manage_process_definitions.do").forward(request, response);
            return true;
        }

        if (exception instanceof ru.runa.wf.ProcessInstanceDoesNotExistException) {
            ActionExceptionHelper.addException(getActionErrors(request), exception);
            servletContext.getRequestDispatcher("/manage_process_instances.do").forward(request, response);
            return true;
        }

        if (exception instanceof ru.runa.wf.TaskDoesNotExistException) {
            ActionExceptionHelper.addException(getActionErrors(request), exception);
            servletContext.getRequestDispatcher("/manage_tasks.do").forward(request, response);
            return true;
        }

        if (exception instanceof java.lang.RuntimeException) {
            ActionExceptionHelper.addException(getActionErrors(request), exception);
            servletContext.getRequestDispatcher("/error.do").forward(request, response);
            return true;
        }

        return false;
    }

    private static ActionErrors getActionErrors(HttpServletRequest request) {
        ActionErrors messages = (ActionErrors) request.getAttribute(Globals.ERROR_KEY);
        if (messages == null) {
            messages = new ActionErrors();
            request.setAttribute(Globals.ERROR_KEY, messages);
        }
        return messages;
    }
}
