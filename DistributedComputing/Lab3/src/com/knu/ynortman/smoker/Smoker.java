package com.knu.ynortman.smoker;

import java.util.concurrent.Semaphore;

public class Smoker implements Runnable {
    private final Item item;
    private final Semaphore ready;
    private final Table table;

    public Smoker(Item item, Semaphore ready, Table table) {
        this.item = item;
        this.ready = ready;
        this.table = table;
    }

    public void smoke() throws InterruptedException {
        table.acquire();
        if(!table.getClosed()) {
            if (table.compare(item)) {
                table.release();
            } else {
                System.out.println("Smoker " + Thread.currentThread().getName() +
                        " is smoking...");
                Thread.sleep(1000);
                ready.release();
            }
        }
        else {
            Thread.currentThread().interrupt();
            table.release();
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                smoke();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }
}
