package com.knu.ynortman;

public class Point {
    private final float x;
    private final float y;
    private final int id;

    private static int ID = 1;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
        id = ID;
        ID++;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }
}
