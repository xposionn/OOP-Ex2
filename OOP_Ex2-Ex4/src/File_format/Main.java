package File_format;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Csv2kml csv2kml = new Csv2kml();
        csv2kml.makeKmlFile("WigleWifi_20171201110209.csv");
        //test
    }
}
