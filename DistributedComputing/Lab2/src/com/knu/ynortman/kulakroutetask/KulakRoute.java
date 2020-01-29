package com.knu.ynortman.kulakroutetask;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class KulakRoute extends RecursiveTask<Monastery> {
    /*public List monks;

    public KulakRoute(List monks) {
        this.monks = monks;
    }*/
    private List<Monk> monks;
    public KulakRoute (List<Monk> monks) {
        this.monks = monks;
    }

    /*public static Monastery findWinner(List<Monk> monks) {

    }*/

    @Override
    protected Monastery compute() {
        if(monks.size() == 2 || monks.size() == 3) {
            return monks.get(0).getBetter(monks.get(1)).getMonastery();
        }
        List<Monk> firstGroup = monks.subList(0, monks.size()/2);
        List<Monk> secondGroup = monks.subList(monks.size()/2, monks.size());
        List<Monk> winnersGroup = new ArrayList<Monk>();
        for(int i = 0; i < firstGroup.size(); ++i) {
            winnersGroup.add(firstGroup.get(i).getBetter(secondGroup.get(i)));
        }
        return new KulakRoute(winnersGroup).compute();
    }
}
