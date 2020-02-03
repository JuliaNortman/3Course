package com.knu.ynortman.smoker;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Mediator implements Runnable {
    private final Semaphore ready;
    private final Table table;

    public Mediator(Semaphore ready, Table table) {
        this.ready = ready;
        this.table = table;
    }

    public void putComponents() throws InterruptedException {
        ready.acquire();
        int first = 0;
        int second = 0;
        while (first == second) {
            first = new Random().nextInt(3);
            second = new Random().nextInt(3);
        }

        if(first == 0) {
            table.setFirst(Item.TOBACCO);
        }
        else if(first == 1) {
            table.setFirst(Item.PAPER);
        }
        else {
            table.setFirst(Item.MATCHES);
        }
        if(second == 0) {
            table.setSecond(Item.TOBACCO);
        }
        else if(second == 1) {
            table.setSecond(Item.PAPER);
        }
        else {
            table.setSecond(Item.MATCHES);
        }
        System.out.println("Mediator has put: " + table.getFirst() + " " + table.getSecond());
        table.release();
    }



    @Override
    public void run() {
        for (int i = 0; i < 20; ++i) {
            try {
                putComponents();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Mediator has gone away");
                table.setClosed();
            }
        }
        table.setClosed();
    }
}
