package common;

import java.awt.*;
import java.util.ArrayList;


public class Computations {

    public static boolean isOnTheLeftSide(Point vectorA, Point vectorB) {
        int res = vectorA.x * vectorB.y - vectorA.y * vectorB.x;
        return res > 0;
    }

    private static boolean isBetween(int value, int from, int to) {
        return ((from <= value) && (value < to)) || ((to <= value) && (value < from));
    }

    private static boolean isPointInsideRectangle(Point point, int x, int y, int width, int height) {
        return isBetween(point.x, x, x + width) && isBetween(point.y, y, y + height);
    }

    public static Point findTheHighestPoint(ArrayList<Point> points) {
        Point highest = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            if (points.get(i).y < highest.y) {
                highest = points.get(i);
            }
        }
        return highest;
    }

    public static double distanceFromLineToPoint(Edge pointingVector, Point point) {
        double A = pointingVector.getTo().getY() - pointingVector.getFrom().getY();
        double B = - pointingVector.getTo().getX() + pointingVector.getFrom().getX();
        double C = - A * pointingVector.getFrom().getX() - B * pointingVector.getFrom().getY();
        return Math.abs(A * point.getX() + B * point.getY() + C) / Math.sqrt(A * A + B * B);
    }

    public static double radiusOfCircumscribedCircle(Polygon triangle) {
        double a = length(createVector(triangle.getVertex(0), triangle.getVertex(1)));
        double b = length(createVector(triangle.getVertex(1), triangle.getVertex(2)));
        double c = length(createVector(triangle.getVertex(2), triangle.getVertex(0)));
        double p = (a + b + c) / 2;
        return a * b * c / (4 * Math.sqrt(p * (p - a) * (p - b) * (p - c)));
    }

    public static boolean isPointInsideCircle(Point point, Polygon triangle) {
        if (point == null)
            return false;
        Point A = triangle.getVertex(0);
        Point B = triangle.getVertex(1);
        Point C = triangle.getVertex(2);
        double a = 0;
        double b = 0;
        if (A.getX() != B.getX()) {
            double k1 = (B.getX() - C.getX()) * (C.getX() - A.getX()) + (B.getY() * B.getY() - C.getY() * C.getY()) +
                    (A.getY() * A.getY() - B.getY() * B.getY()) * (B.getX() - C.getX()) / (A.getX() - B.getX());
            double k2 = (B.getY() - C.getY()) - (A.getY() - B.getY()) * (B.getX() - C.getX()) / (A.getX() - B.getX());
            b = k1 / (2 * k2);
            a = ((A.getX() * A.getX() - B.getX() * B.getX()) + (A.getY() * A.getY() - C.getY() * C.getY())
                    - 2 * b * (A.getY() - B.getY())) / (2 * (A.getX() - B.getX()));
        } else if (A.getY() != B.getY()) {
            double k1 = (B.getY() - C.getY()) * (C.getY() - A.getY()) + (B.getX() * B.getX() - C.getX() * C.getX()) +
                    (A.getX() * A.getX() - B.getX() * B.getX()) * (B.getY() - C.getY()) / (A.getY() - B.getY());
            double k2 = (B.getX() - C.getX()) - (A.getX() - B.getX()) * (B.getY() - C.getY()) / (A.getY() - B.getY());
            a = k1 / (2 * k2);
            b = ((A.getY() * A.getY() - B.getY() * B.getY()) + (A.getX() * A.getX() - C.getX() * C.getX())
                    - 2 * a * (A.getX() - B.getX())) / (2 * (A.getY() - B.getY()));
        }
        double radius = radiusOfCircumscribedCircle(triangle);
        double sum =  Math.pow(point.x - a, 2) + Math.pow(point.y - b, 2);
        return sum < radius * radius;
    }

    public static Point createVector(Point from, Point to) {
        return new Point(to.x - from.x, to.y - from.y);
    }

    public static Polygon createTriangle(Point... points) {
        Polygon triangle = new Polygon();
        triangle.addVertex(points[0]);
        triangle.addVertex(points[1]);
        triangle.addVertex(points[2]);
        return triangle;
    }

    static Point pointAddition(Point a, Point b) {
        return new Point(a.x + b.x, a.y + b.y);
    }

    static Point pointDifference(Point a, Point b) {
        return new Point(a.x - b.x, a.y - b.y);
    }

    static Point multiply(double value, Point a) {
        a.setLocation(a.getX() * value, a.getY() * value);
        return a;
    }

    public static IntersectionResult intersect(Edge firstEdge, Edge secondEdge) {
        IntersectionResult result = new IntersectionResult();
        Point a = firstEdge.getFrom();
        Point b = firstEdge.getTo();
        Point c = secondEdge.getFrom();
        Point d = secondEdge.getTo();
        Point n = new Point(pointDifference(d, c).y, pointDifference(c, d).x);
        double denom = dotProduct(n, pointDifference(b,a));
        if (denom ==0.0) {
            POSITION aclass = classify(a, secondEdge);
            if ((aclass == POSITION.LEFT) || (aclass == POSITION.RIGHT)) {
                result.setLinePosition(LINE_POSITION.PARALLEL);
                return result;
            }
            else {
                result.setLinePosition(LINE_POSITION.COLLINEAR);
                return result;
            }
        }
        double num = dotProduct(n, pointDifference(a, c));
        result.setParameter(-num / denom);
        result.setLinePosition(LINE_POSITION.SKEW);
        return result;
    }

    private static double dotProduct(Point p, Point q)
    {
        return (p.x * q.x + p.y * q.y);
    }

    public static POSITION classify(Point classified, Edge edge) {
        Point a = pointDifference(edge.getTo(), edge.getFrom());
        Point b = pointDifference(classified, edge.getFrom());
        double sa = a. x * b.y - b.x * a.y;
        if (sa > 0.0)
            return POSITION.LEFT;
        if (sa < 0.0)
            return POSITION.RIGHT;
        if ((a.x * b.x < 0.0) || (a.y * b.y < 0.0))
            return POSITION.BEHIND;
        if (length(a) < length(b))
            return POSITION.BEYOND;
        if (edge.getFrom() == classified)
            return POSITION.ORIGIN;
        if (edge.getTo() == classified)
            return POSITION.DESTINATION;
        return POSITION.BETWEEN;
    }

    private static double length(Point p) {
        return Math.sqrt(p.x * p.x + p.y * p.y);
    }


    public enum POSITION {LEFT,  RIGHT,  BEYOND,  BEHIND, BETWEEN, ORIGIN, DESTINATION}

    public enum LINE_POSITION {PARALLEL, COLLINEAR, SKEW}

    public static class IntersectionResult {
        private LINE_POSITION linePosition;
        private double parameter;

        public LINE_POSITION getLinePosition() {
            return linePosition;
        }

        void setLinePosition(LINE_POSITION linePosition) {
            this.linePosition = linePosition;
        }

        public double getParameter() {
            return parameter;
        }

        void setParameter(double parameter) {
            this.parameter = parameter;
        }
    }
}
