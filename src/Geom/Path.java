package Geom;

import Geom.Point3D;

import java.util.ArrayList;
import java.util.Iterator;

public class Path extends ArrayList<Point3D> {

    //add gps data as needed

    public double totalLength(){
        double sumLen = 0;
        Point3D firstPt,secondPt;
        Iterator<Point3D> firstIt = this.iterator();
        Iterator<Point3D> secondIt = this.iterator();
        if(secondIt.hasNext()==false){ //zero gps points in this arraylist.
            return 0;
        }
        else {
            secondIt.next();
            if (secondIt.hasNext() == false) { //only 1 gps point in this arraylist.
                return 0;
            }
        }
        while(firstIt.hasNext() && secondIt.hasNext()){ //firstIt starts at null, secondIt starts at first point. at least 2 points guaranteed.
            firstPt = firstIt.next();
            secondPt = secondIt.next();
            sumLen += firstPt.distance3D(secondPt);
        }
        return sumLen;
    }
}
