package GIS;

import Geom.Point3D;

import java.awt.*;

public class Meta_data_obj implements Meta_data {
    Color color;
    long UTCtime;

    public Meta_data_obj(Color rgbColor, long UTCtimeLONG){
        this.color = rgbColor;
        this.UTCtime = UTCtimeLONG;
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
}
