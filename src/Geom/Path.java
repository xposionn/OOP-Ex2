package Geom;

import Coords.MyCoords;
import Game.Fruit;
import Game.Packman;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Path{
        /**
         * This class represent a Path.
         * Path include: Packman and his Fruit Routes.
         */

    private Packman pacmanInPath;
    private ArrayList<Fruit> fruitsInPath;
    private Point3D pacmanStartPosition;
    private Color lineColor;
    //add gps data as needed

    //constructor:

        /**
         * Create a Path by getting packman(that will run in this route,and an array in his Route order.)
         * @param pacForPath - Packman
         * @param fruitsInPath - Fruits array
         */
    public Path(Packman pacForPath, ArrayList<Fruit> fruitsInPath){
        this.pacmanInPath = pacForPath;
        this.fruitsInPath = fruitsInPath;
        this.pacmanStartPosition = (Point3D)pacmanInPath.getGeom();
        Random rand = new Random();
        int red = rand.nextInt(10)*10 + rand.nextInt(10)*10+rand.nextInt(5)*10;
        int green = rand.nextInt(10)*10 + rand.nextInt(10)*10+rand.nextInt(5)*10;
        int blue = rand.nextInt(10)*10 + rand.nextInt(10)*10+rand.nextInt(5)*10;
        this.lineColor = new Color(red, green, blue);
    }


    //Gets the distance of the path traveled from pacman position to fruit index in path.
    //to get the total distance traveled in the path, call this function with fruit index as Arraylist.size()-1.

        /**this methods get an index of fruit in the Path and calculate the Distance between the packman and the fruit index
         * considering all the fruits in the route.
         *
         * @param fruitIndex
         * @return distance of the path.
         */
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
        else {
            pathDistance += coordsConv.distance3d(pacmanStartPosition, getFruitLoc(0)); //distance from pacman first Pos to first Fruit.
        }
        // Loop through our fruits locations till the last one, including the path from the (last-1) to (last) .
        for (int fruitLocIndex = 0; fruitLocIndex < fruitIndex; fruitLocIndex++) {
            Point3D fromFruit = getFruitLoc(fruitLocIndex);  // Get fruit location we're travelling from
            Point3D destFruitLoc = fromFruit;  // fruit we're travelling to
            if (fruitLocIndex + 1 <= fruitIndex) {
                destFruitLoc = getFruitLoc(fruitLocIndex + 1);
            }
            // add the distance between the two fruits
            pathDistance += coordsConv.distance3d(fromFruit, destFruitLoc);
        }
        return pathDistance;
    }

        /**this method will calculate the time to finish all the route if fruits by the specific packman that own that route.
         *
         * @return time in seconds
         */
   public double getTravelTimeForPacmanWholePath(){
        if(this.fruitsInPath.size()==0){
            return 0;
        }else {
            return getDistance(this.fruitsInPath.size() - 1) / pacmanInPath.getSpeed() * 1000;
        }
   }

    /**
     * Gets time (in MS) and returns how many fruits the pacman ate in the path.
     * @param timeInMillis time in seconds.
     */
   public int getHowManyEatenAfterXtime(long timeInMillis) {
       int counterFruits = this.fruitsInPath.size();
       int fruitEaten = 0;
       while (counterFruits > 0 && fruitEaten < this.fruitsInPath.size()) {
           if (getDistance(fruitEaten++) / (this.pacmanInPath.getSpeed()) * 1000 > timeInMillis) {
               return this.fruitsInPath.size() - counterFruits;
           }
           counterFruits--;
       }
       return fruitEaten;
   }

        /**This method will compute the position of packman after X time, considering his Fruits Route.
         *
         * @param timeInMillis - after how much time in MS
         * @return Point3D point - the location of packman after X time.
         */
   public Point3D getPacPositionAfterXtime(long timeInMillis){
       if(this.fruitsInPath.size()==0){
           return pacmanStartPosition;
       }
       int alreadyEatenDuringThisTime = getHowManyEatenAfterXtime(timeInMillis);
       MyCoords coordsConv = new MyCoords(); //we use MyCoords object to calculate vector between two Point3D points.
       Point3D fromFruitLoc;
       Point3D toFruitLoc;
       long timeFromPacToEatenFruit;
       if(alreadyEatenDuringThisTime==0) {
           fromFruitLoc = pacmanStartPosition;
           timeFromPacToEatenFruit = 0;
           toFruitLoc = getFruitLoc(0);
       }
       else {
           fromFruitLoc = getFruitLoc(alreadyEatenDuringThisTime - 1);
           timeFromPacToEatenFruit = (long) (getDistance(alreadyEatenDuringThisTime - 1) / (this.pacmanInPath.getSpeed())*1000);
           if(alreadyEatenDuringThisTime==this.fruitsInPath.size()) //ate all
               return getFruitLoc(alreadyEatenDuringThisTime-1);
           else
               toFruitLoc = getFruitLoc(alreadyEatenDuringThisTime);
       }

       Point3D vectorBetween = coordsConv.vector3D(fromFruitLoc,toFruitLoc);
       long timeLeftToMove = (timeInMillis - timeFromPacToEatenFruit);
       double timeForNextRoute = (coordsConv.distance3d(fromFruitLoc, toFruitLoc) / (this.pacmanInPath.getSpeed())*1000);
       double percentagePathToMove = timeLeftToMove/timeForNextRoute;
       Point3D newPosMov = new Point3D(percentagePathToMove * vectorBetween.x(), percentagePathToMove * vectorBetween.y(), percentagePathToMove * vectorBetween.z());
       return coordsConv.add(fromFruitLoc,newPosMov);
   }

        /**
         * This method will return the location of Fruit in index X , 0<=x<size of FruitArr
         * @param pathPosition
         * @return Point3D - the location of Fruit
         */
    public Point3D getFruitLoc(int pathPosition) { //returns fruit GPS location in Point3D object.
        if(pathPosition<0 || pathPosition>=this.fruitsInPath.size()){
            throw new RuntimeException("You asked to get location of fruit index outside of the fruit arrayList bounds!");
        }
        return (Point3D)fruitsInPath.get(pathPosition).getGeom();
    }

        /**
         * This method will add fruit to path, in the last position of the Array.
         * @param fruitToAdd - fruit to add to packman's Route
         */
    public void addFruitToPath(Fruit fruitToAdd){
        this.fruitsInPath.add(fruitToAdd);
    }

        /**
         * This method will return the ID of packman in this specific Route.
         * @return an ID of packman.
         */
    public int getPacmanIDInPath() {
        return this.pacmanInPath.getID();
    }

        /**
         * This method will return the packman that own the Route.
         * @return Packman who own the Path.
         */
    public Packman getPacmanInPath() {
        return pacmanInPath;
    }

    @Override
    public String toString() {
        return "Path{" +
                "pacman=" + pacmanInPath.getID() +
                ", fruitsInPath=" + fruitsInPath +
                '}';
    }

        /**
         * This function will return the Fruite Route
         * @return Array of Fruits.
         */
    public ArrayList<Fruit> getFruitsInPath() {
        return fruitsInPath;
    }

        /**
         * This method will return the starting position of a packman.
         * @return Point3D - starting position of packman.
         */
    public Point3D getPacmanStartPosition() {
        return pacmanStartPosition;
    }

        /**
         * This method will return the Color object of line that will be displayed as a line between the Packman and his Fruits Route
         * @return Color - the color of the line.
         */
    public Color getColor() {
       return this.lineColor;
    }
}
