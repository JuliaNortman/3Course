package com.knu.ynortman.kulakroutetask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        ArrayList<Monk> firstMonastery = new ArrayList<>();
        ArrayList<Monk> secondMonastery = new ArrayList<>();
        for(int i = 0; i < 9; ++i) {
            firstMonastery.add(new Monk(Monastery.GUAN_YIN, new Random().nextInt(50)));
            secondMonastery.add(new Monk(Monastery.GUAN_YANG, new Random().nextInt(50)));
        }
        System.out.println(firstMonastery);
        System.out.println(secondMonastery);
        ArrayList<Monk> list = new ArrayList<>(firstMonastery);
        list.addAll(secondMonastery);
        //System.out.println((list));
        ForkJoinPool pool = new ForkJoinPool(4);
        KulakRoute fbn = new KulakRoute(list);
        System.out.println(pool.invoke(fbn));
    }
}
