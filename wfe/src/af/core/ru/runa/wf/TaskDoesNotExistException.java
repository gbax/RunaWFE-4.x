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
package ru.runa.wf;

import ru.runa.InternalApplicationException;

/**
 * Signals that instance does not exists in system.
 * 
 */
public class TaskDoesNotExistException extends InternalApplicationException {

    private static final long serialVersionUID = 3874401594823589089L;
    private final String name;

    public TaskDoesNotExistException(String name) {
        super("Task " + name + " does not exists.");
        this.name = name;
    }

    public TaskDoesNotExistException(Long id) {
        this("with id = " + String.valueOf(id));
    }

    public String getName() {
        return name;
    }
}
