package com.knu.ynortman;

public class Line {
    private final Straight straight;
    private final Direction direction;
    private Point point;

    public Line(Straight straight, Direction direction) {
        this.straight = straight;
        this.direction = direction;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Straight getStraight() {
        return straight;
    }

    public Direction getDirection() {
        return direction;
    }
}
