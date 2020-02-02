package com.knu.ynortman.algorithm;

import com.knu.ynortman.datastructure.*;


public class Lab1Algo {

    public static int countLeftIntersections(Polygon polygon, Point point) {
        int L = 0; //intersection number
        for(int i = 0; i < polygon.getEdges().size(); ++i) {
            Edge e = polygon.getEdges().get(i);
            if(e.isAlreadyUsed()) {
                continue;
            }
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
            else if(e.isHorizontalLineIntersect(point.getY()) == Intersection.VERTEX &&
                    e.getXHorizontalIntersection(point.getY()) <= point.getX()) {
                Point s = null; //intersection vertex point
                Edge adjacentEdge = null; //edge that intersect the given edge in the vertex v
                /*
                * Find an intersection vertex v and adjacent edge
                */
                if(Math.abs(e.getA().getY()-point.getY()) < 0.001) {
                    s = e.getA();
                    adjacentEdge = polygon.previousEdge(i);
                }
                else {
                    s = e.getB();
                    adjacentEdge = polygon.nextEdge(i);
                }

                if(point.equals(s)) {
                    return 1;
                }
                else if(e.intersectsLine(new Line(point, new Point(s.getX(), s.getY()-0.01))) &&
                    adjacentEdge.intersectsLine(new Line(point, new Point(s.getX(), s.getY()-0.01)))) {
                    L += 2;
                }
                else if(e.intersectsLine(new Line(point, new Point(s.getX(), s.getY()-0.01))) ||
                        adjacentEdge.intersectsLine(new Line(point, new Point(s.getX(), s.getY()-0.01)))) {
                    L += 1;
                }
                adjacentEdge.setAlreadyUsed(true);
                e.setAlreadyUsed(true);
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

    public static Polygon createPolygon() {
        Polygon polygon = new Polygon();
        polygon.addEdge(new Edge(new Point(-150, 90), new Point(-90, 180)));
        polygon.addEdge(new Edge(new Point(-90, 180), new Point(-60, 120)));
        polygon.addEdge(new Edge(new Point(-60, 120), new Point(-30, 150)));
        polygon.addEdge(new Edge(new Point(-30, 150), new Point(210, 150)));
        polygon.addEdge(new Edge(new Point(210, 150), new Point(180, 90)));
        polygon.addEdge(new Edge(new Point(180, 90), new Point(210, -90)));
        polygon.addEdge(new Edge(new Point(210, -90), new Point(150, -90)));
        polygon.addEdge(new Edge(new Point(150, -90), new Point(150, -30)));
        polygon.addEdge(new Edge(new Point(150, -30), new Point(60, -30)));
        polygon.addEdge(new Edge(new Point(60, -30), new Point(60, -90)));
        polygon.addEdge(new Edge(new Point(60, -90), new Point(-30, 60)));
        polygon.addEdge(new Edge(new Point(-30, 60), new Point(-150, 90)));

        return polygon;
    }
}
