package GIS;

import Coords.MyCoords;
import Geom.Geom_element;
import Geom.Point3D;

public class GIS_element_obj implements GIS_element {
    Geom_element geom;
    Meta_data metaData;


    /**
     * Constructor for the GIS_element object. gets a Geom_element and Meta_data.
     * @param geometryOfElement Geom_element, the geometry object of the element.
     * @param dataOfElement Meta_data, the data of the element.
     */
    public GIS_element_obj(Geom_element geometryOfElement, Meta_data dataOfElement) {
        this.geom = geometryOfElement;
        this.metaData = dataOfElement;
    }

    /******** Getters and Setters ********/
    /**
     *
     * @param geom
     */
    public void setGeom(Geom_element geom) {
        this.geom = geom;
    }

    public void setMetaData(Meta_data metaData) {
        this.metaData = metaData;
    }

    @Override
    public Geom_element getGeom() {
        return this.geom;
    }

    @Override
    public Meta_data getData() {
        return this.metaData;
    }

    @Override
    public void translate(Point3D vec) {
        MyCoords coords = new MyCoords();
        this.geom = coords.add((Point3D)this.geom,vec);
    }
}
