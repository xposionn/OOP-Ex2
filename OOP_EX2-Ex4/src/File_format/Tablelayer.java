package File_format;

import GIS.GIS_element_obj;
import GIS.GIS_layer;
import GIS.GIS_layer_obj;

import java.util.Iterator;

public class Tablelayer {


    public static GIS_layer TableLayer(csvTable csvTable){

        GIS_layer layer = new GIS_layer_obj();
        Iterator<String []> iterator = csvTable.iterator();
        String[] colums = iterator.next();
        while(iterator.hasNext()){
            String[] element = iterator.next();
            GIS_element_obj element_obj = new GIS_element_obj(element);
            layer.add(element_obj);
        }
        return layer;

    }


}
