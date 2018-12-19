package Game;

import File_format.Csv2Layer;
import GIS.*;
import Geom.Point3D;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

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
        pacmen.setMeta(new Meta_data_layerAndProject("Pacmen Layer"));
        fruits = new GIS_layer_obj();
        fruits.setMeta(new Meta_data_layerAndProject("Fruits Layer"));
        Csv2Layer layer = new Csv2Layer();
        GIS_layer fullLayer = layer.csv2Layer(csvGameFile.getAbsolutePath());
        Iterator fullIterator = fullLayer.iterator();
        while(fullIterator.hasNext()){
            GIS_element elem = (GIS_element) fullIterator.next();
            if(elem.getData().getType().equals("P")){
                Packman pac = (Packman)elem;
                pacmen.add(pac);
            }else if(elem.getData().getType().equals("F")){
                Fruit fruit = (Fruit)elem;
                fruits.add(fruit);
            }
        }
    }

    /**
     * Saves current game state into CSV file. returns CSV file path.
     * @return file path for CSV file.
     */
    public void saveGameToCsv(String fullPath){
        final String COMMA = ",";
        final String NEW_LINE = "\n";
        String CSVheader = "Type,id,Lat,Lon,Alt,Speed/Weight,Radius";
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fullPath);
            fileWriter.append(CSVheader); //add header
            fileWriter.append(NEW_LINE); //new line separator after the header

            //Write all pacmen objects to the CSV file
            Iterator itpackman = this.pacmen.iterator();
            while (itpackman.hasNext()) {
                Packman pacman = (Packman)itpackman.next();
                fileWriter.append(pacman.getData().getType()); //type
                fileWriter.append(COMMA);
                fileWriter.append(String.valueOf(pacman.getID())); //ID
                fileWriter.append(COMMA);
                fileWriter.append(String.valueOf(((Point3D)pacman.getGeom()).x())); //lat
                fileWriter.append(COMMA);
                fileWriter.append(String.valueOf(((Point3D)pacman.getGeom()).y())); //lon
                fileWriter.append(COMMA);
                fileWriter.append(String.valueOf(((Point3D)pacman.getGeom()).z())); //alt
                fileWriter.append(COMMA);
                fileWriter.append(String.valueOf(pacman.getSpeed()));
                fileWriter.append(COMMA);
                fileWriter.append(String.valueOf(pacman.getEatRadius()));
                fileWriter.append(NEW_LINE);
            }
            //Write all fruits objects to the CSV file
            Iterator itFruit = this.fruits.iterator();
           while(itFruit.hasNext()) {
                Fruit fruit = (Fruit)itFruit.next();
                fileWriter.append(fruit.getData().getType()); //type
                fileWriter.append(COMMA);
                fileWriter.append(String.valueOf(fruit.getID())); //ID
                fileWriter.append(COMMA);
                fileWriter.append(String.valueOf(((Point3D)fruit.getGeom()).x())); //lat
                fileWriter.append(COMMA);
                fileWriter.append(String.valueOf(((Point3D)fruit.getGeom()).y())); //lon
                fileWriter.append(COMMA);
                fileWriter.append(String.valueOf(((Point3D)fruit.getGeom()).z())); //alt
                fileWriter.append(COMMA);
                fileWriter.append(String.valueOf(fruit.getWeight()));
                fileWriter.append(NEW_LINE);
            }
            System.out.println("CSV file created successfully");
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
        }
    }

    public GIS_layer getFruits() {
        return fruits;
    }

    public GIS_layer getPacmen() {
        return pacmen;
    }

}
