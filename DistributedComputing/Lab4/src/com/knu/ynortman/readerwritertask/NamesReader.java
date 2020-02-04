package com.knu.ynortman.readerwritertask;

public class NamesReader implements Runnable {
    private Database db;

    public NamesReader(Database db) {
        this.db = db;
    }

    @Override
    public void run() {
        for (int i = 0; i < 7 && !Thread.currentThread().isInterrupted(); ++i) {
            try {
                db.readName(3333);
                Thread.sleep(1100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
