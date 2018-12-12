package GIS;

import Geom.Point3D;

public interface Meta_data {
	/** returns the Universal Time Clock associated with this data; */
	long getUTC();
	/** return a String representing this data */
	String toString();
	/**
	 * @return the orientation: yaw, pitch and roll associated with this data;
	 */
	Point3D get_Orientation();

	String getName();

	String toStringKML();

	String getColor();

	void setColor(String color);
	void setUTCtime(long UTCtime);
}
