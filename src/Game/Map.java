package Game;

import Coords.MyCoords;
import Geom.Point3D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This Class represents a Map Object.
 * Map object contains
 * - Image (as File and as BufferedImage)
 * - topLeft corner of global Point of the Map
 * - downRight corner of global Point of the Map
 */
public class Map implements MapInterface{

    private File Image;
    private BufferedImage ImageFile;
    private Point3D topLeft;
    private Point3D downRight;
    private Point3D topLeftPixel;
    private Point3D rightDownPixel;

    /**
     * Constructor method for the Map object. will create new Map with provided image File, topLeft GPS coordinates, and down right GPS coordinates.
     * @param image File, our map image file.
     * @param topLeft a GPS location of real-world coordinates of topLeft corner of image.
     * @param downRight a GPS location of real-world coordinates of downRight corner of image.
     */
    public Map(File image, Point3D topLeft, Point3D downRight) {
        this.Image = image;
        try {
            ImageFile = ImageIO.read(image);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.topLeft = topLeft;
        this.downRight = downRight;
        this.topLeftPixel = new Point3D(0,0,0);
        this.rightDownPixel = new Point3D(ImageFile.getWidth(),ImageFile.getHeight(),0);
    }

    /**
     * This method will calculate on a given Point p (in global terms) to pixel in GUI, considering the size of the panel.
     * @param p - global point
     * @param panelHeight - the height of the panel
     * @param panelWidth - the width of the panel
     * @param latLonSwitched
     * @return
     */
    @Override
    public Point3D CoordsToPixels(Point3D p,double panelHeight,double panelWidth,boolean latLonSwitched){
        double rightX = downRight.x(); //30
        double leftX = topLeft.x();//10
        double maxY = topLeft.y(); //10
        double minY = downRight.y();//0

        double x = p.x(); //20
        double y = p.y();//5
        double xRange =  rightX - leftX; //20
        double yRange =  maxY - minY; // 5

        double xRatio = (x-leftX)/(xRange);
        double yRatio = (y-minY)/(yRange);

        double wPixel = panelWidth*xRatio;
        double hPixel = panelHeight-panelHeight*yRatio;
        Point3D pixel;
        if(!latLonSwitched) {
            pixel = new Point3D(wPixel,hPixel,0);
        } else {
            pixel = new Point3D(wPixel,hPixel,1);
        }
        if(pixel.x()>=0 && pixel.x() <= panelWidth && pixel.y()>=0 && pixel.y()<=panelWidth) //check if inside panel.
            return pixel;
        else{ //out of bounds from our panel.
            if(pixel.z()==1){ //after switching lat-lon
                throw new RuntimeException("You Provided GPS points with coordinates outside of the game map.");
            }
            Point3D switchedLanLon = new Point3D(p.y(),p.x(),0);
            return CoordsToPixels(switchedLanLon,panelHeight,panelWidth,true);
        } //TODO: ask Boaz if this is acceptable.
    }

    /**
     * This function will calculate the global points of pixel point, considering the frame height and frame width.
     * @param p - pixel point.
     * @param frameHeight - the height of the frame
     * @param frameWidth - the width of the frame
     * @return
     */
    @Override
    public Point3D PixelsToCoords(Point3D p,double frameHeight,double frameWidth) {

        double latRange = downRight.x()-topLeft.x();
        double longRange = topLeft.y()-downRight.y();

        double xToCoords = (p.x()/frameWidth)*latRange + topLeft.x();
        double yToCoords = (1-(p.y()/frameHeight))*longRange + downRight.y();

        Point3D returnPoint = new Point3D(xToCoords,yToCoords,0);
        return returnPoint;

    }

    /**\
     * This method will calcutae the distance between 2 pixels and the real gloal points.
     * @param p1 - first pixel
     * @param p2 - seond pixel
     * @param frameHeight - the height of the frame
     * @param frameWidth - the width of the frame
     * @return
     */
    public double distance2Pixels(Point3D p1,Point3D p2,double frameHeight,double frameWidth){
        MyCoords coords = new MyCoords();
        Point3D p1toGPS = this.PixelsToCoords(p1,frameHeight,frameWidth);
        Point3D p2toGPS = this.PixelsToCoords(p2,frameHeight,frameWidth);
        double distance = coords.distance3d(p1toGPS,p2toGPS);
        return distance;
    }

    /**
     * This method will calculate the azimut between 2 pixels.
     * @param p1 - first pixel
     * @param p2 - second pixel
     * @return double eagle - the azimut between those 2 pixels.
     */
    public double azimut2Pixels(Point3D p1,Point3D p2){

        double a = p2.x()-p1.x();
        double b = p2.y()-p2.y();
        double c = Math.sqrt(a*a+b*b);
        double eagle = Math.asin(Math.abs(a)/c);
        return eagle;
    }

    /**
     * This method will return the path of the image that this Map class using.
     * @return - String - the path of the image.
     */
    public String getImagePath() {
        return this.Image.getPath();
    }
}
