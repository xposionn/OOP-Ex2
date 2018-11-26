package GIS;

import Coords.MyCoords;
import Geom.Geom_element;
import Geom.Point3D;

public class GIS_element_obj implements GIS_element {
    Geom_element geom;
    Meta_data metaData;


    public GIS_element_obj(Geom_element geometryOfElement, Meta_data dataOfElement) {
        this.geom = geometryOfElement;
        this.metaData = dataOfElement;
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
