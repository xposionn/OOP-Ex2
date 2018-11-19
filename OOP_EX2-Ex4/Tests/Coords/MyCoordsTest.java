package Coords;

import Geom.Point3D;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MyCoordsTest {

    MyCoords coords = new MyCoords();
    Point3D b9 = new Point3D(32.103315, 35.209039, 670);
    Point3D bHummus = new Point3D(32.106352, 35.205225, 650);
    Point3D vectorBetween = new Point3D(337.6989921, -359.2492069, -20);
    Point3D zeroPt = new Point3D(0, 0, 0);
    Point3D onLonoPt = new Point3D(-10, 0, 0);

    @Test
    void add() {
        coords.add(b9, vectorBetween);
    }

    @Test
    void distance3d() {
        coords.distance3d(b9, bHummus);
    }

    @Test
    void vector3D() {
        Point3D diffVector = coords.vector3D(b9, bHummus);
        //update: finish this test

    }

    @Test
    void azimuth_elevation_dist() {
        double[] azim_ele_dist = coords.azimuth_elevation_dist(zeroPt, onLonoPt);
        System.out.println(Arrays.toString(azim_ele_dist));
    }

    @Test
    void isValid_GPS_Point() {
    }
}