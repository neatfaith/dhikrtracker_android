package com.neatfaith.dhikrtracker.core.utils;

import android.text.TextUtils;

public class ValidationUtils {

    public static boolean isValidText(String text){
        if (TextUtils.isEmpty(text)){
            return false;
        }


        return true;
    }

    public static boolean isValidLong(String text){

        //check for empty string
        if (TextUtils.isEmpty(text)){
            return false;
        }

        //check for long
        try {
            Long.parseLong(text);
        } catch (NumberFormatException e) {
            return false;
        }


        return true;
    }
}
