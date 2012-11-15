package ru.runa.gpd.editor.gef.figure;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.RoutingListener;
import org.eclipse.swt.SWT;

import ru.runa.gpd.Activator;
import ru.runa.gpd.PluginConstants;
import ru.runa.gpd.editor.gef.ActionGraphUtils;
import ru.runa.gpd.editor.gef.figure.bpmn.DefaultDecisionFlowDecoration;
import ru.runa.gpd.editor.gef.figure.bpmn.DiamondDecoration;

public class TransitionFigure extends PolylineConnection {
    public static final int LINE_WIDTH_UNSELECTED = 1;
    public static final int LINE_WIDTH_SELECTED = 2;

    private Label label;
    private boolean bpmnNotation = false;
    private boolean exclusive = false;
    private boolean defaultFlow = false;

    public void init(boolean bpmnNotation) {
        this.bpmnNotation = bpmnNotation;

        label = new Label();
        ConnectionEndpointLocator locator = new ConnectionEndpointLocator(this, false);
        locator.setUDistance(10);
        add(label, locator);

        PolygonDecoration arrow = new PolygonDecoration();
        arrow.setTemplate(PolygonDecoration.TRIANGLE_TIP);
        arrow.setScale(5, 2.5);
        setTargetDecoration(arrow);
        setConnectionRouter(new ReferencedBendpointConnectionRouter());
        setRoutingConstraint(new ArrayList<Bendpoint>());
        
        if (bpmnNotation) {
            setForegroundColor(ColorConstants.gray);
        }
    }
    
	public boolean setExclusive(boolean exclusive) {
	    boolean changed = this.exclusive != exclusive;
	    this.exclusive = exclusive;
	    return changed;
	}
	
	public boolean setDefaultFlow(boolean defaultFlow) {
        boolean changed = this.defaultFlow != defaultFlow;
        this.defaultFlow = defaultFlow;
        return changed;
    }
	
	public boolean hasSourceDecoration() {
		return getSourceDecoration() != null;
	}

	public void updateSourceDecoration() {
        if (bpmnNotation) {
            if (defaultFlow) {
                setSourceDecoration(new DefaultDecisionFlowDecoration());
            } else if (exclusive) {
                setSourceDecoration(new DiamondDecoration());
            } else {
                setSourceDecoration(null);
            }
            
        }
    }
    
    public void setLabelText(String text) {
        label.setText(text);
    }

    @Override
    public void paint(Graphics graphics) {
        if (!Activator.getDefault().getDialogSettings().getBoolean(PluginConstants.DISABLE_ANTIALIASING)) {
            graphics.setTextAntialias(SWT.ON);
            graphics.setAntialias(SWT.ON);
        }
        super.paint(graphics);
    }
    
    @Override
    public void remove(IFigure figure) {
        if (figure instanceof ActionFigure && !actionsFitInFigure) {
            cachedFigures.remove(figure);
            checkActionsFitInFigure();
        } else {
            super.remove(figure);
        }
    }

    private boolean actionsFitInFigure = true;
    private List<ActionFigure> cachedFigures = null;
    private ActionFigure multipleFigure = null;
    private ActionFigure getMultipleFigure() {
        if (multipleFigure == null) {
            multipleFigure = ActionFigure.getMultipleFigure();
            addRoutingListener(new RoutingListener() {
                public void invalidate(Connection connection) {
                }
                public void postRoute(Connection connection) {
                    //if (getParent() == null) return;
                    multipleFigure.setLocation(
                            ActionGraphUtils.getActionFigureLocation(TransitionFigure.this, 0, 0, false));
                }
                public void remove(Connection connection) {
                }
                public boolean route(Connection connection) {
                    return false;
                }
                public void setConstraint(Connection connection, Object constraint) {
                }
            });
        }
        return multipleFigure;
    }
    @SuppressWarnings("unchecked")
	public void checkActionsFitInFigure() {
        List<ActionFigure> actionFigures = new ArrayList<ActionFigure>();
        for (IFigure figure : (List<IFigure>) getChildren()) {
            if (figure instanceof ActionFigure) {
                actionFigures.add((ActionFigure) figure);
            }
        }
        int actionsCount;
        if (actionsFitInFigure) {
            actionsCount = actionFigures.size();
        } else {
            actionsCount = cachedFigures.size();
        }
        boolean nowActionsFitInFigure = ActionGraphUtils.areActionsFitInLine(this, actionsCount);
        if (nowActionsFitInFigure == actionsFitInFigure) {
            return;
        }
        this.actionsFitInFigure = nowActionsFitInFigure;
        if (actionsFitInFigure) {
            remove(getMultipleFigure());
            if (cachedFigures == null) {
                throw new NullPointerException("Transition.cachedFigures = null");
            }
            for (ActionFigure figure : cachedFigures) {
                figure.setVisible(true);
                add(figure);
            }
        } else {
            cachedFigures = actionFigures;
            for (ActionFigure figure : cachedFigures) {
                figure.setVisible(false);
                super.remove(figure);
            }
            add(getMultipleFigure());
        }

    }
}
