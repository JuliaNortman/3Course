package com.knu.ynortman;

import com.knu.ynortman.datastructures.Graph;
import com.knu.ynortman.datastructures.Point;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Point[] points = {
                new Point(3, -5),
                new Point(-5, 0),
                new Point(4, 1),
                new Point(1, 2),
                new Point(-1, 6)};

        boolean[][] matrix = {
                {false, true, true, true, false},
                {true, false, false, true, true},
                {true, false, false, false, true},
                {true, true, false, false, false},
                {false, true, true, false, false}};

        /*Point[] points = {
                new Point(-7, -1),
                new Point(7, 3),
                new Point(0, 8)
        };

        boolean[][] matrix = {
                {false, true, true},
                {true, false, true},
                {true, true, false}
        };*/

        Graph g = new Graph(points, matrix);
    }
}
