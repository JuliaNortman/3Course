package com;

public class Main {

    public static void main(String[] args) {
        SkipList list = new SkipList(8);
        list.add(1);
        list.add(2);
        list.add(-2);
        list.add(20);
        list.add(42);
        list.add(22);
        list.add(12);
        String s = list.toString();
        System.out.println(s);
        list.displayList();
    }
}
