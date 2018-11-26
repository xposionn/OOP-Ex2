package GIS;

import Geom.Point3D;

import java.awt.*;

public class Meta_data_obj implements Meta_data {
    String name;
    Color color;
    long UTCtime;

    public Meta_data_obj(String name,String rgbColor, long UTCtimeLONG){
        this.name = name;
        this.color = new Color(255,255,255); //White
        this.UTCtime = UTCtimeLONG;
    }

    public String allInfo(){
        StringBuilder string = new StringBuilder();
        string.append(this.color.toString()+",");
        string.append(this.UTCtime+",");
    return string.toString();
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
