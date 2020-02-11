package com.knu.ynortman.bustask;

import com.knu.ynortman.lock.ReadWriteLock;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private class Cell {
        int from;
        int to;

        public Cell(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }

    private ArrayList<String> cities;
    private ArrayList<ArrayList<Integer>> graph;
    private ReadWriteLock lock;

    public Graph(ArrayList<String> cities, ArrayList<ArrayList<Integer>> graph) {
        this.cities = cities;
        this.graph = graph;
        this.lock = new ReadWriteLock();
    }

    public Graph() {
        this.cities = new ArrayList<>();
        this.graph = new ArrayList<>();
        this.lock = new ReadWriteLock();
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(ArrayList<String> cities) {
        this.cities = cities;
    }

    public ArrayList<ArrayList<Integer>> getGraph() {
        return graph;
    }

    public void setGraph(ArrayList<ArrayList<Integer>> graph) {
        this.graph = graph;
    }

    private Cell findCities(String cityFrom, String cityTo) {
        Cell cell = new Cell(-1, -1);
        for(int i = 0; i < cities.size(); ++i) {
            if(cities.get(i).equals(cityFrom)) {
                cell.from = i;
            }
            if(cities.get(i).equals(cityTo)) {
                cell.to = i;
            }
            if(cell.from != -1 && cell.to != -1) {
                break;
            }
        }
        return cell;
    }

    public void changePrice(String cityFrom, String cityTo, int newPrice) {
        //start writing
        Cell cell = findCities(cityFrom, cityTo);
        if(cell.from == -1 || cell.to == -1) {
            //end writing
            return;
        }
        graph.get(cell.from).set(cell.to, newPrice);
        graph.get(cell.to).set(cell.from, newPrice);
        //end writing
    }

    public void changeTrip(String cityFrom, String cityTo, int newPrice) {
        changePrice(cityFrom, cityTo, newPrice);
    }

    public void addCities(String city) {
        //start writing
        cities.add(city);
        for(int i = 0; i < graph.size(); ++i) {
            graph.get(i).add(0);
        }
        ArrayList<Integer> list = new ArrayList<>(cities.size());
        for(int i = 0; i < cities.size(); ++i) {
            list.add(0);
        }
        graph.add(list);
    }

    public void printGraph() {
        System.out.println("Cities: " + cities);

        for(int i = 0; i < graph.size(); ++i) {
            System.out.println(graph.get(i));
        }

    }

}
