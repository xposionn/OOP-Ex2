package Algorithms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeChangeTest {

    String dateInStr = "2015-03-30T13:47:58Z";
    long dateinlong = 1427712478000L;


    @Test
    void stringUTCtoLong() {
        assertEquals(dateinlong, Algorithms.TimeChange.stringUTCtoLong(dateInStr));
    }

    @Test
    void longToUTC() {
        String utc = TimeChange.longtoUTC(dateinlong);
        if(utc.equals(dateInStr)){

        }else{
            fail(utc + "should be equals to: " +dateInStr);
        }
    }
}