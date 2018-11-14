package Coords;

import Geom.Point3D;

public class MyCoords implements coords_converter {
    @Override
    public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
        //update: Check if it is what we asked
        double x=gps.x()+local_vector_in_meter.x();
        double y=gps.y()+local_vector_in_meter.y();
        double z=gps.z()+local_vector_in_meter.z();
        return new Point3D(x,y,z);
    }

    @Override
    public double distance3d(Point3D gps0, Point3D gps1) {
    //computes distance between 2 dots, p1,p2
        double dx = gps0.x()-gps1.x();
        double dy = gps0.y()-gps1.y();
        double dz = gps0.z()-gps1.z();
        dx = Math.pow(dx,2);
        dy = Math.pow(dy,2);
        dz = Math.pow(dz,2);
        return Math.sqrt(dx+dy+dz);
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
