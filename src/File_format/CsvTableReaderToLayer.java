package File_format;

import GIS.*;
import Game.Fruit;
import Game.Packman;
import Geom.Point3D;

import java.util.Iterator;

/**
 * This class will transform a CsvTable object into GIS_layer to be used across the application.
 * It will check for specific values inside the CsvTable including Lon, Lat, Alt, Name, color, type, time(timestamp), and some extra data if available. (speed etc)
 * it will generate the GIS_layer according to values available inside the csvTable, and if some are not available, it will ignore them and will only apply
 * and add the values to the GIS_layer if they are present.
 */
public class CsvTableReaderToLayer {

    /**
     * This method will iterate through the csv header and check for specific index of values, such as Lon,Lat,Alt, name, color, type, etc.
     * Then, for each available variable it finds, it will add it to an GIS_layer object accordingly and will return the completed GIS layer.
     *
     * @param csvTable The Csv table we are transforming into the GIS_layer.
     * @param fileName the file name of the Csv table, it will be used to update the GIS layer META DATA with this name.
     * @return GIS_layer, completed layer with all relevant variables.
     */
    GIS_layer CsvTableReaderToLayer(CsvTable csvTable, String fileName) {

        GIS_layer layer = new GIS_layer_obj();
        Iterator<String[]> iterator = csvTable.iterator();
        String[] header = csvTable.getHeader();
        int latIndex = -1;
        int longIndex = -1;
        int altIndex = -1;
        int nameIndex = -1;
        int colorIndex = -1;
        int timeIndex = -1;
        int speedOrWeightIndex = -1;
        int typeIndex = -1;
        int idIndex = -1;
        int radiusIndex = -1;
        for (int i = 0; i < header.length; i++) {
            if (header[i].equals("CurrentLatitude") || header[i].equals("Lat")) {
                latIndex = i;
            } else if (header[i].equals("CurrentLongitude") || header[i].equals("Lon")) {
                longIndex = i;
            } else if (header[i].equals("AltitudeMeters") || header[i].equals("Alt")) {
                altIndex = i;
            } else if (header[i].equals("SSID") || header[i].equals("Name")) { //name
                nameIndex = i;
            } else if (header[i].equals("Color")) {
                colorIndex = i; //will be used later in assignments. extra functionality.
            } else if (header[i].equals("FirstSeen") || header[i].equals("Timestamp")) {
                timeIndex = i;
            } else if (header[i].contains("Speed")) {
                speedOrWeightIndex = i;
            } else if (header[i].equals("Type")) {
                typeIndex = i;
            } else if (header[i].equals("id")) {
                idIndex = i;
            } else if (header[i].equals("Radius")) {
                radiusIndex = i;
            }
        }//end for loop on header.
        //check for must-have indexes found in file.
        if(latIndex== -1 || longIndex == -1 || altIndex == -1 || typeIndex == -1 || speedOrWeightIndex == -1 || idIndex == -1){
            throw new RuntimeException("CSV file is not compatible.\n Must have the following in header: lat,lon,alt,type,id,speed/weight");
        }
        else {
            double elemLat = 0;
            double elemLon = 0;
            double elemAlt = 0;
            long elemTime = 0;
            double speedOrWeight = 0;
            double radius = 0;
            int ID = 0;
            iterator.next(); //ignore the header
            while (iterator.hasNext()) {
                String[] element = iterator.next();
                try {
                    elemLat = Double.parseDouble(element[latIndex]);
                    elemLon = Double.parseDouble(element[longIndex]);
                    elemAlt = Double.parseDouble(element[altIndex]);
                    speedOrWeight = Double.parseDouble(element[speedOrWeightIndex]);
                    ID = Integer.parseInt(element[idIndex]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                Point3D elementGeom = new Point3D(elemLon,elemLat, elemAlt); //TODO: we read opposite because Boaz provided bad csv examples.
                Meta_data metaDataElem = new Meta_data_element(element[idIndex], element[typeIndex]);
                if (colorIndex != -1) { //optional.
                    String colorHEXvalue = element[colorIndex];
                    metaDataElem.setColor(colorHEXvalue);
                }
                if (timeIndex != -1) {//optional. //TODO: will have to change once we code the best best-route algorithm.
                    elemTime = Algorithms.TimeChange.stringUTCtoLong(element[timeIndex]);
                    metaDataElem.setUTCtime(elemTime);
                }
                if (element[typeIndex].equals("P")) {
                    radius = Double.parseDouble(element[radiusIndex]);
                    Packman element_obj = new Packman(elementGeom, (Meta_data_element) metaDataElem,ID, speedOrWeight, radius);
                    layer.add(element_obj);
                } else if (element[typeIndex].equals("F")) {
                    Fruit element_obj = new Fruit(elementGeom, (Meta_data_element) metaDataElem,ID, speedOrWeight);
                    layer.add(element_obj);
                }
            }
            layer.setMeta(new Meta_data_layerAndProject(fileName)); //meta of the layer. initiated with time as the creation time of the layer!
            return layer;
        }
    }
}
