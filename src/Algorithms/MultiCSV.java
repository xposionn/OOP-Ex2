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
        GIS_project project = new GIS_project_obj("Filename");
        Collection<GIS_layer> all = new ArrayList<>();
        addTree(new File("."), all,project);
        project.toKml("test.kml");
    }

    //Scan all the folder

    static void addTree(File file, Collection<GIS_layer> all,GIS_project project) {
        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                if(child.toString().endsWith(".csv")) {
                    GIS_layer layer = Csv2Layer.csv2Layer(child.getName());
                    project.add(layer);

                }
                addTree(child, all,project);
            }
        }
    }

}
