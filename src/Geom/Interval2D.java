package Geom;

/**
 * This class represents an Interval between two points on a 2D surface.
 *
 * @author Liad and Timor.
 */
public class Interval2D implements Geom_element{

    private Point3D firstP, secondP;


    public Interval2D(Point3D firstP, Point3D secondP) {
        this.firstP = firstP;
        this.secondP = secondP;
    }



    public void setFirstP(Point3D firstP) {
        this.firstP = firstP;
    }

    public void setSecondP(Point3D secondP) {
        this.secondP = secondP;
    }
    public Point3D getFirstP() {
        return firstP;
    }

    public Point3D getSecondP() {
        return secondP;
    }

    /**
     * 3D distance from a 2d Interval to a 3d point doesn't make sense. we will calculate the 2d distance anyway.
     * @param p another Point to calculate distance to.
     * @return the shortest distance between any point in our Interval to another point given.
     */
    @Override
    public double distance3D(Point3D p) {
        return distance2D(p);
    }

    /**
     * Calculates the shortest distance available between our Interval and a point given. could be calculated from any point in our Interval.
     * @param p another Point to calculate distance to.
     * @return  the shortest distance between (any point in our) Interval to another point given.
     */
    @Override
    public double distance2D(Point3D p) {
        return 0; //not yet implemented
    }
}
