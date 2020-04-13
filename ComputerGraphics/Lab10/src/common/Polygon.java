package common;

import java.awt.*;
import java.util.ArrayList;

/**
 * Polygon - structure with list of points which represents vertexes
 * of the polygon. Vertexes are listed in order to follow.
 *
 */
public class Polygon {
    private ArrayList<Point> points;

    public Polygon() {
        points = new ArrayList<>(3);
    }

    public Point getVertex(int i) {
        return points.get(i);
    }

    public void addVertex(Point point) {
        points.add(point);
    }
}
