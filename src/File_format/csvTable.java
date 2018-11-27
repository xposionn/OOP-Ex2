package File_format;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class csvTable {

    ArrayList<String[]> csvTable;

    public String[] getHeader() {
        return csvTable.get(0);
    }

    @Override
    public String toString() {
        return "csvTable{" +
                "csvTable=" + csvTable +
                '}';
    }

    public csvTable(File csv) throws IOException {
        csvTable = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(csv));
        String line;
        reader.readLine();
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
