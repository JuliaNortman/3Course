package com.knu.ynortman;

import com.knu.ynortman.algorithm.Lab1Algo;
import com.knu.ynortman.datastructure.Point;
import com.knu.ynortman.view.WindowComponent;


public class Main {

    public static void main(String[] args) {
        Point point  = new Point(2, -2);
        System.out.println(Lab1Algo.pointIsInPolygon(Lab1Algo.createPolygon(), point));
        WindowComponent comp = new WindowComponent(Lab1Algo.createPolygon(), point);
    }


}
