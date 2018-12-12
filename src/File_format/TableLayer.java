package File_format;

import GIS.*;
import Geom.Point3D;

import java.util.Iterator;

/**
 * This class will transform a CsvTable object into GIS_layer to be used across the application.
 * It will check for specific values inside the CsvTable including Lon, Lat, Alt, Name, color, type, time(timestamp), and some extra data if available. (speed etc)
 * it will generate the GIS_layer according to values available inside the csvTable, and if some are not available, it will ignore them and will only apply
 * and add the values to the GIS_layer if they are present.
 */
public class TableLayer {

    /**
     * This method will iterate through the csv header and check for specific index of values, such as Lon,Lat,Alt, name, color, type, etc.
     * Then, for each available variable it finds, it will add it to an GIS_layer object accordingly and will return the completed GIS layer.
     *
     * @param csvTable The Csv table we are transforming into the GIS_layer.
     * @param fileName the file name of the Csv table, it will be used to update the GIS layer META DATA with this name.
     * @return GIS_layer, completed layer with all relevant variables.
     */
    GIS_layer tableLayer(CsvTable csvTable, String fileName) {

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
//        int BSSIDindex = -1;
//        int CapabilitiesIndex = -1;
//        int AccuracyMetersIndex = -1;
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
//            } else if (header[i].equals("MAC")) {
//                BSSIDindex = i;
//            } else if (header[i].equals("AuthMode")) {
//                CapabilitiesIndex = i;
//            } else if (header[i].equals("AccuracyMeters")) {
//                AccuracyMetersIndex = i;
//            }
            }//end for loop on header.
//        boolean itsWifiPointObject = (BSSIDindex!= -1) && (CapabilitiesIndex != -1) && (AccuracyMetersIndex != -1);
            double elemLat = 0;
            double elemLon = 0;
            double elemAlt = 0;
            long elemTime = 0;
            iterator.next(); //ignore the header
            while (iterator.hasNext()) {
                String[] element = iterator.next();
                try {
                    elemLat = Double.parseDouble(element[latIndex]);
                    elemLon = Double.parseDouble(element[longIndex]);
                    elemAlt = Double.parseDouble(element[altIndex]);
                    elemTime = Algorithms.TimeChange.stringUTCtoLong(element[timeIndex]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                Point3D elementGeom = new Point3D(elemLat, elemLon, elemAlt);
                Meta_data metaData;
//            if(itsWifiPointObject){ //to call for relevant constructor of meta.
//                String[] wifiMeta = new String[3];
//                wifiMeta[0] = element[BSSIDindex];
//                wifiMeta[1] = element[CapabilitiesIndex];
////                wifiMeta[2] = element[AccuracyMetersIndex];
//                metaData = new Meta_data_obj(element[nameIndex],elemTime,wifiMeta);
//            }
//            else { //its not a wifi point. check if it player or fruit here etc. to call for relevant constructor of meta.
                metaData = new Meta_data_obj(element[nameIndex], elemTime);
//            }
                GIS_element_obj element_obj = new GIS_element_obj(elementGeom, metaData);
                layer.add(element_obj);
            }
            layer.setMeta(new Meta_data_obj(fileName, System.currentTimeMillis())); //meta of the layer. initiated with time as the creation time of the layer!
            return layer;
        }
    }
