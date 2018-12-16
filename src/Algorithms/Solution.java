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
        this.paths = new ArrayList<>();
        ArrayList packmen1 = new ArrayList<>();
        packmen1.addAll(packmen);
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
            ArrayList<Fruit> fruits = new ArrayList<>();
            Path path = new Path(packman,fruits);
            paths.add(path);
        }
    }

    public double timeToComplete(){
        Iterator<Path> pathIt = paths.iterator();
        double maxTime = Double.MIN_VALUE;
        while(pathIt.hasNext()){
            Path currentPath = pathIt.next();
            double timeToPathCompletion = currentPath.getTravelTimeForPacmanWholePath();
            if(timeToPathCompletion>maxTime){
                maxTime = timeToPathCompletion;
            }
        }
        return maxTime;
    }

    public Path getPath(int id) {
        Iterator<Path> pathIterator = this.paths.iterator();
        while(pathIterator.hasNext()){
            Path current = pathIterator.next();
            if(current.getPacmanIDInPath()==id){
                return current;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "paths=" + paths +
                '}';
    }
    public ArrayList<Path> getPaths() {
        return paths;
    }
}
