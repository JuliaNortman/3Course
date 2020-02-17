package com.knu.ynortman;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(-8, 6));
        points.add(new Point(-6, -2));
        points.add(new Point(-5, 2));
        points.add(new Point(-3, 3));
        points.add(new Point(-2, -5));
        points.add(new Point(-1, 7));
        points.add(new Point(1, -4));
        points.add(new Point(2, -1));
        points.add(new Point(3, 6));
        points.add(new Point(5, 0));
        points.add(new Point(6, 5));


        KdTree kdTree = new KdTree(points);
    }
}
