package GIS;

import Geom.Point3D;


/**
 * This class represents data to be saved for GIS elements, layers, projects.
 * Meta_data will include a name, color, UTCtime, type, speed, radius, wifi point data(when necessary, such in Ex2 requested in assignment to include everything shown in KML).
 */
public class Meta_data_obj implements Meta_data {
    String name; //name of the obj (placemark or layer)
    String color; //color of the placemark specified in HEX.
    long UTCtime; //timestamp in UTC (long) of the recording time of this placemark (or creation time of a layer).
    String type; //type will be used to indicate whether specific placemark is a Player (P) or Food/Fruit (F)
    Double speed; //speed is the speed in meters/sec of a player.
    String[] wifiPointEx2DATA; //all data used in Ex2 to be saved for same-KML as requested.
    Double radius; //radius is the 'eating radius' of a player, the smallest distance a player can eat a fruit.

    /**
     * Constructor function, will be called with only a name and UTCtime as used in GIS layer and GIS project meta data.
     * @param name name of the object including this meta data.
     * @param UTCtimeLONG time of creation of the parent object in UTC long value.
     */
    public Meta_data_obj(String name, long UTCtimeLONG){ //constructor used with only name and time.
        this.name = name;
        this.UTCtime = UTCtimeLONG;
    }

    //a constructor used relevant for Ex2 with wifi points

    /**
     * Constructor function, will be called with only a name, UTCtime and wifiPointDATA -- relevant for Ex2 with wifi points data.
     * @param name the name of the object including this meta data.
     * @param UTCtimeLONG time of creation of the parent object in UTC long value OR the timestamp.
     * @param wifiPointDATA String[] including BSSID, Capabilities, AccuracyMeters of the wifi point data.
     */
    public Meta_data_obj(String name, long UTCtimeLONG,String[] wifiPointDATA){ //constructor used relevant for Ex2, we send also wifi points data.
        this.name = name;
        this.UTCtime = UTCtimeLONG;
        this.wifiPointEx2DATA = wifiPointDATA; //[0] = BSSID,  [1] = Capabilities , [2] = AccuracyMeters
    }

    //a constructor used relevant for Ex3

    /**
     * Constructor function, will be called with a name, UTCtime, type, speed, radius.
     * this constructor is relevant for Ex3 with Pacman (player) or Fruit(food) including all its information.
     * @param name name of the player.
     * @param UTCtimeLONG timestamp in UTC long.
     * @param type indication P = Pacman, F = Fruit.
     * @param speed speed of the player.
     * @param radius eating radius of a player, the smallest distance a player can eat a fruit.
     */
    public Meta_data_obj(String name, long UTCtimeLONG, String type, double speed, double radius){
        this.name = name;
        this.UTCtime = UTCtimeLONG;
        this.type = type;
        this.speed = speed;
        this.radius = radius;
    }

    /**
     * This method will check which of its fields is available (not null) to represent for the user in a KML format string.
     * it will return a String containing all the meta_data as in KML format ready.
     * @return String containing all the meta_data as in KML format ready.
     */
    public String toStringKML(){ //name is also handled separately, as a placemark name.
        StringBuilder info = new StringBuilder();
        info.append("Name: <b>").append(this.name).append("</b><br/>");
        info.append("Timestamp (UTC): <b>").append(this.UTCtime).append("</b><br/>");
        info.append("Date: <b>").append(Algorithms.TimeChange.longtoUTC(this.UTCtime).replaceAll("[T,Z]", " ")).append("</b><br/>");
        if(wifiPointEx2DATA!=null){
            info.append("BSSID: <b>").append(wifiPointEx2DATA[0]).append("</b><br/>");
            info.append("Capabilities: <b>").append(wifiPointEx2DATA[1]).append("</b><br/>");
            info.append("AccuracyMeters: <b>").append(wifiPointEx2DATA[2]).append("</b><br/>");
        }
        if(this.color!=null)
            info.append("Color in HEX: <b>").append(this.color).append("</b><br/>");
        if(this.type != null)
            if (this.type.equals("P"))
                info.append("Type: Player<br/>");
            else if (this.type.equals("F"))
                info.append("Type: Fruit<br/>");
        if (this.speed != null)
            info.append("Speed: ").append(this.speed).append("<br/>");
        if(this.radius != null)
            info.append("Eating Radius: ").append(radius).append("<br/>");

    return info.toString();
    }

//    /**
//     * This method will return a String color in KML format used in Google Earth to indicate if wifi point on map
//     * should be red, yellow, or green, based on security.
//     * algorithm to decide if wifi point is green/yellow/red. based on security.
//     * this.wifiPointEx2DATA[1] is the capabilities (security) for the wifi point. determine by this factor.
//     * @return String, red yellow or green in KML format ready.
//     */
//    @Override
//    public String getStyleUrlColor() {
//        if(this.wifiPointEx2DATA!=null && this.wifiPointEx2DATA[1]!=null){
//            //this.wifiPointEx2DATA[1] is the capabilities (security) for the wifi point. determine by this factor.
//            String securityWifi = wifiPointEx2DATA[1];
//            if (securityWifi.contains("WPA")) { //includes WPA2. both red.
//                return "#red";
//            } else if (securityWifi.contains("WEP")) {
//                return "#yellow";
//            }
//            else{ //probably bad security. ESS,WPS,BLE,IBSS, UNKNOWN;il  etc.    wifi point is green.
//                return "#green";
//            }
//        }
//        else{ //default color to return.
//        }
//        return null;
//    }


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
