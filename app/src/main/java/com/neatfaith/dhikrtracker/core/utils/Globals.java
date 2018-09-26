package com.neatfaith.dhikrtracker.core.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Globals {

    public static final DateFormat dateFormatter = new SimpleDateFormat("d MMM yyyy");
    public static final DateFormat timeFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


    public static DateFormat getDateFormatter() {
        return dateFormatter;
    }

    public static DateFormat getTimeFormatter() {
        return timeFormatter;
    }
}
