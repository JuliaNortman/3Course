package com.knu.ynortman.kulakroutetask;

public class Monk implements Comparable<Monk>{
    private Monastery monastery;
    private int power;

    public Monk(Monastery monastery, int power) {
        this.monastery = monastery;
        this.power = power;
    }

    public Monastery getMonastery() {
        return monastery;
    }

    public void setMonastery(Monastery monastery) {
        this.monastery = monastery;
    }


    @Override
    public int compareTo(Monk monk) {
        if(power > monk.power) {
            return 1;
        }
        if(power < monk.power) {
            return -1;
        }
        return 0;
    }

    public Monk getBetter(Monk monk) {
        if(this.compareTo(monk) >= 0) {
            return this;
        }
        return monk;
    }

    @Override
    public String toString() {
        return Integer.toString(power);
    }
}
