package Game;

import GIS.GIS_layer;
import GIS.GIS_layer_obj;
import Geom.Point3D;

import java.io.File;

public class Game {
    GIS_layer fruits;
    GIS_layer pacmen;


    public Game() {
        Point3D topLeft = new Point3D(35.212336,32.10569);
        Point3D downRight = new Point3D(35.20238,32.10190);
        pacmen = new GIS_layer_obj();
        fruits = new GIS_layer_obj();
    }

    /**
     * Constructor to build the game from a saved CSV file. loads the data and creates the object.
     * @param csvGameFile CSV file to load.
     */

    public Game(File csvGameFile) {
    }

    /**
     * Saves current game state into CSV file. returns CSV file path.
     * @return file path for CSV file.
     */
    public String saveGameToCsv(){
        return "csv file path";
    }

    public GIS_layer getFruits() {
        return fruits;
    }

    public GIS_layer getPacmen() {
        return pacmen;
    }

}
