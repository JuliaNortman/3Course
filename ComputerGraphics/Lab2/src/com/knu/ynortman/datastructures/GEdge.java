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

    public double length() {
        return sqrt(pow(getBx()-getAx(), 2)+(pow(getBy()-getAy(), 2)));
    }

    public double xProectionLength() {
        return abs(getBx() - getAx());
    }

    public double cos() {
        //System.out.println(b);
        //System.out.println(a);
        if(Float.compare(b.getX(), a.getX()) < 0 && Float.compare(b.getY(), a.getY()) >= 0) {
            return -xProectionLength() / length();
        }
        if(Float.compare(b.getX(), a.getX()) > 0 && Float.compare(b.getY(), a.getY()) > 0) {
            return 1-(xProectionLength() / length());
        }

        if(Float.compare(b.getX(), a.getX()) < 0 && Float.compare(b.getY(), a.getY()) < 0) {
            return -xProectionLength() / length();
        }
        if(Float.compare(b.getX(), a.getX()) > 0 && Float.compare(b.getY(), a.getY()) <= 0) {
            return 1-(xProectionLength() / length());
        }
        return 0;
    }
}
