package Game;

import GIS.GIS_layer;
import GIS.GIS_layer_obj;
import GIS.Meta_data_layerAndProject;
import Geom.Point3D;

import java.io.File;

public class Game {

    GIS_layer fruits;
    GIS_layer pacmen;


    public Game() {
        pacmen = new GIS_layer_obj();
        pacmen.setMeta(new Meta_data_layerAndProject("Pacmen Layer"));
        fruits = new GIS_layer_obj();
        fruits.setMeta(new Meta_data_layerAndProject("Fruits Layer"));
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
