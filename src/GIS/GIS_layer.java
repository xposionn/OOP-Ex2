package GIS;
import java.util.Set;

public interface GIS_layer extends Set<GIS_element> {
	Meta_data get_Meta_data();
	void setMeta(Meta_data meta);
	String toKml();
	String toKmlForProject();
}
