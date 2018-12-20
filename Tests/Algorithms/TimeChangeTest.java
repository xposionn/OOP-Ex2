package Algorithms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeChangeTest {
    //TODO: FIX IT
    String dateInStr1 = "2015-03-30T10:47:58Z";
    long dateinlong1 = 1427712478000L;

    String dateInStr2 = "2016-04-04T01:52:49Z";
    long dateinlong2 = 1459734769000L ;


    String dateInStr3 = "2015-03-30T10:56:18Z";
    long dateinlong3 = 1427712978000L;

    String dateInStr4 = "1970-01-01T00:00Z";
    long dateinlong4 = 0L;


    @Test
    void stringUTCtoLong1() {
        assertEquals(dateinlong1, Algorithms.TimeChange.stringUTCtoLong(dateInStr1));
    }
    @Test
    void stringUTCtoLong2() {
        assertEquals(dateinlong2, Algorithms.TimeChange.stringUTCtoLong(dateInStr2));
    }
    @Test
    void stringUTCtoLong3() {
        assertEquals(dateinlong3, Algorithms.TimeChange.stringUTCtoLong(dateInStr3));
    }

    @Test
    void longToUTC1() {
        String utc = TimeChange.longtoUTC(dateinlong1);
        if(utc.equals(dateInStr1)){

        }else{
            fail(utc + "should be equals to: " + dateInStr1);
        }
    }

    @Test
    void longToUTC2() {
        String utc = TimeChange.longtoUTC(dateinlong2);
        if(utc.equals(dateInStr2)){

        }else{
            fail(utc + "should be equals to: " + dateInStr2);
        }
    }

    @Test
    void longToUTC3() {
        String utc = TimeChange.longtoUTC(dateinlong3);
        if(utc.equals(dateInStr3)){

        }else{
            fail(utc + "should be equals to: " + dateInStr3);
        }
    }

    @Test
    void longToUTC4() {
        String utc = TimeChange.longtoUTC(dateinlong4);
        if(utc.equals(dateInStr4)){

        }else{
            fail(utc + "should be equals to: " + dateInStr4);
        }
    }
}