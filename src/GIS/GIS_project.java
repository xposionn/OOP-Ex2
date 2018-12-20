package GIS;

import java.util.Set;

public interface GIS_project extends Set<GIS_layer> {
	Meta_data get_Meta_data();
	void toKml(String fileNameForNewKML);
	
}
