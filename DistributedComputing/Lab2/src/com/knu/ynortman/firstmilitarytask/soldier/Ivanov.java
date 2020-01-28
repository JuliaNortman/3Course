package com.knu.ynortman.firstmilitarytask.soldier;

import com.knu.ynortman.firstmilitarytask.Good;
import com.knu.ynortman.firstmilitarytask.Storage;

import java.util.ArrayList;

public class Ivanov implements Runnable {
    private ArrayList<Good> warehouse;
    private Storage outdoor;

    public Ivanov(ArrayList<Good> warehouse, Storage outdoor) {
        this.warehouse = warehouse;
        this.outdoor = outdoor;
    }

    @Override
    public void run() {
        while (warehouse.size() > 0) {
            Good good = warehouse.get(warehouse.size()-1);
            warehouse.remove(warehouse.size()-1);
            outdoor.put(good);
        }
        outdoor.setFinish();
    }
}
