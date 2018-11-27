package Algorithems;

import java.time.Instant;
import java.time.ZoneOffset;

public class TimeChange {

//    public static long UTCtolong(String dateinstring){
//
//    }

    public static String longtoUTC(long time){

        String out = Instant.ofEpochMilli(time).atOffset(ZoneOffset.UTC).toString();
        out = out.replaceAll("T", " ");
        out = out.substring(0,out.indexOf("Z"));
        return out;
    }

}
