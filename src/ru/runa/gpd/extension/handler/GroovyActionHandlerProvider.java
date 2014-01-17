package ru.runa.gpd.extension.handler;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.HyperlinkGroup;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;

import ru.runa.gpd.Localization;
import ru.runa.gpd.extension.DelegableConfigurationDialog;
import ru.runa.gpd.extension.DelegableProvider;
import ru.runa.gpd.extension.HandlerArtifact;
import ru.runa.gpd.lang.model.Delegable;
import ru.runa.gpd.lang.model.GraphElement;
import ru.runa.gpd.lang.model.ProcessDefinition;
import ru.runa.gpd.lang.model.Variable;
import ru.runa.gpd.ui.custom.JavaHighlightTextStyling;
import ru.runa.gpd.ui.dialog.ChooseVariableDialog;

import com.google.common.collect.Lists;

public class GroovyActionHandlerProvider extends DelegableProvider {
    @Override
    protected DelegableConfigurationDialog createConfigurationDialog(Delegable delegable) {
        if (!HandlerArtifact.ACTION.equals(delegable.getDelegationType())) {
            throw new IllegalArgumentException("For action handler only");
        }
        ProcessDefinition definition = ((GraphElement) delegable).getProcessDefinition();
        return new ConfigurationDialog(delegable.getDelegationConfiguration(), definition.getVariables(true, true));
    }

    private static class ConfigurationDialog extends DelegableConfigurationDialog {
        private final List<String> variableNames = Lists.newArrayList();
        private HyperlinkGroup hyperlinkGroup = new HyperlinkGroup(Display.getCurrent());

        public ConfigurationDialog(String initialValue, List<Variable> variables) {
            super(initialValue);
            for (Variable variable : variables) {
                this.variableNames.add(variable.getScriptingName());
            }
        }

        @Override
        protected void createDialogHeader(Composite parent) {
            Composite composite = new Composite(parent, SWT.NONE);
            composite.setLayout(new GridLayout(2, false));
            composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            Hyperlink hl3 = new Hyperlink(composite, SWT.NONE);
            hl3.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.HORIZONTAL_ALIGN_END));
            hl3.setText(Localization.getString("button.insert_variable"));
            hl3.addHyperlinkListener(new HyperlinkAdapter() {
                @Override
                public void linkActivated(HyperlinkEvent e) {
                    ChooseVariableDialog dialog = new ChooseVariableDialog(variableNames);
                    String variableName = dialog.openDialog();
                    if (variableName != null) {
                        styledText.insert(variableName);
                        styledText.setFocus();
                        styledText.setCaretOffset(styledText.getCaretOffset() + variableName.length());
                    }
                }
            });
            hyperlinkGroup.add(hl3);
        }

        @Override
        protected void createDialogFooter(Composite composite) {
            styledText.addLineStyleListener(new JavaHighlightTextStyling(variableNames));
        }
    }
}