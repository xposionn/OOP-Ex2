package Algorithms;

import Game.Fruit;
import Game.Packman;
import Geom.Path;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * This class represents a Solution
 * Solution containts a Paths.
 *
 * @author : Liad and Timor
 */
public class Solution {
    private ArrayList<Path> paths;
    private long timeStart;

    /**
     * This is a Constructor method.
     *
     * @param packmen - as Priority Queue
     * @param timeStart - the time of the Solution to be calculated.
     */
    public Solution(PriorityQueue packmen, long timeStart) {
        this.paths = new ArrayList<>();
        this.timeStart = timeStart;
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
            ArrayList<Fruit> fruits = new ArrayList<>();
            Path path = new Path(packman,fruits);
            paths.add(path);
        }
    }

    /**
     * This method calculate the time to finish for each Path and take the maximum between all those finishing time.
     * @return time to finish.
     */
    public double timeToComplete(){
        Iterator<Path> pathIt = this.paths.iterator();
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

    /**
     * This method will return the path of a specific packman
     * @param id - id of Packman
     * @return Path that own that packman.
     */
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

    /**
     * This method will return the Paths of a Solution
     * @return Arraylist of Path.
     */
    public ArrayList<Path> getPaths() {
        return paths;
    }

    /**
     * This method will return the starting time to compute this specific location.
     * @return
     */
    public long getTimeStart() {
        return timeStart;
    }
}
