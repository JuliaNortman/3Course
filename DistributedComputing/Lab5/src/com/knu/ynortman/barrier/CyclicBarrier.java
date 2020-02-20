package com.knu.ynortman.barrier;

public class CyclicBarrier {
    private int parties;
    private Runnable runnable;
    private int arrived = 0;

    public CyclicBarrier(int parties) {
        this.parties = parties;
    }

    public CyclicBarrier(int parties, Runnable runnable) {
        this.parties = parties;
        this.runnable = runnable;
    }

    public synchronized void await() throws InterruptedException {
        if(arrived == parties-1) {
            arrived = 0;
            runnable.run();
            notifyAll();
        }
        else {
            arrived++;
            wait();
        }
    }
}
