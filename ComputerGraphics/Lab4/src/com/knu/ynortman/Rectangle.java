package com.knu.ynortman;

public class Rectangle {
    private Float left, right, top, bottom;

    public Rectangle() {
        left = bottom = Float.NEGATIVE_INFINITY;
        right = top = Float.POSITIVE_INFINITY;
    }

    public Rectangle(Float left, Float right, Float top, Float bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public Float getLeft() {
        return left;
    }

    public void setLeft(Float left) {
        this.left = left;
    }

    public Float getRight() {
        return right;
    }

    public void setRight(Float right) {
        this.right = right;
    }

    public Float getTop() {
        return top;
    }

    public void setTop(Float top) {
        this.top = top;
    }

    public Float getBottom() {
        return bottom;
    }

    public void setBottom(Float bottom) {
        this.bottom = bottom;
    }

    public boolean isPointInside(Point point) {
        if(Float.compare(point.getX(), top) <= 0 &&
                Float.compare(point.getX(), bottom) >= 0 &&
                Float.compare(point.getY(), left) >= 0 &&
                Float.compare(point.getY(), right) <= 0) {
            return true;
        }
        return false;
    }
}
