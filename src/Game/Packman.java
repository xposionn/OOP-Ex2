package Game;

import Geom.Point3D;

public class Packman {
    private Point3D location;
    private double speed;
    private double orientationHORIZONTAL;
    private double orientationVERTICAL;

    /**
     * Constructor for out packman robot.
     * @param location point3d location in lat,lon,alt.
     * @param speed the packman speed.
     * @param orientationHORIZONTAL its horizontal orientation.
     * @param orientationVERTICAL its vertical orientation.
     */
    public Packman(Point3D location, long speed, long orientationHORIZONTAL, long orientationVERTICAL) {
        this.location = location;
        this.speed = speed;
        this.orientationHORIZONTAL = orientationHORIZONTAL;
        this.orientationVERTICAL = orientationVERTICAL;
    }

    /***** Setters and getters *****/
    public Point3D getLocation() {
        return location;
    }

    public void setLocation(Point3D location) {
        this.location = location;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getOrientationHORIZONTAL() {
        return orientationHORIZONTAL;
    }

    public void setOrientationHORIZONTAL(double orientationHORIZONTAL) {
        this.orientationHORIZONTAL = orientationHORIZONTAL;
    }

    public double getOrientationVERTICAL() {
        return orientationVERTICAL;
    }

    public void setOrientationVERTICAL(double orientationVERTICAL) {
        this.orientationVERTICAL = orientationVERTICAL;
    }

}
