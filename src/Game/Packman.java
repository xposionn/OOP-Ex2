package Game;

import Geom.Point3D;

public class Packman {
    private Point3D location;
    private long speed;
    private long orientationHORIZONTAL;
    private long orientationVERTICAL;

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
}
