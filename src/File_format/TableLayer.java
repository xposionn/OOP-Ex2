package File_format;

import GIS.*;
import Geom.Point3D;

import java.util.Iterator;

public class TableLayer {


    public GIS_layer TableLayer(CsvTable csvTable, String fileName){

        GIS_layer layer = new GIS_layer_obj();
        Iterator<String []> iterator = csvTable.iterator();
        String[] header = csvTable.getHeader();
        int latIndex=0;
        int longIndex=0;
        int altIndex=0;
        int nameIndex=0;
        int colorIndex=0;
        int timeIndex=0;
        int BSSIDindex = -1;
        int CapabilitiesIndex = -1;
        int AccuracyMetersIndex = -1;
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
                colorIndex = i;
            } else if (header[i].equals("FirstSeen") || header[i].equals("Timestamp")) {
                timeIndex = i;
            } else if (header[i].equals("MAC")) {
                BSSIDindex = i;
            } else if (header[i].equals("AuthMode")) {
                CapabilitiesIndex = i;
            } else if (header[i].equals("AccuracyMeters")) {
                AccuracyMetersIndex = i;
            }
        }
        boolean itsWifiPointObject = (BSSIDindex!= -1) && (CapabilitiesIndex != -1) && (AccuracyMetersIndex != -1);
        double elemLat = 0;
        double elemLon = 0;
        double elemAlt = 0;
        long elemTime=0;
        iterator.next(); //ignore the header
        while(iterator.hasNext()){
            String[] element = iterator.next();
            try {
                elemLat = Double.parseDouble(element[latIndex]);
                elemLon = Double.parseDouble(element[longIndex]);
                elemAlt = Double.parseDouble(element[altIndex]);
                elemTime = Algorithms.TimeChange.stringUTCtoLong(element[timeIndex]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            Point3D elementGeom = new Point3D(elemLat,elemLon,elemAlt);
            Meta_data metaData;
            if(itsWifiPointObject){ //to call for relevant constructor of meta.
                String[] wifiMeta = new String[3];
                wifiMeta[0] = element[BSSIDindex];
                wifiMeta[1] = element[CapabilitiesIndex];
                wifiMeta[2] = element[AccuracyMetersIndex];
                metaData = new Meta_data_obj(element[nameIndex],elemTime,wifiMeta);
            }
            else { //its not a wifi point. check if it player or fruit here etc. to call for relevant constructor of meta.
                metaData = new Meta_data_obj(element[nameIndex], elemTime);
            }
            GIS_element_obj element_obj = new GIS_element_obj(elementGeom,metaData);
            layer.add(element_obj);
        }
        layer.setMeta(new Meta_data_obj(fileName,System.currentTimeMillis())); //meta of the layer. initiated with time as the creation time of the layer!
        return layer;
    }
}
