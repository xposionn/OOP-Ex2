package Algorithms;

import File_format.Csv2Layer;
import GIS.GIS_layer;
import GIS.GIS_project;
import GIS.GIS_project_obj;

import java.io.File;

/**
 * This class represents an algorithm which gets a folder name, scanning it recursively for CSV files, creating a project (a HashMap of
 * GIS_layers (which are HashMaps of GIS_elements)). Saving the data in KML format file to work with Google Earth,
 * with timestamps to show timeline.
 */
public class MultiCSV {

    /**
     * Driver method to create a project using the recursive method.
     * @param folderName folder name to scan for all CSV files.
     */
    public static void createProject(String folderName) {
        GIS_project project = new GIS_project_obj(folderName);
        addTree(new File("./"+folderName),project);
        project.toKml(project.get_Meta_data().getName()+".kml");
    }

    /**
     * This method will scan the whole folder for CSV files, for each CSV file, create a GIS layer in a project,
     * and adding the layer to the project.
     * @param file a specific file in a folder.
     * @param project the GIS project we add the layers to.
     */
    static void addTree(File file,GIS_project project) {
        File[] children = file.listFiles(); //all files inside directory
        if (children != null) {
            for (File child : children) {
                if(child.toString().endsWith(".csv")) {
                    GIS_layer layer = Csv2Layer.csv2Layer(child.getAbsolutePath());
                    project.add(layer);
                }
                addTree(child,project);
            }
        }
    }

}
