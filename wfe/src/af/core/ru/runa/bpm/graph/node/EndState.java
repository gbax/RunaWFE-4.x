/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package ru.runa.bpm.graph.node;

import org.dom4j.Element;

import ru.runa.bpm.graph.def.Event;
import ru.runa.bpm.graph.def.ExecutableProcessDefinition;
import ru.runa.bpm.graph.def.Node;
import ru.runa.bpm.graph.def.Transition;
import ru.runa.bpm.graph.exe.ExecutionContext;
import ru.runa.bpm.jpdl.xml.JpdlXmlReader;

public class EndState extends Node {
    private static final long serialVersionUID = 1L;

    private boolean endCompleteProcess;

    public static final String[] supportedEventTypes = new String[] { Event.EVENTTYPE_NODE_ENTER };

    @Override
    public String[] getSupportedEventTypes() {
        return supportedEventTypes;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.EndState;
    }

    @Override
    public void read(ExecutableProcessDefinition processDefinition, Element nodeElement, JpdlXmlReader jpdlXmlReader) {
        endCompleteProcess = Boolean.valueOf(nodeElement.attributeValue("end-complete-process", "false"));
    }

    @Override
    public void execute(ExecutionContext executionContext) {
        if (endCompleteProcess) {
            executionContext.getProcessInstance().end(executionContext);
        } else {
            executionContext.getToken().end(executionContext);
        }
    }

    @Override
    public Transition addLeavingTransition(Transition t) {
        throw new UnsupportedOperationException("can't add a leaving transition to an end-state");
    }
}
