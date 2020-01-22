package com.knu.ynortman;

public class SimpleThreadTask {
    private Slider slider;

    public SimpleThreadTask() {
        slider = new Slider();
    }

    public void execute() {
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

        Thread t1 = new Thread(right);
        Thread t2 = new Thread(left);
        t1.start();
        t2.start();
        /*You can set the thread priority here*/
        //t2.setPriority(8);
    }
}
