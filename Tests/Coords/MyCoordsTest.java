package Coords;

import Geom.Point3D;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyCoordsTest {

    MyCoords coords = new MyCoords();

    Point3D testa1 = new Point3D(32.103315, 35.209039, 670);
    Point3D testa2 = new Point3D(32.106352, 35.205225, 650);
    Point3D vtesta = new Point3D(337.6989921, -359.2492069, -20);

//    Point3D testb1 = new Point3D();
//    Point3D testb2 = new Point3D();
//    Point3D vtestb = new Point3D();
//
//
//    Point3D testc1 = new Point3D();
//    Point3D testc2 = new Point3D();
//    Point3D vtestc = new Point3D();




    Point3D zeroPt = new Point3D(0, 0, 0);
    Point3D onLonoPt = new Point3D(-10, 0, 0);
    final double ERROR_MARGIN = 0.0001;

    @Test
    void adda() {
        Point3D aPlusvTesta = coords.add(testa1, vtesta);
        assertEquals(aPlusvTesta.x(), testa2.x(),ERROR_MARGIN);
        assertEquals(aPlusvTesta.y(), testa2.y(),ERROR_MARGIN);
        assertEquals(aPlusvTesta.z(), testa2.z(),ERROR_MARGIN);
    }
//    @Test
//    void addb() {
//        Point3D bPlusvTestb = coords.add(testb1, vtestb);
//        assertEquals(bPlusvTestb.x(), testb2.x(),ERROR_MARGIN);
//        assertEquals(bPlusvTestb.y(), testb2.y(),ERROR_MARGIN);
//        assertEquals(bPlusvTestb.z(), testb2.z(),ERROR_MARGIN);
//    }

//    @Test
//    void addc() {
//        Point3D cPlusvTestc = coords.add(testc1, vtestc);
//        assertEquals(cPlusvTestc.x(), testc2.x(),ERROR_MARGIN);
//        assertEquals(cPlusvTestc.y(), testc2.y(),ERROR_MARGIN);
//        assertEquals(cPlusvTestc.z(), testc2.z(),ERROR_MARGIN);
//    }

    @Test
    void distance3d() {
        assertEquals(493.45780156501763,coords.distance3d(testa1, testa2),ERROR_MARGIN); //from excel.
    }

    @Test
    void vector3D() {
        Point3D diffVector = coords.vector3D(testa1, testa2);
        Point3D expected = vtesta; //for easier readability
        assertEquals(expected.x(), diffVector.x(), ERROR_MARGIN,"Something is wrong with the X logic in Vector3D function");
        assertEquals(expected.y(), diffVector.y(), ERROR_MARGIN,"Something is wrong with the Y logic in Vector3D function");
        assertEquals(expected.z(), diffVector.z(), ERROR_MARGIN,"Something is wrong with the Z logic in Vector3D function");
    }

    @Test
    void azimuth_elevation_dist() {
        double[] azim_ele_dist = coords.azimuth_elevation_dist(zeroPt, onLonoPt);
        assertEquals(270.0,azim_ele_dist[0],ERROR_MARGIN,"Something is wrong with the azimuth calculation");
        assertEquals(0,azim_ele_dist[1],ERROR_MARGIN,"Something is wrong with the elevation calculation");
        assertEquals(1106312.539916,azim_ele_dist[2],ERROR_MARGIN,"Something is wrong with the distance calculation");
        }

    @Test
    void isValid_GPS_Point() {
        boolean test1 = coords.isValid_GPS_Point(vtesta);
        if(test1){
            fail(vtesta.toString() + "is not valid.");
        }
        boolean test2 = coords.isValid_GPS_Point(testa1);
        if(test2) {
        //all good
        }else{
            fail(testa1 + " is vaild.");
        }

    }
}