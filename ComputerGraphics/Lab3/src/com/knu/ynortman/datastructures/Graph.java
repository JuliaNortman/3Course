package com.knu.ynortman.datastructures;

import com.knu.ynortman.datastructures.tree.Node;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Graph {
    private List<GVertex> vertexes; //sorted by y coordinate
    private List<GEdge> edges;
    private final int N;
    private Trapezium trapezium;
    public Node myRoot;

    public Graph(Point[] points, boolean[][] matrix) throws IOException {
        N = points.length;
        vertexes = new ArrayList<>(N);
        edges = new ArrayList<>();

        for(Point p : points) {
            vertexes.add(new GVertex(p));
        }

        for(int i = 0; i < N; ++i) {
            for(int j = i; j < N; ++j) {
                if(matrix[i][j]) {
                    edges.add(new GEdge(vertexes.get(i), vertexes.get(j)));
                }
            }
        }
        vertexes.sort(Comparator.comparingDouble(GVertex::getY));
        List<GVertex> xSortedVetrtexes = new ArrayList<>(N);
        xSortedVetrtexes.addAll(vertexes);
        xSortedVetrtexes.sort(Comparator.comparingDouble(GVertex::getX));

        GEdge left = new GEdge(new GVertex(xSortedVetrtexes.get(0).getX(), vertexes.get(0).getY()),
                new GVertex(xSortedVetrtexes.get(0).getX(), vertexes.get(N-1).getY()));
        GEdge right = new GEdge(new GVertex(xSortedVetrtexes.get(N-1).getX(), vertexes.get(0).getY()),
                new GVertex(xSortedVetrtexes.get(N-1).getX(), vertexes.get(N-1).getY()));

        trapezium = new Trapezium(left, right, vertexes.get(0).getY(), vertexes.get(N-1).getY());
        edges.add(left);
        edges.add(right);
        edges.sort(Comparator.comparingDouble(obj->obj.middleXInInterval(vertexes.get(0).getY(), vertexes.get(N-1).getY())));

        for(int i = 0; i < edges.size(); ++i) {
            edges.get(i).setName("e" + i);
        }

        myRoot = buildTrapezium(vertexes, edges, trapezium);
        graphviz();
        localization(myRoot, new Point(4, -5));
    }



    public Node buildTrapezium(List<GVertex> V, List<GEdge> E, Trapezium T) {
        if(V.size() == 0) {
            //System.out.println(T);
            return new Node(T, 0); //leaf
        }

        List<List<GEdge>> Edg = new ArrayList<>(); //edges of trapezium
        List<List<GVertex>> Vert = new ArrayList<>(); //vertexes of trapezium sorted by y-coordinate
        List<List<Node>> U = new ArrayList<>(); //list of trapeziums
        Trapezium[] Tr = new Trapezium[2];
        int weight = 0;
        for(int i = 0; i < 2; ++i) {
            Edg.add(new ArrayList<>());
            Vert.add(new ArrayList<>());
            U.add(new ArrayList<>());
            Tr[i] = new Trapezium();
        }
        /*Trapezium T1 = new Trapezium();
        Trapezium T2 = new Trapezium();*/

        float yMed = V.get((V.size()-1)/2).getY(); //mediana
        Tr[0].setMinY(T.getMinY());
        Tr[0].setMaxY(yMed);
        Tr[1].setMinY(yMed);
        Tr[1].setMaxY(T.getMaxY());

        E.sort(Comparator.comparingDouble(obj->obj.middleXInInterval(Tr[0].getMinY(), Tr[0].getMaxY())));
        for(GEdge e : E) {
            for(int i = 0; i < 1; ++i) {
                int in = Tr[i].edgeBelongs(e);
                //e has end in Tr[i]
                if(in == 1) {
                    Edg.get(i).add(e);
                    if(Tr[i].vertexBelongs(e.getA())) {
                        Vert.get(i).add(e.getA());
                        weight++;
                    }
                    if(Tr[i].vertexBelongs(e.getB())) {
                        Vert.get(i).add(e.getB());
                        weight++;
                    }
                    //remove duplicates
                    Set<GVertex> set = new LinkedHashSet<>(Vert.get(i));
                    Vert.get(i).clear();
                    Vert.get(i).addAll(set);
                    Vert.get(i).sort(Comparator.comparingDouble(GVertex::getY));
                }
                //e intersects Tr[i]
                else if(in == 0 || e == Tr[i].getRight()) {
                    Edg.get(i).add(e);
                    if(Tr[i].getLeft() == null) {
                        Tr[i].setLeft(e);
                    }
                    else {
                        Tr[i].setRight(e);
                        Node n = buildTrapezium(Vert.get(i), Edg.get(i), Tr[i]);
                        U.get(i).add(n);
                        if (e != T.getRight()) {
                            U.get(i).add(new Node(e));
                        }
                        Edg.get(i).clear();
                        Vert.get(i).clear();
                        Edg.get(i).add(e);
                        Tr[i] = new Trapezium();
                        Tr[0].setMinY(T.getMinY());
                        Tr[0].setMaxY(yMed);
                        Tr[1].setMinY(yMed);
                        Tr[1].setMaxY(T.getMaxY());
                        Tr[i].setLeft(e);
                        Tr[i].setRight(null);
                    }
                }
            }
        }


        E.sort(Comparator.comparingDouble(obj->obj.middleXInInterval(Tr[1].getMinY(), Tr[1].getMaxY())));
        for(GEdge e : E) {
            for(int i = 1; i < 2; ++i) {
                int in = Tr[i].edgeBelongs(e);
                //e has end in Tr[i]
                if(in == 1) {
                    Edg.get(i).add(e);
                    if(Tr[i].vertexBelongs(e.getA())) {
                        Vert.get(i).add(e.getA());
                        weight++;
                    }
                    if(Tr[i].vertexBelongs(e.getB())) {
                        Vert.get(i).add(e.getB());
                        weight++;
                    }
                    //remove duplicates
                    Set<GVertex> set = new LinkedHashSet<>(Vert.get(i));
                    Vert.get(i).clear();
                    Vert.get(i).addAll(set);
                    Vert.get(i).sort(Comparator.comparingDouble(GVertex::getY));
                }
                //e intersects Tr[i]
                else if(in == 0 || e == Tr[i].getRight()) {
                    Edg.get(i).add(e);
                    if(Tr[i].getLeft() == null) {
                        Tr[i].setLeft(e);
                    }
                    else {
                        Tr[i].setRight(e);
                        Node n = buildTrapezium(Vert.get(i), Edg.get(i), Tr[i]);
                        U.get(i).add(n);
                        if (e != T.getRight()) {
                            U.get(i).add(new Node(e));
                        }
                        Edg.get(i).clear();
                        Vert.get(i).clear();
                        Edg.get(i).add(e);
                        Tr[i] = new Trapezium();
                        Tr[0].setMinY(T.getMinY());
                        Tr[0].setMaxY(yMed);
                        Tr[1].setMinY(yMed);
                        Tr[1].setMaxY(T.getMaxY());
                        Tr[i].setLeft(e);
                        Tr[i].setRight(null);
                    }
                }
            }
        }


        Node root = new Node(yMed, weight+1);
        root.setLeft(balance(U.get(0)));
        root.setRight(balance(U.get(1)));
        return root;
    }

    public Node balance(List<Node> U) {
        List<Node> edgs = new ArrayList<>();
        //List<Node> trapeziums = new ArrayList<>();
        List<Node> leaves = new ArrayList<>();
        //int weight = 0;
        for(int i = 0; i < U.size(); ++i) {
            if(i%2 == 0) {
                //trapeziums.add(U.get(i));
                leaves.add(U.get(i));
                //weight += U.get(i).getWeight();
            }
            else {
                edgs.add(U.get(i));
            }
        }
       // if(weight == 0) {
            Node root = balancedEdgeTree(edgs, 0, edgs.size()-1);
            balancedTree(root, leaves);
            return  root;
       // }

        /*int r = 0;
        for(int i = 1; i < trapeziums.size(); ++i) {
            if(Float.compare(trapeziumsWeight(trapeziums, i-1), trapeziumsWeight(trapeziums, i)/(float)2) < 0 &&
                    Float.compare(trapeziumsWeight(trapeziums, i), trapeziumsWeight(trapeziums, i)/(float)2) >= 0) {
                r = i;
                break;
            }
        }

        List<Node> U1 = new ArrayList<>();
        List<Node> U2 = new ArrayList<>();
        Node e1 = null, e2 = null, Tr = null;
        int j = 0;
        for(int i = 0; i < U.size(); ++i) {
            if(j < r-1) {
                U1.add(U.get(i));
                i++;
                U1.add(U.get(i));
                j++;
            }
            else if(j == r-1) {
                U1.add(U.get(i));
                i++;
                e1 = U.get(i);
                i++;
                Tr = U.get(i);
                i++;
                e2 = U.get(i);
                j = r+1;
            }
            else if(j >= r+1) {
                U2.add(U.get(i));
            }
        }

        Node t1 = balance(U1);
        Node t2 = balance(U2);
        if(e2 != null) {
            e2.setLeft(Tr);
            e2.setRight(t2);
        }
        if(e1 != null) {
            e1.setLeft(t1);
            e1.setRight(e2);
        }
        return e1;*/
    }

    /*private int trapeziumsWeight(List<Node> trapeziums, int r) {
        int result = 0;
        for(int i = 0; i <= r; ++i) {
            result += trapeziums.get(i).getWeight();
        }
        return result;
    }*/

    /**
     * @param edgs - list of edges
     * @return root of balanced tree
     */
    private Node balancedEdgeTree(List<Node> edgs, int i, int j) {
        if(i == j) {
            return edgs.get(i);
        }
        if(i > j) {
            return null;
        }
        int k = (i+j)/2;
        Node n = edgs.get(k);
        n.setLeft(balancedEdgeTree(edgs, i, k-1));
        n.setRight(balancedEdgeTree(edgs, k+1, j));
        return n;
    }

    private void balancedTree(Node root, List<Node> leaves) {
        if(root == null) {
            return;
        }

        if(root.getLeft() != null && root.getRight() != null) {
            balancedTree(root.getLeft(), leaves);
        }
        if(root.getLeft() == null) {
            root.setLeft(leaves.remove(0));
        }
        if(root.getRight() == null) {
            root.setRight(leaves.remove(0));
            return;
        }
        balancedTree(root.getRight(), leaves);
    }

    public void printNodeShapeToFile(Node root, File file) {
        if(root != null) {
            printNodeShapeToFile(root.getLeft(), file);

            try (FileWriter writer = new FileWriter(file, true)) {
                writer.write(root.shape());
                writer.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            printNodeShapeToFile(root.getRight(), file);
        }
    }

    public void printTreeToFile(Node root, File file) {
        if(root != null) {
            printTreeToFile(root.getLeft(), file);

            if(root.getLeft() != null) {
                try (FileWriter writer = new FileWriter(file, true)) {
                    writer.write("\"" + root + "\"->\"" + root.getLeft() + "\";\n");
                    writer.flush();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if(root.getRight() != null) {
                try (FileWriter writer = new FileWriter(file, true)) {
                    writer.write("\"" + root + "\"->\"" + root.getRight() + "\";\n");
                    writer.flush();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }

            printTreeToFile(root.getRight(), file);
        }
    }

    public void localization(Node root, Point point) {
        if(root.getLeft() == null && root.getRight() == null) {
            Trapezium t = root.getTrapezium();
            if(Float.compare(t.getMinY(), point.getY()) > 0) {
                System.out.println("Point is out of the graph;");
            }
            else if(Float.compare(t.getMaxY(), point.getY()) < 0) {
                System.out.println("Point is out of the graph;");
            }
            else if(t.getLeft().getSide(point) == -1 || t.getRight().getSide(point) == 1) {
                System.out.println("Point is out of the graph;");
            }
            else {
                System.out.println(t);
            }
            return;
        }
        else if(root.getEdge() != null) {
            GEdge edge = root.getEdge();
            if(edge.getSide(point) == 0) {
                System.out.println("point is on the edge " + edge);
                return;
            }
            else if(edge.getSide(point) == -1) {
                localization(root.getLeft(), point);
            }
            else {
                localization(root.getRight(), point);
            }
        }
        else {
            Float vertex = root.getMedian();
            if(Float.compare(vertex, point.getY()) > 0) {
                localization(root.getLeft(), point);
            }
            else {
                localization(root.getRight(), point);
            }
        }
    }

    public void graphviz() throws IOException {
        File file = new File("graphviz.txt");
        var newFile = file.createNewFile();
        try(FileWriter writer = new FileWriter(file))
        {
            writer.write("digraph G {\n");
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        printNodeShapeToFile(myRoot, file);
        printTreeToFile(myRoot, file);
        try(FileWriter writer = new FileWriter(file, true))
        {
            writer.write("}");
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
