package com.knu.ynortman.datastructures;

public class Trapezium {
    private GEdge left = null;
    private GEdge right = null;
    private float minY;
    private float maxY;

    public Trapezium() {}

    public Trapezium(GEdge left, GEdge right, float minY, float maxY) {
        this.left = left;
        this.right = right;
        this.minY = minY;
        this.maxY = maxY;
    }

    public GEdge getLeft() {
        return left;
    }

    public void setLeft(GEdge left) {
        this.left = left;
    }

    public GEdge getRight() {
        return right;
    }

    public void setRight(GEdge right) {
        this.right = right;
    }

    public float getMinY() {
        return minY;
    }

    public void setMinY(float minY) {
        this.minY = minY;
    }

    public float getMaxY() {
        return maxY;
    }

    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    /*
    * 1 - belongs
    * -1 - no
    * 0 - intersection
    */
    public int edgeBelongs(GEdge e) {
        if(Float.compare(e.getAy(), minY) > 0 && Float.compare(e.getAy(), maxY) < 0 ||
                Float.compare(e.getBy(), minY) > 0  && Float.compare(e.getBy(), maxY) < 0) {
            return 1; //(1), (2), (7), (8)
        }

        if(Float.compare(e.getAy(), minY) == 0 && Float.compare(e.getBy(), maxY) == 0 ||
                Float.compare(e.getBy(), minY) == 0  && Float.compare(e.getAy(), maxY) == 0) {
            return 0; //(4)
        }
        if(Float.compare(e.getAy(), minY) == 0 && Float.compare(e.getBy(), maxY) > 0 ||
                Float.compare(e.getBy(), minY) == 0 && Float.compare(e.getAy(), maxY) > 0) {
            return 0; //(6)
        }
        if(Float.compare(e.getAy(), maxY) == 0 && Float.compare(e.getBy(), minY) < 0 ||
                Float.compare(e.getBy(), maxY) == 0 && Float.compare(e.getAy(), minY) < 0) {
            return 0; //(10)
        }
        if(Float.compare(e.getAy(), maxY) > 0 && Float.compare(e.getBy(), minY) < 0 ||
                Float.compare(e.getBy(), maxY) > 0 && Float.compare(e.getAy(), minY) < 0) {
            return 0; //(9)
        }
        return -1;
    }

    public boolean vertexBelongs(GVertex v) {
        return Float.compare(v.getY(), minY) > 0 && Float.compare(v.getY(), maxY) < 0;
    }

    @Override
    public String toString() {
        return "{" +
                "left=" + left +
                "right=" + right +
                "minY=" + minY +
                ", maxY=" + maxY +
                "}";
    }
}
