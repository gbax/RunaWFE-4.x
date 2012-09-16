package ru.runa.bpm.ui.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import ru.runa.bpm.ui.DesignerLogger;
import ru.runa.bpm.ui.common.model.ProcessDefinition;
import ru.runa.bpm.ui.common.model.Swimlane;
import ru.runa.bpm.ui.orgfunctions.ISwimlaneElementListener;
import ru.runa.bpm.ui.orgfunctions.OrgFunctionDefinition;
import ru.runa.bpm.ui.orgfunctions.OrgFunctionsRegistry;
import ru.runa.bpm.ui.orgfunctions.SwimlaneElement;
import ru.runa.bpm.ui.orgfunctions.SwimlaneElementRegistry;
import ru.runa.bpm.ui.resource.Messages;

public class SwimlaneConfigDialog extends Dialog implements ISwimlaneElementListener{
    private List<SwimlaneElement> swimlaneElements = SwimlaneElementRegistry.getSwimlaneElements();
    private CTabFolder typeTabFolder;
    private CTabFolder orgFunctionsTabFolder;

    private final Swimlane swimlane;
    private String configuration;
    private String path;
    private boolean publicVisibility;

    public SwimlaneConfigDialog(ProcessDefinition definition, Swimlane swimlane, String path) {
        super(Display.getCurrent().getActiveShell());
        setShellStyle(getShellStyle() | SWT.RESIZE);
        this.swimlane = swimlane;
        this.configuration = swimlane.getDelegationConfiguration();
        this.publicVisibility = swimlane.isPublicVisibility();
        this.path = path;
        for (SwimlaneElement swimlaneElement : swimlaneElements) {
            swimlaneElement.setProcessDefinition(definition);
        }
    }

    @Override
    protected Point getInitialSize() {
        return new Point(750, 500);
    }

    public void completed(String path, OrgFunctionDefinition definition) {
        this.configuration = definition.createSwimlaneConfiguration();
        this.path = path;
    }

    public void opened(String path) {
        try {
            for (SwimlaneElement swimlaneElement : swimlaneElements) {
                if (path.startsWith(swimlaneElement.getName())) {
                    int index = swimlaneElements.indexOf(swimlaneElement);
                    if (index > 1) {
                        orgFunctionsTabFolder.setSelection(index-1);
                    }
                    OrgFunctionDefinition definition = OrgFunctionsRegistry.parseSwimlaneConfiguration(configuration);
                    swimlaneElement.open(path, swimlane.getName(), definition);
                } else {
                    swimlaneElement.close();
                }
            }
        } catch (Exception e) {
        	DesignerLogger.logError(e);
        }
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        getShell().setText(Messages.getString("SwimlaneConfigDialog.title"));

        typeTabFolder = new CTabFolder(parent, SWT.BOTTOM | SWT.BORDER);
        typeTabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));

        Composite composite1 = new Composite(typeTabFolder, SWT.NONE);
        composite1.setLayout(new GridLayout());
        CTabItem tabItem1 = new CTabItem(typeTabFolder, SWT.NONE);
        tabItem1.setText(Messages.getString("tab.constructor.relation"));
        tabItem1.setControl(composite1);
        
        {
            SwimlaneElement swimlaneElement = swimlaneElements.get(0);
            Composite composite = new Composite(composite1, SWT.NONE);
            composite.setLayout(new GridLayout());
            swimlaneElement.createGUI(composite);
            swimlaneElement.addElementListener(this);
            for (SwimlaneElement childElement : swimlaneElement.getChildren()) {
                childElement.createGUI(swimlaneElement.getClientArea());
            }
        }
        
        Composite composite2 = new Composite(typeTabFolder, SWT.NONE);
        composite2.setLayout(new GridLayout());
        CTabItem tabItem2 = new CTabItem(typeTabFolder, SWT.NONE);
        tabItem2.setText(Messages.getString("tab.constructor.orgfunction"));
        tabItem2.setControl(composite2);

        orgFunctionsTabFolder = new CTabFolder(composite2, SWT.TOP | SWT.BORDER);
        orgFunctionsTabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));

        for (int i=1; i<swimlaneElements.size(); i++) {
            SwimlaneElement swimlaneElement = swimlaneElements.get(i);
            Composite composite = new Composite(orgFunctionsTabFolder, SWT.NONE);
            composite.setLayout(new GridLayout());
            swimlaneElement.createGUI(composite);
            swimlaneElement.addElementListener(this);
            for (SwimlaneElement childElement : swimlaneElement.getChildren()) {
                childElement.createGUI(swimlaneElement.getClientArea());
            }

            CTabItem tabItem = new CTabItem(orgFunctionsTabFolder, SWT.NONE);
            tabItem.setText(swimlaneElement.getDisplayName());
            tabItem.setControl(composite);
        }

        boolean rel = configuration != null && configuration.indexOf("@") == 0;
        typeTabFolder.setSelection(rel ? 0 : 1);
        
        typeTabFolder.addSelectionListener(new TypeTabSelectionHandler());
        orgFunctionsTabFolder.addSelectionListener(new TabSelectionHandler());
        
        return orgFunctionsTabFolder;
    }
    
    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        ((GridLayout) parent.getLayout()).numColumns++;
        ((GridLayout) parent.getLayout()).makeColumnsEqualWidth = false;
        final Button publicVisibilityCheckbox = new Button(parent, SWT.CHECK);
        publicVisibilityCheckbox.setSelection(publicVisibility);
        publicVisibilityCheckbox.setText(Messages.getString("Variable.property.publicVisibility"));
        publicVisibilityCheckbox.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                publicVisibility = publicVisibilityCheckbox.getSelection(); 
            }
            
        });
        setButtonLayoutData(publicVisibilityCheckbox);
        
        super.createButtonsForButtonBar(parent);
    }
    
    @Override
    protected void initializeBounds() {
        super.initializeBounds();
        opened(path);
    }

    public String getConfiguration() {
        return configuration;
    }

    public boolean isPublicVisibility() {
        return publicVisibility;
    }
    
    public String getPath() {
        return path;
    }
    
    private class TypeTabSelectionHandler extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent e) {
            if (typeTabFolder.getSelectionIndex() == 1 && orgFunctionsTabFolder.getSelection() == null) {
                orgFunctionsTabFolder.setSelection(0);
            }
        }

    }

    private class TabSelectionHandler extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent e) {
            String path = swimlaneElements.get(orgFunctionsTabFolder.getSelectionIndex()+1).getName();
            opened(path);
        }

    }

}
