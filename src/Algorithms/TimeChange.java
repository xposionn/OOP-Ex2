package Algorithms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * This class represents algorithm to transform UTC time from String to long and vice-versa.
 */
public class TimeChange {

    /**
     * This method will get UTC time in a String, transform it into 'long' value (millis) and return the answer.
     * @param dateInString the time as String we want to transform.
     * @return long, the UTC time as long variable.
     */
    public static long stringUTCtoLong(String dateInString){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = null;
        try {
            dateInString = dateInString.replaceAll(" ", "T");
            dateInString += 'Z';
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * This method will get UTC time in a long variable, transform it into a string and return this string.
     * @param time the UTC time in long milliseconds.
     * @return String, the UTC time represented as String.
     */
    public static String longtoUTC(long time){

        return Instant.ofEpochMilli(time+10800000).atOffset(ZoneOffset.UTC).toString(); //10 800 000 means 3 hours (cuz utc)
    }

}
