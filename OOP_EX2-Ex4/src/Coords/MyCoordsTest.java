package Coords;

import Geom.Point3D;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.fail;
import static junit.framework.TestCase.assertTrue;

class MyCoordsTest {

    MyCoords coordsConvert = new MyCoords();
    Point3D p1 = new Point3D(2,3,1);
    Point3D p2 = new Point3D(8,-5,0);


    @Test
    void add() {
        Point3D p3 = coordsConvert.add(p1,p2);
        if(p3.x() != 10 || p3.y() != -2 || p3.z() != 1){
            fail("Something wrong with the add function");
        }

    }

    @Test
    void distance3d() {
        double distance = coordsConvert.distance3d(p1,p2);
        assertTrue("ERR: Something wrong with the distance.",(distance>10.04&&distance<10.05));

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