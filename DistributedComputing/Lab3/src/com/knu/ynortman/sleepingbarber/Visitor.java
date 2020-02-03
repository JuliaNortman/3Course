package com.knu.ynortman.sleepingbarber;

public class Visitor implements Runnable {
    WaitingRoom waitingRoom;

    public Visitor(WaitingRoom waitingRoom) {
        this.waitingRoom = waitingRoom;
    }

    @Override
    public void run() {
        try {
            waitingRoom.getIn();
        } catch (InterruptedException e) {
            System.out.println("Visitor " + Thread.currentThread().getName() +
                    "was thrown away from the barbershop");
            Thread.currentThread().interrupt();
        }
    }
}
