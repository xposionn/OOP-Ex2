package Game;

import Coords.MyCoords;
import Geom.Point3D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Map implements MapInterface{

    private File Image;
    private BufferedImage ImageFile;
    private Point3D topLeft;
    private Point3D downRight;
    private Point3D topLeftPixel;
    private Point3D rightDownPixel;

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

    @Override
    public Point3D PixelsToCoords(Point3D p,double frameHeight,double frameWidth) {

        double latRange = downRight.x()-topLeft.x();
        double longRange = topLeft.y()-downRight.y();

        double xToCoords = (p.x()/frameWidth)*latRange + topLeft.x();
        double yToCoords = (1-(p.y()/frameHeight))*longRange + downRight.y();

        Point3D returnPoint = new Point3D(xToCoords,yToCoords,0);
        return returnPoint;

    }

    public double distance2Pixels(Point3D p1,Point3D p2,double frameHeight,double frameWidth){
        MyCoords coords = new MyCoords();
        Point3D p1toGPS = this.PixelsToCoords(p1,frameHeight,frameWidth);
        Point3D p2toGPS = this.PixelsToCoords(p2,frameHeight,frameWidth);
        double distance = coords.distance3d(p1toGPS,p2toGPS);
        return distance;
    }

    public double azimut2Pixels(Point3D p1,Point3D p2){

        double a = p2.x()-p1.x();
        double b = p2.y()-p2.y();
        double c = Math.sqrt(a*a+b*b);
        double eagle = Math.asin(Math.abs(a)/c);
        return eagle;
    }


    public String getImagePath() {
        return this.Image.getPath();
    }
}
