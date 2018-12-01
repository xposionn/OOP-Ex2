package File_format;


import Algorithms.MultiCSV;

public class Main {
    public static void main(String[] args) {
        MultiCSV.createProject("dataExamples");
        Csv2kml.makeKmlFile("WigleWifi_20171201110209.csv");
    }
}
