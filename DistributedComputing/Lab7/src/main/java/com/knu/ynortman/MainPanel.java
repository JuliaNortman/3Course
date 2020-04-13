package com.knu.ynortman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainPanel extends JPanel {
    private Image backgroundImage;
    private Image targetImage;
    private Image targetReverseImage;
    private Image bulletImage;
    private Image hunterImage;
    static int targetWidth = 80;
    static int targetHeight = 80;
    private Hunter hunter;
    private final ArrayList<Target> targets;

    MainPanel(ArrayList<Target> targets, Hunter hunter){

        this.targets = targets;
        this.hunter = hunter;
        try {
            backgroundImage = new ImageIcon("src/main/resources/background.gif").getImage();
            targetImage = new ImageIcon("src/main/resources/bird.gif").getImage();
            targetReverseImage = new ImageIcon("src/main/resources/birdReverse.gif").getImage();
            bulletImage = new ImageIcon("src/main/resources/bomb.gif").getImage();
            hunterImage = new ImageIcon("src/main/resources/hunter.gif").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {

            }
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                synchronized (targets) {
                    for (Target b : targets) {
                        if (new Rectangle(b.getX(), b.getY(), targetWidth, targetHeight).contains(p.getX(), p.getY()))
                            b.setAlive(false);
                    }
                }
            }
        });
    }

    public void notifyAllTargets() {
        if (targets.isEmpty()) System.exit(1);
        for(Target t: targets){
            t.update(hunter.getBullets());
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        notifyAllTargets();
        if (backgroundImage != null) g.drawImage(backgroundImage, 0, 0, HuntHandler.width, HuntHandler.height, this);
        if (targetImage != null) {
            for (int i = 0; i < targets.size(); i++) {
                if (targets.get(i).getAlive()) {
                    if (targets.get(i).getDirection() == 1) {
                        g.drawImage(targetImage, targets.get(i).getX(), targets.get(i).getY(),
                                targetWidth, targetHeight, this);
                    }
                    else {
                        g.drawImage(targetReverseImage, targets.get(i).getX(), targets.get(i).getY(),
                                targetWidth, targetHeight, this);
                    }
                }
                else {
                    synchronized (targets){
                        targets.remove(i);
                    }
                }
            }
        }
        if (bulletImage != null)
            for (int i =0; i < hunter.getBullets().size(); i++) {
                if(hunter.getBullets().get(i).getY() < 0 || !hunter.getBullets().get(i).getRunning()){
                    try {
                        hunter.getBullets().get(i).join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    hunter.removeBullet(i);
                }
                else
                    g.drawImage(bulletImage, hunter.getBullets().get(i).getX(), hunter.getBullets().get(i).getY(), 20,20, this);
            }
        if (hunterImage != null) g.drawImage(hunterImage, hunter.getX(), hunter.getY(), 140, 140, this);
    }
}
