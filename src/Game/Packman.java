package Game;

import GIS.GIS_element_obj;
import GIS.Meta_data;
import GIS.Meta_data_element;
import Geom.Geom_element;
import Geom.Point3D;

public class Packman extends GIS_element_obj {
    private Point3D orientation; //Yaw, Roll, Pitch. as in: https://upload.wikimedia.org/wikipedia/commons/5/54/Flight_dynamics_with_text.png
    private double speed;
    private double eatRadius;

    /**
     * Constructor for the GIS_element object. gets a Geom_element and Meta_data.
     * @param geometryOfElement Geom_element, the geometry object of the element.
     * @param dataOfElement     Meta_data, the data of the element.
     */
    public Packman(Geom_element geometryOfElement, Meta_data_element dataOfElement) {
        super(geometryOfElement, dataOfElement);
        this.orientation = new Point3D(1,1,1);
        this.speed = 1;
        this.eatRadius = 1;
    }

    public Packman(Geom_element geometryOfElement, Meta_data_element dataOfElement, double speed, double eatRadius) {
        super(geometryOfElement, dataOfElement);
        this.orientation = new Point3D(1,1,1);
        this.speed = speed;
        this.eatRadius = eatRadius;
    }

    public Packman(Geom_element geometryOfElement, Meta_data dataOfElement, Point3D orientation, double speed, double eatRadius) {
        super(geometryOfElement, dataOfElement);
        this.orientation = orientation;
        this.speed = speed;
        this.eatRadius = eatRadius;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getEatRadius() {
        return eatRadius;
    }

    public void setEatRadius(double eatRadius) {
        this.eatRadius = eatRadius;
    }

    public Point3D getOrientation() {
        return orientation;
    }

    public void setOrientation(Point3D orientation) {
        this.orientation = orientation;
    }
}
