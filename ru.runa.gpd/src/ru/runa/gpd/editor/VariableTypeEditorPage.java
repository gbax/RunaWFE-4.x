package ru.runa.gpd.editor;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import ru.runa.gpd.Localization;
import ru.runa.gpd.lang.model.FormNode;
import ru.runa.gpd.lang.model.PropertyNames;
import ru.runa.gpd.lang.model.Variable;
import ru.runa.gpd.lang.model.VariableUserType;
import ru.runa.gpd.lang.par.ParContentProvider;
import ru.runa.gpd.ui.custom.LoggingSelectionAdapter;
import ru.runa.gpd.ui.custom.LoggingSelectionChangedAdapter;
import ru.runa.gpd.ui.dialog.VariableUserTypeDialog;
import ru.runa.gpd.ui.wizard.CompactWizardDialog;
import ru.runa.gpd.ui.wizard.VariableWizard;

public class VariableTypeEditorPage extends EditorPartBase {
    private TableViewer typeTableViewer;
    private Button renameTypeButton;
    private Button deleteTypeButton;
    private TableViewer attributeTableViewer;
    private Button createAttributeButton;
    private Button changeAttributeButton;
    private Button deleteAttributeButton;

    public VariableTypeEditorPage(ProcessEditorBase editor) {
        super(editor);
    }

    @Override
    public void setFocus() {
        super.setFocus();
        updateButtons();
    }

