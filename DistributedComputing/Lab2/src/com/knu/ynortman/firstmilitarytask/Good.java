package com.knu.ynortman.firstmilitarytask;

public class Good {
    private int id;

    public Good(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                '}';
    }
}
