package com.knu.ynortman.sleepingbarber;

public class Barber implements Runnable{
    private BarberRoom barberRoom;

    public Barber(BarberRoom barberRoom) {
        this.barberRoom = barberRoom;
    }

    @Override
    public void run() {
        try {
            barberRoom.makeHairCut();
        } catch (InterruptedException e) {
            System.out.println("Barber went home");
            Thread.currentThread().interrupt();
        }
    }
}
