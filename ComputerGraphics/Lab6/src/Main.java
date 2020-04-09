import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        PointReader pointReader = new PointReader("pointsJarvis.txt");
        ArrayList<Point2D> points = pointReader.getPoints();
        JarvisAlgo jarvisAlgo = new JarvisAlgo(points);

        ArrayList<Point2D> convexHull = jarvisAlgo.getConvexHall();
        FigureDrawer figureDrawer = new FigureDrawer("CONVEX HULL", convexHull,
                points, true);

        System.out.println("Convex hull size: " + convexHull.size());
        System.out.println("Convex hull points: ");
        for (Point2D p : convexHull) {
            System.out.println(p);
        }
    }
}
