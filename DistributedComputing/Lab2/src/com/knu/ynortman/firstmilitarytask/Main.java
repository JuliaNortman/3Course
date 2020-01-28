package com.knu.ynortman.firstmilitarytask;

import com.knu.ynortman.firstmilitarytask.soldier.Ivanov;
import com.knu.ynortman.firstmilitarytask.soldier.Necheporuk;
import com.knu.ynortman.firstmilitarytask.soldier.Petrov;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    public static void main(String[] args) {
        ArrayList<Good> warehouse = new ArrayList<>();
        Storage outdoor = new Storage("outdoor");
        Storage van = new Storage("van");
        for (int i = 0; i < 20; ++i) {
            warehouse.add(new Good(i));
        }
        AtomicBoolean warehouseEmpty = new AtomicBoolean(false);
        Thread ivanov = new Thread(new Ivanov(warehouse, outdoor));
        Thread petrov = new Thread(new Petrov(outdoor, van));
        Thread necheporuk = new Thread(new Necheporuk(van));
        ivanov.setName("Ivanov");
        petrov.setName("Petrov");
        necheporuk.setName("Necheporuk");
        ivanov.start();
        petrov.start();
        necheporuk.start();
        try {
            ivanov.join();
            petrov.join();
            necheporuk.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
