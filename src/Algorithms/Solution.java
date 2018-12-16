package Algorithms;

import Game.Fruit;
import Game.Packman;
import Geom.Path;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Solution {
    ArrayList<Path> paths;

    public Solution(PriorityQueue packmen) {
        ArrayList packmen1 = new ArrayList<>(packmen);
        packmen1.sort((o1, o2) -> {
            if(((Packman)o1).getID()-((Packman)o2).getID()>0){
                return 1;
            }else if(((Packman)o1).getID()-((Packman)o2).getID()<0){
                return -1;
            }else
                return 0;//will never be here, cuz each packman has its unique ID.
            });
        Iterator<Packman> itPackman = packmen1.iterator();
        while(itPackman.hasNext()){
            Packman packman = itPackman.next();
            Path path = new Path(packman,new ArrayList<Fruit>());
            paths.add(path);
        }
    }

    public Path getPath(int id) {
        Iterator<Path> pathIterator = this.paths.iterator();
        while(pathIterator.hasNext()){
            Path current = pathIterator.next();
            if(current.getPackmanIDInPath()==id){
                return current;
            }
        }
        return null;
    }
}
