package Coords;

import Geom.Point3D;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyCoordsTest {

    MyCoords coords = new MyCoords();

    Point3D testa1 = new Point3D(32.103315, 35.209039, 670);
    Point3D testa2 = new Point3D(32.106352, 35.205225, 650);
    Point3D vtesta = new Point3D(337.6989921, -359.2492069, -20);
    double DistanceA = 493.45780156501763;
    double AzimutA = 0;
    double ElevationA = Math.asin((testa1.z()-testa2.z())/DistanceA);

    Point3D testb1 = new Point3D(26,27,33);
    Point3D testb2 = new Point3D(26.0001,27.0010,30);
    Point3D vtestb = new Point3D(11.11949266,99.94133804,-3);
    double DistanceB = 100.558;
    double AzimutB = 41.15;
    double ElevationB = Math.asin((testb1.z()-testb2.z())/DistanceB);


    Point3D testc1 = new Point3D(26,28.009,33);
    Point3D testc2 = new Point3D(26.0001,28.05,30);
    Point3D vtestc = new Point3D(11.11949266,4097.59451,-3);
    double DistanceC = 4097.609597;
    double AzimutC = 180;
    double ElevationC = Math.asin((testc1.z()-testc2.z())/DistanceC);


    Point3D zeroPt = new Point3D(0, 0, 0);
    Point3D onLonoPt = new Point3D(-10, 0, 0);
    final double ERROR_MARGIN = 0.1;

    //Add test
    @Test
    void adda() {
        Point3D aPlusvTesta = coords.add(testa1, vtesta);
        assertEquals(aPlusvTesta.x(), testa2.x(),ERROR_MARGIN);
        assertEquals(aPlusvTesta.y(), testa2.y(),ERROR_MARGIN);
        assertEquals(aPlusvTesta.z(), testa2.z(),ERROR_MARGIN);
    }
    @Test
    void addb() {
        Point3D bPlusvTestb = coords.add(testb1, vtestb);
        assertEquals(bPlusvTestb.x(), testb2.x(),ERROR_MARGIN);
        assertEquals(bPlusvTestb.y(), testb2.y(),ERROR_MARGIN);
        assertEquals(bPlusvTestb.z(), testb2.z(),ERROR_MARGIN);
    }

    @Test
    void addc() {
        Point3D cPlusvTestc = coords.add(testc1, vtestc);
        assertEquals(cPlusvTestc.x(), testc2.x(),ERROR_MARGIN);
        assertEquals(cPlusvTestc.y(), testc2.y(),ERROR_MARGIN);
        assertEquals(cPlusvTestc.z(), testc2.z(),ERROR_MARGIN);
    }

    //Distance test

    @Test
    void distance3da() {
        assertEquals(DistanceA,coords.distance3d(testa1, testa2),ERROR_MARGIN); //from excel.
    }

    @Test
    void distance3b() {
        assertEquals(DistanceB,coords.distance3d(testb1, testb2),ERROR_MARGIN); //from excel.
    }
    @Test
    void distance3c() {
        assertEquals(DistanceC,coords.distance3d(testc1, testc2),ERROR_MARGIN); //from excel.
    }

    //vector test
    @Test
    void vector3Da() {
        Point3D diffVectora = coords.vector3D(testa1, testa2);
        Point3D expected = vtesta; //for easier readability
        assertEquals(expected.x(), diffVectora.x(), ERROR_MARGIN,"Something is wrong with the X logic in Vector3D function");
        assertEquals(expected.y(), diffVectora.y(), ERROR_MARGIN,"Something is wrong with the Y logic in Vector3D function");
        assertEquals(expected.z(), diffVectora.z(), ERROR_MARGIN,"Something is wrong with the Z logic in Vector3D function");
    }


    @Test
    void vector3Db() {
        Point3D diffVectorb = coords.vector3D(testb1, testb2);
        Point3D expected = vtestb; //for easier readability
        assertEquals(expected.x(), diffVectorb.x(), ERROR_MARGIN,"Something is wrong with the X logic in Vector3D function");
        assertEquals(expected.y(), diffVectorb.y(), ERROR_MARGIN,"Something is wrong with the Y logic in Vector3D function");
        assertEquals(expected.z(), diffVectorb.z(), ERROR_MARGIN,"Something is wrong with the Z logic in Vector3D function");
    }

    @Test
    void vector3Dc() {
        Point3D diffVectorc = coords.vector3D(testc1, testc2);
        Point3D expected = vtestc; //for easier readability
        assertEquals(expected.x(), diffVectorc.x(), ERROR_MARGIN,"Something is wrong with the X logic in Vector3D function");
        assertEquals(expected.y(), diffVectorc.y(), ERROR_MARGIN,"Something is wrong with the Y logic in Vector3D function");
        assertEquals(expected.z(), diffVectorc.z(), ERROR_MARGIN,"Something is wrong with the Z logic in Vector3D function");
    }


    //azimut-elev-dist test.
    @Test
    void azimuth_elevation_dist() {
        double[] azim_ele_dist = coords.azimuth_elevation_dist(zeroPt, onLonoPt);
        assertEquals(270.0,azim_ele_dist[0],ERROR_MARGIN,"Something is wrong with the azimuth calculation");
        assertEquals(0,azim_ele_dist[1],ERROR_MARGIN,"Something is wrong with the elevation calculation");
        assertEquals(1106312.539916,azim_ele_dist[2],ERROR_MARGIN,"Something is wrong with the distance calculation");
        }

    @Test
    void azimuth_elevation_dista() {
        double[] azim_ele_dist = coords.azimuth_elevation_dist(testa1, testa2);
        assertEquals(AzimutA,azim_ele_dist[0],ERROR_MARGIN,"Something is wrong with the azimuth calculation");
        assertEquals(ElevationA,azim_ele_dist[1],ERROR_MARGIN,"Something is wrong with the elevation calculation");
        assertEquals(DistanceA,azim_ele_dist[2],ERROR_MARGIN,"Something is wrong with the distance calculation");
    }

    @Test
    void azimuth_elevation_distb() {
        double[] azim_ele_dist = coords.azimuth_elevation_dist(testb1, testb2);
        assertEquals(AzimutB,azim_ele_dist[0],ERROR_MARGIN,"Something is wrong with the azimuth calculation");
        assertEquals(ElevationB,azim_ele_dist[1],ERROR_MARGIN,"Something is wrong with the elevation calculation");
        assertEquals(DistanceB,azim_ele_dist[2],ERROR_MARGIN,"Something is wrong with the distance calculation");
    }

    @Test
    void azimuth_elevation_distc() {
        double[] azim_ele_dist = coords.azimuth_elevation_dist(testc1, testc2);
        assertEquals(AzimutC,azim_ele_dist[0],ERROR_MARGIN,"Something is wrong with the azimuth calculation");
        assertEquals(ElevationC,azim_ele_dist[1],ERROR_MARGIN,"Something is wrong with the elevation calculation");
        assertEquals(DistanceC,azim_ele_dist[2],ERROR_MARGIN,"Something is wrong with the distance calculation");
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