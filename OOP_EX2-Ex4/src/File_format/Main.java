package File_format;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        File csv = new File("D:\\Projects\\IdeaProjects\\OOP-Ex2-4\\OOP_EX2-Ex4\\dataExamples\\data\\WigleWifi_20171201110209.csv");
        csvTable csvTable = new csvTable(csv);
        System.out.println(csvTable.toString());

    }
}
