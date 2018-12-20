package Algorithms;

import Game.Packman;

import java.util.Comparator;

public class PackmanComparator4Algo implements Comparator<Packman> {
    @Override
    public int compare(Packman o1, Packman o2) { //TODO: FIX THIS COMPARATOR.
        if(o1.getTimeTraveled()==o2.getTimeTraveled()){
            return (int)(o2.getSpeed()-o1.getSpeed());
        }
        else{
            return (int)(o1.getTimeTraveled()-o2.getTimeTraveled());

        }
    }
}
