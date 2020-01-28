package com.knu.ynortman.firstmilitarytask;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Storage {
    private ArrayList<Good> storage;
    private ReentrantLock lock;
    private Condition cond;
    private String name;
    private AtomicBoolean finish;

    public Storage(String name) {
        this.name = name;
        storage = new ArrayList<>();
        lock = new ReentrantLock();
        cond = lock.newCondition();
        finish = new AtomicBoolean(false);
    }

    public void put(Good good) {
        lock.lock();
        if(good != null) {
            storage.add(good);
            System.out.println(Thread.currentThread().getName() + " put " + good.toString() + " to " + name);
            cond.signalAll();
        }
        lock.unlock();
    }

    public void setFinish() {
        lock.lock();
        finish.set(true);
        cond.signalAll();
        lock.unlock();
    }

    public Good get() {
        lock.lock();
        Good good = null;
        try {
            while (storage.size() < 1 && !finish.get()) {
                cond.await();
            }
            if(storage.size() > 0) {
                good = storage.get(storage.size() - 1);
                storage.remove(storage.size() - 1);
                System.out.println(Thread.currentThread().getName() + " get " + good.toString() + " from " + name);
                //cond.signalAll();
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
            return good;
        }
    }

    public boolean isFinished() {
        return finish.get();
    }

    public boolean isEmpty() {
        return storage.size() < 1;
    }
}
