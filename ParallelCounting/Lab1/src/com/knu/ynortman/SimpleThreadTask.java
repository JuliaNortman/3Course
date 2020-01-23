package com.knu.ynortman;

public class SimpleThreadTask {
    private Slider slider;
    private Thread t1;
    private Thread t2;

    public SimpleThreadTask() {
        slider = new Slider();
        Runnable right = () -> {
            while(true) {
                slider.moveOnePositionRight();
            }
        };

        Runnable left = () -> {
            while(true) {
                slider.moveOnePositionLeft();
            }
        };
        t1 = new Thread(right, "Thread1");
        t2 = new Thread(left,  "Thread2");
        slider.setLeftDecrActionListener(this::decrLeftPriority );
        slider.setLeftIncrActionListener(this::incrLeftPriority);
        slider.setRightDecrActionListener(this::decrRightPriority);
        slider.setRightIncrActionListener(this::incrRightPriority);
    }

    public void execute() {

        t1.start();
        t2.start();
        /*You can set the thread priority here*/
        //t2.setPriority(8);
    }

    public synchronized void incrRightPriority() {
        if(t1.getPriority() < 10) {
            t1.setPriority(t1.getPriority() + 1);
        }
        slider.setRightLableText(Integer.toString(t1.getPriority()));
    }
    public synchronized void incrLeftPriority() {
        if(t2.getPriority() < 10) {
            t2.setPriority(t2.getPriority() + 1);
        }
        slider.setLeftLableText(Integer.toString(t2.getPriority()));
    }
    public synchronized void decrRightPriority() {
        if(t1.getPriority() > 1) {
            t1.setPriority(t1.getPriority() - 1);
        }
        slider.setRightLableText(Integer.toString(t1.getPriority()));
    }
    public synchronized void decrLeftPriority() {
        if(t2.getPriority() > 1) {
            t2.setPriority(t2.getPriority() - 1);
        }
        slider.setLeftLableText(Integer.toString(t2.getPriority()));
    }
}
