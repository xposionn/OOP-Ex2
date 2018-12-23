# Java GIS-like System & GUI  with mTSP/VRP Solution



[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://github.com/xposionn/OOP-Ex2/)
© Written by Liad Cohen and Timor Sharabi.

OOP-Ex2, Ex3, Ex4 are 3 assignments we (Liad and Timor) were doing as part of homework tasks in the Java Object Oriented Course in Ariel University.


## The whole project represents a Geographic Information System (GIS) with an amazing GUI.

## Table of Contents:
- Project overview
- Showcase
- EX2 - Release 2.0 (Basic Infrastructure)
- EX3 - Release 3.0 (Full Project , 100% Production ready)
- EX4 - Pre-Release 4.0 - (Assignment not yet given).
- How-To
- Algorithms Explanation
- Project Structure, Packages, Classes and Class Diagram



## Project Overview 
Named "Pacman and Fruits" as assignment asked, but this can be used as a real-life GIS including a GUI with real-time Elements/Objects moving according to real-life speeds, 3D distances, Lat-Lon-Alt GPS coordinates converters and more.
Saving, Loading CSV files to work inside the application, as well as Exporting to KML files which are compatible with Google Earth, as well as showing the full animation of objects moving in the Java GUI (using threads), AND in Google Earth itself!
Algorithms to solve mTSP/VRP problems as all "Pacmen" will have to eat all "Fruits" as fast as possible (different speeds).

## Showcase
More Pictures and GIFs here after Ex4 is completed.
![alt text](https://camo.githubusercontent.com/6720886e5d4570689d94e5adc5162f3c489d8cb3/68747470733a2f2f692e6962622e636f2f5a4b714c6b64622f53637265656e73686f742d312e706e67)

## EX2 - Release 2.0 (Basic Infrastructure)
We developed a GIS (Geographic Information System)-like system including GIS infrastructure, calculating real-life 3D distances between objects using our Lat-Lon-Alt coordinates converters, time changes between Long Epoch time to UTC, 
Reading CSV files & reading whole folders with CSV files, constructing objects from CSV and showing on Google Earth after exporting to KML files. 
(No GUI in this Release).


## EX3 - Release 3.0 (Full Project)
Java GUI including image map for a "game", real-life speeds of objects in a map, as well as real-life distances between pixels in a map, movement (with real-life speed) of elements and some algorithms to solve the mTSP "Pac-men on a map to eat all fruits as fast as possible". (with different speeds variant of the problem!).
Includes a "Painter" thread which will show an animation of the solved solution of the algorithm, moving the pac-men in real-time according to their speed (With respect to real-life distances). This thread will paint according to the user preferred FPS, and for how long he wants the animation to show.  Look here for more information.

This release includes "Path", "Solution" and "ShortestPathAlgo", "Game", "Map" "Fruits", "Packman" (Yes, with a typo because of the assignment), "GUI" classes and more.

## EX4 - Pre-Release 4.0 - (Assignment not yet given).

##  How-To
### Load/Save file Menu
![alt text](https://i.imgur.com/BiGHdNc.jpg)
### Load a file

>![alt text](https://i.imgur.com/UIJZV0I.png)
### Save a file

![alt text](https://i.imgur.com/ckQPGyL.png)

### Export to KML file (compatible with Google Earth)

![alt text](https://i.imgur.com/rLI1jXf.jpg)

## Algorithms Explanation
We use heuristics algorithms to solve the mTSP / VRP problem (With different speeds variant of the problem).
Using a min-heap (priority queue) with a comperator we agreed on, we then do run this simple algorithm:

Before each run, we check if a fruit is inside any 'eating radius' of some pacman. We then immediately can add this fruit for the pacman Path Solution as it does not add any time for the complete solution.
````Java
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
````
We then run through all the remaining fruits:
````Java
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
  p.setGeom(coords.add((Point3D) p.getGeom(), new Point3D(vectorBetween.x() * ratio, vectorBetween.y() * ratio, vectorBetween.z() * ratio))); //packman will be placed at the next fruit position that he will eat.  
  solution.getPath(p.getID()).addFruitToPath(eatMe);  
  fruits.remove(eatMe);  
  packmen.add(p);  
}
````
Our comparator for the pacman priority queue: (Which pacman we choose to move, each step)

````Java
public class PackmanComparator4Algo implements Comparator<Packman> {
    @Override
    public int compare(Packman o1, Packman o2) {
        if(o1.getTimeTraveled()==o2.getTimeTraveled()){
            return (int)(o2.getSpeed()-o1.getSpeed());
        }
        else{
            return (int)(o1.getTimeTraveled()-o2.getTimeTraveled());

        }
    }
}
````

This algorithm is not guaranteed to provide the best/optimal solution.
But then, we are running this algorithm for many times, each time taking the Hash Set of pacmen and Shuffling it inside an
ArrayList so the priority queue is different for each algorithm run - for the first pick of pacman at the start of the algorithm.
We run the algorithm for num of pacmen* num of fruits * 2. We found these are enough algorithm runs to provide a better solution for us.

````Java
 private void runAlgo() {
        if(this.game.getPacmen().size() == 0){
            throw new RuntimeException("No pacmen to calculate solution.");
        } else if(this.game.getFruits().size() == 0){
            throw new RuntimeException("No fruits to calculate solution.");
        }
        ArrayList<GIS_element> packmen = new ArrayList<>(this.game.getPacmen());
        Solution bestSolution = null;
        long bestTime = Long.MAX_VALUE;
        for(int i=0;i<packmen.size()*game.getFruits().size()*2;i++) { //change to get faster speed -> less optimized solution.
            Collections.shuffle(packmen);
            ShortestPathAlgo algo = new ShortestPathAlgo(packmen,game.getFruits());
            Solution algoSolution = algo.runAlgo();
            if (bestTime > algoSolution.timeToComplete()) {
                bestSolution = algoSolution;
                bestTime = (long)algoSolution.timeToComplete();
            }
        }
        linesSolution = bestSolution;
        resetTimeAfterAlgoAndSetEatenTimes(linesSolution);
        System.out.println("Total time to complete all paths: " + linesSolution.timeToComplete()/1000);
        paintThread = new Painter(bestSolution,ourJFrame);
        Thread repainter = new Thread(paintThread);
        repainter.start();

    }
````
After each algorithm iteration is done, we reset the pacmen position and fruits back into their arraylist, so the next iteration can calculate properly:
````Java
        //reset pacman position
        Iterator<Path> solutionPath = solution.getPaths().iterator();
        while (solutionPath.hasNext()) {
            Path toChange = solutionPath.next();
            Packman packmantoChange = toChange.getPacmanInPath();
            packmantoChange.setGeom(toChange.getPacmanStartPosition());
            packmantoChange.setTimeTraveled(packmantoChange.getTimeTraveled()*0.75); //can change to manipulate algo.
        }
        this.fruits = new ArrayList<>(Backupfruits);
````

Notice, now, instead of actually zero-ing the timeTraveled of each pacman before the next iteration, we are taking the total time traveled for this pacman for all iterations done before hand.
````Java
       packmantoChange.setTimeTraveled(packmantoChange.getTimeTraveled()*0.75); //can change to manipulate algo.
````
This way, the next time the algorithm runs, it will take into account the time traveled for a pacmen in all previous iterations.
This will yeild better solutions in these situations: When there is a game with many pacmen with different speeds.
<br/>
These situations are considered to be harder problems to solve, and results in up to 50% better optimized solutions than other classmates solutions (comparing the solution time to complete the "map/level" provided in the assignment).

____________________________________________________________________________________________________________



## Project Structure, Packages, Classes and Class Diagram

### Overview:
| Package| Description|
| ------ | ------ |
| Coords| [Will be updated for Ex4][sameREADME]|
| GIS 	| [Will be updated for Ex4][sameREADME] |
| Geom | [Will be updated for Ex4][sameREADME] |
| Game | [Will be updated for Ex4][sameREADME] |
| GUI| [Will be updated for Ex4][sameREADME] |
| Algorithms | [Will be updated for Ex4][sameREADME] |
| File_format| [Will be updated for Ex4][sameREADME] |


   [sameREADME]: <https://github.com/xposionn/OOP-Ex2/edit/master/README.md>

### Class Diagram: (Enlarge to view whole image)
![alt text](https://github.com/xposionn/OOP-Ex2/blob/master/ClassDiagram/Ex3-NoDependenciesClassDiagram.png?raw=true)
### Class Diagram with dependencies: (Enlarge to view whole image)
![alt text](https://github.com/xposionn/OOP-Ex2/blob/master/ClassDiagram/Ex3-WithDependenciesClassDiagram.png)
Will edit more for Ex4.
