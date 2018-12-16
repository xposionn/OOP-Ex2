package Geom;

import Coords.MyCoords;
import Game.Fruit;
import Game.Packman;

import java.util.ArrayList;
import java.util.Iterator;

public class Path {


    Packman pacmanInPath;
    ArrayList<Fruit> fruitsInPath;

    //add gps data as needed

    //constructor:
    public Path(Packman pacForPath, ArrayList<Fruit> fruitsInPath){
        this.pacmanInPath = pacForPath;
        this.fruitsInPath = fruitsInPath;
    }


    //Gets the distance of the path traveled from pacman position to fruit index in path.
    //to get the total distance traveled in the path, call this function with fruit index as Arraylist.size()-1.
    public double getDistance(int fruitIndex) {
        if(fruitIndex>=this.fruitsInPath.size())
            throw new RuntimeException("You tried to calculate distance in path for more fruits than in the path");
        else if(fruitIndex<0){
            throw new RuntimeException("You entered invalid fruit index in path.");
        }
        MyCoords coordsConv = new MyCoords(); //we use MyCoords object to calculate distance between two Point3D points.
        double pathDistance = 0;
        if(fruitsInPath.size()==0){
            return 0;
        }
        else
            pathDistance += coordsConv.distance3d((Point3D)this.pacmanInPath.getGeom(),getFruitLoc(0)); //distance from pacman to first Fruit.
        // Loop through our fruits locations till the last one, including the path from the (last-1) to (last) .
        for (int fruitLocIndex = 0; fruitLocIndex <= fruitIndex; fruitLocIndex++) {
            Point3D fromFruit = getFruitLoc(fruitLocIndex);  // Get fruit location we're travelling from
            Point3D destFruitLoc = fromFruit;  // fruit we're travelling to
            if (fruitLocIndex + 1 < fruitsInPath.size()) {
                destFruitLoc = getFruitLoc(fruitLocIndex + 1);
            }
            // Get the distance between the two fruits
            pathDistance += coordsConv.distance3d(fromFruit, destFruitLoc); //TODO: change distance to be actual cost in TIME to get from point1 to point2 (using speed of pacmans).
        }
        return pathDistance;
    }

   public double getTravelTimeForPacmanWholePath(){
        return getDistance(this.fruitsInPath.size()-1)/pacmanInPath.getSpeed();
   }

    /**
     * Gets time (in MS) and returns how many fruits the pacman ate in the path.
     * @param timeInMillis time in seconds.
     */
   public int getHowManyEatenAfterXtime(double timeInSeconds){
       for(int fruitEaten = 0; fruitEaten<this.fruitsInPath.size(); fruitEaten++){
           if(getDistance(fruitEaten)/this.pacmanInPath.getSpeed() > timeInSeconds){
               return fruitEaten;
           }
       }
       return fruitsInPath.size();
   }

   public Point3D getPacPositionAfterXtime(double timeInMillis){
        //TODO: finish this.
        return null;
   }

    public Point3D getFruitLoc(int pathPosition) { //returns fruit GPS location in Point3D object.
        return (Point3D)fruitsInPath.get(pathPosition).getGeom();
    }

    public void addFruitToPath(Fruit fruitToAdd){
        this.fruitsInPath.add(fruitToAdd);
    }

    public int getPacmanIDInPath() {
        return this.pacmanInPath.getID();
    }

}
