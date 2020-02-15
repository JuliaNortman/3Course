package com.knu.ynortman.datastructures;

import static java.lang.Math.*;

public class GEdge {
    private final GVertex a;
    private final GVertex b;
    private final Point middle;
    private String name;

    public GEdge(GVertex a, GVertex b) {
        this.a = a;
        this.b = b;
        middle = new Point((a.getX()+b.getX())/2, (a.getY()+b.getY())/2);
    }

    public GEdge(GEdge copy) {
        this.a = copy.a;
        this.b = copy.b;
        this.middle = copy.middle;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean pointIsYBetween(Point point) {
        if(Float.compare(point.getY(), a.getY()) <= 0 && Float.compare(point.getY(), b.getY()) >= 0
                && Float.compare(a.getY(), b.getY()) >= 0) {
            return true;
        }
        if(Float.compare(point.getY(), a.getY()) >= 0 && Float.compare(point.getY(), b.getY()) <= 0
            && Float.compare(a.getY(), b.getY()) <= 0) {
            return true;
        }
        return false;
    }

    public double equation(Point point) {
        if(Float.compare(a.getX(), b.getX()) == 0) {
            return point.getX() - a.getX();
        }
        if(Float.compare(a.getY(), b.getY()) == 0) {
            return point.getY() - a.getY();
        }
        return ((point.getX()-a.getX())/(b.getX() - a.getX())) - ((point.getY()-a.getY())/(b.getY()-a.getY()));
    }

    public double getXCoordinate(float y) {
        return ((y-a.getY())*(b.getX()-a.getX()))/(b.getY()-a.getY()) + a.getX();
    }

    public double middleXInInterval(float yMin, float yMax) {
        GVertex top;
        GVertex bottom;
        if(Float.compare(a.getY(), b.getY()) > 0) {
            top = a;
            bottom = b;
        }
        else {
            top = b;
            bottom = a;
        }
        if(Float.compare(top.getY(), yMin) <= 0 || Float.compare(bottom.getY(), yMax) >= 0) {
            return Double.MAX_VALUE;
        }



        float xTop, xBottom;
        if(Float.compare(top.getY(), yMax) > 0) {
            xTop = (float)getXCoordinate(yMax);
        }
        else {
            xTop = top.getX();
        }

        if(Float.compare(bottom.getY(), yMin) < 0) {
            xBottom =(float)getXCoordinate(yMin);
        }
        else {
            xBottom = bottom.getX();
        }
        return (xTop+xBottom)/2;
    }


    //-1 - left
    //0 - intersect
    // 1 - right
    public int getSide(Point point) {
        double equation = equation(point);
        if(Double.compare(equation, 0) == 0) {
            //edge is horizontal
            if(Float.compare(getAy(), getBy()) == 0) {
                if(Float.compare(point.getX(), getAx()) < 0 && Float.compare(point.getX(), getBx()) < 0) {
                    return -1;
                }
                if(Float.compare(point.getX(), getAx()) > 0 && Float.compare(point.getX(), getBx()) > 0) {
                    return 1;
                }
            }
            return 0;
        }
        else if(Double.compare(equation, 0) > 0) {
            //(1)
            if(Float.compare(getBy(), getAy()) > 0 && Float.compare(getBx(), getAx()) >= 0) {
                return 1;
            }
            if(Float.compare(getAy(), getBy()) > 0 && Float.compare(getAx(), getBx()) >= 0) {
                return 1;
            }
            //(2)
            if(Float.compare(getBy(), getAy()) > 0 && Float.compare(getBx(), getAx()) <= 0) {
                return -1;
            }
            if(Float.compare(getAy(), getBy()) > 0 && Float.compare(getAx(), getBx()) <= 0) {
                return -1;
            }
        }
        else {
            //(1)
            if(Float.compare(getBy(), getAy()) > 0 && Float.compare(getBx(), getAx()) >= 0) {
                return -1;
            }
            if(Float.compare(getAy(), getBy()) > 0 && Float.compare(getAx(), getBx()) >= 0) {
                return -1;
            }
            //(2)
            if(Float.compare(getBy(), getAy()) > 0 && Float.compare(getBx(), getAx()) <= 0) {
                return 1;
            }
            if(Float.compare(getAy(), getBy()) > 0 && Float.compare(getAx(), getBx()) <= 0) {
                return 1;
            }
        }
        return 0;
    }

    public double cos() {
        if(Float.compare(b.getX(), a.getX()) < 0 && Float.compare(b.getY(), a.getY()) >= 0) {
            return -xProectionLength() / length();
        }
        if(Float.compare(b.getX(), a.getX()) > 0 && Float.compare(b.getY(), a.getY()) > 0) {
            return (xProectionLength() / length());
        }

        if(Float.compare(b.getX(), a.getX()) < 0 && Float.compare(b.getY(), a.getY()) < 0) {
            return -xProectionLength() / length();
        }
        if(Float.compare(b.getX(), a.getX()) > 0 && Float.compare(b.getY(), a.getY()) <= 0) {
            return (xProectionLength() / length());
        }
        return 0;
    }

    public Point getMiddle() {
        return this.middle;
    }

    @Override
    public String toString() {
        return " {" + name + "} ";
    }
}
