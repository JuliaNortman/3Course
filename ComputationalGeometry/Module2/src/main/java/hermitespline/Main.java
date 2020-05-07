package hermitespline;

import java.awt.geom.Point2D;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        double[][] controlPoints = {
                {1, 1},
                {2, 2},
                {3, 3},
                {5, 3},
                {6, 1},
                {7, 2},
                {8, 3},
                {9, 1},
                {10, 3} };

        HermiteSpline spline = new HermiteSpline(controlPoints);
        List<Point2D> points = spline.HermiteSpline();
        List<Point2D> splineControlPoints = spline.getControlPoints();
        scalePoints(55, points);
        scalePoints(55, splineControlPoints);
        Drawer drawer = new Drawer("Hermite Spline", points, splineControlPoints, true);
    }

    public static void scalePoints(int coef, List<Point2D> points){
        points.forEach(p -> {
            p.setLocation(p.getX() * coef, p.getY() * coef);
        });
    }
}
