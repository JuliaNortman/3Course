package com.knu.ynortman;

import com.knu.ynortman.algorithm.Lab1Algo;
import com.knu.ynortman.datastructure.Point;
import com.knu.ynortman.view.WindowComponent;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        //System.out.println(Lab1Algo.execute());
        WindowComponent comp = new WindowComponent(Lab1Algo.createPolygon(), new Point(2, -2));
    }


}
