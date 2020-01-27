package com.knu.ynortman.datastructure;

public class Line {
    private Point a;
    private Point b;

    public Point getA() {
        return a;
    }

    public void setA(Point a) {
        this.a = a;
    }

    public Point getB() {
        return b;
    }

    public void setB(Point b) {
        this.b = b;
    }

    public Line(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    public double lineEquation(Point point) {
        return ((point.getX()-a.getX())/(b.getX()-a.getX()))-((point.getY()-a.getY())/(b.getY()-a.getY()));
    }
}
