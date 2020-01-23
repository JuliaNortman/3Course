package com.knu.ynortman.secondPart;


public class SemaphoreThreadTask {
    private int semaphore = 0;
    private SliderSemaphore slider;
    protected Thread t1;
    protected Thread t2;
    protected Runnable right;
    protected Runnable left;

    public SemaphoreThreadTask() {
        super();
        slider = new SliderSemaphore();
        slider.setStartLeftActionListener(this::startLeftThread);
        slider.setStartRightActionListener(this::startRightThread);
        slider.setStopLeftActionListener(this::stopLeftThread);
        slider.setStopRightActionListener(this::stopRightThread);
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


    public synchronized void startRightThread() {
        if(semaphore == 1) {
            slider.setWarning();
            return;
        }
        semaphore = 1;
        t1 = new Thread(right, "Thread1");
        t1.start();
        slider.clearWarning();
    }

    public synchronized void startLeftThread() {
        if(semaphore == 1) {
            slider.setWarning();
            return;
        }
        semaphore = 1;
        t2 = new Thread(left, "Thread2");
        t2.start();
        slider.clearWarning();
    }

    public synchronized void stopRightThread() {
        slider.clearWarning();
        if(semaphore == 0) {
            return;
        }
        if(t1 != null) {
            semaphore = 0;
            t1.stop();
            t1 = null;
        }
    }

    public synchronized void stopLeftThread() {
        slider.clearWarning();
        if(semaphore == 0) {
            return;
        }
        if(t2 != null) {
            semaphore = 0;
            t2.stop();
            t2 = null;
        }
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
