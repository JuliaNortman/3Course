package com.knu.ynortman.smoker;

import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Semaphore ready = new Semaphore(1);
        Table table = new Table();
        Thread mediator = new Thread(new Mediator(ready, table), "Mediator");
        mediator.setDaemon(true);

        Thread tobacco = new Thread(new Smoker(Item.TOBACCO, ready, table), "Tobacco");
        Thread paper = new Thread(new Smoker(Item.PAPER, ready, table), "Paper");
        Thread matches = new Thread(new Smoker(Item.MATCHES, ready, table), "Matches");

        mediator.start();
        tobacco.start();
        paper.start();
        matches.start();
    }
}
