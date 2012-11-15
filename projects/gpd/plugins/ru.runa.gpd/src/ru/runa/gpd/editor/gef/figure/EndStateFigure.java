package ru.runa.gpd.editor.gef.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.geometry.Rectangle;

import ru.runa.gpd.SharedImages;

public class EndStateFigure extends TerminalFigure {

    @Override
    public void init(boolean bpmnNotation) {
        super.init(bpmnNotation);
        if (!bpmnNotation) {
            addEllipse();
            addLabel();
        }
    }

    @Override
    protected void addEllipse() {
        ellipse.setBounds(new Rectangle(3, 3, 16, 16));

        Ellipse inner = new Ellipse();
        inner.setBounds(new Rectangle(6, 6, 10, 10));
        inner.setBackgroundColor(ColorConstants.black);
        ellipse.add(inner);

        Ellipse outer = new Ellipse();
        outer.setSize(22, 22);
        outer.setOutline(false);
        outer.add(ellipse);

        GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
        gridData.horizontalSpan = 2;
        add(outer, gridData);
    }

    @Override
    protected void paintBPMNFigure(Graphics g, Rectangle r) {
        g.drawImage(SharedImages.getImage("icons/bpmn/graph/end.png"), r.getLocation());
    }

}
