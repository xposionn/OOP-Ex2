package Algorithms;

import Coords.MyCoords;
import GIS.GIS_element;
import GIS.GIS_layer;
import Game.Fruit;
import Game.Packman;
import Geom.Path;
import Geom.Point3D;

import java.util.*;

public class ShortestPathAlgo {
    PriorityQueue<Packman> packmen;
    ArrayList<Fruit> fruits;
    ArrayList<Fruit> Backupfruits;
    MyCoords coords = new MyCoords();
    long currentTimeStamp;


    public ShortestPathAlgo(ArrayList<GIS_element> packmen, GIS_layer fruits) {
        this.currentTimeStamp = System.currentTimeMillis();
        this.packmen = new PriorityQueue<Packman>(packmen.size(), new PackmanComparatorTime());
        Iterator<GIS_element> packmanIterator = packmen.iterator();
        while (packmanIterator.hasNext()) {
            Packman p = (Packman) packmanIterator.next();
            this.packmen.add(p);
        }
        this.fruits = new ArrayList<>();
        this.Backupfruits = new ArrayList<>();
        Iterator<GIS_element> fruitIterator = fruits.iterator();
        while (fruitIterator.hasNext()) {
            Fruit f = (Fruit) fruitIterator.next();
            this.fruits.add(f);
            this.Backupfruits.add(f);
        }
    }

    public Solution runAlgo() {
        Solution solution = new Solution(packmen,currentTimeStamp);
        Iterator<Packman> packmanIterator = packmen.iterator();
        while (packmanIterator.hasNext()) {
            Packman p = packmanIterator.next();
            Iterator<Fruit> fruitIterator = fruits.iterator();
            while (fruitIterator.hasNext()) {
                Fruit f = fruitIterator.next();
                if (p.distancePointFromEatRadius((Point3D) f.getGeom()) == 0) {
                    solution.getPath(p.getID()).addFruitToPath(f);
                    fruitIterator.remove();
                }
            }
        }
        while (!fruits.isEmpty()) {
            Packman p = packmen.poll();
            double min = Double.MAX_VALUE;
            Fruit eatMe = null;
            Iterator<Fruit> itFruit = this.fruits.iterator();
            while (itFruit.hasNext()) {
                Fruit f = itFruit.next();
                double timeToEat = p.distancePointFromEatRadius((Point3D) f.getGeom()) / p.getSpeed();
                if (timeToEat < min) {
                    eatMe = f;
                    min = timeToEat;
                }
            }
            p.addTimeTraveled(min);
            double ratio = p.distancePointFromEatRadius((Point3D) eatMe.getGeom()) / coords.distance3d((Point3D) p.getGeom(), (Point3D) eatMe.getGeom());
            Point3D vectorBetween = coords.vector3D((Point3D) p.getGeom(), (Point3D) eatMe.getGeom());
            p.setGeom(coords.add((Point3D) p.getGeom(), new Point3D(vectorBetween.x() * ratio, vectorBetween.y() * ratio, vectorBetween.z() * ratio))); //TODO:packman will be placed at the next fruit position that he will be eat.
            solution.getPath(p.getID()).addFruitToPath(eatMe);
            fruits.remove(eatMe);
            packmen.add(p);
        }

        //reset pacman position
        Iterator<Path> solutionPath = solution.getPaths().iterator();
        while (solutionPath.hasNext()) {
            Path toChange = solutionPath.next();
            Packman packmantoChange = toChange.getPacmanInPath();
            packmantoChange.setGeom(toChange.getPacmanStartPosition());
            packmantoChange.setTimeTraveled(packmantoChange.getTimeTraveled()*0.75); //TODO: can change to manipulate algo.
        }
        this.fruits = new ArrayList<>(Backupfruits);
        return solution;
    }
}
