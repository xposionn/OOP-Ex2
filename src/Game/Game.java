package Game;

import File_format.Csv2Layer;
import GIS.*;
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
        pacmen = new GIS_layer_obj();
        fruits = new GIS_layer_obj();
        Csv2Layer layer = new Csv2Layer();
        GIS_layer fullLayer = layer.csv2Layer(csvGameFile.getAbsolutePath());
        for(GIS_element elem:fullLayer){
            if(elem.getData().getType().equals("P")){
                pacmen.add(new Packman(elem.getGeom(), (Meta_data_element) elem.getData(),1,1));
            }else if(elem.getData().getType().equals("F")){
                fruits.add(new Fruit(elem.getGeom(), (Meta_data_element) elem.getData(),1));
            }
        }
    }

    /**
     * Saves current game state into CSV file. returns CSV file path.
     * @return file path for CSV file.
     */
    public String saveGameToCsv(){
        //TODO: implement method.
        return "csv file path";
    }

    public GIS_layer getFruits() {
        return fruits;
    }

    public GIS_layer getPacmen() {
        return pacmen;
    }

}
