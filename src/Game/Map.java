package Game;

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
    public Point3D CoordsToPixels(Point3D p,double Height,double Width){
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


        double wPixel = Width*xRatio;
        double hPixel = Height*yRatio;

        Point3D pixel = new Point3D(wPixel,hPixel,0);
        return pixel;
    }

    @Override
    public Point3D PixelsToCoords(Point3D p,double Height,double Width) {

        double width = rightDownPixel.x();
        double latRange = downRight.x()-topLeft.x();
        double xToCoords = (p.x()/Width)*latRange + topLeft.x();

        double height = rightDownPixel.y();
        double longRange = topLeft.y()-downRight.y();

        double yToCoords = (p.y()/Height)*longRange + downRight.y();

        Point3D returnPoint = new Point3D(xToCoords,yToCoords,0);
        return returnPoint;

    }


    public String getImagePath() {
        return this.Image.getPath();
    }
}
