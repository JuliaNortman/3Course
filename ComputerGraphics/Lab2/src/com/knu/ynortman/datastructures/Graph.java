package com.knu.ynortman.datastructures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.*;

public class Graph {
    private GVertex[] vertexes;
    private GEdge[][] edges;
    private final int N;
    private boolean isRegular;
    private List<Chain> chains;

    public Graph(Point[] points, boolean[][] matrix) {
        N = points.length;
        vertexes = new GVertex[N];
        edges = new GEdge[N][N];
        chains = new ArrayList<>();

        for(int i = 0; i < N; ++i) {
            vertexes[i] = new GVertex(i, points[i]);
        }

        for(int i = 0; i < N; ++i) {
            for(int j = 0; j < N; ++j) {
                if(matrix[i][j]) {
                    edges[i][j] = new GEdge(vertexes[i], vertexes[j]);
                }
            }
        }
        sortVertexes();
        balance();
        splitIntochains();
    }

    public void sortVertexes() {
        for(int i = 0; i < N; ++i) {
            List<GEdge> out = new ArrayList<>();
            List<GEdge> in = new ArrayList<>();
            for(int j = 0; j < N; ++j) {
                if(edges[i][j]!= null) {
                    if (vertexes[i].compareTo(vertexes[j]) < 0) {
                        out.add(edges[i][j]);
                    } else if (vertexes[i].compareTo(vertexes[j]) > 0) {
                        in.add(edges[i][j]);
                    }
                }
            }
            out.sort(Comparator.comparingDouble(GEdge::cos));
            in.sort(Comparator.comparingDouble(GEdge::cos));

            for(GEdge e : out) {
                vertexes[i].addOut(e.getB());
            }
            for(GEdge e : in) {
                vertexes[i].addIn(e.getB());
            }
        }
    }

    public void balance() {
        for(int i = 1; i < N-1; ++i) {
            int inWeight = 0;
            for (GVertex v : vertexes[i].getIn()) {
                inWeight += edges[v.getI()][i].getWeight();
            }
            vertexes[i].setInWeight(inWeight);
            GEdge d = edges[i][vertexes[i].getOut().get(0).getI()];
            if(vertexes[i].getInWeight() > vertexes[i].getOut().size()) {
                d.setWeight(vertexes[i].getInWeight() - vertexes[i].getOut().size() + 1);
                d = edges[vertexes[i].getOut().get(0).getI()][i];
                d.setWeight(vertexes[i].getInWeight() - vertexes[i].getOut().size() + 1);
            }
        }

        for(int i = N-2; i > 0; --i) {
            int outWeight = 0;
            for (GVertex v : vertexes[i].getOut()) {
                outWeight += edges[v.getI()][i].getWeight();
            }
            vertexes[i].setOutWeight(outWeight);
            GEdge d = edges[i][vertexes[i].getIn().get(0).getI()];
            if(vertexes[i].getOutWeight() > vertexes[i].getInWeight()) {
                d.setWeight(vertexes[i].getOutWeight() - vertexes[i].getInWeight() + d.getWeight());
                d = edges[vertexes[i].getIn().get(0).getI()][i];
                d.setWeight(vertexes[i].getOutWeight() - vertexes[i].getInWeight() + d.getWeight());
            }
        }
    }

    public void splitIntochains() {
        for(GVertex vNext : vertexes[0].getOut()) {
            while(edges[0][vNext.getI()].getWeight() > 0) {
                GVertex v = vertexes[0];
                chains.add(new Chain());
                while (v.getOut().size() > 0) {
                    for (GVertex out : v.getOut()) {
                        int edgeWeight = edges[v.getI()][out.getI()].getWeight();
                        if (edgeWeight > 0) {
                            edgeWeight--;
                            edges[v.getI()][out.getI()].setWeight(edgeWeight);
                            edges[out.getI()][v.getI()].setWeight(edgeWeight);
                            chains.get(chains.size() - 1).addEdge(edges[v.getI()][out.getI()]);
                            v = out;
                            break;
                        }
                    }
                }
            }
        }

        /*if(v.getOut().size() == 0) {
            chains.add(new Chain());
            return;
        }
        for(GVertex vertex : v.getOut()) {
            int outWeight = edges[vertex.getI()][v.getI()].getWeight();
            if(outWeight > 0) {
                outWeight--;
                edges[vertex.getI()][v.getI()].setWeight(outWeight);
                edges[v.getI()][vertex.getI()].setWeight(outWeight);
                chains.get(chains.size()-1).addEdge(edges[vertex.getI()][v.getI()]);
                splitIntochains(vertex);
            }
        }*/
    }

    public void print() {
        for(GVertex v : vertexes) {
            System.out.println(v);
            System.out.println();
        }
    }

    public void printEdges() {
        for(int i = 0; i < N; ++i) {
            for(int j = 0; j < N; ++j) {
                System.out.println("I="+i+" J="+j + " " + edges[i][j]);
            }
            System.out.println();
        }
    }

    public void printChains() {
        for(Chain c: chains) {
            System.out.println(c);
            System.out.println();
        }
    }
}
