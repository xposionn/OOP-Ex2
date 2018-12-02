package File_format;

import GIS.GIS_layer;

import java.io.File;
import java.io.IOException;

/**
 * This class will transform a CSV file to a CsvTable (as in arrayList) to GIS_layer and then to KML file to work with Google Earth.
 */
public class Csv2kml {

    /**
     * This method will transform a CSV file into CsvTable (as in arrayList) to a GIS_layer, and then to KML file to work with Google Earth.
     * @param fileName fileName of the CSV file to transform into KML file.
     */
    public static void makeKmlFile(String fileName) {
        String path = System.getProperty("user.dir")+ File.separator;
        File csv = new File(path+File.separator+"dataExamples"+File.separator+fileName);
        CsvTable csvTable = null;
        try {
            csvTable = new CsvTable(csv);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TableLayer layer = new TableLayer();
        GIS_layer testlayer = layer.TableLayer(csvTable, csv.getName());
        testlayer.toKml();
    }
}
