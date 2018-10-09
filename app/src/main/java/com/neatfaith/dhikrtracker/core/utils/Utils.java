package com.neatfaith.dhikrtracker.core.utils;

import com.neatfaith.dhikrtracker.core.model.ItemType;
import com.neatfaith.dhikrtracker.core.model.ItemTypeSubItem;
import com.neatfaith.dhikrtracker.core.model.User;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

public class Utils {

    public static long getUnixTimestamp(){

        return System.currentTimeMillis() / 1000L;

    }

    public static String timestampToDateString(long unixSeconds){

        Date date = new Date(unixSeconds*1000L); // convert seconds to milliseconds

        return DateFormat.getDateInstance().format(date);
    }

    public static String timestampToTimeString(long unixSeconds){

        Date date = new Date(unixSeconds*1000L); // convert seconds to milliseconds

        return DateFormat.getDateTimeInstance().format(date);

    }

    public static String formatNumber(long number){
        return NumberFormat.getNumberInstance().format(number);
    }


    public static User userForId(long id, ArrayList<User> users){

        for (User user: users) {

            if (user.getId() == id){
                return user;
            }
        }

        return null;
    }


    public static ItemType itemTypeForId(long id, ArrayList<ItemType> itemTypes){

        for (ItemType type: itemTypes) {

            if (type.getId() == id){
                return type;
            }

        }

        return null;
    }

    public static ItemTypeSubItem subItemForId(long id, ArrayList<ItemTypeSubItem> subItems){

        for (ItemTypeSubItem subitem: subItems) {

            if (subitem.getId() == id){
                return subitem;
            }
        }

        return null;
    }

}
