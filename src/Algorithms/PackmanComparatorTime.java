package Algorithms;

import Game.Packman;

import java.util.Comparator;

public class PackmanComparatorTime implements Comparator<Packman> {
    @Override
    public int compare(Packman o1, Packman o2) {
        return (int)(o1.getTimeTraveled()-o2.getTimeTraveled());
        }
    }
