package com.knu.ynortman.readerwritertask;

import com.knu.ynortman.rwlock.ReadWriteLock;

import java.io.*;
import java.util.LinkedList;
import java.util.List;


public class Database {
    private File file;
    private ReadWriteLock lock;


    public Database(File file) {
        this.file = file;
        this.lock = new ReadWriteLock();
    }



    public List<Data> readName(long phone) throws InterruptedException {
        lock.startRead();
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
        lock.endRead();
        return result;
    }

    public List<Data> readPhone(String name) throws InterruptedException {
        lock.startRead();
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
        lock.endRead();
        return result;
    }

    public void write(Data data) throws InterruptedException {
        lock.startWrite();

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
        lock.endWrite();
    }
}
