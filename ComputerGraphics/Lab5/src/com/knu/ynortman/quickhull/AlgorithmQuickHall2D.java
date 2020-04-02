package com.knu.ynortman.quickhull;

//import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
//import sun.security.x509.CertificateSubjectName;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * suppose line direct is left-to-right (from first point to second like a vector)
 * */

public class AlgorithmQuickHall2D {
    private static final double EPS = 0.000000001;
    private static final double EPS_OFFSET = 0.00000001;

    public static ArrayList<Point2D> quickHullAlgoMain(ArrayList<Point2D> points){
        int veryLeftPointIndex = 0;
        for (int i = 0; i < points.size(); i++){
            if (points.get(i).getX() < points.get(veryLeftPointIndex).getX()){
                veryLeftPointIndex = i;
            }
        }

        Point2D l0 = points.get(veryLeftPointIndex);
        Point2D r0 = new Point2D.Double(l0.getX(), (l0.getY() - EPS_OFFSET));

        ArrayList<Point2D> result = quickHall(l0, r0, points);

        //deleting r0 point
        result.remove(result.size() - 1);

        return result;
    }

    public static ArrayList<Point2D> quickHall(Point2D l, Point2D r, ArrayList<Point2D> points){
        if (points.size() <= 2){ // points contains only l and r points
            return points;
        }

        int hIndex = mostDistantPointFromLineIndex(l, r, points);
        ArrayList<Point2D> s1 = new ArrayList<>(),
                            s2 = new ArrayList<>();
        s1.add(l);
        s1.add(points.get(hIndex));
        s2.add(points.get(hIndex));
        s2.add(r);
        for (int i = 0; i < points.size(); i++){
            if (liesRelativeToLine(l, points.get(hIndex), points.get(i)) == PointRelativeToLine.LEFT){
                s1.add(points.get(i));
            }
            else if (liesRelativeToLine(points.get(hIndex), r, points.get(i)) == PointRelativeToLine.LEFT){
                s2.add(points.get(i));
            }
        }

        ArrayList<Point2D> result1 =  quickHall(l, points.get(hIndex), s1);
        ArrayList<Point2D> result2 =  quickHall(points.get(hIndex), r, s2);

        result1.remove(result1.size() - 1);

        ArrayList<Point2D> result = result1;
        result.addAll(result2);

        return result;
    }

    /**
     * Suppose the line is determined by the vector directed from lineBegin to lineEnd points.
     * Therefore the line sides are defined.
     * */
    public static PointRelativeToLine liesRelativeToLine(Point2D lineBegin, Point2D lineEnd, Point2D p){
        double expr = (lineEnd.getX() - lineBegin.getX()) * (p.getY() - lineBegin.getY()) -
                (lineEnd.getY() - lineBegin.getY()) * (p.getX() - lineBegin.getX());
        if (expr > 0.0){
            return PointRelativeToLine.LEFT;
        }
        else if (expr < 0.0){
            return PointRelativeToLine.RIGHT;
        }
        else {
            return PointRelativeToLine.LIES_ON_lINE;
        }
    }

    /**
     * Calculates the triangle square with 3 points provided.
     * Uses the Heron's formula
     * */
    public static double triangleSquare(Point2D a, Point2D b, Point2D c){
        double ab = Math.pow((Math.pow(b.getX() - a.getX(), 2.0) + Math.pow(b.getY() - a.getY(), 2.0)), 0.5);
        double bc = Math.pow((Math.pow(c.getX() - b.getX(), 2.0) + Math.pow(c.getY() - b.getY(), 2.0)), 0.5);
        double ca = Math.pow((Math.pow(a.getX() - c.getX(), 2.0) + Math.pow(a.getY() - c.getY(), 2.0)), 0.5);
        double p = (ab + bc + ca) / 2;

        return Math.pow(p*(p-ab)*(p-bc)*(p-ca), 0.5);
    }

    /**
     * The line is defined by a and b points.
     * PointSet set defines the set from which the index of found point is returned.
     * Function computes the square of all the triangles composed from 3 points:
     * a, b and the one chosen from the set.
     * The triangle with the largest square have the longest height, so the distance from the line
     * is maximum.
     * If 2 triangles have equal squares, the one with the largest baPoint angle is selected
     * */
    public static int mostDistantPointFromLineIndex (Point2D a, Point2D b, ArrayList<Point2D> pointsSet){
        double prevTriangleSquare = 0.0;
        int bestChoicePointIndex = 0;
        double currentTriangleSquare = 0.0;

        for (int i=0; i<pointsSet.size(); i++){
            currentTriangleSquare = triangleSquare(a, b, pointsSet.get(i));
            if (Math.abs(currentTriangleSquare - prevTriangleSquare) < EPS){ //equal
                if (angleBetweenTwoVectorsWithTheSameOrigin(a, b, pointsSet.get(i)) >
                        angleBetweenTwoVectorsWithTheSameOrigin(a, b, pointsSet.get(bestChoicePointIndex))){
                    bestChoicePointIndex = i;
                }
            }
            else if (currentTriangleSquare > prevTriangleSquare){
                bestChoicePointIndex = i;
                prevTriangleSquare = currentTriangleSquare;
            }
        }

        return bestChoicePointIndex;
    }

    /**
     * Where the point lies relatively to the line
     * */
    enum PointRelativeToLine{
        LEFT, RIGHT, LIES_ON_lINE
    }

    /**
     * calculates the angle between vectors ab1 and ab2
     * angle is between 0 and 180 degrees
     * the value is returned in radians (between 0 and Pi)
     * */
    public static double angleBetweenTwoVectorsWithTheSameOrigin(Point2D a, Point2D b1, Point2D b2){
        double vectModab1 = Math.pow(Math.pow(b1.getX() - a.getX(), 2) + Math.pow(b1.getY() - a.getY(), 2), 0.5);
        double vectModab2 = Math.pow(Math.pow(b2.getX() - a.getX(), 2) + Math.pow(b2.getY() - a.getY(), 2), 0.5);

        return Math.acos(((b1.getX() - a.getX())*(b2.getX() - a.getX()) + (b1.getY() - a.getY())*(b2.getY() - a.getY()))
                / (vectModab1 * vectModab2));
    }
}