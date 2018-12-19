package GIS;

import Algorithms.TimeChange;
import Game.Fruit;
import Geom.Point3D;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class represents a GIS layer as a HashMap including GIS_elements inside, and also meta data for the layer
 * such as name of the layer, date of creation for the layer, etc.
 */
public class GIS_layer_obj extends HashSet<GIS_element> implements GIS_layer {

    private Meta_data layerMeta;

    /**
     * Getter method for the layer meta data.
     * @return Meta data, the layer meta data.
     */
    @Override
    public Meta_data get_Meta_data() {
        return this.layerMeta;
    }

    /**
     * Setter method for the layer meta data.
     * @param meta meta data, the layer meta we want to set to.
     */
    public void setMeta(Meta_data meta) {
        this.layerMeta = meta;
    }

    /**
     * This method will transform the current GIS_Layer (the current HashMap of GIS_elements) into one KML file.
     * It will include a complete code which can be run through Google Earth application.
     * The method will return the filename of the KML output file as string.
     * @return String, the filename of the KML output file.
     */
    public String toKml(){
        String fileNameForNewKML = layerMeta.getName().substring(0,layerMeta.getName().length()-4)+"_KML.kml";
        ArrayList<String> kmlContent = new ArrayList<>();
        String kmlStart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n"+
                "<Document>\n"+
                "<Style id=\"red\">\n"+
                "<IconStyle>\n"+
                "<Icon><href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href></Icon>\n"+
                "</IconStyle>\n"+
                "</Style>\n"+
                "<Style id=\"yellow\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/yellow-dot.png</href></Icon></IconStyle></Style><Style id=\"green\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/green-dot.png</href></Icon></IconStyle></Style>\n"+
                "<Folder>\n" +
                "<name>"+layerMeta.getName()+"</name>\n";
        kmlContent.add(kmlStart);
        String kmlEnd = "</Folder>\n" +
                "</Document></kml>";
        try {
            FileWriter fw = new FileWriter(fileNameForNewKML);
            BufferedWriter bw = new BufferedWriter(fw);
            //first two lines is irrelevant for the google format KML.
            for(GIS_element elem: this){
                Point3D point = (Point3D)elem.getGeom();
                String kmlElement = "<Placemark>\n" +
                        "<name>" + elem.getData().getName() + "</name>\n" +
                        "<description>" + elem.getData().toStringKML() + "</description>\n" +
//                        "<styleUrl>"+ elem.getData().getStyleUrlColor()+"</styleUrl>\n" +
                        "<TimeStamp><when>"+ Algorithms.TimeChange.longtoUTC(elem.getData().getUTC())+"</when></TimeStamp>\n"+
                        "<Point>\n"+
                        "<coordinates>" + point.y() + "," + point.x() + ",0 </coordinates>\n" + //0 at Z is relative to ground height
                        "</Point>\n" +
                        "</Placemark>\n";
                kmlContent.add(kmlElement);
            }
            kmlContent.add(kmlEnd);
            String output = kmlContent.toString().replaceAll(", <", "<");
            bw.write(output.substring(1,output.length()-1));
            bw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return fileNameForNewKML;
    }

    /**
     * This method will transform the current layer (HashMap of GIS_elements) into a working KML FOLDER as part of
     * a big fully-project KML file. it will work perfectly with Google Earth as an added folder with all elements inside.
     * @return String, the content of a folder as in KML format.
     */
    @Override
    public String toKmlForProject() {
        //first two lines is irrelevant for the google format KML.

        String kmlContent = "<Folder>\n<name>"+layerMeta.getName()+"</name>\n";
        for (GIS_element elem : this) {
            Fruit fruit = (Fruit)elem;
            Point3D point = (Point3D) elem.getGeom();
            kmlContent += "<Placemark>\n" +
                    "<name>" + fruit.getData().getName() + "</name>\n" +
                    "<description>" + fruit.getData().toStringKML() + "</description>\n" +
                    "<styleUrl>" + "#default-icon" + "</styleUrl>\n" + //can change default icons.
                    "<TimeSpan><begin>" + TimeChange.longtoUTC(fruit.getData().getUTC()) + "</begin><end>"+TimeChange.longtoUTC(fruit.getData().getUTC()+fruit.getTimeToEat()) +"</end></TimeSpan>\n" +
                    "<Point>\n" +
                    "<coordinates>" + point.y() + "," + point.x() + ",0 </coordinates>\n" + //0 at Z is relative to ground height
                    "</Point>\n" +
                    "</Placemark>\n";
        }
        return kmlContent+"</Folder>";
    }

}
