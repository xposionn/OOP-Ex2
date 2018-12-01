package Coords;

import Geom.Point3D;

import java.awt.*;


public class MyCoords implements coords_converter {
    private final long RADIUS = 6371000;

    /**
     * computes a new point which is the gps point transformed by a 3D vector (in meters)
     * @param gps Point3D as our initial gps point we want to transform.
     * @param local_vector_in_meter the vector to transform the point with.
     * @return the new GPS point transformed by the vector.
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
     * @param gps0 first gps point.
     * @param gps1 second gps point.
     * @return double, the distance between the two gps points.
     */
    @Override
    public double distance3d(Point3D gps0, Point3D gps1) {
        Point3D diffVec = vector3D(gps0, gps1);
        return diffVec.distance3D(new Point3D(0,0,0)); //difference vector is related to 0,0,0 point hence we calc distance according to that.
    }

    /**
     * computes the 3D vector (in meters) between two gps like points.
     * @param gps0 the first gps point.
     * @param gps1 second gps point.
     * @return the 3D vector between the two gps points.
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
     * returns the azimuth, elevation, distance between 2 points.
     * @param gps0 first gps point.
     * @param gps1 second gps point.
     * @return azimuth,elevation,distance between 2 points.
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
        while (azimuth<0){ //only positive azimuth.
            azimuth += 360;
        }
        while (azimuth >= 360){ //between 0 to 360.
            azimuth -= 360;
        }
        double elevation = Math.asin(vectorBetween.z() / dist);

        azim_ele_dist[0] = azimuth;
        azim_ele_dist[1] = elevation;
        azim_ele_dist[2] = dist;
        return azim_ele_dist;
    }

    /**
     * return true iff this point is a valid lat, lon , alt coordinate: [-180,+180],[-90,+90],[-450, +8848]
     *
     * @param p the GPS point to check if it is a valid point.
     * @return true iff valid.
     */
    @Override
    public boolean isValid_GPS_Point(Point3D p) {

        boolean lat = (-180 <= p.x()) && (p.x() <= 180);
        boolean lon = (-90 <= p.y()) && (p.y() <= 90);
        boolean alt = (-450 <= p.z()) && (p.z() <= 8848); // Everest High -> 8848
        return lat&&lon&&alt;
    }

}