    @Override
    public void createPartControl(Composite parent) {
        SashForm sashForm = createSashForm(parent, SWT.HORIZONTAL, "VariableUserType.collection.desc");

        Composite leftComposite = createSection(sashForm, "VariableUserType.collection");
        typeTableViewer = new TableViewer(leftComposite, SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
        getToolkit().adapt(typeTableViewer.getControl(), false, false);
        typeTableViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
        typeTableViewer.setLabelProvider(new TypeLabelProvider());
        typeTableViewer.setContentProvider(new ArrayContentProvider());
        createContextMenu(typeTableViewer.getControl());
        getSite().setSelectionProvider(typeTableViewer);
        Table typesTable = typeTableViewer.getTable();
        typesTable.setHeaderVisible(true);
        typesTable.setLinesVisible(true);
        TableColumn nameColumn = new TableColumn(typesTable, SWT.LEFT);
        nameColumn.setText(Localization.getString("property.name"));
        nameColumn.setWidth(200);
        typeTableViewer.addSelectionChangedListener(new LoggingSelectionChangedAdapter() {
            @Override
            protected void onSelectionChanged(SelectionChangedEvent event) throws Exception {
                updateButtons();
                updateAttributeViewer();
            }
        });

        Composite typeButtonsBar = getToolkit().createComposite(leftComposite);
        typeButtonsBar.setLayout(new GridLayout(1, false));
        GridData gridData = new GridData();
        gridData.horizontalAlignment = SWT.LEFT;
        gridData.verticalAlignment = SWT.TOP;
        typeButtonsBar.setLayoutData(gridData);
        addButton(typeButtonsBar, "button.create", new CreateTypeSelectionListener(), false);
        renameTypeButton = addButton(typeButtonsBar, "button.rename", new RenameTypeSelectionListener(), true);
        deleteTypeButton = addButton(typeButtonsBar, "button.delete", new RemoveTypeSelectionListener(), true);

        Composite rightComposite = createSection(sashForm, "VariableUserType.attributes");
        rightComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
        attributeTableViewer = new TableViewer(rightComposite, SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
        getToolkit().adapt(attributeTableViewer.getControl(), false, false);
        attributeTableViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
        attributeTableViewer.setLabelProvider(new AttributeLabelProvider());
        attributeTableViewer.setContentProvider(new ArrayContentProvider());
        createContextMenu(attributeTableViewer.getControl());
        getSite().setSelectionProvider(attributeTableViewer);
        Table attributeTable = attributeTableViewer.getTable();
        attributeTable.setHeaderVisible(true);
        attributeTable.setLinesVisible(true);
        String[] columnNames = new String[] { Localization.getString("property.name"), 
        		Localization.getString("Variable.property.format"),
                Localization.getString("Variable.property.defaultValue") };
        int[] columnWidths = new int[] { 200, 200, 200 };
        int[] columnAlignments = new int[] { SWT.LEFT, SWT.LEFT, SWT.LEFT };
        for (int i = 0; i < columnNames.length; i++) {
            TableColumn tableColumn = new TableColumn(attributeTable, columnAlignments[i]);
            tableColumn.setText(columnNames[i]);
            tableColumn.setWidth(columnWidths[i]);
        }

        Composite attributeButtonsBar = getToolkit().createComposite(rightComposite);
        attributeButtonsBar.setLayout(new GridLayout(1, false));
        gridData = new GridData();
        gridData.horizontalAlignment = SWT.LEFT;
        gridData.verticalAlignment = SWT.TOP;
        attributeButtonsBar.setLayoutData(gridData);
        createAttributeButton = addButton(attributeButtonsBar, "button.create", new CreateAttributeSelectionListener(), false);
        changeAttributeButton = addButton(attributeButtonsBar, "button.change", new ChangeAttributeSelectionListener(), true);
        deleteAttributeButton = addButton(attributeButtonsBar, "button.delete", new RemoveAttributeSelectionListener(), true);
        attributeTableViewer.addSelectionChangedListener(new LoggingSelectionChangedAdapter() {
            @Override
            protected void onSelectionChanged(SelectionChangedEvent event) throws Exception {
                updateButtons();
            }
        });
        updateTypeViewer();
        updateButtons();
    }
    
    private void updateTypeViewer() {
        List<VariableUserType> userTypes = getDefinition().getVariableUserTypes();
    	typeTableViewer.setInput(userTypes);
        for (VariableUserType userType : userTypes) {
            userType.addPropertyChangeListener(this);
        }
    	updateAttributeViewer();
    }
    
    @Override
    public void dispose() {
        for (VariableUserType userType : getDefinition().getVariableUserTypes()) {
            userType.removePropertyChangeListener(this);
        }
        super.dispose();
    }
    
    private VariableUserType getTypeSelection() {
    	return (VariableUserType) ((IStructuredSelection) typeTableViewer.getSelection()).getFirstElement();
    }

    private void updateAttributeViewer() {
    	VariableUserType type  = getTypeSelection();
    	if (type != null) {
    		attributeTableViewer.setInput(type.getAttributes());
    	} else {
    		attributeTableViewer.setInput(new Object[0]);
    	}
    }
 
    private Variable getAttributeSelection() {
    	return (Variable) ((IStructuredSelection) attributeTableViewer.getSelection()).getFirstElement();
    }

    private void updateButtons() {
    	VariableUserType selectedType = getTypeSelection();
        enableAction(deleteTypeButton, selectedType != null);
        enableAction(renameTypeButton, selectedType != null);
        enableAction(createAttributeButton, selectedType != null);
        Variable attribute = getAttributeSelection();
        enableAction(changeAttributeButton, attribute != null);
        enableAction(deleteAttributeButton, attribute != null);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String type = evt.getPropertyName();
        if (PropertyNames.PROPERTY_USER_TYPES_CHANGED.equals(type)) {
            updateTypeViewer();
        } else if (evt.getSource() instanceof VariableUserType) {
            if (PropertyNames.PROPERTY_NAME.equals(type)) {
                typeTableViewer.refresh(evt.getSource());
            }
            if (PropertyNames.PROPERTY_CHILDS_CHANGED.equals(type)) {
                updateAttributeViewer();
            }
        }
    }

    private class CreateTypeSelectionListener extends LoggingSelectionAdapter {
        @Override
        protected void onSelection(SelectionEvent e) throws Exception {
            VariableUserTypeDialog dialog = new VariableUserTypeDialog(getDefinition(), null);
            if (dialog.open() == Window.OK) {
            	VariableUserType type = new VariableUserType();
            	type.setName(dialog.getName());
                getDefinition().addVariableUserType(type);
                typeTableViewer.setSelection(new StructuredSelection(type));
            }
        }
    }

    private class RenameTypeSelectionListener extends LoggingSelectionAdapter {
        @Override
        protected void onSelection(SelectionEvent e) throws Exception {
            VariableUserType type = getTypeSelection();
            VariableUserTypeDialog dialog = new VariableUserTypeDialog(getDefinition(), type);
            if (dialog.open() == Window.OK) {
            	type.setName(dialog.getName());
            }
        }
    }

    private class RemoveTypeSelectionListener extends LoggingSelectionAdapter {
        @Override
        protected void onSelection(SelectionEvent e) throws Exception {
        	VariableUserType type = getTypeSelection();
            List<Variable> variables = getDefinition().getVariables(false, false, type.getName());
            StringBuffer info = new StringBuffer();
            if (variables.size() > 0) {
                for (Variable variable : variables) {
                	info.append(" - ").append(variable.getName()).append("\n");
                }
                info.append(Localization.getString("UserDefinedVariableType.deletion.VariablesWillBeRemoved"));
            } else {
            	info.append(Localization.getString("UserDefinedVariableType.deletion.NoUsageFound"));
            }
            if (MessageDialog.openConfirm(Display.getCurrent().getActiveShell(), Localization.getString("confirm.delete"), info.toString())) {
            	for (Variable variable : variables) {
            		getDefinition().removeChild(variable);
            	}
                getDefinition().removeVariableUserType(type);
            }
        }
    }

    private static class TypeLabelProvider extends LabelProvider implements ITableLabelProvider {
        @Override
        public String getColumnText(Object element, int index) {
            VariableUserType type = (VariableUserType) element;
            switch (index) {
            case 0:
                return type.getName();
            default:
                return "unknown " + index;
            }
        }

        @Override
        public String getText(Object element) {
            VariableUserType type = (VariableUserType) element;
            return type.getName();
        }

        @Override
        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }
    }

    private class CreateAttributeSelectionListener extends LoggingSelectionAdapter {
        @Override
        protected void onSelection(SelectionEvent e) throws Exception {
        	VariableUserType type = getTypeSelection();
            VariableWizard wizard = new VariableWizard(getDefinition(), type, null, true, true, false);
            CompactWizardDialog dialog = new CompactWizardDialog(wizard);
            if (dialog.open() == Window.OK) {
                Variable variable = wizard.getVariable();
                type.addAttribute(variable);
                attributeTableViewer.setSelection(new StructuredSelection(variable));
            }
        }
    }
    
    private class ChangeAttributeSelectionListener extends LoggingSelectionAdapter {
        @Override
        protected void onSelection(SelectionEvent e) throws Exception {
        	VariableUserType type = getTypeSelection();
        	Variable variable = getAttributeSelection();
            VariableWizard wizard = new VariableWizard(getDefinition(), type, variable, true, true, false);
            CompactWizardDialog dialog = new CompactWizardDialog(wizard);
            if (dialog.open() == Window.OK) {
                variable.setFormat(wizard.getVariable().getFormat());
                variable.setName(wizard.getVariable().getName());
                variable.setDefaultValue(wizard.getVariable().getDefaultValue());
            	getDefinition().setDirty();
                updateAttributeViewer();
                attributeTableViewer.setSelection(new StructuredSelection(variable));
            }
        }
    }
    
    private class RemoveAttributeSelectionListener extends LoggingSelectionAdapter {
        @Override
        protected void onSelection(SelectionEvent e) throws Exception {
            Variable attribute = getAttributeSelection();
            List<FormNode> nodesWithVar = ParContentProvider.getFormsWhereVariableUsed(editor.getDefinitionFile(), getDefinition(), attribute);
            StringBuffer formNames = new StringBuffer(Localization.getString("Variable.ExistInForms")).append("\n");
            if (nodesWithVar.size() > 0) {
                for (FormNode node : nodesWithVar) {
                    formNames.append(" - ").append(node.getName()).append("\n");
                }
                formNames.append(Localization.getString("Variable.WillBeRemovedFromFormAuto"));
            } else {
                formNames.append(Localization.getString("Variable.NoFormsUsed"));
            }
            if (MessageDialog.openConfirm(Display.getCurrent().getActiveShell(), Localization.getString("confirm.delete"), formNames.toString())) {
                // remove variable from form validations
                ParContentProvider.rewriteFormValidationsRemoveVariable(editor.getDefinitionFile(), nodesWithVar, attribute);
                // remove variable from definition
                getTypeSelection().removeAttribute(attribute);
            }
        }
    }

    private static class AttributeLabelProvider extends LabelProvider implements ITableLabelProvider {
    	@Override
        public String getColumnText(Object element, int index) {
            Variable variable = (Variable) element;
            switch (index) {
            case 0:
                return variable.getName();
            case 1:
                return variable.getFormatLabel();
            case 2:
                return variable.getDefaultValue() != null ? variable.getDefaultValue() : "";
            default:
                return "unknown " + index;
            }
        }

        @Override
        public String getText(Object element) {
            Variable variable = (Variable) element;
            return variable.getName();
        }

        @Override
        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }
    }

}
