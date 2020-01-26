package com.knu.ynortman.datastructure;

import java.util.ArrayList;

public class Polygon {
    private ArrayList<Edge> edges;

    public Polygon() {
        edges = new ArrayList<>();
    }
    public Polygon(ArrayList<Edge> edges) {
        if(edges != null) {
            this.edges = edges;
        }
        else {
            this.edges = new ArrayList<>();
        }
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }
}
