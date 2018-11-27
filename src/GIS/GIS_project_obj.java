package GIS;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GIS_project_obj implements GIS_project {

    Set<GIS_layer> set;
    Meta_data projectMeta;


    public GIS_project_obj(String projectName) {
        set = new HashSet<>();
        projectMeta = new Meta_data_obj(projectName,null,0);
    }

    public void toKml(String fileNameForNewKML) {
        ArrayList<String> kmlContent = new ArrayList<>();
        String kmlStart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n" +
                "<Document>\n" +
                "<Style id=\"red\">\n" +
                "<IconStyle>\n" +
                "<Icon><href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href></Icon>\n" +
                "</IconStyle>\n" +
                "</Style>\n" +
                "<Style id=\"yellow\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/yellow-dot.png</href></Icon></IconStyle></Style><Style id=\"green\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/green-dot.png</href></Icon></IconStyle></Style>\n" +
                "<Folder>\n" +
                "<name>" + projectMeta.getName() + "</name>\n";
        kmlContent.add(kmlStart);
        String kmlEnd = "</Folder>\n" +
                "</Document></kml>";
        try {
            FileWriter fw = new FileWriter(fileNameForNewKML);
            BufferedWriter bw = new BufferedWriter(fw);

            Iterator<GIS_layer> iterator = set.iterator();
            while (iterator.hasNext()) {
                GIS_layer layer = iterator.next();
                String kmllayer = layer.toKmlforproject();
                kmlContent.add(kmllayer);
            }
            kmlContent.add(kmlEnd);
            String writeto = kmlContent.toString().replaceAll(", <P", "<P");
            writeto = writeto.substring(1);
            writeto = writeto.substring(0,writeto.length()-1);
            bw.write(writeto);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public Meta_data get_Meta_data() {
        return this.projectMeta;
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return set.contains(o);
    }

    @Override
    public Iterator<GIS_layer> iterator() {
        return set.iterator();
    }

    @Override
    public Object[] toArray() {
        return set.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return set.toArray(a);
    }

    @Override
    public boolean add(GIS_layer gis_elements) {
        return set.add(gis_elements);
    }

    @Override
    public boolean remove(Object o) {
        return set.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return set.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends GIS_layer> c) {
        return set.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return set.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return set.removeAll(c);
    }

    @Override
    public void clear() {
        set.clear();

    }
}
