package com.knu.ynortman.sleepingbarber;

import java.util.concurrent.Semaphore;

public class BarberShop {
    public static void main(String[] args) throws InterruptedException {
        int chairsNumber = 5;
        Semaphore barber = new Semaphore(1, true);

        Semaphore wr = new Semaphore(chairsNumber, true);
        int permits = wr.availablePermits();
        for(int i = 0; i < permits; ++i) {
            wr.acquire();
        }

        WaitingRoom waitingRoom = new WaitingRoom(chairsNumber, barber, wr);
        BarberRoom barberRoom = new BarberRoom(barber, wr, waitingRoom);
        Thread tBarber = new Thread(new Barber(barberRoom), "Barber");
        tBarber.setDaemon(true);
        tBarber.start();

        for(int i = 0; i < 20; ++i) {
            Thread t = new Thread(new Visitor(waitingRoom), Integer.toString(i));
            t.start();
            //t.join();
        }
    }
}
