package GIS;

import Geom.Point3D;

import java.util.ArrayList;


public class Meta_data_obj implements Meta_data {
    String name; //name of the obj (placemark or layer)
    String color; //color of the placemark specified in HEX.
    long UTCtime; //timestamp in UTC (long) of the recording time of this placemark (or creation time of a layer).
    String type; //type will be used to indicate whether specific placemark is a Player (P) or Food/Fruit (F)
    Double speed; //speed is the speed in meters/sec of a player.
    String[] wifiPointEx2DATA; //all data used in Ex2 to be saved for same-KML as requested.

    Double radius; //radius is the 'eating radius' of a player, the smallest distance a player can eat a fruit.

    public Meta_data_obj(String name, long UTCtimeLONG){ //constructor used with only name and time.
        this.name = name;
        this.UTCtime = UTCtimeLONG;
    }

    //a constructor used relevant for Ex2 with wifi points
    public Meta_data_obj(String name, long UTCtimeLONG,String[] wifiPointDATA){ //constructor used relevant for Ex2, we only need name and time.
        this.name = name;
        this.UTCtime = UTCtimeLONG;
        this.wifiPointEx2DATA = wifiPointDATA; //[0] = BSSID,  [1] = Capabilities , [2] = Frequency
    }

    //a constructor used relevant for Ex3
    public Meta_data_obj(String name, long UTCtimeLONG, String type, double speed, double radius){
        this.name = name;
        this.UTCtime = UTCtimeLONG;
        this.type = type;
        this.speed = speed;
        this.radius = radius;
    }

    public String allInfo(){ //name is also handled separately, as a placemark name.
        StringBuilder info = new StringBuilder();
        info.append("Name: <b>" + this.name +"</b><br/>");
        info.append("Timestamp (UTC): <b>" + this.UTCtime +"</b><br/>");
        info.append("Date: <b>" + Algorithms.TimeChange.longtoUTC(this.UTCtime).replaceAll("[T,Z]"," ")+"</b><br/>");
        if(wifiPointEx2DATA!=null){
            info.append("BSSID: <b>" + wifiPointEx2DATA[0] + "</b><br/>");
            info.append("Capabilities: <b>" + wifiPointEx2DATA[1] + "</b><br/>");
            info.append("Frequency: <b>" + wifiPointEx2DATA[2] + "</b><br/>");
        }
        if(this.color!=null)
            info.append("Color in HEX: <b>" + this.color +"</b><br/>");
        if(this.type != null)
            if (this.type.equals("P"))
                info.append("Type: Player<br/>");
            else if (this.type.equals("F"))
                info.append("Type: Fruit<br/>");
        if (this.speed != null)
            info.append("Speed: " + this.speed +"<br/>");
        if(this.radius != null)
            info.append("Eating Radius: " + radius +"<br/>");

    return info.toString();
    }

    @Override
    public String getStyleUrlColor() {
        if(this.wifiPointEx2DATA!=null && this.wifiPointEx2DATA[1]!=null){
            //implement algorithm to decide if point is green/yellow/red.
            //this.wifiPointEx2DATA[1] is the capabilities (security) for the wifi point. determine by this factor.

        }
        else{ //default color to return.
        }
        return null;
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

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
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
