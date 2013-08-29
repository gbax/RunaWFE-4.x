package ru.runa.gpd.swimlane;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;

import ru.runa.gpd.Localization;
import ru.runa.gpd.ui.custom.LoggingHyperlinkAdapter;
import ru.runa.gpd.ui.dialog.ChooseItemDialog;
import ru.runa.gpd.wfe.WFEServerExecutorsImporter;
import ru.runa.wfe.extension.orgfunction.ExecutorByNameFunction;

public class ExecutorSwimlaneElement extends OrgFunctionSwimlaneElement {
    private int mask;
    private Text selectionText;

    public ExecutorSwimlaneElement() {
        super(ExecutorByNameFunction.class.getName());
    }

    public void setMask(String maskString) {
        this.mask = Integer.parseInt(maskString);
    }

    @Override
    public void createGUI(Composite parent) {
        Composite clientArea = createSection(parent, 2);
        selectionText = new Text(clientArea, SWT.READ_ONLY | SWT.BORDER);
        selectionText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        Hyperlink h1 = createLink(clientArea, Localization.getString("button.choose"));
        h1.addHyperlinkListener(new LoggingHyperlinkAdapter() {
            @Override
            protected void onLinkActivated(HyperlinkEvent e) throws Exception {
                List<String> items = new ArrayList<String>();
                Map<String, Boolean> executors = WFEServerExecutorsImporter.getInstance().loadCachedData();
                for (String name : executors.keySet()) {
                    boolean isGroup = executors.get(name);
                    if (isGroup && (mask & 2) != 0) {
                        items.add(name);
                    }
                    if (!isGroup && (mask & 1) != 0) {
                        items.add(name);
                    }
                }
                ChooseItemDialog dialog = new ChooseItemDialog(Localization.getString("WFDialog.Text"), null, true);
                dialog.setItems(items);
                //dialog.setLabelProvider(new LabelProvider());
                if (dialog.open() == IDialogConstants.OK_ID) {
                    selectionText.setText((String) dialog.getSelectedItem());
                    setOrgFunctionParameterValue(0, selectionText.getText());
                    fireCompletedEvent();
                }
            }
        });
    }

    @Override
    public void open(String path, String swimlaneName, OrgFunctionSwimlaneInitializer swimlaneInitializer) {
        super.open(path, swimlaneName, swimlaneInitializer);
        selectionText.setText(getOrgFunctionParameterValue(0));
    }
}
