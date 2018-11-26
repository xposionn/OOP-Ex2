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
    final double ERROR_MARGIN = 0.0001;

    @Test
    void add() {
        coords.add(b9, vectorBetween);
    }

    @Test
    void distance3d() {
        assertEquals(493.45780156501763,coords.distance3d(b9, bHummus),ERROR_MARGIN); //from excel.
    }

    @Test
    void vector3D() {
        Point3D diffVector = coords.vector3D(b9, bHummus);
        Point3D expected = vectorBetween; //for easier readability
        assertEquals(expected.x(), diffVector.x(), ERROR_MARGIN,"Something is wrong with the X logic in Vector3D function");
        assertEquals(expected.y(), diffVector.y(), ERROR_MARGIN,"Something is wrong with the Y logic in Vector3D function");
        assertEquals(expected.z(), diffVector.z(), ERROR_MARGIN,"Something is wrong with the Z logic in Vector3D function");
    }

    @Test
    void azimuth_elevation_dist() {
        double[] azim_ele_dist = coords.azimuth_elevation_dist(zeroPt, onLonoPt);
        assertEquals(270.0,azim_ele_dist[0],ERROR_MARGIN,"Something is wrong with the azimuth calculation");
        assertEquals(0,azim_ele_dist[1],ERROR_MARGIN,"Something is wrong with the elevation calculation");
        assertEquals(1106312.539916,azim_ele_dist[1],ERROR_MARGIN,"Something is wrong with the distance calculation");
        }

    @Test
    void isValid_GPS_Point() {
        boolean test1 = coords.isValid_GPS_Point(vectorBetween);
        if(test1){
            fail(vectorBetween.toString() + "is not valid.");
        }
        boolean test2 = coords.isValid_GPS_Point(b9);
        if(test2) {
        //all good
        }else{
            fail(b9 + " is vaild.");
        }

    }
}