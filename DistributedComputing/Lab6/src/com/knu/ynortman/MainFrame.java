package com.knu.ynortman;

import javax.swing.*;
import java.awt.*;

class MainFrame extends JFrame {
    private JPanel fieldPanel = null;
    private JButton	startBtn = null;
    private JButton	stopBtn	= null;
    private MainFrame self = this;
    private JScrollPane civilScrollPane = null;
    private int scrollPane = 1;

    MainFrame() {
        super("LIFE");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        add(toolBar, BorderLayout.SOUTH);

        fieldPanel = new FieldPanel(50, 50, 11);
        add(fieldPanel);
        startBtn = new JButton("Start");
        toolBar.add(startBtn);
        stopBtn = new JButton("Stop");
        stopBtn.setEnabled(true);
        toolBar.add(stopBtn);


        final DefaultComboBoxModel<String> civilAmountModel = new DefaultComboBoxModel<>();

        civilAmountModel.addElement("1");
        civilAmountModel.addElement("2");
        civilAmountModel.addElement("3");
        //civilAmountModel.addElement("Peer");

        final JComboBox<String> civilCombo = new JComboBox<>(civilAmountModel);
        civilCombo.setSelectedIndex(0);

        civilScrollPane = new JScrollPane(civilCombo);

        toolBar.add(civilScrollPane);

        startBtn.addActionListener(e -> {
            if (civilCombo.getSelectedIndex() != -1) {
                scrollPane = Integer.parseInt(civilCombo.getItemAt(civilCombo.getSelectedIndex()));
                System.out.println(scrollPane);
            }
            float density;
            if(scrollPane == 1) {
                density = 0.2f;
            }
            else if(scrollPane == 2) {
                density = 0.5f;
            }
            else {
                density = 0.5f;
            }
            ((FieldPanel)fieldPanel).startSimulation(scrollPane, density);
            startBtn.setEnabled(false);
            stopBtn.setEnabled(true);
        });

        stopBtn.addActionListener(e -> {
            ((FieldPanel)fieldPanel).stopSimulation(startBtn);
            stopBtn.setEnabled(false);
        });
        pack();
        setVisible(true);
    }
}
