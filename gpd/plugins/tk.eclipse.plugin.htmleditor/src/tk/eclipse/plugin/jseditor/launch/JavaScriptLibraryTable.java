package tk.eclipse.plugin.jseditor.launch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import tk.eclipse.plugin.htmleditor.HTMLPlugin;
import tk.eclipse.plugin.htmleditor.TableViewerSupport;

/**
 * 
 * @author Naoki Takezoe
 */
public class JavaScriptLibraryTable {
	
	public static final String PREFIX = "entry:";
	
	private TableViewer tableViewer;
	private List tableModel = new ArrayList();
	
	private Composite composite;
	private Button add;
	private Button addExternal;
	private Button remove;
	
	public JavaScriptLibraryTable(final Composite parent){
		composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// list
		tableViewer = new TableViewer(composite);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = 250;
		tableViewer.getTable().setLayoutData(gd);
		tableViewer.getTable().addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent evt){
				remove.setEnabled(tableViewer.getTable().getSelectionCount() != 0);
			}
		});
		tableViewer.setContentProvider(new TableViewerSupport.ListContentProvider());
		tableViewer.setLabelProvider(new ITableLabelProvider(){
			public Image getColumnImage(Object element, int columnIndex) {
				if(element instanceof File){
					return HTMLPlugin.getDefault().getImageRegistry().get(HTMLPlugin.ICON_JAR_EXT);
				} else if(element instanceof IFile){
					return HTMLPlugin.getDefault().getImageRegistry().get(HTMLPlugin.ICON_JAR);
				}
				return null;
			}
			public String getColumnText(Object element, int columnIndex) {
				if(element instanceof File){
					return ((File)element).getAbsolutePath();
				} else if(element instanceof IFile){
					return ((IFile)element).getFullPath().toString();
				}
				return element.toString();
			}
			public void addListener(ILabelProviderListener listener) {
			}
			public void dispose() {
			}
			public boolean isLabelProperty(Object element, String property) {
				return false;
			}
			public void removeListener(ILabelProviderListener listener) {
			}
		});
		tableViewer.setInput(tableModel);
		
		// buttons
		Composite buttons = new Composite(composite, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		buttons.setLayout(layout);
		buttons.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		
		add = new Button(buttons, SWT.PUSH);
		add.setText(HTMLPlugin.getResourceString("Button.Add"));
		add.setLayoutData(createButtonGridData());
		add.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent evt){
				IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();
				ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
						parent.getShell(),
						new WorkbenchLabelProvider(), 
						new WorkbenchContentProvider());
				
				dialog.setTitle(HTMLPlugin.getResourceString("JavaScriptPropertyPage.ChooseJavaScript"));
				dialog.setMessage(HTMLPlugin.getResourceString("JavaScriptPropertyPage.ChooseJavaScript.Description"));
				dialog.setInput(wsroot);
				dialog.setValidator(new ISelectionStatusValidator(){
					private IStatus okStatus = new Status(Status.OK, HTMLPlugin.getDefault().getPluginId(), Status.OK, "", null);
					private IStatus ngStatus = new Status(Status.ERROR, HTMLPlugin.getDefault().getPluginId(), Status.ERROR, "", null);
					
					public IStatus validate(Object[] selection) {
						for(int i=0;i<selection.length;i++){
							if(!(selection[i] instanceof IFile)){
								return ngStatus;
							}
							if(!((IFile)selection[i]).getName().endsWith(".js")){
								return ngStatus;
							}
						}
						if(selection.length==0){
							return ngStatus;
						}
						return okStatus;
					}
				});
				if (dialog.open() == Dialog.OK) {
					Object[] results = dialog.getResult();
					for(int i=0;i<results.length;i++){
						tableModel.add((IFile)results[i]);
					}
					tableViewer.refresh();
					modelChanged();
				}
			}
		});
		
		addExternal = new Button(buttons, SWT.PUSH);
		addExternal.setText(HTMLPlugin.getResourceString("Button.AddExternal"));
		addExternal.setLayoutData(createButtonGridData());
		addExternal.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent evt){
				FileDialog dialog = new FileDialog(parent.getShell(), SWT.OPEN|SWT.MULTI);
				dialog.setFilterExtensions(new String[]{"*.js"});
				String result = dialog.open();
				if(result!=null){
					String dir = dialog.getFilterPath();
					String[] fileNames = dialog.getFileNames();
					for(int i=0;i<fileNames.length;i++){
						tableModel.add(new File(dir, fileNames[i]));
					}
					tableViewer.refresh();
					modelChanged();
				}
			}
		});
		
		remove = new Button(buttons, SWT.PUSH);
		remove.setText(HTMLPlugin.getResourceString("Button.Remove"));
		remove.setLayoutData(createButtonGridData());
		remove.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent evt){
				IStructuredSelection sel = (IStructuredSelection)tableViewer.getSelection();
				tableModel.removeAll(sel.toList());
				remove.setEnabled(false);
				tableViewer.refresh();
				modelChanged();
			}
		});
		remove.setEnabled(false);
	}
	
	protected void modelChanged(){
	}
	
	public Control getControl(){
		return composite;
	}
	
	public List getModel(){
		return tableModel;
	}
	
	public void refresh(){
		tableViewer.refresh();
	}
	
	private static GridData createButtonGridData(){
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 100;
		return gd;
	}	
	
}
