package Algorithms;

import File_format.Csv2Layer;
import GIS.GIS_layer;
import GIS.GIS_project;
import GIS.GIS_project_obj;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class MultiCSV {

    public static void main(String[] args) {
        GIS_project project = new GIS_project_obj("Project Name....");
        addTree(new File("."),project);
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
