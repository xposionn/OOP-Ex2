package Algorithms;

import Coords.MyCoords;
import Game.Fruit;
import Game.Packman;
import Geom.Point3D;

import java.util.*;

public class ShortestPathAlgo {
    PriorityQueue<Packman> packmen;
    ArrayList<Fruit> fruits;
    MyCoords coords = new MyCoords();

    public ShortestPathAlgo(HashSet<Packman> packmen, HashSet<Fruit> fruits) {
        this.packmen = new PriorityQueue(packmen.size(), new PackmanComparatorID());
        this.packmen.addAll(packmen);
        this.fruits = new ArrayList<>();
        this.fruits.addAll(fruits);
    }

    public Solution runAlgo() {

        Solution solution = new Solution(packmen);
        while (!fruits.isEmpty()) {
            Packman p = packmen.peek();
            double min = Double.MAX_VALUE;
            Fruit eatMe;
            Iterator<Fruit> itFruit = this.fruits.iterator();
            while (itFruit.hasNext()) {
                Fruit f = itFruit.next();
                double timeToEat = coords.distance3d((Point3D) p.getGeom(), (Point3D) f.getGeom()) / p.getSpeed();
                if(timeToEat<min){
                    eatMe = f;
                    min = timeToEat;
                }
            }
            solution.getPath(p.getID()).addFruitToPath(eatMe);
        }
        return solution;


    }
}
