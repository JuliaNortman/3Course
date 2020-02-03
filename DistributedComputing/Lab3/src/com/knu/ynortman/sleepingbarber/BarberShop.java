package com.knu.ynortman.sleepingbarber;

import java.util.concurrent.Semaphore;

public class BarberShop {
    public static void main(String[] args) throws InterruptedException {
        int chairsNumber = 5;

        WaitingRoom waitingRoom = new WaitingRoom(chairsNumber);
        BarberRoom barberRoom = new BarberRoom();
        waitingRoom.setBarberRoom(barberRoom);
        barberRoom.setWaitingRoom(waitingRoom);

        Thread tBarber = new Thread(new Barber(barberRoom), "Barber");
        tBarber.setDaemon(true);
        tBarber.start();

        for(int i = 0; i < 20; ++i) {
            Thread t = new Thread(new Visitor(waitingRoom), Integer.toString(i));
            t.start();
        }
    }
}
