package ru.runa.gpd.extension.decision;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.window.Window;

import ru.runa.gpd.PluginLogger;
import ru.runa.gpd.extension.DelegableProvider;
import ru.runa.gpd.extension.HandlerArtifact;
import ru.runa.gpd.extension.decision.GroovyDecisionModel.IfExpr;
import ru.runa.gpd.lang.ValidationError;
import ru.runa.gpd.lang.model.Decision;
import ru.runa.gpd.lang.model.Delegable;
import ru.runa.gpd.lang.model.GraphElement;
import ru.runa.gpd.lang.model.ProcessDefinition;
import ru.runa.gpd.lang.model.Transition;
import ru.runa.gpd.lang.model.Variable;

import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

public class GroovyDecisionProvider extends DelegableProvider implements IDecisionProvider {
    @Override
    public String showConfigurationDialog(Delegable delegable) {
        if (!HandlerArtifact.DECISION.equals(delegable.getDelegationType())) {
            throw new IllegalArgumentException("For decision handler only");
        }
        ProcessDefinition definition = ((GraphElement) delegable).getProcessDefinition();
        List<Transition> transitions = ((Decision) delegable).getLeavingTransitions();
        List<String> transitionNames = new ArrayList<String>();
        for (Transition transition : transitions) {
            transitionNames.add(transition.getName());
        }
        GroovyEditorDialog dialog = new GroovyEditorDialog(definition, transitionNames, delegable.getDelegationConfiguration());
        if (dialog.open() == Window.OK) {
            return dialog.getResult();
        }
        return null;
    }

    @Override
    public boolean validateValue(Delegable delegable, List<ValidationError> errors) {
        String configuration = delegable.getDelegationConfiguration();
        if (configuration.trim().length() == 0) {
            errors.add(ValidationError.createLocalizedError((GraphElement) delegable, "delegable.invalidConfiguration.empty"));
        } else {
            try {
                Binding binding = new Binding();
                GroovyShell shell = new GroovyShell(binding);
                shell.parse(configuration);
            } catch (Exception e) {
                errors.add(ValidationError.createLocalizedError((GraphElement) delegable, "delegable.invalidConfigurationWithError", e));
                PluginLogger.logErrorWithoutDialog("Script parse error: " + configuration, e);
            }
        }
        return true;
    }

    @Override
    public Set<String> getTransitionNames(Decision decision) {
        try {
            return GroovyDecisionModel.getTransitionNames(decision.getDelegationConfiguration());
        } catch (Exception e) {
            PluginLogger.logErrorWithoutDialog("getTransitionNames", e);
            return null;
        }
    }

    @Override
    public String getDefaultTransitionName(Decision decision) {
        try {
            List<Variable> variables = decision.getProcessDefinition().getVariables(true, true);
            GroovyDecisionModel model = new GroovyDecisionModel(decision.getDelegationConfiguration(), variables);
            return model.getDefaultTransitionName();
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public void transitionRenamed(Decision decision, String oldName, String newName) {
        String conf = decision.getDelegationConfiguration();
        conf = conf.replaceAll(Pattern.quote("\"" + oldName + "\""), Matcher.quoteReplacement("\"" + newName + "\""));
        decision.setDelegationConfiguration(conf);
    }

    @Override
    public List<String> getUsedVariableNames(Delegable delegable) throws Exception {
        List<Variable> variables = ((GraphElement) delegable).getProcessDefinition().getVariables(true, true);
        GroovyDecisionModel model = new GroovyDecisionModel(delegable.getDelegationConfiguration(), variables);
        List<String> result = Lists.newArrayList();
        for (IfExpr expr : model.getIfExprs()) {
            if (expr.getVariable1() != null) {
                result.add(expr.getVariable1().getName());
            }
            if (expr.getLexem2() instanceof Variable) {
                result.add(((Variable) expr.getLexem2()).getName());
            }
        }
        return result;
    }

    @Override
    public String getConfigurationOnVariableRename(Delegable delegable, Variable currentVariable, Variable previewVariable) {
        try {
            List<Variable> variables = ((GraphElement) delegable).getProcessDefinition().getVariables(true, true);
            GroovyDecisionModel model = new GroovyDecisionModel(delegable.getDelegationConfiguration(), variables);
            for (IfExpr expr : model.getIfExprs()) {
                if (Objects.equal(expr.getVariable1(), currentVariable)) {
                    expr.setVariable1(previewVariable);
                }
                if (Objects.equal(expr.getLexem2(), currentVariable)) {
                    expr.setLexem2(previewVariable);
                }
            }
            return model.toString();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

}
