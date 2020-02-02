package com.knu.ynortman.datastructures;

import java.util.ArrayList;
import java.util.List;

public class GVertex implements  Comparable<GVertex>{
    private final int i;
    private final Point point;
    private List<GVertex> in;
    private List<GVertex> out;
    private int inWeight;
    private int outWeight;

    public GVertex(int i, Point point) {
        this.i = i;
        this.point = point;
        this.in = new ArrayList<>();
        this.out = new ArrayList<>();
        this.inWeight = 1;
        this.outWeight = 1;
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

    public int getInWeight() {
        return  this.inWeight;
    }
    public void setInWeight(int weight) {
        this.inWeight = weight;
    }
    public int getOutWeight() {
        return  this.outWeight;
    }
    public void setOutWeight(int weight) {
        this.outWeight = weight;
    }

    public List<GVertex> getIn() {
        return in;
    }

    public List<GVertex> getOut() {
        return out;
    }

    public int getI() {
        return this.i;
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
        String s =  "GVertex{" +
                "i=" + i +
                ", point=" + point.toString() +
                ", in=[";
        for(GVertex v : in) {
            s += v.i + " ";
        }
        s += "], out=[";
        for(GVertex v : out) {
            s += v.i + " ";
        }
        s += "]}";
        return s;
    }
}
