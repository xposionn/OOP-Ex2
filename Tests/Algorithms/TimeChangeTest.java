package Algorithms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeChangeTest {

    String dateInStr = "2015-03-30 13:47:58.405";
    long dateinlong = 1427723278405L;


    @Test
    void UTCtolong() {

    }

    @Test
    void longtoUTC() {
        String utc = TimeChange.longtoUTC(dateinlong);
        if(utc.equals(dateInStr)){

        }else{
            fail(utc + "should be equals to: " +dateInStr);
        }
    }
}