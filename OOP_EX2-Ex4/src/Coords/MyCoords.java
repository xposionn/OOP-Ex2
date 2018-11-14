package Coords;

import Geom.Point3D;

public class MyCoords implements coords_converter {
    @Override
    public Point3D add(Point3D gps, Point3D local_vector_in_meter) {

        return null;
    }

    @Override
    public double distance3d(Point3D gps0, Point3D gps1) {


        return 0;
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
