package Algorithems;

import File_format.Csv2kml;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class MultiCSV {

    public static void main(String[] args) {


        Collection<File> all = new ArrayList<File>();
        addTree(new File("."), all);
        for (File file : all) {
            Csv2kml c2k = new Csv2kml();
            String fileName = file.toString();
            fileName = fileName.substring(fileName.indexOf("a\\")+2);
            c2k.changeToKML(fileName); //update Location
        }
    }

    //Scan all the folder

    static void addTree(File file, Collection<File> all) {
        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                if (child.toString().endsWith(".csv")) {
                    all.add(child);
                }
                addTree(child, all);
            }
        }
    }

}
