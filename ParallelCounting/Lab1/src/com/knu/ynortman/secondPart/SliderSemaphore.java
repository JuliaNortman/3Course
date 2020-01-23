package com.knu.ynortman.secondPart;

import com.knu.ynortman.Slider;

import javax.swing.*;

public class SliderSemaphore extends Slider {
    private JButton startRight;
    private JButton startLeft;
    private JButton stopRight;
    private JButton stopLeft;
    private JLabel warningLabel;

    public SliderSemaphore() {
        super();
        startRight = new JButton("Start right");
        startLeft = new JButton("Start left");
        stopRight = new JButton("Stop right");
        stopLeft = new JButton("Stop left");
        warningLabel = new JLabel();

        panel.add(startLeft);
        panel.add(startRight);
        panel.add(stopLeft);
        panel.add(stopRight);
        panel.add(warningLabel);
    }

    public void setWarning() {
        warningLabel.setText("SEMAPHORE IS BUSY!");
    }

    public void clearWarning() {
        warningLabel.setText("");
    }

    public synchronized void setStartRightActionListener(Runnable r) {
        startRight.addActionListener(actionEvent -> r.run());
    }
    public synchronized void setStartLeftActionListener(Runnable r) {
        startLeft.addActionListener(actionEvent -> r.run());
    }
    public synchronized void setStopRightActionListener(Runnable r) {
        stopRight.addActionListener(actionEvent -> r.run());
    }
    public synchronized void setStopLeftActionListener(Runnable r) {
        stopLeft.addActionListener(actionEvent -> r.run());
    }
}
