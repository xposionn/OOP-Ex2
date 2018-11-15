package Coords;

import Geom.Point3D;


public class MyCoords implements coords_converter {
    private static long RADIUS = 6371000;
    private final double e = 8.1819190842622e-2;  // eccentricity
    private final double asq = Math.pow(RADIUS,2);
    private final double esq = Math.pow(e,2);



    @Override
    public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
        Point3D gpsConvertToECEF = convertGpsToECEF(gps);
        double x = gpsConvertToECEF.x() + local_vector_in_meter.x();
        double y = gpsConvertToECEF.y() + local_vector_in_meter.y();
        double z = gpsConvertToECEF.z() + local_vector_in_meter.z();
        Point3D toGps = new Point3D(x,y,z);
        Point3D ecefConvertToGPS = convertECEFToGps(toGps);
        return ecefConvertToGPS;
    }



    @Override
    public double distance3d(Point3D gps0, Point3D gps1) {
        double latDiff = gps0.x() - gps1.x();
        double lonDiff = gps0.y() - gps1.y();
        double altDiff = gps0.z() - gps1.z();
        double lonNorm = Math.cos(gps0.x() * Math.PI / 180);
        double latDiffRadian = latDiff * Math.PI / 180;
        double lonDiffRadian = lonDiff * Math.PI / 180;
        double toMeter1 = Math.sin(latDiffRadian) * RADIUS;
        double toMeter2 = Math.sin(lonDiffRadian) * RADIUS * lonNorm;
        return Math.sqrt(Math.pow(toMeter1, 2) + Math.pow(toMeter2, 2));

    }

    @Override
    public Point3D vector3D(Point3D gps0, Point3D gps1) {
        Point3D gps0inECEF = convertGpsToECEF(gps0);
        Point3D gps1inECEF = convertGpsToECEF(gps1);
        double x = gps0inECEF.x() - gps1inECEF.x();
        double y = gps0inECEF.y() - gps1inECEF.y();
        double z = gps0inECEF.z() - gps1inECEF.z();
        return new Point3D(x,y,z);
    }

    @Override
    public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {

        Point3D vectorBetween = vector3D(gps0,gps1);
        Point3D gps0inECEF = convertGpsToECEF(gps0);
        double x = gps0inECEF.x();
        double y = gps0inECEF.y();
        double z = gps0inECEF.z();
        double dx = vectorBetween.x();
        double dy = vectorBetween.y();
        double dz = vectorBetween.z();
        //delta between lat long..
        double dlat = gps1.x()-gps0.x();
        double dlon = gps1.y()-gps0.y();


        double elevation = Math.acos((x*dx + y*dy + z*dz) / Math.sqrt((x*x+y*y+z*z)*(dx*dx+dy*dy+dz*dz)));
        double azimuth = Math.atan2(dz,dx);
        double azimuth2 = Math.atan2 ((Math.sin(dlon)*Math.cos(gps1.y())), (Math.cos(gps0.x())*Math.sin(gps1.x()) - Math.sin(gps0.x())*Math.cos(gps1.x())*Math.cos(dlon)));
        double distance = distance3d(gps0,gps1);
        double[] send = {elevation,azimuth2,distance};

        System.out.println(elevation);
        System.out.println(Math.toDegrees(azimuth2));
        System.out.println(distance);

        return send;
    }

    @Override
    public boolean isValid_GPS_Point(Point3D p) {
        return false;
    }
    //Privates


    private Point3D convertGpsToECEF(Point3D p) {

        double a = RADIUS/1000;
        double b = 6356.8;
        double N;
        double lat = p.x();
        double longi = p.y();
        double alt = p.z();
        double e = 1 - (Math.pow(b, 2) / Math.pow(a, 2));
        N = a / (Math.sqrt(1.0 - (e * Math.pow(Math.sin(Math.toRadians(lat)), 2))));
        double cosLatRad = Math.cos(Math.toRadians(lat));
        double cosLongiRad = Math.cos(Math.toRadians(longi));
        double sinLatRad = Math.sin(Math.toRadians(lat));
        double sinLongiRad = Math.sin(Math.toRadians(longi));
        double x = (N + 0.001 * alt) * cosLatRad * cosLongiRad;
        double y = (N + 0.001 * alt) * cosLatRad * sinLongiRad;
        double z = ((Math.pow(b, 2) / Math.pow(a, 2)) * N + 0.001 * alt) * sinLatRad;
//      System.out.println(x +"," + y+","+z);  update  : remove me
        return new Point3D(x, y, z);

    }



    private Point3D convertECEFToGps(Point3D ecef){
        double x = ecef.x();
        double y = ecef.y();
        double z = ecef.z();
        double b = Math.sqrt( asq * (1-esq) );
        double bsq = Math.pow(b,2);
        double ep = Math.sqrt( (asq - bsq)/bsq);
        double p = Math.sqrt( Math.pow(x,2) + Math.pow(y,2) );
        double th = Math.atan2(RADIUS *z, b*p);
        double lon = Math.atan2(y,x);
        double lat = Math.atan2( (z + Math.pow(ep,2)*b*Math.pow(Math.sin(th),3) ), (p - esq* RADIUS *Math.pow(Math.cos(th),3)) );
        double N = RADIUS/( Math.sqrt(1-esq*Math.pow(Math.sin(lat),2)) );
        double alt = p / Math.cos(lat) - N;
        // mod lat to 0-2pi
        lon = lon % (2*Math.PI);
        // correction for altitude near poles left out.
        return new Point3D(lat,lon,alt);
    }



}
