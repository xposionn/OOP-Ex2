package Algorithems;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class TimeChange {

//    public static long UTCtolong(String dateinstring){
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
//        format.setTimeZone(TimeZone.getTimeZone("UTC"));
//
//        Date date = format.parse(text);
//        long millis = date.getTime();
//
//        String inputRaw = "2012-06-14 05:01:25";
//        String input = inputRaw.replace( " ", "T" );  // Replace SPACE with a 'T'.
//
//    }

    public static String longtoUTC(long time){

        ZonedDateTime utc = Instant.ofEpochMilli(1427723278405L).atZone(ZoneOffset.UTC);
        String out = utc.toString();
        out = out.substring(0,out.length()-1);
        out = out.replaceAll("T"," ");
        return out;
    }

    public static void main(String[] args) {

        System.out.println(TimeChange.longtoUTC(1427723278405L));
//        System.out.println(TimeChange.UTCtolong("2015-03-30 13:47:58.405"));
    }

}
