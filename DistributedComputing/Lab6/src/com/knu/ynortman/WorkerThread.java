package com.knu.ynortman;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class WorkerThread extends Thread {
    private volatile FieldModel fieldModel;
    private CyclicBarrier barrier;
    private ReentrantReadWriteLock lock;
    private int types;
    private int start;
    private int finish;

    WorkerThread(FieldModel fieldModel, CyclicBarrier barrier, ReentrantReadWriteLock lock, int start, int finish, int types){
        this.fieldModel = fieldModel;
        this.barrier = barrier;
        this.lock = lock;
        this.types = types;
        this.start = start;
        this.finish = finish;
    }

    @Override
    public void run() {
        while (!isInterrupted()){
            lock.readLock().lock();
            fieldModel.simulate(types, start, finish);
            lock.readLock().unlock();
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                this.interrupt();
            }
        }
    }
}
