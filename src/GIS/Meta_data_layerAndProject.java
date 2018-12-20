package GIS;

import Geom.Point3D;

public class Meta_data_layerAndProject implements Meta_data {
    private String name;

    public Meta_data_layerAndProject(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns the Universal Time Clock associated with this data;
     */
    @Override
    public long getUTC() {
        return 0;
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

    @Override
    public String toStringKML() {
        return null;
    }

    @Override
    public String getColor() {
        return null;
    }

    @Override
    public void setColor(String color) {

    }

    @Override
    public void setUTCtime(long UTCtime) {

    }

    @Override
    public String getType() {
        return null;
    }
}
