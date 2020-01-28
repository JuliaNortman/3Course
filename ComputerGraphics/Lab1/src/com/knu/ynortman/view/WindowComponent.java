package com.knu.ynortman.view;

import com.knu.ynortman.datastructure.Edge;
import com.knu.ynortman.datastructure.Polygon;

import javax.swing.*;
import java.awt.*;

public class WindowComponent extends JComponent {
    private Polygon polygon;

    public WindowComponent(Polygon polygon) {
        this.polygon = polygon;
    }

    private static final double SCALE_X = 20;

    private static final double SCALE_Y = 15;

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawGraph(g2);
    }

    private void drawGraph(Graphics2D g2) {
        double lastX = 0;
        double lastY1 = Main.HEIGHT / 2;
        double lastY2 = Main.HEIGHT / 2;
        double lastY3 = Main.HEIGHT / 2;
        for (int x = 1; x <= Main.WIDTH; x++) {
            double y1 = Main.HEIGHT / 2 - func1(x / SCALE_X) * SCALE_Y;
            double y2 = Main.HEIGHT / 2 - func2(x / SCALE_X) * SCALE_Y;
            double y3 = Main.HEIGHT / 2 - func3(x / SCALE_X) * SCALE_Y;
            g2.drawLine((int)lastX, (int)lastY1, (int)x, (int)y1);
            g2.drawLine((int)lastX, (int)lastY2, (int)x, (int)y2);
            g2.drawLine((int)lastX, (int)lastY3, (int)x, (int)y3);
            lastX = x;
            lastY1 = y1;
            lastY2 = y2;
            lastY3 = y3;
        }
    }
   /* @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Edge e : polygon.getEdges()) {
            g.setColor(Color.BLACK);
            g.drawLine((int)e.getA().getX()*10, (int)e.getA().getY()*10, (int)e.getB().getX()*10, (int)e.getB().getY()*10);
        }
    }*/
}
