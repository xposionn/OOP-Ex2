package File_format;

import GIS.GIS_layer;

import java.io.File;
import java.io.IOException;

public class Csv2kml {


    public static void makeKmlFile(String fileName) {
        File csv = new File(fileName);
        csvTable csvTable = null;
        try {
            csvTable = new csvTable(csv);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tablelayer layer = new Tablelayer();
        GIS_layer testlayer = layer.TableLayer(csvTable, csv.getName());
        testlayer.toKml();
    }
}
