package com.knu.ynortman.secondPart;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreThreadTask {
    private AtomicInteger semaphore;
    private SliderSemaphore slider;
    private Thread t1;
    private Thread t2;
    private Runnable right;
    private Runnable left;
    private volatile AtomicBoolean rightRunning;
    private volatile AtomicBoolean leftRunning;

    public SemaphoreThreadTask() {
        super();
        rightRunning = new AtomicBoolean(true);
        leftRunning = new AtomicBoolean(true);
        slider = new SliderSemaphore();
        semaphore.set(0);
        right = () -> {
            while(rightRunning.get()) {
                slider.moveOnePositionRight();
            }
        };

        left = () -> {
            while(leftRunning.get()) {
                slider.moveOnePositionLeft();
            }
        };

        slider.setStartLeftActionListener(this::startLeftThread);
        slider.setStartRightActionListener(this::startRightThread);
        slider.setStopLeftActionListener(this::stopLeftThread);
        slider.setStopRightActionListener(this::stopRightThread);
        slider.setLeftDecrActionListener(this::decrLeftPriority );
        slider.setLeftIncrActionListener(this::incrLeftPriority);
        slider.setRightDecrActionListener(this::decrRightPriority);
        slider.setRightIncrActionListener(this::incrRightPriority);
    }


    public synchronized void startRightThread() {
        if(semaphore.get() == 1) {
            slider.setWarning();
            return;
        }
        if(semaphore.compareAndSet(0, 1)) {
            rightRunning.set(true);
            t1 = new Thread(right, "Thread1");
            t1.start();
            slider.clearWarning();
        }
    }

    public synchronized void startLeftThread() {
        if(semaphore.get() == 1) {
            slider.setWarning();
            return;
        }
        if(semaphore.compareAndSet(0, 1)) {
            leftRunning.set(true);
            t2 = new Thread(left, "Thread2");
            t2.start();
            slider.clearWarning();
        }
    }

    public synchronized void stopRightThread() {
        slider.clearWarning();
        if(semaphore.get() == 0) {
            return;
        }
        if(t1 != null) {
            if(semaphore.compareAndSet(1, 0)) {
                rightRunning.set(false);
                System.out.println(rightRunning);
                t1 = null;
            }
        }
    }

    public synchronized void stopLeftThread() {
        slider.clearWarning();
        if(semaphore == 0) {
            return;
        }
        if(t2 != null) {
            semaphore = 0;
            leftRunning.set(false);
            System.out.println(leftRunning);
            t2 = null;
        }
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
    public  void decrLeftPriority() {
        if(t2.getPriority() > 1) {
            t2.setPriority(t2.getPriority() - 1);
        }
        slider.setLeftLableText(Integer.toString(t2.getPriority()));
    }

    /*public void print() {
        System.out.println("Right running: " + rightRunning);
        System.out.println("Left running: " + leftRunning);
        System.out.println();
    }*/
}
