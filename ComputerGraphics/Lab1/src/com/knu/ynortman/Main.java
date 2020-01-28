package com.knu.ynortman;

import com.knu.ynortman.algorithm.Lab1Algo;
import com.knu.ynortman.view.WindowComponent;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public static void main(String[] args) {
	// write your code here
        //System.out.println(Lab1Algo.execute());
        Main app = new Main();
        app.exec();
    }

    public void exec() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        WindowComponent comp = new WindowComponent(Lab1Algo.createPolygon());
        comp.setPreferredSize(new Dimension(320, 200));
        getContentPane().add(comp, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }


}
