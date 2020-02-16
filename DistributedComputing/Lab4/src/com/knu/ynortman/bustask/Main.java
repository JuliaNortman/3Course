package com.knu.ynortman.bustask;

public class Main {
    public static void main(String[] args) {
        Graph g = new Graph();
        new Thread(new IsTripReader(g)).start();
        new Thread(new AddTripsWriter(g)).start();
        new Thread(new ChangePriceWriter(g)).start();
        new Thread(new ChangeTripsWriter(g)).start();
    }
}
