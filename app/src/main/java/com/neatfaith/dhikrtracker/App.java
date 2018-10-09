package com.neatfaith.dhikrtracker;

import android.app.Application;
import android.content.Context;

import com.neatfaith.dhikrtracker.core.manager.DBManager;
import com.neatfaith.dhikrtracker.core.model.Item;
import com.neatfaith.dhikrtracker.core.model.ItemType;
import com.neatfaith.dhikrtracker.core.model.ItemTypeSubItem;
import com.neatfaith.dhikrtracker.core.model.ResponseStatus;
import com.neatfaith.dhikrtracker.core.model.User;
import com.neatfaith.dhikrtracker.core.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class App extends Application{
    private static Application instance;

    public static Application getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance.getApplicationContext();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        this.prepareDB();


    }



    private void prepareDB(){


        //-----------------insert item types
        String types = "[" +
                "{'id': 1, 'title': 'Adhkar'}," +
                "{'id': 2, 'title': 'Fasting'}," +
                "{'id': 3, 'title': 'Prayers'}," +
                "{'id': 4, 'title': 'Reading'}," +
                "{'id': 5, 'title': 'Writing'}," +
                "{'id': 6, 'title': 'Listening'}" +
                "]";


        try{
            JSONArray types_arr = new JSONArray(types);
            DBManager.getInstance().insertItemTypes(types_arr);

        }
        catch (JSONException e){
            e.printStackTrace();
        }


        //-----------------insert item type subitems
        String type_subitems = "[" +

                //################## ADHKAR
                "{'title': 'Subhanallah', 'titleArabic' : 'سُبْحَنَ', 'typeId' : 1}," +
                "{'title': 'Alhamdulillah', 'titleArabic' : 'اَلحَمْدُ', 'typeId' : 1}," +
                "{'title': 'Subhaanal-laahi wa bihamdih Subhaanal-laahil-Adheem', 'titleArabic' : 'سُبْحَانَ اللَّهِ وَبِحَمْدِهِ سُبْحَانَ اللَّهِ الْعَظِيمِ', 'typeId' : 1}," +

                "{'title': 'Other', 'titleArabic' : '', 'typeId' : 1}," +


                //################### FASTING
                "{'title': 'Monday/Thursday Fasting', 'titleArabic' : '', 'typeId' : 2}," +
                "{'title': 'Every Other Day (Dawood, a.s)', 'titleArabic' : '', 'typeId' : 2}," +
                "{'title': '6 Days In Shawwal', 'titleArabic' : '', 'typeId' : 2}," +
                "{'title': 'Day Of Ashura - 10th Of Muharram', 'titleArabic' : '', 'typeId' : 2}," +
                "{'title': 'Arafah - 9th Of Dhul Hijjah', 'titleArabic' : '', 'typeId' : 2}," +
                "{'title': '3 Days Every Month (13th,14th,15th)', 'titleArabic' : '', 'typeId' : 2}," +
                "{'title': 'Ramadan', 'titleArabic' : '', 'typeId' : 2}," +
                "{'title': 'Other', 'titleArabic' : '', 'typeId' : 2}," +





                //################# PRAYERS
                //fardh
                "{'title': 'Fajr', 'titleArabic' : '', 'typeId' : 3}," +
                "{'title': 'Dhuhr', 'titleArabic' : '', 'typeId' : 3}," +
                "{'title': 'Asr', 'titleArabic' : '', 'typeId' : 3}," +
                "{'title': 'Maghrib', 'titleArabic' : '', 'typeId' : 3}," +
                "{'title': 'Isha', 'titleArabic' : '', 'typeId' : 3}," +
                "{'title': 'Friday', 'titleArabic' : '', 'typeId' : 3}," +


                //wajib
                "{'title': 'Witr', 'titleArabic' : '', 'typeId' : 3}," +
                "{'title': 'Eid-ul-Fitr', 'titleArabic' : '', 'typeId' : 3}," +
                "{'title': 'Eid-ul-Adha', 'titleArabic' : '', 'typeId' : 3}," +


                "{'title': 'Tahajjud (Qiyam)', 'titleArabic' : '', 'typeId' : 3}," +
                "{'title': 'Dhuha', 'titleArabic' : '', 'typeId' : 3}," +
                "{'title': 'Other', 'titleArabic' : '', 'typeId' : 3}," +


                //################ READING
                //reading
                "{'title': 'Quran Arabic', 'titleArabic' : '', 'typeId' : 4}," +
                "{'title': 'Quran Translation', 'titleArabic' : '', 'typeId' : 4}," +

                //Hadith Collections
                "{'title': 'Sahih Bukhari', 'titleArabic' : '', 'typeId' : 4}," +
                "{'title': 'Sahih Muslim', 'titleArabic' : '', 'typeId' : 4}," +
                "{'title': 'Sunan Abu Dawood', 'titleArabic' : '', 'typeId' : 4}," +
                "{'title': 'Sunan at-Tirmidhi', 'titleArabic' : '', 'typeId' : 4}," +
                "{'title': 'Sunan an-Nasai', 'titleArabic' : '', 'typeId' : 4}," +
                "{'title': 'Sunan Ibn Majah', 'titleArabic' : '', 'typeId' : 4}," +
                "{'title': 'Other', 'titleArabic' : '', 'typeId' : 4}," +


                //################ LISTENING
                //listening
                "{'title': 'Quran Arabic', 'titleArabic' : '', 'typeId' : 6}," +
                "{'title': 'Other', 'titleArabic' : '', 'typeId' : 6}," +



                "]";


        try{
            JSONArray sub_arr = new JSONArray(type_subitems);
            DBManager.getInstance().insertItemTypeSubItems(sub_arr);

        }
        catch (JSONException e){
            e.printStackTrace();
        }


    }
}
