package com.knu.ynortman;

import javax.swing.*;

public class Slider {
    private JSlider slider;

    public Slider() {
        // Create and set up a frame window
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the panel to add buttons
        JPanel panel = new JPanel();
        // Different settings on the sliders
        slider = new JSlider();

        // Add the slider to the panel
        panel.add(slider);

        // Set the window to be visible as the default to be false
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public synchronized void  moveOnePositionRight() {
        slider.setValue(slider.getValue()+1);
        try {
            Thread.currentThread().sleep(200);
        }
        catch (InterruptedException e) {}
    }

    public synchronized void moveOnePositionLeft() {
        slider.setValue(slider.getValue()-1);
        try {
            Thread.currentThread().sleep(200);
        }
        catch (InterruptedException e) {}
    }
}
