package Algorithms;

import File_format.Csv2Layer;
import GIS.GIS_layer;
import GIS.GIS_project;
import GIS.GIS_project_obj;

import java.io.File;


public class MultiCSV {

    public static void createProject(String folderName) {
        GIS_project project = new GIS_project_obj(folderName);
        addTree(new File("./"+folderName),project);
        project.toKml(project.get_Meta_data().getName()+".kml");
    }

    //Scan all the folder

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
