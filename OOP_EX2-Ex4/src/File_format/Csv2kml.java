package File_format;

import java.io.*;
import java.util.ArrayList;

public class Csv2kml {


    //parsing the csv file is inspired by the code from this link: https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
    //returns filepath for KML file.
    //Added by timor


    public Csv2kml() {
    }

    public String changeToKML(String csvFileName) {
        String fileNameForNewKML = csvFileName+"_inKMLdata.kml";
        ArrayList<String> kmlContent = new ArrayList<>();
        String kmlStart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\"><Document><Style id=\"red\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href></Icon></IconStyle></Style><Style id=\"yellow\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/yellow-dot.png</href></Icon></IconStyle></Style><Style id=\"green\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/green-dot.png</href></Icon></IconStyle></Style><Folder><name>Wifi Networks</name>\n";
        kmlContent.add(kmlStart);
        String kmlEnd = "</Folder>\n" +
                "</Document></kml>";

        //We also read the CSV file.
        String csvFile = "D:\\Projects\\IdeaProjects\\OOP-Ex2-4\\OOP_EX2-Ex4\\dataExamples\\data\\"+csvFileName;
        String line;
        String cvsSplitBy = ",";

        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            FileWriter fw = new FileWriter(fileNameForNewKML);
            BufferedWriter bw = new BufferedWriter(fw);
            //first two lines is irrelevant for the google format KML.
            if (br.readLine() != null) {
                br.readLine();
            }
            while ((line = br.readLine()) != null) {
                // using comma as separator
                String[] wifiPoint = line.split(cvsSplitBy);
                String kmlElement = "<Placemark>\n" +
                        "<name><![CDATA[" + wifiPoint[1] + "]]</name>\n" +
                        "<description><![CDATA[MAC: <b>" + wifiPoint[0] + "</b><br/>Capabilities: <b>" + wifiPoint[2] + "</b><br/>FirstSeen: <b>" + wifiPoint[3] + "</b><br/>Channel: <b>" + wifiPoint[4] + "</b><br/>RSSI: <b>" + wifiPoint[5] +  "</b><br/>AltitudeMeters: <b>" + wifiPoint[8] + "</b><br/>AccuracyMeters: <b>" + wifiPoint[9] + "</b><br/>Type: <b>" + wifiPoint[10] + "</description>\n" +
                        "<coordinates>" + wifiPoint[6] + "," + wifiPoint[7] +",0"+ "</coordinates>\n" +
                        "</Point>\n" +
                        "</Placemark>\n";
                kmlContent.add(kmlElement);
            }
            kmlContent.add(kmlEnd);
            String fromTheCSV = kmlContent.toString().replaceAll(",", "").replace("[", "").replace("]", "");
            bw.write(fromTheCSV);
            bw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return fileNameForNewKML;
    } //end of changeToKML function
}
