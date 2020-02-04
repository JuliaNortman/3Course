package com.knu.ynortman;

import com.knu.ynortman.readerwritertask.*;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        File file = new File("database.txt");
        file.createNewFile();

        Database db = new Database(file);
        db.write(new Data("name1", 1111));
        db.write(new Data("name2", 2222));
        db.write(new Data("name12", 1111));
        Thread phoneReader = new Thread(new PhonesReader(db), "Phone Reader");
        Thread nameReader = new Thread(new NamesReader(db), "Name Reader");
        Thread writer = new Thread(new Writer(db), "Writer");
        phoneReader.start();
        nameReader.start();
        writer.start();

        /*db.write(new Data("name1", 1111));
        db.write(new Data("name2", 2222));
        db.write(new Data("name12", 1111));

        System.out.println(db.readName(1111));
        System.out.println(db.readName(0000));
        System.out.println(db.readPhone("name1"));*/
    }
}

