package com.knu.ynortman.datastructure;

import static java.lang.Math.*;

public class Edge {
    private Point a;
    private Point b;
    private boolean alreadyUsed;

    public Edge() {}
    public Edge(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    public Edge(Double aX, Double aY, Double bX, Double bY) {
        this.a = new Point(aX, aY);
        this.b = new Point(bX, bY);
    }

    public Point getA() {
        return this.a;
    }
    public Point getB() {
        return  this.b;
    }

    public void setA(Point a) {
        this.a = a;
    }

    public void setB(Point b) {
        this.b = b;
    }

    public boolean isAlreadyUsed() {
        return alreadyUsed;
    }

    public void setAlreadyUsed(boolean alreadyUsed) {
        this.alreadyUsed = alreadyUsed;
    }

    /*
    * Перевірка чи перетинається ребро з горизонтальною прямою
    * проведеною через точку з координатою у
    *
    * Check whether edge intersects with horizontal line that
    * has y coordinate
    */
    public Intersection isHorizontalLineIntersect(double y) {
        if(abs(y - a.getY()) < 0.001 || abs(y - b.getY()) < 0.001) {
            return Intersection.VERTEX;
        }
        if(a.getY() < y && b.getY() < y) {
            return Intersection.NO_INTERSECTION;
        }
        if(a.getY() > y && b.getY() > y) {
            return Intersection.NO_INTERSECTION;
        }
        return Intersection.INTERSECTION;
    }


    /*
    * Повертає координату х, у якій дане ребро перетинається з горизонтальною
    * прямою, що проходить через точку з ординатою у
    *
    * Returns x coordinate in which edge intersects the horizontal line
    * that has y coordinate
    *
    * Formula
    * (x-x1)/(x2-x1) = (y-y1)/(y2-y1)  ==>
    * x = (((x2-x1)*(y-y1)) / (y2-y1)) + x1
    *
    */
    public double getXHorizontalIntersection(double y) {
        return (((b.getX()-a.getX())*(y - a.getY())/(b.getY()-a.getY()))+a.getX());
    }

    public boolean pointBelongsToEdge(Point point) {
        if (Math.abs(point.getX() - (((b.getX()-a.getX())*(point.getY() - a.getY())
                /(b.getY()-a.getY()))+a.getX())) < 0.001) {
            if((point.getX() <= b.getX() && point.getX() >= a.getX()) ||
                    (point.getX() >= b.getX() && point.getX() <= a.getX())) {
                if((point.getY() <= b.getY() && point.getY() >= a.getY()) ||
                        (point.getY() >= b.getY() && point.getY() <= a.getY())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "a=" + a.toString() +
                ", b=" + b.toString() +
                '}';
    }

    /*
    * Checks whether the given line intersects edge
    *
    * Line splits the plane into two half-planes
    * In all points of first half-plane the line equation will be > 0
    * In all points of other half-plane the line equation will be < 0 */
    public boolean intersectsLine(Line line) {
        double first = line.lineEquation(a);
        double second = line.lineEquation(b);
        if(first*second <= 0) {
            return true;
        }
        return false;
    }
}
