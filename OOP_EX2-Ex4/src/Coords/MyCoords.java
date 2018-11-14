package Coords;

import Geom.Point3D;

import java.util.ArrayList;
import java.util.List;


public class MyCoords implements coords_converter {
    private static long RADIUS = 6371000;

    @Override
    public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
//        double latRadian = Math.toRadians(gps.x());
//        double lonRadian = Math.toRadians(gps.y());
//        double x = RADIUS * Math.cos(latRadian) * Math.cos(lonRadian);
//        double y = RADIUS * Math.cos(latRadian) * Math.sin(lonRadian);
//        double z = RADIUS *Math.sin(latRadian);
//        System.out.println(x + ", "+ y + ", "+z);
//
//
//        List xyz = convertGpsToECEF(gps.x(),gps.y(),gps.z());
//        System.out.println(xyz.get(0)+ ", "+xyz.get(1)+", "+xyz.get(2));
//        return null;
    }


    @Override
    public double distance3d(Point3D gps0, Point3D gps1) {
        double latDiff = gps0.x()-gps1.x();
        double lonDiff = gps0.y()-gps1.y();
        double altDiff = gps0.z()-gps1.z();
        double lonNorm = Math.cos(gps0.x()*Math.PI/180);
        double latDiffRadian = latDiff*Math.PI/180;
        double lonDiffRadian = lonDiff*Math.PI/180;
        double toMeter1 = Math.sin(latDiffRadian)*RADIUS;
        double toMeter2 = Math.sin(lonDiffRadian)*RADIUS*lonNorm;
        return Math.sqrt(Math.pow(toMeter1,2)+Math.pow(toMeter2,2));

    }

    @Override
    public Point3D vector3D(Point3D gps0, Point3D gps1) {


        return null;
    }

    @Override
    public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {


        return new double[0];
    }

    @Override
    public boolean isValid_GPS_Point(Point3D p) {
        return false;
    }

    //Priverts

    private List<Double> convertGpsToECEF(double lat, double longi, double alt) {

        double a=6378.1;
        double b=6356.8;
        double N;
        double e= 1-(Math.pow(b, 2)/Math.pow(a, 2));
        N= a/(Math.sqrt(1.0-(e*Math.pow(Math.sin(Math.toRadians(lat)), 2))));
        double cosLatRad=Math.cos(Math.toRadians(lat));
        double cosLongiRad=Math.cos(Math.toRadians(longi));
        double sinLatRad=Math.sin(Math.toRadians(lat));
        double sinLongiRad=Math.sin(Math.toRadians(longi));
        double x =(N+0.001*alt)*cosLatRad*cosLongiRad;
        double y =(N+0.001*alt)*cosLatRad*sinLongiRad;
        double z =((Math.pow(b, 2)/Math.pow(a, 2))*N+0.001*alt)*sinLatRad;
        List<Double> ecef= new ArrayList<>();
        ecef.add(x);
        ecef.add(y);
        ecef.add(z);

        return ecef;
    }


}
