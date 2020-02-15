package com.knu.ynortman.datastructures.tree;

import com.knu.ynortman.datastructures.GEdge;
import com.knu.ynortman.datastructures.Trapezium;

public class Node {
    private Node left = null;
    private Node right = null;
    private Trapezium trapezium;
    private GEdge edge;
    private Float median = null;
    private int weight = 0;
    private static int i = 0;
    private int id;

    public Trapezium getTrapezium() {
        return this.trapezium;
    }

    public GEdge getEdge() {
        return this.edge;
    }

    public Float getMedian() {
        return median;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public Node(Trapezium trapezium, int weight) {
        this.trapezium = trapezium;
        this.weight = weight;
        i++;
        id = i;
    }

    public Node(GEdge edge) {
        this.edge = edge;
        i++;
        id = i;
    }

    public Node(Float median, int weight) {
        this.median = median;
        this.weight = weight;
        i++;
        id = i;
    }

    public int getWeight() {
        return this.weight;
    }

    @Override
    public String toString() {
        String s = "";
        if(edge != null) {
            s += "Node " + id + ": " + edge.toString();
        }
        else if(trapezium != null) {
            s += trapezium.toString();
        }
        else {
            s += median.toString();
        }
        return s;
    }

    public String shape() {
        int shape;
        String s = "";
        if(edge != null) {
            s += "\"Node " + id + ": " + edge.toString();
            shape = 1;
        }
        else if(trapezium != null) {
            s += "\"" + trapezium.toString();
            shape = 2;
        }
        else {
            s += "\"" + median.toString();
            shape = 3;
        }
        s+="\"";
        if(shape == 2) {
            s += "[shape=\"rectangle\"];\n";
        }
        else if(shape == 3) {
            s += "[shape=\"triangle\"];\n";
        }

        return s;
    }
}
