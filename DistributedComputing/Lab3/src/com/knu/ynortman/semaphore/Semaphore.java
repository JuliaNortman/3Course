package com.knu.ynortman.semaphore;

public class Semaphore {
    private final int permits;
    private final boolean fair;
    private int free;

    public Semaphore(int permits) {
        this.permits = permits;
        this.fair = false;
        this.free = permits;
    }

    public Semaphore(int permits, boolean fair) {
        this.permits = permits;
        this.free = permits;
        this.fair = fair;
    }

    public synchronized void acquire() throws InterruptedException {
        while (free == 0) {
            wait();
        }
        free--;
    }

    public synchronized void release() {
        free++;
        if(fair) {
            notify();
        }
        else {
            notifyAll();
        }
    }
}
