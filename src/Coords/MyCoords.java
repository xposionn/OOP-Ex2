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
        if(!isValid_GPS_Point(gps)) throw new RuntimeException("You entered invalid GPS point for the add function.");
        double norm = Math.cos(Math.toRadians(gps.x()));
        double diffNewX = Math.toDegrees(Math.asin(local_vector_in_meter.x() / RADIUS));
        double newX = gps.x() + diffNewX;
        double diffNewY = Math.toDegrees(Math.asin(local_vector_in_meter.y() / (RADIUS * norm)));
        double newY = gps.y() + diffNewY;
        double newZ = gps.z() + local_vector_in_meter.z();
        while(newX>90){
            newX -= 180;
        }
        while(newX<-90){
            newX += 180;
        }
        while(newY>180){
            newX -= 360;
        }
        while(newY<-180){
            newY += 360;
        }
        Point3D newPoint = new Point3D(newX, newY, newZ);
        if(newZ>8848 || newZ<-450){
            throw new RuntimeException("You tried to move below the lowest point on earth or above the highest point on Mt. Everest. THAT IS UN-ACCEPTABLE!!");
        }
        if(!isValid_GPS_Point(newPoint)){
            throw new RuntimeException("Something is wrong with the vector you tried to add to that point. The calculated point is invalid in our GPS system!");
        }
        return newPoint;
    }

    /**
     * computes the 3D distance (in meters) between the two gps like points
     * @param gps0 first gps point.
     * @param gps1 second gps point.
     * @return double, the distance between the two gps points.
     */
    @Override
    public double distance3d(Point3D gps0, Point3D gps1) {
        if(!isValid_GPS_Point(gps0) || !isValid_GPS_Point(gps1)) throw new RuntimeException("You entered invalid GPS point for the distance3D function.");
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
        if(!isValid_GPS_Point(gps0) || !isValid_GPS_Point(gps1)) throw new RuntimeException("You entered invalid GPS point for the vector3D function.");
        double diffLAT = Math.sin(Math.toRadians(gps1.x()-gps0.x()))*RADIUS;
        double diffLON = Math.sin(Math.toRadians(gps1.y()-gps0.y()))*RADIUS * Math.cos(Math.toRadians(gps0.x()));
        double diffALT = gps1.z()-gps0.z();
        return new Point3D(diffLAT, diffLON, diffALT);
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
        if(!isValid_GPS_Point(gps0) || !isValid_GPS_Point(gps1)) throw new RuntimeException("You entered invalid GPS point for the azimuth_elevation_dist function.");
        double[] azim_ele_dist = new double[3];
        Point3D vectorBetween = vector3D(gps0, gps1);
        double dist = distance3d(gps0, gps1);
        double azimuth = 0;
        boolean azimuthMikreKaze = false;
        if(vectorBetween.y() == 0){
            if(vectorBetween.x()==0){
                azimuth = 0;
                azimuthMikreKaze = true;
            }
        }
        if(!azimuthMikreKaze) {
            azimuth = Math.atan2(Math.sin(Math.toRadians(gps1.y()-gps0.y())) * Math.cos(Math.toRadians(gps1.x())),Math.cos(Math.toRadians(gps0.x()))*Math.sin(Math.toRadians(gps1.x())) - Math.sin(Math.toRadians(gps0.x()))*Math.cos(Math.toRadians(gps1.x()))*Math.cos(Math.toRadians(gps1.y()-gps0.y())));
            azimuth = Math.toDegrees(azimuth);
        }
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
        boolean lat = (-90 <= p.x()) && (p.x() <= 90);
        boolean lon = (-180 <= p.y()) && (p.y() <= 180);
        boolean alt = (-450 <= p.z()) && (p.z() <= 8848); // Everest High -> 8848
        return lat&&lon&&alt;
    }

}
