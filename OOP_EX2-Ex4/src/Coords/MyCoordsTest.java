package Coords;

import Geom.Point3D;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertTrue;

class MyCoordsTest {

    MyCoords coordsConvert = new MyCoords();
    Point3D Building9 = new Point3D(32.103315,35.209039,670);
    Point3D Humus = new Point3D(32.106352,35.205225,650);
    Point3D vector = new Point3D(0,0,0);

    @Test
    void add() {
        coordsConvert.add(Building9,vector);

    }

    @Test
    void distance3d() {
        double distance = coordsConvert.distance3d(Building9,Humus);
        System.out.println(distance);
        assertTrue("ERR: Something wrong with the distance.",(distance>493.05&&distance<493.06));

    }

    @Test
    void vector3D() {
    }

    @Test
    void azimuth_elevation_dist() {
    }

    @Test
    void isValid_GPS_Point() {
    }
}