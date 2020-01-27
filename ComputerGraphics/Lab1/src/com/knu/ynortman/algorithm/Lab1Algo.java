package com.knu.ynortman.algorithm;

import com.knu.ynortman.datastructure.Edge;
import com.knu.ynortman.datastructure.Intersection;
import com.knu.ynortman.datastructure.Point;
import com.knu.ynortman.datastructure.Polygon;

public class Lab1Algo {

    public static int countLeftIntersections(Polygon polygon, Point point) {
        int L = 0; //intersection number
        for(int i = 0; i < polygon.getEdges().size(); ++i) {
            Edge e = polygon.getEdges().get(i);

            /*
            * If point belongs to the edge than it belongs to the polygon,
            * so algorithm returns odd number
            */
            if(e.pointBelongsToEdge(point)) {
                return 1;
            }
            /*
            * If there is an intersection between horizontal line and polygon`s edge
            * than increment an intersection number
            */
            if(e.isHorizontalLineIntersect(point.getY()) == Intersection.INTERSECTION &&
                e.getXHorizontalIntersection(point.getY()) < point.getX()) {
                L++;
            }
            /*
            * If horizontal line intersects polygon in vertex than it is a degenerate case
            */
            if(e.isHorizontalLineIntersect(point.getY()) == Intersection.VERTEX &&
                    e.getXHorizontalIntersection(point.getY()) <= point.getX()) {
                Point v = null; //intersection vertex point
                Edge adjacentEdge = null; //edge that intersect the given edge in the vertex v
                /*
                * Find an intersection vertex v and adjacent edge
                */
                if(Math.abs(e.getA().getY()-point.getY()) < 0.001) {
                    v = e.getA();
                    adjacentEdge = polygon.previousEdge(i);
                }
                else {
                    v = e.getB();
                    adjacentEdge = polygon.nextEdge(i);
                }

                
                if(point.equals(v)) {
                    return 2;
                }
                else if(e.pointBelongsToEdge(new Point(point.getX(), point.getY()-0.01)) &&
                    adjacentEdge.pointBelongsToEdge(new Point(point.getX(), point.getY()-0.01))) {
                    L += 2;
                }
                else if(e.pointBelongsToEdge(new Point(point.getX(), point.getY()-0.01)) ||
                        adjacentEdge.pointBelongsToEdge(new Point(point.getX(), point.getY()-0.01))) {
                    L += 1;
                }
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
