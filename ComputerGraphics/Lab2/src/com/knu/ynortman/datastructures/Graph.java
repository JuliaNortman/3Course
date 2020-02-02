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

    public Graph(Point[] points, boolean[][] matrix) {
        N = points.length;
        vertexes = new GVertex[N];
        edges = new GEdge[N][N];

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

        for(int i = 0; i < N; ++i) {
            List<GEdge> out = new ArrayList<>();
            List<GEdge> in = new ArrayList<>();
            for(int j = 0; j < N; ++j) {
                if(vertexes[i].compareTo(vertexes[j]) < 0) {
                    out.add(edges[i][j]);
                    System.out.println(i+" out: " + edges[i][j]);
                }
                else if(vertexes[i].compareTo(vertexes[j]) > 0) {
                    in.add(edges[i][j]);
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

    public void print() {
        for(GVertex v : vertexes) {
            System.out.println(v);
            System.out.println();
        }
    }
}
