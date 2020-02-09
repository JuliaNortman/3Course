package com.knu.ynortman.datastructures;

import static java.lang.Math.*;

public class GEdge {
    private final GVertex a;
    private final GVertex b;
    private int weight;

    public GEdge(GVertex a, GVertex b) {
        this.a = a;
        this.b = b;
        weight = 1;
    }
    public GVertex getA() {
        return a;
    }
    public GVertex getB() {
        return b;
    }
    public float getAx() {
        return a.getX();
    }
    public float getBx() {
        return b.getX();
    }
    public float getAy() {
        return a.getY();
    }
    public float getBy() {
        return  b.getY();
    }
    public int getWeight() {
        return this.weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double length() {
        return sqrt(pow(getBx()-getAx(), 2)+(pow(getBy()-getAy(), 2)));
    }

    public double xProectionLength() {
        return abs(getBx() - getAx());
    }

    public double cos() {
        if(Float.compare(b.getX(), a.getX()) < 0 && Float.compare(b.getY(), a.getY()) >= 0) {
            return -xProectionLength() / length();
        }
        if(Float.compare(b.getX(), a.getX()) > 0 && Float.compare(b.getY(), a.getY()) > 0) {
            return (xProectionLength() / length());
        }

        if(Float.compare(b.getX(), a.getX()) < 0 && Float.compare(b.getY(), a.getY()) < 0) {
            return -xProectionLength() / length();
        }
        if(Float.compare(b.getX(), a.getX()) > 0 && Float.compare(b.getY(), a.getY()) <= 0) {
            return (xProectionLength() / length());
        }
        return 0;
    }

    public boolean pointIsYBetween(Point point) {
        if(Float.compare(point.getY(), a.getY()) <= 0 && Float.compare(point.getY(), b.getY()) >= 0
                && Float.compare(a.getY(), b.getY()) >= 0) {
            return true;
        }
        if(Float.compare(point.getY(), a.getY()) >= 0 && Float.compare(point.getY(), b.getY()) <= 0
            && Float.compare(a.getY(), b.getY()) <= 0) {
            return true;
        }
        return false;
    }

    public double equation(Point point) {
        if(Float.compare(a.getX(), b.getX()) == 0) {
            return point.getX() - a.getX();
        }
        if(Float.compare(a.getY(), b.getY()) == 0) {
            return point.getY() - a.getY();
        }
        return ((point.getX()-a.getX())/(b.getX() - a.getX())) - ((point.getY()-a.getY())/(b.getY()-a.getY()));
    }

    @Override
    public String toString() {
        return "GEdge{" +
                "a=" + a +
                ", b=" + b +
                ", weight=" + weight +
                '}';
    }
}
