package com.knu.ynortman.readerwritertask;

public class PhonesReader implements Runnable {
    private Database db;

    public PhonesReader(Database db) {
        this.db = db;
    }

    @Override
    public void run() {
        for (int i = 0; i < 12 && !Thread.currentThread().isInterrupted(); ++i) {
            try {
                db.readPhone("name1");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
