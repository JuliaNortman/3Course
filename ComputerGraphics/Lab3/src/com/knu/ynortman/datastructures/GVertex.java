package com.knu.ynortman.datastructures;

import java.util.ArrayList;
import java.util.List;

public class GVertex implements  Comparable<GVertex>{
    private final Point point;

    public GVertex(Point point) {
        this.point = point;
    }

    public GVertex(float x, float y){
        this.point = new Point(x, y);
    }

    public float getX() {
        return point.getX();
    }

    public float getY() {
        return point.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GVertex gVertex = (GVertex) o;
        return point.equals(gVertex.point);
    }

    @Override
    public int compareTo(GVertex gVertex) {
        if(this.equals(gVertex)) {
            return 0;
        }
        if(Float.compare(getY(), gVertex.getY()) < 0) {
            return -1;
        }
        if(Float.compare(getY(), gVertex.getY()) == 0 && Float.compare(getX(), gVertex.getX()) > 0) {
            return -1;
        }
        return 1;
    }

    @Override
    public String toString() {
        return "{" + point.getX() + ", " + point.getY() + "}";
    }
}
