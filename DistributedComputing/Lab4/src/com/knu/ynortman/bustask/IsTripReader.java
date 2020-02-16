package com.knu.ynortman.bustask;

public class IsTripReader implements Runnable {
    private Graph graph;

    public IsTripReader(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void run() {
        boolean isTrip = false;
        String from = "Moscow";
        String to = "Kyiv";
        isTrip = graph.isTrip(from, to);
        if(isTrip) {
            System.out.println("There is a trip between " + from + " and " + to);
        }
        else {
            System.out.println("There is no trip between " + from + " and " + to);
        }
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        isTrip = false;
        from = "London";
        to = "Minsk";
        isTrip = graph.isTrip(from, to);
        if(isTrip) {
            System.out.println("There is a trip between " + from + " and " + to);
        }
        else {
            System.out.println("There is no trip between " + from + " and " + to);
        }
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        isTrip = false;
        from = "Berlin";
        to = "NY";
        isTrip = graph.isTrip(from, to);
        if(isTrip) {
            System.out.println("There is a trip between " + from + " and " + to);
        }
        else {
            System.out.println("There is no trip between " + from + " and " + to);
        }
        graph.printGraph();
    }
}
