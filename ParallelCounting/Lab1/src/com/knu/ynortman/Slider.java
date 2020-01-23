package com.knu.ynortman;

import javax.swing.*;

public class Slider extends JFrame {
    private JButton incrLeft;
    private JButton incrRight;
    private JButton decrLeft;
    private JButton decrRight;
    private JLabel rightLabel;
    private JLabel leftLabel;

    protected JSlider slider;
    protected JPanel panel;

    public Slider() {
        // Create and set up a frame window
        JFrame.setDefaultLookAndFeelDecorated(true);
        //JFrame frame = new JFrame();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the panel to add buttons
        panel = new JPanel();
        // Different settings on the sliders
        slider = new JSlider();
        incrLeft = new JButton("+");
        incrRight = new JButton("+");
        decrLeft = new JButton("-");
        decrRight = new JButton("-");
        rightLabel = new JLabel();
        leftLabel = new JLabel();

        // Add the slider to the panel
        panel.add(leftLabel);
        panel.add(incrLeft);
        panel.add(decrLeft);
        panel.add(slider);
        panel.add(incrRight);
        panel.add(decrRight);
        panel.add(rightLabel);

        // Set the window to be visible as the default to be false
        add(panel);
        pack();
        setVisible(true);
    }

    public synchronized void  moveOnePositionRight() {
        slider.setValue(slider.getValue()+1);
        try {
            Thread.sleep(200);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void moveOnePositionLeft() {
        slider.setValue(slider.getValue()-1);
        try {
            Thread.sleep(200);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void setRightIncrActionListener(Runnable r) {
        incrRight.addActionListener(actionEvent -> r.run());
    }
    public synchronized void setLeftIncrActionListener(Runnable r) {
        incrLeft.addActionListener(actionEvent -> r.run());
    }
    public synchronized void setRightDecrActionListener(Runnable r) {
        decrRight.addActionListener(actionEvent -> r.run());
    }
    public synchronized void setLeftDecrActionListener(Runnable r) {
        decrLeft.addActionListener(actionEvent -> r.run());
    }

    public void setRightLableText(String text) {
        rightLabel.setText(text);
    }

    public void setLeftLableText(String text) {
        leftLabel.setText(text);
    }
}
