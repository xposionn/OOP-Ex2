package Coords;

import Geom.Point3D;

import java.awt.*;


public class MyCoords implements coords_converter {
    private final long RADIUS = 6371000;
//    private final double LON_NORM = 0.847091174;


    /**
     * computes a new point which is the gps point transformed by a 3D vector (in meters)
     */
    @Override
    public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
        double norm = Math.cos(Math.toRadians(gps.x()));
        double diffNewX = Math.toDegrees(Math.asin(local_vector_in_meter.x() / RADIUS));
        double newX = gps.x() + diffNewX;
        double diffNewY = Math.toDegrees(Math.asin(local_vector_in_meter.y() / (RADIUS * norm)));
        double newY = gps.y() + diffNewY;
        double newZ = gps.z() + local_vector_in_meter.z();
        return new Point3D(newX, newY, newZ);
    }

    /**
     * computes the 3D distance (in meters) between the two gps like points
     */
    @Override
    public double distance3d(Point3D gps0, Point3D gps1) {
        Point3D diffVec = vector3D(gps0, gps1);
        double distance = Math.sqrt(diffVec.x() * diffVec.x() + diffVec.y() * diffVec.y()+ diffVec.z()*diffVec.z());
        return distance;
    }

    /**
     * computes the 3D vector (in meters) between two gps like points.
     */
    @Override
    public Point3D vector3D(Point3D gps0, Point3D gps1) {
        double diffLAT = Math.sin(Math.toRadians(gps1.x()-gps0.x()))*RADIUS;
        double diffLON = Math.sin(Math.toRadians(gps1.y()-gps0.y()))*RADIUS * Math.cos(Math.toRadians(gps0.x()));
        double diffALT = gps1.z()-gps0.z();
        Point3D vectorDiff = new Point3D(diffLAT, diffLON, diffALT);
        return vectorDiff;
    }

    /**
     * computes the polar representation of the 3D vector be gps0-->gps1
     * Note: this method should return an azimuth (aka yaw), elevation (pitch), and distance
     */
    @Override
    public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {
        double[] azim_ele_dist = new double[3];
        Point3D vectorBetween = vector3D(gps0, gps1);
        double dist = distance3d(gps0, gps1);
        double azimuth;
        if(vectorBetween.y() == 0){
            if(vectorBetween.x()==0){
                azimuth = 0;
            }
        }
        azimuth = Math.toDegrees(Math.atan(vectorBetween.x() / vectorBetween.y())); //in degrees.
        while (azimuth<0){
            azimuth += 360;
        }
        while (azimuth >= 360){
            azimuth -= 360;
        }
        double elevation = Math.asin(vectorBetween.z() / dist);

        azim_ele_dist[0] = azimuth;
        azim_ele_dist[1] = elevation;
        azim_ele_dist[2] = dist;
        return azim_ele_dist;
    }

    /**
     * return true iff this point is a valid lat, lon , lat coordinate: [-180,+180],[-90,+90],[-450, +inf]
     *
     * @param p
     * @return
     */
    @Override
    public boolean isValid_GPS_Point(Point3D p) {

        return false;
    }

}
