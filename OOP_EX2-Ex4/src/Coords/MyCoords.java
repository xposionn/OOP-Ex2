package Coords;

import Geom.Point3D;



public class MyCoords implements coords_converter {
    private static long RADIUS = 6371000;

    @Override
    public Point3D add(Point3D gps, Point3D local_vector_in_meter) {

        return null;
    }

    @Override
    public double distance3d(Point3D gps0, Point3D gps1) {
        double latDiff = gps0.x()-gps1.x();
        double lonDiff = gps0.y()-gps1.y();
        double altDiff = gps0.z()-gps1.z();
        double lonNorm = Math.cos(gps0.x()*Math.PI/180);
        double latDiffRadian = latDiff*Math.PI/180;
        double lonDiffRadian = lonDiff*Math.PI/180;
        double toMeter1 = Math.sin(latDiffRadian)*RADIUS;
        double toMeter2 = Math.sin(lonDiffRadian)*RADIUS*lonNorm;
        return Math.sqrt(Math.pow(toMeter1,2)+Math.pow(toMeter2,2));

    }

    @Override
    public Point3D vector3D(Point3D gps0, Point3D gps1) {


        return null;
    }

    @Override
    public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {


        return new double[0];
    }

    @Override
    public boolean isValid_GPS_Point(Point3D p) {
        return false;
    }
}
