package com.knu.ynortman.datastructure;

import static java.lang.Math.*;

public class Edge {
    private Point a;
    private Point b;

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
        return  (Math.abs(point.getX() - (((b.getX()-a.getX())*(point.getY() - a.getY())
                /(b.getY()-a.getY()))+a.getX())) < 0.001);
    }
}
