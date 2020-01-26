package com.knu.ynortman.algorithm;

import com.knu.ynortman.datastructure.Edge;
import com.knu.ynortman.datastructure.Intersection;
import com.knu.ynortman.datastructure.Point;
import com.knu.ynortman.datastructure.Polygon;

public class Lab1Algo {
    /*private Polygon polygon;
    private Point point;*/

    public static int countLeftIntersections(Polygon polygon, Point point) {
        int L = 0;
        for(Edge e : polygon.getEdges()) {
            if(e.isHorizontalLineIntersect(point.getY()) == Intersection.INTERSECTION &&
                e.getXHorizontalIntersection(point.getY()) < point.getX()) {
                L++;
            }
            if(e.isHorizontalLineIntersect(point.getY()) == Intersection.VERTEX &&
                    e.getXHorizontalIntersection(point.getY()) < point.getX()) {
                // вироджений випадок
            }
        }
        return L;
    }

    public static boolean pointIsInPolygon(Polygon polygon, Point point) {
        int L = countLeftIntersections(polygon, point);
        if(L == 0) {
            return false;
        }
        if(L % 2 == 0) {
            return false;
        }
        return true;
    }
}
