package com.knu.ynortman.datastructures;

import java.util.ArrayList;
import java.util.List;

public class GVertex implements  Comparable<GVertex>{
    private final int i;
    private final Point point;
    private List<GVertex> in;
    private List<GVertex> out;

    public GVertex(int i, Point point) {
        this.i = i;
        this.point = point;
        in = new ArrayList<>();
        out = new ArrayList<>();
    }

    public void addIn(GVertex vertex) {
        in.add(vertex);
    }

    public void addOut(GVertex vertex) {
        out.add(vertex);
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
        return "GVertex{" +
                "i=" + i +
                ", point=" + point +
                ", in=" + in +
                ", out=" + out +
                '}';
    }
}
