package com.knu.ynortman;

public class Node {
    private Node left, right;
    private Rectangle rect;
    private Line line;

    public Node(Rectangle rect, Line line) {
        this.rect = rect;
        this.line = line;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    @Override
    public String toString() {
        if(line == null) {
            return "leaf";
        }
        return line.getPoint().toString();
    }
}
