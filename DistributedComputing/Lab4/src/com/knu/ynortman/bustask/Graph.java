package com.knu.ynortman.bustask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
    private ReadWriteLock rwLock;
    private Lock readLock;
    private Lock writeLock;

    public Graph(ArrayList<String> cities, ArrayList<ArrayList<Integer>> graph) {
        this.cities = cities;
        this.graph = graph;
        this.rwLock = new ReentrantReadWriteLock();
        this.readLock = rwLock.readLock();
        this.writeLock = rwLock.writeLock();
    }

    public Graph() {
        this.cities = new ArrayList<>();
        this.graph = new ArrayList<>();
        this.rwLock = new ReentrantReadWriteLock();
        this.readLock = rwLock.readLock();
        this.writeLock = rwLock.writeLock();
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
        writeLock.lock();
        Cell cell = findCities(cityFrom, cityTo);
        if(cell.from == -1 || cell.to == -1) {
            writeLock.unlock();
            return;
        }
        graph.get(cell.from).set(cell.to, newPrice);
        graph.get(cell.to).set(cell.from, newPrice);
        writeLock.unlock();
    }

    /**
     * function that deletes and adds trips between cities
     * To delete a trip the ticket price should be set to zero
     * To add a trip a price should be set to a value more than zero
     */
    public void changeTrip(String cityFrom, String cityTo, int newPrice) {
        changePrice(cityFrom, cityTo, newPrice);
    }

    /**
     * Add new cities
     */
    public void addCities(String city) {
        writeLock.lock();
        cities.add(city);
        for (ArrayList<Integer> integers : graph) {
            integers.add(0);
        }
        ArrayList<Integer> list = new ArrayList<>(cities.size());
        for(int i = 0; i < cities.size(); ++i) {
            list.add(0);
        }
        graph.add(list);
        writeLock.unlock();
    }

    public void printGraph() {
        readLock.lock();
        System.out.println("Cities: " + cities);

        for (ArrayList<Integer> integers : graph) {
            System.out.println(integers);
        }
        readLock.unlock();
    }

    /*
    * Checks whether there is a direct trip between to cities
    */
    public boolean isTrip(String cityFrom, String cityTo) {
        readLock.lock();
        Cell cell = findCities(cityFrom, cityTo);
        if(cell.from == -1 || cell.to == -1) {
            readLock.unlock();
            return false;
        }
        if(graph.get(cell.from).get(cell.to) > 0) {
            readLock.unlock();
            return true;
        }
        readLock.unlock();
        return false;
    }
}
