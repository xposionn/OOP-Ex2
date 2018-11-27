package File_format;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CsvTable {

    ArrayList<String[]> csvTable;

    public String[] getHeader() {
        return csvTable.get(0);
    }

    @Override
    public String toString() {
        return "CsvTable{" +
                "CsvTable=" + csvTable +
                '}';
    }

    public CsvTable(File csv) throws IOException {
        csvTable = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(csv));
        String line;
        while((line = reader.readLine())!=null){
            String[] add = line.split(",");
            csvTable.add(add);
        }
    }

    public List getCsvTable() {
        return csvTable;
    }

    public Iterator iterator() {
        return csvTable.iterator();
    }
}
