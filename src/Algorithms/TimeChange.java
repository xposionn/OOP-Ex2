package Algorithms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Date;

public class TimeChange {

    public static long UTCtolong(String dateinstring){
        SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        dateinstring = dateinstring.replace(" ","T");
        dateinstring += "Z";
        try {
            Date d = f.parse(dateinstring);
            long milliseconds = d.getTime();
            return milliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String longtoUTC(long time){

        String out = Instant.ofEpochMilli(time).atOffset(ZoneOffset.UTC).toString(); //from long to String output: dateThourZ
        return out;
    }

}
