package com.knu.ynortman.soldiers;

public class Main {
    public static void main(String[] args) {
        final int threadNum = 5;
        final Formation formation = new Formation(500, threadNum);
        for(int i = 0; i < threadNum; ++i) {
            new Thread(new Worker(formation, i*100, (i+1)*100 - 1)).start();
        }
    }
}
