package GIS;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 *  This class represents a GIS Project as a HashMap including GIS layers inside, and also meta data for the project
 *  such as name of the project, date of creation for the project, etc.
 */
public class GIS_project_obj extends HashSet<GIS_layer> implements GIS_project {

    private Meta_data projectMeta;

    public Meta_data getProjectMeta() {
        return projectMeta;
    }

    public void setProjectMeta(Meta_data projectMeta) {
        this.projectMeta = projectMeta;
    }

    /**
     * Constructor for the GIS_project. takes projectName as string to create the project with current system time in meta.
     * @param projectName string, name for the project object.
     */
    public GIS_project_obj(String projectName) {
        projectMeta = new Meta_data_layerAndProject(projectName);
    }

    /**
     * Getter function for the meta data of the project.
     * @return Meta_data, the data of the project incl. name, time of creation, etc.
     */
    @Override
    public Meta_data get_Meta_data() {
        return projectMeta;
    }

    /**
     * This method will transform the current GIS_project (the current HashMap of GIS_Layers) into one KML file.
     * It will include a complete code which can be run through Google Earth application.
     * The method will return the filename of the KML output file as string.
     * @param fileNameForNewKML String, the filename requested as the name of the file output.
     */
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
            for (GIS_layer layer : this) {
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
