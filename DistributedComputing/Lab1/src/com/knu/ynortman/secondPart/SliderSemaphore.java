package com.knu.ynortman.secondPart;

import com.knu.ynortman.firstPart.Slider;

import javax.swing.*;

public class SliderSemaphore extends Slider {
    private JButton startRight;
    private JButton startLeft;
    private JButton stopRight;
    private JButton stopLeft;
    private JLabel warningLabel;

    public SliderSemaphore() {
        super();
        startRight = new JButton("Start");
        startRight.setBounds(340, 60, 70, 50);
        startLeft = new JButton("Start");
        startLeft.setBounds(20, 60, 70, 50);
        stopRight = new JButton("Stop");
        stopRight.setBounds(415, 60, 70, 50);
        stopLeft = new JButton("Stop");
        stopLeft.setBounds(95, 60, 70, 50);
        warningLabel = new JLabel();
        warningLabel.setBounds(190, 120, 150, 30);

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

    public void setStartRightActionListener(Runnable r) {
        startRight.addActionListener(actionEvent -> r.run());
    }
    public void setStartLeftActionListener(Runnable r) {
        startLeft.addActionListener(actionEvent -> r.run());
    }
    public void setStopRightActionListener(Runnable r) {
        stopRight.addActionListener(actionEvent -> r.run());
    }
    public void setStopLeftActionListener(Runnable r) {
        stopLeft.addActionListener(actionEvent -> r.run());
    }
}
