package Algorithms;

import java.time.Instant;
import java.time.ZoneOffset;

public class TimeChange {

//    public static long UTCtolong(String dateinstring){
//
//    }

    public static String longtoUTC(long time){

        String out = Instant.ofEpochMilli(time).atOffset(ZoneOffset.UTC).toString(); //from long to String output: dateThourZ
        return out;
    }

}
