package com.knu.ynortman.sleepingbarber;

import java.util.ArrayDeque;
import java.util.concurrent.Semaphore;

public class WaitingRoom {
    private int freeChairs;
    private Semaphore accessChair;
    private Semaphore waitingRoom;
    private Semaphore barber;
    private ArrayDeque<String> visitors;

    public WaitingRoom(int chairsNum, Semaphore barber, Semaphore waitingRoom) {
        System.out.println("There are " + chairsNum + " chairs in the barbershop");
        this.freeChairs = chairsNum;
        this.accessChair = new Semaphore(1, true);
        this.barber = barber;
        this.waitingRoom = waitingRoom;
        this.visitors = new ArrayDeque<>(chairsNum);
    }

    public void  getIn() throws InterruptedException {
        accessChair.acquire();
        if(freeChairs == 0) {
            System.out.println("No free chairs! Visitor " + Thread.currentThread().getName() +
                    " has left");
            accessChair.release();
        }
        else {
            takeSit();
            System.out.println("Visitor " + Thread.currentThread().getName() +
                    " has taken a chair");
            visitors.add(Thread.currentThread().getName());
            accessChair.release();
            /*
            * Notify a barber that there is a client
            */
            waitingRoom.release();
            /*
            * Try to get a haircut
            * If the barber is free than the visitor cuts his hair
            * Else the visitor waits until barber.release
            * */
            barber.acquire();
            /*System.out.println("Visitor " + Thread.currentThread().getName() +
                    " has taken a hair cut");*/
            Thread.currentThread().interrupt();
        }
    }

    public void takeSit() {
        freeChairs--;
    }

    public void releaseSeat() {
        freeChairs++;
    }

    public String getVisitor() {
        return visitors.poll();
    }
}
