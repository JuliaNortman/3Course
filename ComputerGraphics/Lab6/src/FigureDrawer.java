import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Visualize a set of line segments.
 * Segment is determined with 2 points.
 * The frame shown fits your screen dimension and covers your viewport.
 * The origin of cartesian coordinate system locates in the center of your viewport.
 *
 * Point is the segment with the same ends.
 * */
public class FigureDrawer extends JPanel{
    public FigureDrawer(String title, ArrayList<Point2D> convexHullPointsOrdered, ArrayList<Point2D> allPoints, boolean showAxis) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int taskBarHeight = 40;
        int screenHeight = screenSize.height - taskBarHeight;
        int screenWidth = screenSize.width;
        JFrame frame = new JFrame();
        frame.setTitle(title);
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

                g.setColor(new Color(255,0,0));
                if (showAxis){
                    g2.draw(new Line2D.Double(-1000,0,1000,0));
                    g2.draw(new Line2D.Double(0,-1000,0,1000));
                }

                g2.setColor(Color.BLACK);
                for (int i=0; i<convexHullPointsOrdered.size()-1; i++){
                    g2.draw(new Line2D.Double(convexHullPointsOrdered.get(i), convexHullPointsOrdered.get(i+1)));
                }
                g2.draw(new Line2D.Double(convexHullPointsOrdered.get(convexHullPointsOrdered.size()-1),
                        convexHullPointsOrdered.get(0)));

                g2.setColor(new Color(0, 25, 255));
                for (Point2D p : allPoints){
                    g2.fill(new Ellipse2D.Double(p.getX() -3 , p.getY() - 3,6,6));
                }
            }
        };

        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
