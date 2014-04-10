package ru.runa.gpd.ui.dialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;

import ru.runa.gpd.Localization;
import ru.runa.gpd.lang.model.MessagingNode;
import ru.runa.gpd.lang.model.ProcessDefinition;
import ru.runa.gpd.ui.custom.DragAndDropAdapter;
import ru.runa.gpd.ui.custom.DropDownButton;
import ru.runa.gpd.ui.custom.LoggingDoubleClickAdapter;
import ru.runa.gpd.ui.custom.LoggingSelectionAdapter;
import ru.runa.gpd.ui.custom.LoggingSelectionChangedAdapter;
import ru.runa.gpd.ui.custom.SWTUtils;
import ru.runa.gpd.ui.custom.TableViewerLocalDragAndDropSupport;
import ru.runa.gpd.util.VariableMapping;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

public class MessageNodeDialog extends Dialog {
    private final ProcessDefinition definition;
    private final List<VariableMapping> variableMappings;
    private final boolean sendMode;
    private TableViewer selectorTableViewer;
    private TableViewer dataTableViewer;
    private Button selectorChangeButton;
    private Button selectorDeleteButton;
    private Button dataMoveUpButton;
    private Button dataMoveDownButton;
    private Button dataChangeButton;
    private Button dataDeleteButton;

