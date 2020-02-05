package com.knu.ynortman.sleepingbarber;

import java.util.concurrent.Semaphore;
//import com.knu.ynortman.semaphore.Semaphore;

public class BarberRoom {
    private Semaphore barber;
    private WaitingRoom room;

    public BarberRoom() throws InterruptedException {
        barber = new Semaphore(1, true);
    }

    public void setWaitingRoom(WaitingRoom room) {
        this.room = room;
    }

    public void makeHairCut() throws InterruptedException {
        while (true) {
            /*
            * Checks whether there are visitors in the waiting room
            * If no barber will sleep
            * Else he will continue working*/
            room.acquire();
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

    public void acquire() throws InterruptedException {
        barber.acquire();
    }

    public void release() {
        barber.release();
    }
}
