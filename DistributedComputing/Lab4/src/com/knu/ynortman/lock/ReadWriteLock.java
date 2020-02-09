package com.knu.ynortman.rwlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReadWriteLock {
    private int readerWait; //number of waiting readers
    private int writerWait; //number of waiting writers
    private int readerActive; //number of active readers
    private int writerActive; //number of active writers
    private ReentrantLock readerLock;
    private ReentrantLock writerLock;
    private Condition readerCond;
    private Condition writerCond;

    public ReadWriteLock() {
        readerWait = 0;
        writerWait = 0;
        readerActive = 0;
        writerActive = 0;
        readerLock = new ReentrantLock();
        writerLock = new ReentrantLock();
        readerCond = readerLock.newCondition();
        writerCond = writerLock.newCondition();
    }

    public void startWrite() throws InterruptedException {
        writerLock.lock();
        writerWait++;
        while(writerActive == 1 || readerWait > 0) {
            writerCond.await();
        }
        writerWait--;
        writerActive = 1;
        writerLock.unlock();
    }

    public void endWrite() {
        writerLock.lock();
        writerActive = 0;
        if(readerWait > 0) {
            readerLock.lock();
            readerCond.signalAll();
            readerLock.unlock();
        }
        else {
            readerLock.lock();
            readerCond.signal();
            readerLock.unlock();
        }
        writerLock.unlock();
    }

    public void startRead() throws InterruptedException {
        readerLock.lock();
        readerWait++;
        while(writerActive == 1 || writerWait > 0) {
            readerCond.await();
        }
        readerWait--;
        readerActive++;
        readerCond.signalAll();
        readerLock.unlock();
    }

    public void endRead() {
        readerLock.lock();
        readerActive--;
        if(readerActive == 0) {
            writerLock.lock();
            writerCond.signal();
            writerLock.unlock();
        }
        readerLock.unlock();
    }
}
