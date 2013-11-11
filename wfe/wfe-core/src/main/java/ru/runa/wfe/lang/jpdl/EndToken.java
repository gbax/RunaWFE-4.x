package ru.runa.wfe.lang.jpdl;

import java.util.Set;

import ru.runa.wfe.execution.ExecutionContext;
import ru.runa.wfe.execution.Token;
import ru.runa.wfe.lang.Event;
import ru.runa.wfe.lang.Node;
import ru.runa.wfe.lang.NodeType;
import ru.runa.wfe.lang.Transition;

import com.google.common.collect.Sets;

public class EndToken extends Node {
    private static final long serialVersionUID = 1L;
    private static final String[] supportedEventTypes = new String[] { Event.EVENTTYPE_NODE_ENTER };

    @Override
    public String[] getSupportedEventTypes() {
        return supportedEventTypes;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.END_TOKEN;
    }

    @Override
    public Transition addLeavingTransition(Transition t) {
        throw new UnsupportedOperationException("can't add a leaving transition to " + this);
    }

    @Override
    public void execute(ExecutionContext executionContext) {
        executionContext.getToken().end(executionContext, null);
        if (!executionContext.getProcess().hasEnded() && executionContext.getProcess().getRootToken().hasEnded()) {
            executionContext.getProcess().end(executionContext, null);
        }
        // If this token was forked
        Token parentToken = executionContext.getToken().getParent();
        if (parentToken != null && parentToken.getNodeType() == NodeType.FORK && parentToken.getActiveChildren().size() == 0) {
            Set<Join> joins = Sets.newHashSet();
            for (Token childToken : parentToken.getChildren()) {
                if (childToken.getNodeType() == NodeType.JOIN) {
                    joins.add((Join) childToken.getNode(executionContext.getProcessDefinition()));
                }
            }
            for (Join join : joins) {
                join.execute(executionContext);
            }
        }
    }
}
