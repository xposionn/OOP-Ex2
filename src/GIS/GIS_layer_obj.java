package GIS;

import Geom.Point3D;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class GIS_layer_obj extends HashSet<GIS_element> implements GIS_layer {

    Meta_data layerMeta;

    @Override
    public Meta_data get_Meta_data() {
        return this.layerMeta;
    }

    public void setMeta(Meta_data meta) {
        this.layerMeta = meta;
    }

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
                        "<description>" + elem.getData().allInfo() + "</description>\n" +
                        "<styleUrl>"+ elem.getData().getStyleUrlColor()+"</styleUrl>\n" +
                        "<TimeStamp><when>"+ Algorithms.TimeChange.longtoUTC(elem.getData().getUTC())+"</when></TimeStamp>\n"+
                        "<Point>\n"+
                        "<coordinates>" + point.y() + "," + point.x() + ",0 </coordinates>\n" + //0 at Z is relative to ground height
                        "</Point>\n" +
                        "</Placemark>\n";
                kmlContent.add(kmlElement);
            }
            kmlContent.add(kmlEnd);
            bw.write(kmlContent.toString().replaceAll(", <P", "<P"));
            bw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return fileNameForNewKML;
    }

    @Override
    public String toKmlForProject() {

        //first two lines is irrelevant for the google format KML.

        String kmlContent = "<Folder>\n<name>"+layerMeta.getName()+"</name>\n";
        Iterator<GIS_element> it = this.iterator();
        while(it.hasNext()) {
            GIS_element elem = it.next();
            Point3D point = (Point3D) elem.getGeom();
            kmlContent += "<Placemark>\n" +
                    "<name>" + elem.getData().getName() + "</name>\n" +
                    "<description>" + elem.getData().allInfo() + "</description>\n" +
                    "<styleUrl>"+ elem.getData().getStyleUrlColor()+"</styleUrl>\n" +
                    "<TimeStamp><when>"+ Algorithms.TimeChange.longtoUTC(elem.getData().getUTC())+"</when></TimeStamp>\n"+
                     "<Point>\n"+
                    "<coordinates>" + point.y() + "," + point.x() + ",0 </coordinates>\n" + //0 at Z is relative to ground height
                    "</Point>\n" +
                    "</Placemark>\n";
        }
        return kmlContent+"</Folder>";
    }

}
