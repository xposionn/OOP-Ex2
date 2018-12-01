package GIS;

import Coords.MyCoords;
import Geom.Geom_element;
import Geom.Point3D;

/**
 *  This class represents a GIS element with geometric representation and meta data such as:
 *  color, string, timing...
 */
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
     * Setter function for Geom.
     * @param geom the geom we set the element to.
     */
    public void setGeom(Geom_element geom) {
        this.geom = geom;
    }

    /**
     * Setter function for Meta data.
     * @param metaData the meta data we set the element with.
     */
    public void setMetaData(Meta_data metaData) {
        this.metaData = metaData;
    }

    /**
     * Getter function for Geom.
     * @return a Geom_element, the geometry of the element.
     */
    @Override
    public Geom_element getGeom() {
        return this.geom;
    }

    /**
     * Getter function for the meta data.
     * @return Meta_data, the meta data of the element.
     */
    @Override
    public Meta_data getData() {
        return this.metaData;
    }

    /**
     * Function getting a 3d vector and transforming the current GIS_Element point location adding the 3d vector.
     * @param vec Point3d, the 3d vector we are adding to the current position of element.
     */
    @Override
    public void translate(Point3D vec) {
        MyCoords coords = new MyCoords();
        this.geom = coords.add((Point3D)this.geom,vec);
    }
}
