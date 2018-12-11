package Game;

import Geom.Point3D;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {
    File image = new File("D:\\Projects\\IdeaProjects\\OOP-Ex2\\Resources\\GameMaps\\Ariel1.png");
    Point3D topleft = new Point3D(10,10,0);
    Point3D downright = new Point3D(30,0,0);
    Point3D pixel = new Point3D(20,2,0);
    Point3D coords = new Point3D(716.5,128.4,0.0);
    Map map = new Map(image,topleft,downright);

    @Test
    void coordsToPixels() {

    }

    @Test
    void pixelsToCoords() {

    }
}