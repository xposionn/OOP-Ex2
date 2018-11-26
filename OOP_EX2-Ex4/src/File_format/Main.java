package File_format;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Csv2kml csv2kml = new Csv2kml();
        String dir = System.getProperty("user.dir");
        String path = dir + File.separator;
        System.out.println(path);
        csv2kml.makeKmlFile(path+File.separator+"OOP_EX2-Ex4"+File.separator+"dataExamples"+File.separator+"WigleWifi_20171201110209.csv");
    }
}
