package com.knu.ynortman;

public class Region {
    private Point bottomLeft;
    private Point topRight;

    public Region(Point bottomLeft, Point topRight) {
        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
    }

    public Point getBottomLeft() {
        return bottomLeft;
    }

    public Point getTopRight() {
        return topRight;
    }

    public boolean isPointInside(Point point) {
        if(Float.compare(point.getX(), bottomLeft.getX()) >= 0 &&
        Float.compare(point.getX(), topRight.getX()) <= 0 &&
        Float.compare(point.getY(), bottomLeft.getY()) >= 0 &&
        Float.compare(point.getY(), topRight.getY()) <= 0) {
            return true;
        }
        return false;
    }
}
