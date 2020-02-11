package com.knu.ynortman.lock;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ReentrantLock implements Lock {
    private boolean isLocked = false;
    private Thread lockedBy = null;
    private int lockCount = 0;
    private final ReentrantLock.Sync sync;

    public ReentrantLock() {
        this.sync = new ReentrantLock.Sync();
    }

    static final class Sync extends AbstractQueuedSynchronizer {
        final ConditionObject newCondition() {
            return new ConditionObject();
        }
    }

    public synchronized void lock() {
        while(isLocked && Thread.currentThread() != lockedBy){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isLocked = true;
        lockedBy = Thread.currentThread();
        lockCount++;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
        return false;
    }

    public synchronized void unlock(){
        if (!isLocked || lockedBy != Thread.currentThread()) {
            return;
        }
        lockCount--;
        if(lockCount == 0){
            isLocked = false;
            lockedBy = null;
            this.notify();
        }
    }

    @Override
    public Condition newCondition() {
        return this.sync.newCondition();
    }
}
