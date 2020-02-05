package com.knu.ynortman.bustask;

public class Main {
    public static void main(String[] args) {
        Graph g = new Graph();
        g.addCities("Kyiv");
        g.addCities("Moscow");
        g.addCities("Minsk");
        g.addCities("London");
        g.changePrice("Moscow", "London", 15);
        g.printGraph();
    }
}