    public MessageNodeDialog(ProcessDefinition definition, List<VariableMapping> variableMappings, boolean sendMode) {
        super(PlatformUI.getWorkbench().getDisplay().getActiveShell());
        this.variableMappings = Lists.newArrayList(variableMappings);
        this.definition = definition;
        this.sendMode = sendMode;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);
        composite.setLayout(new GridLayout());
        Group routeGroup = new Group(composite, SWT.NONE);
        routeGroup.setLayout(new GridLayout(2, false));
        routeGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
        routeGroup.setText(Localization.getString(sendMode ? "MessageNodeDialog.SelectorSend" : "MessageNodeDialog.SelectorReceive"));
        createSelectorTableViewer(routeGroup);
        addSelectorButtons(routeGroup);
        Group variablesGroup = new Group(composite, SWT.NONE);
        variablesGroup.setLayout(new GridLayout(2, false));
        variablesGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
        variablesGroup.setText(Localization.getString("MessageNodeDialog.VariablesList"));
        createDataTableViewer(variablesGroup);
        addDataButtons(variablesGroup);
        return composite;
    }

    private void createSelectorTableViewer(Composite parent) {
        selectorTableViewer = new TableViewer(parent, SWT.MULTI | SWT.V_SCROLL | SWT.FULL_SELECTION);
        GridData data = new GridData(GridData.FILL_VERTICAL);
        data.minimumHeight = 100;
        selectorTableViewer.getControl().setLayoutData(data);
        final Table table = selectorTableViewer.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        String[] columnNames = new String[] { Localization.getString("property.name"), Localization.getString("property.value") };
        for (int i = 0; i < columnNames.length; i++) {
            TableColumn tableColumn = new TableColumn(table, SWT.LEFT);
            tableColumn.setText(columnNames[i]);
            tableColumn.setWidth(300);
        }
        selectorTableViewer.addDoubleClickListener(new LoggingDoubleClickAdapter() {
            @Override
            protected void onDoubleClick(DoubleClickEvent event) {
                IStructuredSelection selection = (IStructuredSelection) selectorTableViewer.getSelection();
                if (!selection.isEmpty()) {
                    VariableMapping mapping = (VariableMapping) selection.getFirstElement();
                    editVariableMapping(mapping, VariableMapping.USAGE_SELECTOR);
                }
            }
        });
        selectorTableViewer.setLabelProvider(new VariableMappingTableLabelProvider());
        selectorTableViewer.setContentProvider(new UsageContentProvider(VariableMapping.USAGE_SELECTOR));
        setSelectorTableInput();
        TableViewerLocalDragAndDropSupport.enable(selectorTableViewer, new DragAndDropAdapter<VariableMapping>() {

            @Override
            public void onDropElement(VariableMapping beforeElement, VariableMapping element) {
                if (variableMappings.remove(element)) {
                    variableMappings.add(variableMappings.indexOf(beforeElement), element);
                }
            }

            @Override
            public void onDrop(VariableMapping beforeElement, List<VariableMapping> elements) {
                super.onDrop(beforeElement, elements);
                setSelectorTableInput();
            }

        });
    }

    private void addSelectorButtons(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout());
        GridData gridData = new GridData();
        gridData.horizontalAlignment = SWT.LEFT;
        gridData.verticalAlignment = SWT.TOP;
        composite.setLayoutData(gridData);
        DropDownButton addButton = new DropDownButton(composite);
        addButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        addButton.setAlignment(SWT.LEFT);
        addButton.setText(Localization.getString("button.add"));
        addButton.addSelectionListener(new LoggingSelectionAdapter() {

            @Override
            protected void onSelection(SelectionEvent e) throws Exception {
                editVariableMapping(null, VariableMapping.USAGE_SELECTOR);
            }
        });
        addButton.addButton(Localization.getString("MessageNodeDialog.addByProcessId"), new LoggingSelectionAdapter() {

            @Override
            protected void onSelection(SelectionEvent e) throws Exception {
                VariableMapping mapping = new VariableMapping("processId", MessagingNode.SELECTOR_CURRENT_PROCESS_ID, VariableMapping.USAGE_SELECTOR);
                if (sendMode) {
                    editVariableMapping(mapping, VariableMapping.USAGE_SELECTOR);
                } else {
                    addVariableMapping(mapping, true);
                }
            }
        });
        addButton.addButton(Localization.getString("MessageNodeDialog.addByProcessName"), new LoggingSelectionAdapter() {

            @Override
            protected void onSelection(SelectionEvent e) throws Exception {
                VariableMapping mapping = new VariableMapping("processDefinitionName", MessagingNode.SELECTOR_CURRENT_PROCESS_DEFINITION_NAME,
                        VariableMapping.USAGE_SELECTOR);
                if (sendMode) {
                    editVariableMapping(mapping, VariableMapping.USAGE_SELECTOR);
                } else {
                    addVariableMapping(mapping, true);
                }
            }
        });
        addButton.addButton(Localization.getString("MessageNodeDialog.addByNodeName"), new LoggingSelectionAdapter() {

            @Override
            protected void onSelection(SelectionEvent e) throws Exception {
                VariableMapping mapping = new VariableMapping("processNodeName", MessagingNode.SELECTOR_CURRENT_NODE_NAME,
                        VariableMapping.USAGE_SELECTOR);
                if (sendMode) {
                    editVariableMapping(mapping, VariableMapping.USAGE_SELECTOR);
                } else {
                    addVariableMapping(mapping, true);
                }
            }
        });
        selectorChangeButton = SWTUtils.createButtonFillHorizontal(composite, Localization.getString("button.change"), new LoggingSelectionAdapter() {

            @Override
            protected void onSelection(SelectionEvent e) throws Exception {
                IStructuredSelection selection = (IStructuredSelection) selectorTableViewer.getSelection();
                if (!selection.isEmpty()) {
                    VariableMapping mapping = (VariableMapping) selection.getFirstElement();
                    editVariableMapping(mapping, VariableMapping.USAGE_SELECTOR);
                }
            }
        });
        selectorDeleteButton = SWTUtils.createButtonFillHorizontal(composite, Localization.getString("button.delete"), new LoggingSelectionAdapter() {

            @Override
            protected void onSelection(SelectionEvent e) throws Exception {
                IStructuredSelection selection = (IStructuredSelection) selectorTableViewer.getSelection();
                List<VariableMapping> mappings = selection.toList();
                for (VariableMapping mapping : mappings) {
                    variableMappings.remove(mapping);
                }
                selectorTableViewer.refresh();
            }
        });
        selectorTableViewer.addSelectionChangedListener(new LoggingSelectionChangedAdapter() {
            @Override
            protected void onSelectionChanged(SelectionChangedEvent event) throws Exception {
                updateSelectorButtons();
            }
        });
        updateSelectorButtons();
    }

    private void createDataTableViewer(Composite parent) {
        dataTableViewer = new TableViewer(parent, SWT.MULTI | SWT.V_SCROLL | SWT.FULL_SELECTION);
        GridData data = new GridData(GridData.FILL_VERTICAL);
        data.minimumHeight = 200;
        dataTableViewer.getControl().setLayoutData(data);
        final Table table = dataTableViewer.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        String[] columnNames = new String[] { Localization.getString("MessageNodeDialog.VariableName"),
                Localization.getString("MessageNodeDialog.Alias") };
        for (int i = 0; i < columnNames.length; i++) {
            TableColumn tableColumn = new TableColumn(table, SWT.LEFT);
            tableColumn.setText(columnNames[i]);
            tableColumn.setWidth(300);
        }
        dataTableViewer.addDoubleClickListener(new LoggingDoubleClickAdapter() {
            @Override
            protected void onDoubleClick(DoubleClickEvent event) {
                IStructuredSelection selection = (IStructuredSelection) dataTableViewer.getSelection();
                if (!selection.isEmpty()) {
                    VariableMapping oldMapping = (VariableMapping) selection.getFirstElement();
                    editVariableMapping(oldMapping, VariableMapping.USAGE_READ);
                }
            }
        });
        dataTableViewer.setLabelProvider(new VariableMappingTableLabelProvider());
        dataTableViewer.setContentProvider(new UsageContentProvider(VariableMapping.USAGE_READ));
        setDataTableInput();
        TableViewerLocalDragAndDropSupport.enable(dataTableViewer, new DragAndDropAdapter<VariableMapping>() {

            @Override
            public void onDropElement(VariableMapping beforeElement, VariableMapping element) {
                if (variableMappings.remove(element)) {
                    variableMappings.add(variableMappings.indexOf(beforeElement), element);
                }
            }

            @Override
            public void onDrop(VariableMapping beforeElement, List<VariableMapping> elements) {
                super.onDrop(beforeElement, elements);
                setDataTableInput();
            }

        });
    }

    private void setSelectorTableInput() {
        selectorTableViewer.setInput(new Object());
    }

    private void setDataTableInput() {
        dataTableViewer.setInput(new Object());
    }

    private void addDataButtons(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout());
        GridData gridData = new GridData();
        gridData.horizontalAlignment = SWT.LEFT;
        gridData.verticalAlignment = SWT.TOP;
        composite.setLayoutData(gridData);
        SWTUtils.createButtonFillHorizontal(composite, Localization.getString("button.add"), new LoggingSelectionAdapter() {

            @Override
            protected void onSelection(SelectionEvent e) throws Exception {
                editVariableMapping(null, VariableMapping.USAGE_READ);
            }
        });
        dataChangeButton = SWTUtils.createButtonFillHorizontal(composite, Localization.getString("button.change"), new LoggingSelectionAdapter() {

            @Override
            protected void onSelection(SelectionEvent e) throws Exception {
                IStructuredSelection selection = (IStructuredSelection) dataTableViewer.getSelection();
                if (!selection.isEmpty()) {
                    VariableMapping oldMapping = (VariableMapping) selection.getFirstElement();
                    editVariableMapping(oldMapping, VariableMapping.USAGE_READ);
                }
            }
        });
        dataMoveUpButton = SWTUtils.createButtonFillHorizontal(composite, Localization.getString("button.up"), new MoveVariableSelectionListener(true));
        dataMoveDownButton = SWTUtils.createButtonFillHorizontal(composite, Localization.getString("button.down"), new MoveVariableSelectionListener(
                false));
        dataDeleteButton = SWTUtils.createButtonFillHorizontal(composite, Localization.getString("button.delete"), new LoggingSelectionAdapter() {

            @Override
            protected void onSelection(SelectionEvent e) throws Exception {
                IStructuredSelection selection = (IStructuredSelection) dataTableViewer.getSelection();
                List<VariableMapping> mappings = selection.toList();
                for (VariableMapping mapping : mappings) {
                    variableMappings.remove(mapping);
                }
                dataTableViewer.refresh();
            }
        });
        dataTableViewer.addSelectionChangedListener(new LoggingSelectionChangedAdapter() {
            @Override
            protected void onSelectionChanged(SelectionChangedEvent event) throws Exception {
                updateDataButtons();
            }
        });
        updateDataButtons();
    }

    private void updateSelectorButtons() {
        List<?> selected = ((IStructuredSelection) selectorTableViewer.getSelection()).toList();
        selectorChangeButton.setEnabled(selected.size() == 1);
        selectorDeleteButton.setEnabled(selected.size() > 0);
    }

    private void updateDataButtons() {
        List<?> selected = ((IStructuredSelection) dataTableViewer.getSelection()).toList();
        dataChangeButton.setEnabled(selected.size() == 1);
        dataMoveUpButton.setEnabled(selected.size() == 1 && variableMappings.indexOf(selected.get(0)) > 0);
        dataMoveDownButton.setEnabled(selected.size() == 1 && variableMappings.indexOf(selected.get(0)) < variableMappings.size() - 1);
        dataDeleteButton.setEnabled(selected.size() > 0);
    }

    private void editVariableMapping(VariableMapping mapping, String usage) {
        MessageVariableDialog dialog = new MessageVariableDialog(definition.getVariableNames(true), VariableMapping.USAGE_SELECTOR.equals(usage),
                mapping);
        if (dialog.open() != IDialogConstants.CANCEL_ID) {
            if (mapping == null) {
                mapping = new VariableMapping();
            }
            mapping.setName(dialog.getVariable());
            mapping.setMappedName(dialog.getAlias());
            mapping.setUsage(usage);
            if (!variableMappings.contains(mapping)) {
                addVariableMapping(mapping, false);
            }
            selectorTableViewer.refresh();
            dataTableViewer.refresh();
        }
    }

    public List<VariableMapping> getVariableMappings() {
        return variableMappings;
    }

    private void addVariableMapping(VariableMapping mapping, boolean updateViewers) {
        for (VariableMapping existingMapping : variableMappings) {
            if (Objects.equal(existingMapping.getName(), mapping.getName())) {
                variableMappings.remove(existingMapping);
                break;
            }
        }
        variableMappings.add(mapping);
        if (updateViewers) {
            selectorTableViewer.refresh();
            dataTableViewer.refresh();
        }
    }

    private class MoveVariableSelectionListener extends LoggingSelectionAdapter {
        private final boolean up;

        public MoveVariableSelectionListener(boolean up) {
            this.up = up;
        }

        @Override
        protected void onSelection(SelectionEvent e) throws Exception {
            IStructuredSelection selection = (IStructuredSelection) dataTableViewer.getSelection();
            VariableMapping mapping = (VariableMapping) selection.getFirstElement();
            int index = variableMappings.indexOf(mapping);
            Collections.swap(variableMappings, index, up ? (index - 1) : (index + 1));
            setDataTableInput();
            dataTableViewer.setSelection(selection);
        }
    }

    private static class VariableMappingTableLabelProvider extends LabelProvider implements ITableLabelProvider {
        @Override
        public String getColumnText(Object element, int index) {
            VariableMapping mapping = (VariableMapping) element;
            switch (index) {
            case 0:
                return mapping.getName();
            case 1:
                return mapping.getMappedName();
            }
            return "";
        }

        @Override
        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }
    }

    private class UsageContentProvider implements IStructuredContentProvider {
        private final String usage;

        private UsageContentProvider(String usage) {
            this.usage = usage;
        }

        @Override
        public Object[] getElements(Object inputElement) {
            List<VariableMapping> list = new ArrayList<VariableMapping>();
            for (VariableMapping variableMapping : variableMappings) {
                if (usage.equals(variableMapping.getUsage())) {
                    list.add(variableMapping);
                }
            }
            return list.toArray(new VariableMapping[list.size()]);
        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            // do nothing.
        }

        @Override
        public void dispose() {
            // do nothing.
        }
    }
}
