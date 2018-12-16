package Algorithms;

import Coords.MyCoords;
import GIS.GIS_element;
import GIS.GIS_layer;
import Game.Fruit;
import Game.Packman;
import Geom.Point3D;

import java.util.*;

public class ShortestPathAlgo {
    PriorityQueue<Packman> packmen;
    ArrayList<Fruit> fruits;
    MyCoords coords = new MyCoords();

    public ShortestPathAlgo(GIS_layer packmen, GIS_layer fruits) {
        this.packmen = new PriorityQueue<Packman>(packmen.size(), new PackmanComparatorTime());
        Iterator<GIS_element> packmanIterator = packmen.iterator();
        while(packmanIterator.hasNext()){
            Packman p = (Packman)packmanIterator.next();
            this.packmen.add(p);
        }
        this.fruits = new ArrayList<>();
        Iterator<GIS_element> fruitIterator = fruits.iterator();
        while(fruitIterator.hasNext()){
            Fruit f = (Fruit) fruitIterator.next();
            this.fruits.add(f);
        }
    }

    public Solution runAlgo() {

        Solution solution = new Solution(packmen);
        while (!fruits.isEmpty()) {
            Packman p = packmen.poll();
            double min = Double.MAX_VALUE;
            Fruit eatMe= null;
            Iterator<Fruit> itFruit = this.fruits.iterator();
            while (itFruit.hasNext()) {
                Fruit f = itFruit.next();
                double timeToEat = coords.distance3d((Point3D) p.getGeom(), (Point3D) f.getGeom()) / p.getSpeed();
                if(timeToEat<min){
                    eatMe = f;
                    min = timeToEat;
                }
            }
            p.addTimeTraveled(min);
            p.setGeom(eatMe.getGeom()); //TODO:packman will be placed at the next fruit position that he will be eat.
            solution.getPath(p.getID()).addFruitToPath(eatMe);
            fruits.remove(eatMe);
            packmen.add(p);
        }
        return solution;


    }
}
