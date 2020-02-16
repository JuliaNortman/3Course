package com.knu.ynortman.bustask;

public class ChangeTripsWriter implements  Runnable {
    private Graph graph;

    public ChangeTripsWriter(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        graph.changeTrip("NY", "Minsk", 18);
    }
}
