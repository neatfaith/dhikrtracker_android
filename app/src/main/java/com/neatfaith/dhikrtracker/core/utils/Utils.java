package com.neatfaith.dhikrtracker.core.utils;

import java.util.Date;

public class Utils {

    public static long getUnixTimestamp(){

        return System.currentTimeMillis() / 1000L;

    }

    public static String timestampToDateString(long unixSeconds){

        Date date = new Date(unixSeconds*1000L); // convert seconds to milliseconds

        return Globals.getDateFormatter().format(date);

    }

    public static String timestampToTimeString(long unixSeconds){

        Date date = new Date(unixSeconds*1000L); // convert seconds to milliseconds

        return Globals.getTimeFormatter().format(date);

    }

}
