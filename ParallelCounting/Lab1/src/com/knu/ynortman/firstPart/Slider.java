package com.knu.ynortman.firstPart;

import javax.swing.*;
import java.awt.*;

public class Slider extends JFrame {
    protected JButton incrLeft;
    protected JButton incrRight;
    protected JButton decrLeft;
    protected JButton decrRight;
    private JLabel rightLabel;
    private JLabel leftLabel;

    protected JSlider slider;
    protected JPanel panel;

    public Slider() {
        // Create and set up a frame window
        JFrame.setDefaultLookAndFeelDecorated(true);
        setPreferredSize(new Dimension(550, 300));
        //setLayout(new GridLayout(3, 7));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the panel to add buttons
        panel = new JPanel();
        // Different settings on the sliders
        slider = new JSlider();
        slider.setBounds(190, 5, 150, 50);
        incrLeft = new JButton("+");
        incrLeft.setBounds(60, 5, 50, 50);
        incrRight = new JButton("+");
        incrRight.setBounds(340, 5, 50, 50);
        decrLeft = new JButton("-");
        decrLeft.setBounds(115, 5, 50, 50);
        decrRight = new JButton("-");
        decrRight.setBounds(395, 5, 50, 50);
        rightLabel = new JLabel();
        rightLabel.setBounds(470, 5, 50, 50);
        rightLabel.setText("5");
        leftLabel = new JLabel();
        leftLabel.setBounds(25, 5, 50, 50);
        leftLabel.setText("5");

        // Add the slider to the panel
        panel.setLayout(null);
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
