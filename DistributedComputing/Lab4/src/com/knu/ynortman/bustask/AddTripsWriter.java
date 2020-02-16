package com.knu.ynortman.bustask;

public class AddTripsWriter implements Runnable {
    private Graph graph;

    public AddTripsWriter(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void run() {
        graph.addCities("Kyiv");
        graph.addCities("Berlin");
        graph.addCities("London");
        graph.addCities("NY");
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        graph.addCities("Moscow");
        graph.addCities("Minsk");
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
