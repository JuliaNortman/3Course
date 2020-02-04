package com.knu.ynortman.smoker;

//import java.util.concurrent.Semaphore;

import com.knu.ynortman.semaphore.Semaphore;

public class Table {
    Semaphore table;
    private Item first;
    private Item second;
    private boolean isClosed;

    public Table() throws InterruptedException {
        table = new Semaphore(1);
        table.acquire();
    }

    public void acquire() throws InterruptedException {
        table.acquire();
    }

    public void release() {
        table.release();
    }

    public Item getFirst() {
        return first;
    }

    public void setFirst(Item first) {
        this.first = first;
    }

    public Item getSecond() {
        return second;
    }

    public void setSecond(Item second) {
        this.second = second;
    }

    public boolean compare(Item item) {
        if(item == first) {
            return true;
        }
        if(item == second) {
            return true;
        }
        return false;
    }

    public void setClosed() {
        isClosed = true;
    }

    public  boolean getClosed() {
        return this.isClosed;
    }
}
