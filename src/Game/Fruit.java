package Game;

import Geom.Point3D;

public class Fruit {
    private Point3D location;
    boolean isEaten;

    public Fruit(Point3D location) {
        this.location = location;
        isEaten = false;
    }

    public void eatFruit(){
        this.isEaten = true;
    }
}
