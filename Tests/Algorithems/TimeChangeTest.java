package Algorithems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeChangeTest {

    String dateinstr = "2015-03-30 13:47:58.405";
    long dateinlong = 1427723278405L;


    @Test
    void UTCtolong() {

    }

    @Test
    void longtoUTC() {
        String utc = TimeChange.longtoUTC(dateinlong);
        if(utc.equals(dateinstr)){

        }else{
            fail(utc + "should be equals to: " +dateinstr);
        }
    }
}