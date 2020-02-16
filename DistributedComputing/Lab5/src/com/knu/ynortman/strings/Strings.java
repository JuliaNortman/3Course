package com.knu.ynortman.strings;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Strings {
    private String[] strings;
    private int[] ABQuantity;
    private CyclicBarrier barrier;

    public Strings() {
        barrier = new CyclicBarrier(4, ()->{
            ABCount();
            System.out.println("ABCount: " + Arrays.toString(ABQuantity));
            print();
        });
        ABQuantity = new int[4];
        strings = new String[4];
        for(int j = 0; j < strings.length; ++j) {
            String s = "";
            for(int i = 0; i < 3; ++i) {
                int r = new Random().nextInt(4);
                switch (r) {
                    case 0:
                        s += "A";
                        break;
                    case 1:
                        s += "B";
                        break;
                    case 2:
                        s += "C";
                        break;
                    case 3:
                        s += "D";
                        break;
                }
            }
            strings[j] = s;
        }
    }

    public String[] getStrings() {
        return strings;
    }

    public void setStrings(String[] strings) {
        this.strings = strings;
    }

    public int[] getABQuantity() {
        return ABQuantity;
    }

    public void setABQuantity(int[] ABQuantity) {
        this.ABQuantity = ABQuantity;
    }

    public void ABCount() {
        for(int i = 0; i < ABQuantity.length; ++i) {
            ABQuantity[i] = (int) strings[i].chars().filter(ch -> ch == 'A' || ch == 'B').count();
        }
    }

    public void changeLetter(int i) throws BrokenBarrierException, InterruptedException {
        int r = new Random().nextInt(strings[i].length());
        char ch;
        if(strings[i].charAt(r) == 'A') {
            ch = 'C';
        }
        else if(strings[i].charAt(r) == 'B') {
            ch = 'D';
        }
        else if(strings[i].charAt(r) == 'C') {
            ch = 'A';
        }
        else {
            ch = 'B';
        }
        strings[i] = strings[i].substring(0, r) + ch + strings[i].substring(r+1);
        barrier.await();
    }

    public void print() {
        for(String s : strings) {
            System.out.println(s);
        }
        System.out.println();
    }

    public boolean threeEquals() {
        if(ABQuantity[0] == ABQuantity[1] && ABQuantity[1] == ABQuantity[2]) {
            return true;
        }
        if(ABQuantity[0] == ABQuantity[1] && ABQuantity[1] == ABQuantity[3]) {
            return true;
        }
        if(ABQuantity[0] == ABQuantity[2] && ABQuantity[2] == ABQuantity[3]) {
            return true;
        }
        if(ABQuantity[1] == ABQuantity[2] && ABQuantity[2] == ABQuantity[3]) {
            return true;
        }
        return false;
    }
}
