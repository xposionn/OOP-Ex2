package Algorithms;

import Game.Packman;

public class PackmanComparatorID implements java.util.Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        if((((Packman)o1).getTimeTraveled()-((Packman)o2).getTimeTraveled())>0){
            return 1;
        }else if((((Packman)o1).getTimeTraveled()-((Packman)o2).getTimeTraveled())==0)
            return 0;
        else
            return -1;
        }
    }
