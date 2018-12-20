package Game;

import Geom.Point3D;

/**
 * This class interface Map class
 * @author : Liad and Timor
 */

public interface MapInterface {
    Point3D CoordsToPixels(Point3D p,double Height,double Width,boolean latLonSwitch);
    Point3D PixelsToCoords(Point3D p,double Height,double Width);
    String getImagePath();

}
