package com.knu.ynortman.readerwritertask;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class Database {
    private File file;
    private int readerWait; //number of waiting readers
    private int writerWait; //number of waiting writers
    private int readerActive; //number of active readers
    private int writerActive; //number of active writers
    private ReentrantLock readerLock;
    private ReentrantLock writerLock;
    private Condition readerCond;
    private Condition writerCond;


    public Database(File file) {
        this.file = file;
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

    public void endWriting() {
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

    public List<Data> readName(long phone) throws InterruptedException {
        startRead();
        System.out.println(Thread.currentThread().getName() + " started reading");
        List<Data> result = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Data d = new Data(line);
                if(d.getPhone() == phone) {
                    result.add(d);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " ended reading");
        endRead();
        return result;
    }

    public List<Data> readPhone(String name) throws InterruptedException {
        startRead();
        System.out.println(Thread.currentThread().getName() + " started reading");
        List<Data> result = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Data d = new Data(line);
                if(d.getName().equals(name)) {
                    result.add(d);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " ended reading");
        endRead();
        return result;
    }

    public void write(Data data) throws InterruptedException {
        startWrite();

        System.out.println(Thread.currentThread().getName() + " started writing");
        try(FileWriter writer = new FileWriter(file, true))
        {
            writer.write(data.getName() + " " + Long.toString(data.getPhone()) + "\n");
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        Thread.sleep(1500);
        System.out.println(Thread.currentThread().getName() + " ended writing");
        endWriting();
    }
}
