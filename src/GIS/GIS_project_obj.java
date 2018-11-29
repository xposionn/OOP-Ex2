package GIS;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GIS_project_obj extends HashSet<GIS_layer> implements GIS_project {

    Meta_data projectMeta;

    public GIS_project_obj(String projectName) {
        projectMeta = new Meta_data_obj(projectName,System.currentTimeMillis()); //meta of the project will now initiate the time to be the creation time of the object.
    }

    @Override
    public Meta_data get_Meta_data() {
        return projectMeta;
    }

    public void toKml(String fileNameForNewKML) {
        ArrayList<String> kmlContent = new ArrayList<>();
        String kmlStart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n" +
                "<Document>\n" +
                "<Style id=\"red\">\n" +
                "<IconStyle>\n" +
                "<Icon><href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href></Icon>\n" +
                "</IconStyle>\n" +
                "</Style>\n" +
                "<Style id=\"yellow\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/yellow-dot.png</href></Icon></IconStyle></Style><Style id=\"green\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/green-dot.png</href></Icon></IconStyle></Style>\n" +
                "<Folder>\n" +
                "<name>" + projectMeta.getName() + "</name>\n";
        kmlContent.add(kmlStart);
        String kmlEnd = "</Folder>\n" +
                "</Document></kml>";
        try {
            FileWriter fw = new FileWriter(fileNameForNewKML);
            BufferedWriter bw = new BufferedWriter(fw);
            Iterator<GIS_layer> iterator = this.iterator();
            while (iterator.hasNext()) {
                GIS_layer layer = iterator.next();
                String kmlLayer = layer.toKmlForProject();
                kmlContent.add(kmlLayer);
            }
            kmlContent.add(kmlEnd);
            String writeTo = kmlContent.toString().replaceAll(", <", "<");
            writeTo = writeTo.substring(1,writeTo.length()-1); //first and last are irrelevant
            bw.write(writeTo);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
