package Game;

import GIS.GIS_element;
import GIS.Meta_data;
import Geom.Geom_element;
import Geom.Point3D;

public class Fruit implements GIS_element {
    private Point3D location;
    boolean isEaten;

    public Fruit(Point3D location) {
        this.location = location;
        isEaten = false;
    }

    public void eatFruit(){
        this.isEaten = true;
    }

    /***** Setters and getters *****/
    public Point3D getLocation() {
        return location;
    }

    public void setLocation(Point3D location) {
        this.location = location;
    }

    public boolean isEaten() {
        return isEaten;
    }

    public void setEaten(boolean eaten) {
        isEaten = eaten;
    }

    @Override
    public Geom_element getGeom() {
        return null;
    }

    @Override
    public Meta_data getData() {
        return null;
    }

    @Override
    public void translate(Point3D vec) {

    }
}
