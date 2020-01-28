package com.knu.ynortman.view;

import com.knu.ynortman.datastructure.Edge;
import com.knu.ynortman.datastructure.Point;
import com.knu.ynortman.datastructure.Polygon;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class WindowComponent extends JPanel {
    private Polygon polygon;

    /*public WindowComponent(Polygon polygon) {
        this.polygon = polygon;
    }*/

    public WindowComponent(Polygon polygon, Point point) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int taskBarHeight = 40;
        int screenHeight = screenSize.height - taskBarHeight;
        int screenWidth = screenSize.width;
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(screenWidth, screenHeight));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(){
            Graphics2D g2;

            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g2 = (Graphics2D) g;
                g2.translate(screenWidth / 2,screenHeight / 2);
                g2.scale(1.0, -1.0);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g.setColor(new Color(195,0,205));
                g2.draw(new Line2D.Double(-1000,0,1000,0));
                g2.draw(new Line2D.Double(0,-1000,0,1000));

                ArrayList<Line2D> edges = transformEdges(polygon);
                g2.setColor(Color.BLACK);
                for (Line2D l : edges){
                    g2.draw(l);
                }
                g2.setColor(Color.GREEN);
                g2.drawOval((int)(point.getX()*10), (int)(point.getY()*10), 2, 2);
            }
        };

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    public ArrayList<Line2D> transformEdges(Polygon polygon) {
        ArrayList<Line2D> edges = new ArrayList<>();
        for(Edge e : polygon.getEdges()) {
            edges.add(new Line2D.Double(e.getA().getX()*10, e.getA().getY()*10, e.getB().getX()*10, e.getB().getY()*10));
        }
        return edges;
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
