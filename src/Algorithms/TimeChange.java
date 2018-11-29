package Algorithms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Date;

public class TimeChange {

    public static long stringUTCtoLong(String dateinstring){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = null;
        try {
            dateinstring = dateinstring.replaceAll(" ", "T");
            dateinstring += 'Z';
            date = (Date)formatter.parse(dateinstring);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long mills = date.getTime();
        return mills;
    }

    public static String longtoUTC(long time){

        String out = Instant.ofEpochMilli(time).atOffset(ZoneOffset.UTC).toString(); //from long to String output: dateThourZ
        return out;
    }

}
