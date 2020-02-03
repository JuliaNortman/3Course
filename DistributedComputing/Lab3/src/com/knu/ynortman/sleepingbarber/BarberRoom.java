package com.knu.ynortman.sleepingbarber;

import java.util.concurrent.Semaphore;

public class BarberRoom {
    private Semaphore barber;
    private Semaphore waitingRoom;
    private WaitingRoom room;

    public BarberRoom(Semaphore barber, Semaphore waitingRoom, WaitingRoom room) throws InterruptedException {
        this.barber = barber;
        this.waitingRoom = waitingRoom;
        this.room = room;
    }

    public void makeHairCut() throws InterruptedException {
        while (true) {
            /*
            * Checks whether there are visitors in the waiting room
            * If no barber will sleep
            * Else he will continue working*/
            waitingRoom.acquire();
            //increase chairs number
            room.releaseSeat();
            String visitor = room.getVisitor();
            System.out.println("Barber has invited client " + visitor);
            Thread.sleep(1000);
            System.out.println("Barber has just served client " + visitor);
            /*
            * Barber finished his work
            * If there are visitors who wait for barber then they will be notificated
            * and barber will take another visitor
            * */
            barber.release();
        }
    }
}
