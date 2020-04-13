package common;

import java.awt.*;

/**
 * Edge have 3 statuses:
 * - sleeping - the edge is not yet detected by the algorithm
 * - alive - the edge is detected, but we know only one adjacent region
 * - dead - the edge is detected and we know two adjacent regions
 *
 */
public class Edge {
    private Point from;
    private Point to;
    private int status;

    public Edge() {
        status = 0;
    }

    public Edge(Point from, Point to) {
        setFrom(from);
        setTo(to);
        setDead();
    }

    public boolean isAlive() {
        return status == 1;
    }

    public boolean isDead() {
        return status == 0;
    }

    public void setAlive() {
        status = 1;
    }

    private void setDead() {
        status = 0;
    }

    public Point getFrom() {
        return from;
    }

    public void setFrom(Point from) {
        this.from = from;
    }

    public Point getTo() {
        return to;
    }

    public void setTo(Point to) {
        this.to = to;
    }

    public Edge rotate() {
        Point m = Computations.multiply(0.5, Computations.pointAddition(from, to));
        Point v = Computations.pointDifference(to, from);
        Point n = new Point(v.y, -v.x);
        Point from = Computations.pointDifference(m, Computations.multiply(0.5, n));
        Point to =  Computations.pointAddition(m, Computations.multiply(0.5, n));
        return new Edge(from, to);
    }

    public Edge flip() {
        Point temp = from;
        from = to;
        to = temp;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Edge.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Edge other = (Edge) obj;
        if ((this.from == null) ? (other.from != null) : this.from != other.from) {
            return false;
        }
        if ((this.to == null) ? (other.to != null) : this.to != other.to) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.from != null ? this.from.hashCode() : 0);
        hash = 53 * hash + (this.to != null ? this.to.hashCode() : 0);
        return hash;
    }
}
