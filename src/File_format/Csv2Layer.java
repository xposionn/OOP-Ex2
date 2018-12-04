package File_format;

import GIS.GIS_layer;

import java.io.File;
import java.io.IOException;

/**
 * This class will transform a CSV file into a GIS_layer and return a GIS_layer object as representation of the csv file.
 */
public class Csv2Layer {
    /**
     * This method will get a CSV file as String (its path) and transform it into a GIS_layer object, then returns the
     * newly created GIS_layer.
     * @param fileName the path for the csv file.
     * @return GIS_layer, the layer we created from the csv file.
     */
    public static GIS_layer csv2Layer(String fileName) { //fileName is the full path to the file
        String path = System.getProperty("user.dir")+ File.separator;
        File csv = new File(fileName);
        CsvTable csvTable = null;
        try {
            csvTable = new CsvTable(csv);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TableLayer layer = new TableLayer();
        GIS_layer testlayer = layer.tableLayer(csvTable, csv.getName());
        return testlayer;
    }
}
