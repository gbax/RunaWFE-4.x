/*
 * This file is part of the RUNA WFE project.
 * 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation; version 2.1 
 * of the License. 
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU Lesser General Public License for more details. 
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this program; if not, write to the Free Software 
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package ru.runa.wfe.graph.history.figure.uml;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import ru.runa.wfe.graph.DrawProperties;
import ru.runa.wfe.graph.history.figure.AbstractFigure;

import com.google.common.base.Objects;

public class TaskNodeFigure extends AbstractFigure {

    @Override
    public Point getTransitionPoint(double x, double y, String transitionName) {
        if (Objects.equal(timerTransitionName, transitionName)) {
            return new Point(coords[0] + DrawProperties.GRID_SIZE, coords[1] + coords[3] - DrawProperties.GRID_SIZE);
        }
        return super.getTransitionPoint(x, y, transitionName);
    }

    @Override
    public void fill(Graphics2D graphics) {
        Rectangle rect = getRectangle();
        if (minimized) {
            graphics.fillRect(rect.x, rect.y, rect.width, rect.height);
        } else {
            graphics.fillRoundRect(rect.x, rect.y, rect.width, rect.height, 20, 10);
        }
        if (!minimized && timerTransitionName != null) {
            graphics.fillOval(coords[0], coords[1] + coords[3] - DrawProperties.GRID_SIZE * 2, DrawProperties.GRID_SIZE * 2,
                    DrawProperties.GRID_SIZE * 2);
        }
    }

    @Override
    public void draw(Graphics2D graphics, boolean cleanMode) {
        Rectangle rect = getRectangle();
        if (minimized) {
            graphics.drawRect(rect.x, rect.y, rect.width, rect.height);
        } else {
            graphics.drawRoundRect(rect.x, rect.y, rect.width, rect.height, 20, 10);
        }
        if (!minimized) {
            drawActions(graphics);
            drawTextInfo(graphics, 1);
        }
        if (!minimized && timerTransitionName != null) {
            // Clean area for timer
            Color orig = graphics.getColor();
            graphics.setColor(DrawProperties.getBackgroundColor());
            graphics.fillOval(coords[0], coords[1] + coords[3] - DrawProperties.GRID_SIZE * 2, DrawProperties.GRID_SIZE * 2,
                    DrawProperties.GRID_SIZE * 2);
            graphics.setColor(orig);

            // Draw timer
            graphics.drawOval(coords[0], coords[1] + coords[3] - DrawProperties.GRID_SIZE * 2, DrawProperties.GRID_SIZE * 2,
                    DrawProperties.GRID_SIZE * 2);
            graphics.drawLine(coords[0] + DrawProperties.GRID_SIZE, coords[1] + coords[3] - DrawProperties.GRID_SIZE, coords[0]
                    + DrawProperties.GRID_SIZE, coords[1] + coords[3] - DrawProperties.GRID_SIZE + 5);
            graphics.drawLine(coords[0] + DrawProperties.GRID_SIZE, coords[1] + coords[3] - DrawProperties.GRID_SIZE, coords[0]
                    + DrawProperties.GRID_SIZE + 5, coords[1] + coords[3] - DrawProperties.GRID_SIZE - 5);
        }
    }

    @Override
    public Rectangle getTextBoundsRectangle() {
        return getRectangle();
    }

    @Override
    public Rectangle getRectangle() {
        if (minimized) {
            return new Rectangle(coords[0] + DrawProperties.GRID_SIZE / 2, coords[1] + DrawProperties.GRID_SIZE / 2, DrawProperties.GRID_SIZE,
                    DrawProperties.GRID_SIZE);
        }
        return new Rectangle(coords[0] + DrawProperties.GRID_SIZE, coords[1], coords[2] - DrawProperties.GRID_SIZE, coords[3]
                - DrawProperties.GRID_SIZE);
    }
}
