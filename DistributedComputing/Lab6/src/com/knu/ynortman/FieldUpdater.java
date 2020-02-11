package com.knu.ynortman;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FieldUpdater implements Runnable {
    FieldPanel fieldPanel;
    volatile FieldModel fieldModel;
    ReentrantReadWriteLock lock;
    int timeSleep = 300;

    FieldUpdater(FieldPanel fieldPanel, FieldModel fieldModel, ReentrantReadWriteLock lock){
        this.fieldPanel = fieldPanel;
        this.fieldModel = fieldModel;
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.writeLock().lock();
        fieldModel.swapField();
        lock.writeLock().unlock();
        fieldPanel.repaint();
        try {
            Thread.sleep(timeSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
