package GIS;

import Geom.Point3D;

public class Meta_data_element implements Meta_data{

    private String name; //name of the element
    private String color; //color of the placemark specified in HEX.
    private long UTCtime; //creation time of the element.
    private String type; //type will be used to indicate whether specific placemark is Food/Fruit (F) or anything else.

    /**
     * Constructor function, will be called with only a name and UTCtime as used in GIS layer and GIS project meta data.
     * @param name name of the object including this meta data.
     * @param type indication P = Packman, F = Fruit.
     */
    public Meta_data_element(String name, String type){ //constructor used with only name and type. assigns default color.
        this.name = name;
        if(type.equals("F")) //color for fruit.
            this.color = "#FF0000"; //default color - white.
        else //color for everything else. pacman for example.
            this.color = "#f0ff00";
        this.type = type;
        this.UTCtime = System.currentTimeMillis();
    }

    /**
     * Constructor function, will be called with only a name and UTCtime as used in GIS layer and GIS project meta data.
     * @param name name of the object including this meta data.
     * @param type indication P = Packman, F = Fruit.
     * @param color color of the object in HEX string.
     */
    public Meta_data_element(String name, String type, String color){
        this.name = name;
        this.color = color;
        this.type = type;
        this.UTCtime = System.currentTimeMillis();
    }

    /**
     * This method will check which of its fields is available (not null) to represent for the user in a KML format string.
     * it will return a String containing all the meta_data as in KML format ready.
     * @return String containing all the meta_data as in KML format ready.
     */
    public String toStringKML(){ //name is also handled separately, as a placemark name.
        StringBuilder info = new StringBuilder();
        info.append("Name: <b>").append(this.name).append("</b><br/>");
        info.append("Timestamp of Algorithm runtime(UTC): <b>").append(this.UTCtime).append("</b><br/>");
        info.append("Color in HEX: <b>").append(this.color).append("</b><br/>");
        if (this.type.equals("P")) {
            info.append("Type: Player<br/>");
        }
        else if (this.type.equals("F"))
            info.append("Type: Fruit<br/>");
        return info.toString();
    }


    /******* Setters and Getters ********/

    /**
     * returns the Universal Time Clock associated with this data;
     */
    @Override
    public long getUTC() {
        return this.UTCtime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() { //string in HEX
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getUTCtime() {
        return UTCtime;
    }

    public void setUTCtime(long UTCtime) {
        this.UTCtime = UTCtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
