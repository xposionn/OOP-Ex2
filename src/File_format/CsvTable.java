package File_format;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class will represent a CSV file inside a data structure (in ArrayList of String[] arrays).
 * it will have the methods to get the header, get its iterator, and a constructor which will get a CSV file.
 */
public class CsvTable {

    ArrayList<String[]> csvTable;

    /**
     * This method will return the header of the csv table, in a string[] array.
     * @return String[] the csv table header.
     */
    public String[] getHeader() {
        return csvTable.get(0);
    }

    /**
     * Override toString method.
     * @return
     */
    @Override
    public String toString() {
        return "CsvTable{" +
                "CsvTable=" + csvTable +
                '}';
    }

    /**
     * This constructor will get a CSV file and will try to read it and construct a csvTable object from it.
     * @param csv
     * @throws IOException
     */
    public CsvTable(File csv) throws IOException {
        csvTable = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(csv));
        String line;
        reader.readLine();
        while((line = reader.readLine())!=null){
            String[] add = line.split(",");
            csvTable.add(add);
        }
    }

    /**
     * Getter method for the csvTable. will return the arraylist it contains.
     * @return returns the arraylist the csvTable contains.
     */
    public List getCsvTable() {
        return csvTable;
    }

    /**
     * Getter method for the csvTable iterator, so we can iterate through the lines of the csv.
     * @return Iterator, to iterate through the lines of the csv.
     */
    public Iterator iterator() {
        return csvTable.iterator();
    }
}
