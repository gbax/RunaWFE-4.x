package ru.runa.bpm.ui.editor;

import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.parts.ScrollableThumbnail;
import org.eclipse.draw2d.parts.Thumbnail;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.PageBook;
import ru.runa.bpm.ui.SharedImages;
import ru.runa.bpm.ui.common.model.GraphElement;
import ru.runa.bpm.ui.common.model.GroupElement;
import ru.runa.bpm.ui.common.model.ProcessDefinition;
import ru.runa.bpm.ui.common.model.Variable;
import ru.runa.bpm.ui.common.part.tree.GroupElementTreeEditPart;
import ru.runa.bpm.ui.common.part.tree.OutlineRootTreeEditPart;
import ru.runa.bpm.ui.common.part.tree.VariableTreeEditPart;

public class OutlineViewer extends ContentOutlinePage implements ISelectionListener {
    private final DesignerGraphicalEditorPart editor;
    private PageBook pageBook;
    private Control treeview;
    private Canvas overview;
    private Thumbnail thumbnail;

    private IAction showOverviewAction;
    private IAction showTreeviewAction;

    public OutlineViewer(DesignerGraphicalEditorPart editor) {
        super(new TreeViewer());
        this.editor = editor;
    }

    @Override
    public void createControl(Composite parent) {
        createToolBar();
        createPageBook(parent);
        getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(this);
    }

    @Override
    public void dispose() {
        if (null != thumbnail) {
            thumbnail.deactivate();
        }
        getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(this);
        super.dispose();
    }

    public void selectionChanged(IWorkbenchPart part, ISelection selection) {
        if (!(selection instanceof IStructuredSelection)) {
            return;
        }
        IStructuredSelection structuredSelection = (IStructuredSelection) selection;
        Object selectedObject = structuredSelection.getFirstElement();
        if (!(selectedObject instanceof EditPart)) {
            return;
        }
        EditPart source = (EditPart) selectedObject;
        select((GraphElement) source.getModel());
    }

    public void select(GraphElement element) {
        EditPart target = (EditPart) getViewer().getEditPartRegistry().get(element);
        if (target != null) {
            getViewer().select(target);
        }
    }

    private void createToolBar() {
        IToolBarManager tbm = getSite().getActionBars().getToolBarManager();
        createShowOverviewAction(tbm);
        createShowTreeviewAction(tbm);
    }

    private void createShowOverviewAction(IToolBarManager tbm) {
        showOverviewAction = new Action() {
            @Override
            public void run() {
                showOverview();
            }
        };
        showOverviewAction.setImageDescriptor(SharedImages.getImageDescriptor("icons/overview.gif"));
        tbm.add(showOverviewAction);
    }

    private void showOverview() {
        showTreeviewAction.setChecked(false);
        showOverviewAction.setChecked(true);
        pageBook.showPage(overview);
        thumbnail.setVisible(true);
    }

    private void createShowTreeviewAction(IToolBarManager tbm) {
        showTreeviewAction = new Action() {
            @Override
            public void run() {
                showTreeview();
            }
        };
        showTreeviewAction.setImageDescriptor(SharedImages.getImageDescriptor("icons/treeview.gif"));
        tbm.add(showTreeviewAction);
    }

    private void showTreeview() {
        showTreeviewAction.setChecked(true);
        showOverviewAction.setChecked(false);
        pageBook.showPage(treeview);
        thumbnail.setVisible(false);
    }

    private void createPageBook(Composite parent) {
        pageBook = new PageBook(parent, SWT.NONE);
        createTreeview(pageBook);
        createOverview(pageBook);
        showTreeview();
    }

    private void createOverview(Composite parent) {
        ScalableFreeformRootEditPart rootEditPart = (ScalableFreeformRootEditPart) editor.getGraphicalViewer().getRootEditPart();
        overview = new Canvas(parent, SWT.NONE);
        LightweightSystem lws = new LightweightSystem(overview);
        thumbnail = new ScrollableThumbnail((Viewport) rootEditPart.getFigure());
        thumbnail.setBorder(new MarginBorder(3));
        thumbnail.setSource(rootEditPart.getLayer(LayerConstants.PRINTABLE_LAYERS));
        lws.setContents(thumbnail);
    }

    private void createTreeview(Composite parent) {
        treeview = getViewer().createControl(parent);
        getSite().setSelectionProvider(getViewer());
        getViewer().setEditDomain(editor.getEditDomain());
        getViewer().setEditPartFactory(new GPDEditPartFactory());
        getViewer().setContents(editor.getEditor().getDefinition());
    }

    private static class GPDEditPartFactory implements EditPartFactory {
        private boolean firstTime = true;

        public EditPart createEditPart(EditPart context, Object model) {
            if (firstTime && model instanceof ProcessDefinition) {
                firstTime = false;
                EditPart rootEditPart = new OutlineRootTreeEditPart();
                rootEditPart.setModel(model);
                return rootEditPart;
            }
            GraphElement element = (GraphElement) model;
            if (element instanceof Variable) {
                return new VariableTreeEditPart((Variable) element);
            }
            if (element instanceof GroupElement) {
                return new GroupElementTreeEditPart((GroupElement) element);
            }
            return element.getTypeDefinition().createTreeEditPart(element);
        }
    }

    public CommandStack getCommandStack() {
        return editor.getCommandStack();
    }

    @Override
    public Control getControl() {
        return pageBook;
    }

}
