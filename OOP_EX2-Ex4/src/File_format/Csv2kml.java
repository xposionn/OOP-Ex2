package File_format;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Csv2kml {
//    private byte[] fileData;
    private String csvFileName =

    public Csv2kml(byte[] data){
        this.fileData = data;
    }

    //parsing the csv file is inspired by the code from this link: https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
    public changeToKml(String csvFileName) {

        //First, we read the CSV file.
        String csvFile = "/dataExamples/data/WigleWifi_20171201110209.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // using comma as separator
                String[] wifiPoints = line.split(cvsSplitBy);

                System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
}
