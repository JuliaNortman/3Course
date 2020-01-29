package com.knu.ynortman.firstPart;

public class SimpleThreadTask {
    private Slider slider;
    private Thread t1;
    private Thread t2;
    private Runnable right;
    private Runnable left;

    public SimpleThreadTask() {
        slider = new Slider();
        right = () -> {
            while(true) {
                slider.moveOnePositionRight();
            }
        };

        left = () -> {
            while(true) {
                slider.moveOnePositionLeft();
            }
        };

        slider.setLeftDecrActionListener(this::decrLeftPriority );
        slider.setLeftIncrActionListener(this::incrLeftPriority);
        slider.setRightDecrActionListener(this::decrRightPriority);
        slider.setRightIncrActionListener(this::incrRightPriority);
    }

    public void execute() {
        t1 = new Thread(right, "Thread1");
        t2 = new Thread(left,  "Thread2");
        t1.setDaemon(true);
        t2.setDaemon(true);
        t1.start();
        t2.start();
    }

    public void incrRightPriority() {
        if(t1.getPriority() < 10) {
            t1.setPriority(t1.getPriority() + 1);
        }
        slider.setRightLableText(Integer.toString(t1.getPriority()));
    }
    public void incrLeftPriority() {
        if(t2.getPriority() < 10) {
            t2.setPriority(t2.getPriority() + 1);
        }
        slider.setLeftLableText(Integer.toString(t2.getPriority()));
    }
    public void decrRightPriority() {
        if(t1.getPriority() > 1) {
            t1.setPriority(t1.getPriority() - 1);
        }
        slider.setRightLableText(Integer.toString(t1.getPriority()));
    }
    public void decrLeftPriority() {
        if(t2.getPriority() > 1) {
            t2.setPriority(t2.getPriority() - 1);
        }
        slider.setLeftLableText(Integer.toString(t2.getPriority()));
    }
}
