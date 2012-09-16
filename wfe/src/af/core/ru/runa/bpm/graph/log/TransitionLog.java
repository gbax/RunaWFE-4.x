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
package ru.runa.bpm.graph.log;

import ru.runa.bpm.graph.def.Node;
import ru.runa.bpm.graph.def.Transition;
import ru.runa.bpm.logging.log.CompositeLog;

public class TransitionLog extends CompositeLog {

    private static final long serialVersionUID = 1L;

    protected Transition transition = null;
    protected Node sourceNode = null;
    protected Node destinationNode = null;

    public TransitionLog() {
    }

    public TransitionLog(Transition transition, Node source) {
        this.transition = transition;
        this.sourceNode = source;
    }

    public Node getDestinationNode() {
        return destinationNode;
    }

    public void setDestinationNode(Node destination) {
        this.destinationNode = destination;
    }

    public Node getSourceNode() {
        return sourceNode;
    }

    public Transition getTransition() {
        return transition;
    }

    public String toString() {
        String from = (sourceNode != null ? sourceNode.getName() : "unnamed-node");
        String to = (destinationNode != null ? destinationNode.getName() : "unnamed-node");
        return "transition[" + from + "-->" + to + "]";
    }
}
