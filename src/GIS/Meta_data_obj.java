package GIS;

import Geom.Point3D;

import java.awt.*;

public class Meta_data_obj implements Meta_data {
    String name; //name of the obj (placemark or layer)
    String color; //color of the placemark specified in HEX.
    long UTCtime; //timestamp in UTC (long) of the recording time of this placemark (or creation time of a layer).

    public Meta_data_obj(String name,String colorHEX, long UTCtimeLONG){
        this.name = name;
        this.color = colorHEX;
        this.UTCtime = UTCtimeLONG;
    }

    public String allInfo(){
        StringBuilder info = new StringBuilder();
        info.append("Color in HEX: " + this.color);
        info.append("Time (UTC): " + this.UTCtime);
    return info.toString();
    }

    /**
     * returns the Universal Time Clock associated with this data;
     */
    @Override
    public long getUTC() {
        return this.UTCtime;
    }

    /**
     * @return the orientation: yaw, pitch and roll associated with this data;
     */
    @Override
    public Point3D get_Orientation() {
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
