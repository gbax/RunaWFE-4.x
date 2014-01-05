package ru.runa.gpd.ui.wizard;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import ru.runa.gpd.Localization;
import ru.runa.gpd.extension.VariableFormatArtifact;
import ru.runa.gpd.lang.model.Variable;
import ru.runa.gpd.ui.custom.DynaContentWizardPage;
import ru.runa.gpd.ui.custom.LoggingModifyTextAdapter;
import ru.runa.gpd.ui.custom.LoggingSelectionAdapter;
import ru.runa.wfe.commons.ClassLoaderUtil;
import ru.runa.wfe.commons.TypeConversionUtil;

import com.google.common.base.Strings;

public class VariableDefaultValuePage extends DynaContentWizardPage {
    private String defaultValue;
    private Button useDefaultValueButton;

    public VariableDefaultValuePage(Variable variable) {
        if (variable != null) {
            this.defaultValue = variable.getDefaultValue();
        }
    }

    @Override
    protected int getGridLayoutColumns() {
        return 1;
    }

    @Override
    protected void createContent(Composite composite) {
        dynaComposite = composite;
    }

    @Override
    protected void createDynaContent() {
        Button dontUseDefaultValueButton = new Button(dynaComposite, SWT.RADIO);
        dontUseDefaultValueButton.setText(Localization.getString("VariableDefaultValuePage.dontUse"));
        useDefaultValueButton = new Button(dynaComposite, SWT.RADIO);
        useDefaultValueButton.setText(Localization.getString("VariableDefaultValuePage.use"));
        final Text text = new Text(dynaComposite, SWT.BORDER);
        text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        if (defaultValue != null) {
            text.setText(defaultValue);
        }
        text.addModifyListener(new LoggingModifyTextAdapter() {
            @Override
            protected void onTextChanged(ModifyEvent e) throws Exception {
                defaultValue = text.getText();
                verifyContentIsValid();
            }
        });
        useDefaultValueButton.addSelectionListener(new LoggingSelectionAdapter() {
            @Override
            protected void onSelection(SelectionEvent e) throws Exception {
                text.setEditable(useDefaultValueButton.getSelection());
                if (!useDefaultValueButton.getSelection()) {
                    text.setText("");
                }
                verifyContentIsValid();
            }
        });
        if (Strings.isNullOrEmpty(defaultValue)) {
            dontUseDefaultValueButton.setSelection(true);
        } else {
            useDefaultValueButton.setSelection(true);
        }
        text.setEditable(useDefaultValueButton.getSelection());
    }

    @Override
    protected void verifyContentIsValid() {
        try {
            if (useDefaultValueButton != null && useDefaultValueButton.getSelection()) {
                VariableFormatPage formatPage = (VariableFormatPage) getWizard().getPage(VariableFormatPage.class.getSimpleName());
                if (formatPage.getUserType() != null) {
                    setErrorMessage(Localization.getString("VariableDefaultValuePage.error.userType"));
                    return;
                }
                TypeConversionUtil.convertTo(ClassLoaderUtil.loadClass(formatPage.getType().getJavaClassName()), defaultValue);
            }
            setErrorMessage(null);
        } catch (Exception e) {
            setErrorMessage(Localization.getString("VariableDefaultValuePage.error.conversion") + ": " + e.getMessage());
        }
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
