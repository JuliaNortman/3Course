package com.knu.ynortman.firstmilitarytask.soldier;

import com.knu.ynortman.firstmilitarytask.Good;
import com.knu.ynortman.firstmilitarytask.Storage;


public class Necheporuk implements Runnable {
    private Storage van;
    public Necheporuk(Storage van) {
        this.van = van;
    }

    @Override
    public void run() {
        while (!van.isFinished() || !van.isEmpty()) {
            Good good = van.get();
        }
    }
}
