package org.reverence.jcommander.pane.icons;

import com.bw.jtools.shape.AbstractShape;
import com.bw.jtools.shape.Context;
import com.bw.jtools.shape.ShapePainter;
import com.bw.jtools.ui.ShapeIcon;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.swing.Icon;
import javax.swing.border.Border;

public class ShapeIconExt extends ShapeIcon implements Icon, Accessible {
    private boolean drawFrame_ = false;
    private Paint framePaint_;
    private final ShapePainter painter_;
    protected String description_;
    private Border margin;
    public ShapeIconExt(AbstractShape shape) {
        super(shape);
        this.framePaint_ = Color.BLACK;
        this.painter_ = new ShapePainter(shape);
    }

    public void setDescription(String description) {
        this.description_ = description;
    }

    public String getDescription() {
        return this.description_;
    }

    public void setShape(AbstractShape shape) {
        this.painter_.setShape(shape);
    }

    public void setInlineBorder(boolean draw, Paint color) {
        this.drawFrame_ = draw;
        this.framePaint_ = color;
    }

    public void setInlineBorder(boolean draw) {
        this.setInlineBorder(draw, Color.BLACK);
    }

    public void setScale(double scaleX, double scaleY) {
        this.painter_.setScale(scaleX, scaleY);
    }

    public double getXScale() {
        return this.painter_.getXScale();
    }

    public double getYScale() {
        return this.painter_.getYScale();
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D)g.create();

        try {
            Context.initGraphics(g2d);
            g2d.translate(x, y);
            if (this.drawFrame_) {
                g2d.setPaint(this.framePaint_);
                g2d.draw(this.painter_.getArea());
            }

            if (c == null) {
                this.painter_.paint(g2d, Color.BLACK, Color.WHITE, false, false);
            } else {
                this.painter_.paint(g2d, c.getForeground(), c.getBackground(), c.isOpaque(), !c.isEnabled());
            }
        } finally {
            g2d.dispose();
        }

    }

    public double getIconWidth2D() {
        return this.painter_.getAreaWidth();
    }

    public double getIconHeight2D() {
        return this.painter_.getAreaHeight();
    }

    public int getIconWidth() {
        return (int)Math.ceil(this.painter_.getAreaWidth());
    }

    public int getIconHeight() {
        return (int)Math.ceil(this.painter_.getAreaHeight());
    }

    public ShapePainter getPainter() {
        return this.painter_;
    }

    public AccessibleContext getAccessibleContext() {
        return null;
    }
}
