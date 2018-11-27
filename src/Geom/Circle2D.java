package Geom;

/**
 * This class represents a circle in a 2 dimension world.
 *
 * @author Liad and Timor.
 */
public class Circle2D implements Geom_element{

    final double PI = 3.14159265359;

    private Point3D middlePoint; //Z coordinate will be zeroed.
    private double radius;

    /**
     * Constructor for the circle class.
     * @param middlePoint our 3D point to be constructed as our middle of circle. Z coordinate is zeroed. (ignored)
     * @param radius radius of the circle.
     */
    public Circle2D(Point3D middlePoint, double radius) {
        Point3D mid = new Point3D(middlePoint.x(),middlePoint.y(),0); //z coordinate zeroed.
        this.middlePoint = mid;
        this.radius = radius;
    }

    /**
     * Constructor for the circle class.
     * @param middleX X coordinate of the middle point of circle.
     * @param middleY Y coordinate of the middle point of circle.
     * @param radius radius of the circle.
     */
    public Circle2D(double middleX, double middleY, double radius) {
        Point3D mid = new Point3D(middleX,middleY,0);
        this.middlePoint = mid;
        this.radius = radius;
    }

    /**
     * 3D distance from a 2d circle to a 3d point doesn't make sense. we will calculate the 2d distance anyway.
     * @param p another Point to calculate distance to.
     * @return the distance between the middle point of current circle to the point.
     */
    @Override
    public double distance3D(Point3D p) {
        return this.distance2D(p);
    }

    /**
     * A 2d distance from a 2d point (z is ignored) to the middle of the circle.
     * @param p a 3D point (actually 2d - z is zeroed) to calculate distance with.
     * @return the distance between the middle point of current circle to the point.
     */
    @Override
    public double distance2D(Point3D p) {
        return p.distance2D(this.middlePoint);
    }
}
