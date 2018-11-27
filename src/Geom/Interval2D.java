package Geom;

/**
 * This class represents an Interval between two points on a 2D surface.
 *
 * @author Liad and Timor.
 */
public class Interval2D {

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
}
