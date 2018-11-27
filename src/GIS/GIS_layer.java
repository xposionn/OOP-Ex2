package GIS;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface GIS_layer extends Set<GIS_element> {
	public Meta_data get_Meta_data();

	public void setMeta(Meta_data meta);
	public String toKml();
	public String toKmlforproject();
}
