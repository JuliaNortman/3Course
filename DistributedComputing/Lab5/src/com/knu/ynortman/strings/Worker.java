package com.knu.ynortman.strings;

import java.util.concurrent.BrokenBarrierException;

public class Worker implements Runnable {
    private int id;
    private Strings str;

    public Worker(int id, Strings str) {
        this.id = id;
        this.str = str;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                str.changeLetter(id);
            } catch (BrokenBarrierException | InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            if(str.threeEquals()) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
