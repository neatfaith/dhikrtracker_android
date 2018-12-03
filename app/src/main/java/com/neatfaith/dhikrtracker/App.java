package com.neatfaith.dhikrtracker;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.neatfaith.dhikrtracker.core.manager.DBManager;

import org.json.JSONArray;
import org.json.JSONException;

public class App extends Application{
    private static Application instance;

    private SharedPreferences sharedPreferences;
    public static final String Key_Default_Account_Created = "defaultAccountCreated";


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

        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        this.prepareDB();


    }



    private void prepareDB(){


        boolean defCreated = sharedPreferences.getBoolean(Key_Default_Account_Created,false);

        //----------------insert user me ---
        if (!defCreated){
            DBManager.getInstance().insertUser("me",""+1);

            //update account created
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Key_Default_Account_Created, true);
            editor.commit();
        }


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
                "{'title': 'SubhanAllah', 'titleArabic' : '', 'typeId' : 1, 'canModify' : 0, 'meaning' : 'Glory be to Allah' }," +
                "{'title': 'Alhamdulillah', 'titleArabic' : '', 'typeId' : 1, 'canModify' : 0, 'meaning' : 'All praise is due to Allah'}," +
                "{'title': 'Allahu Akbar', 'titleArabic' : '', 'typeId' : 1, 'canModify' : 0, 'meaning' : 'Allah is the greatest' }," +
                "{'title': 'La illaha ilAllah', 'titleArabic' : '', 'typeId' : 1, 'canModify' : 0, 'meaning' : 'None has the right to be worshipped but Allah'}," +
                "{'title': 'Astaghfirullah', 'titleArabic' : '', 'typeId' : 1, 'canModify' : 0, 'meaning' : ' I ask Allah for forgiveness'}," +
                "{'title': 'SubhanAllahi wa biHamdihi, Subhan-Allahi l-`adheem', 'titleArabic' : 'سُبْحَانَ اللَّهِ وَبِحَمْدِهِ سُبْحَانَ اللَّهِ الْعَظِيمِ', 'typeId' : 1, 'canModify' : 0, 'meaning' : 'Glory be to Allah and all praise to Him, glory be to Allah the Mighty'}," +
                "{'title': 'La Hawla wa la Quwatta illa Billah', 'titleArabic' : '', 'typeId' : 1, 'canModify' : 0, 'meaning' : 'There is no might nor power except with Allah'}," +
                "{'title': 'La illaha ilAllahu, waHdahu la shareeka lahu, lahul Mulku, wa lahul Hamd, wa Huwa ala kulli shaiy\\'in Qadeer', 'titleArabic' : 'لَا إِلَهَ إِلَّا اللهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ، وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ', 'typeId' : 1, 'canModify' : 0, 'meaning' : 'None has the right to be worshiped but Allah alone, Who has no partner. His is the dominion and His are the praise and He is Able to do all things'}," +
                "{'title': 'Other', 'titleArabic' : '', 'typeId' : 1, 'canModify' : 0}," +


                //################### FASTING
                "{'title': 'Monday/Thursday Fasting', 'titleArabic' : '', 'typeId' : 2, 'canModify' : 0}," +
                "{'title': 'Every Other Day (Fast of Dawood (pbuh) )', 'titleArabic' : '', 'typeId' : 2, 'canModify' : 0}," +
                "{'title': '6 Days In Shawwal', 'titleArabic' : '', 'typeId' : 2, 'canModify' : 0}," +
                "{'title': 'Day Of Ashura - 10th Of Muharram', 'titleArabic' : '', 'typeId' : 2, 'canModify' : 0}," +
                "{'title': 'Arafah - 9th Of Dhul Hijjah', 'titleArabic' : '', 'typeId' : 2, 'canModify' : 0}," +
                "{'title': '3 Days Every Month (13th,14th,15th)', 'titleArabic' : '', 'typeId' : 2, 'canModify' : 0}," +
                "{'title': 'Ramadan', 'titleArabic' : '', 'typeId' : 2, 'canModify' : 0}," +
                "{'title': 'Other', 'titleArabic' : '', 'typeId' : 2, 'canModify' : 0}," +





                //################# PRAYERS
                //fardh
                "{'title': 'Fajr', 'titleArabic' : '', 'typeId' : 3, 'canModify' : 0}," +
                "{'title': 'Dhuhr', 'titleArabic' : '', 'typeId' : 3, 'canModify' : 0}," +
                "{'title': 'Asr', 'titleArabic' : '', 'typeId' : 3, 'canModify' : 0}," +
                "{'title': 'Maghrib', 'titleArabic' : '', 'typeId' : 3, 'canModify' : 0}," +
                "{'title': 'Isha', 'titleArabic' : '', 'typeId' : 3, 'canModify' : 0}," +
                "{'title': 'Friday', 'titleArabic' : '', 'typeId' : 3, 'canModify' : 0}," +


                //wajib
                "{'title': 'Witr', 'titleArabic' : '', 'typeId' : 3, 'canModify' : 0}," +
                "{'title': 'Eid-ul-Fitr', 'titleArabic' : '', 'typeId' : 3, 'canModify' : 0}," +
                "{'title': 'Eid-ul-Adha', 'titleArabic' : '', 'typeId' : 3, 'canModify' : 0}," +


                "{'title': 'Tahajjud (Qiyam)', 'titleArabic' : '', 'typeId' : 3, 'canModify' : 0}," +
                "{'title': 'Dhuha', 'titleArabic' : '', 'typeId' : 3, 'canModify' : 0}," +
                "{'title': 'Other', 'titleArabic' : '', 'typeId' : 3, 'canModify' : 0}," +


                //################ READING
                //reading
                "{'title': 'Quran Arabic', 'titleArabic' : '', 'typeId' : 4, 'canModify' : 0}," +
                "{'title': 'Quran Translation', 'titleArabic' : '', 'typeId' : 4, 'canModify' : 0}," +

                //Hadith Collections
                "{'title': 'Sahih Bukhari', 'titleArabic' : '', 'typeId' : 4, 'canModify' : 0}," +
                "{'title': 'Sahih Muslim', 'titleArabic' : '', 'typeId' : 4, 'canModify' : 0}," +
                "{'title': 'Sunan Abu Dawood', 'titleArabic' : '', 'typeId' : 4, 'canModify' : 0}," +
                "{'title': 'Sunan at-Tirmidhi', 'titleArabic' : '', 'typeId' : 4, 'canModify' : 0}," +
                "{'title': 'Sunan an-Nasai', 'titleArabic' : '', 'typeId' : 4, 'canModify' : 0}," +
                "{'title': 'Sunan Ibn Majah', 'titleArabic' : '', 'typeId' : 4, 'canModify' : 0}," +
                "{'title': 'Notes', 'titleArabic' : '', 'typeId' : 4, 'canModify' : 0}," +
                "{'title': 'Other', 'titleArabic' : '', 'typeId' : 4, 'canModify' : 0}," +



                //################ WRITING
                "{'title': 'Sermon (Khutbah)', 'titleArabic' : '', 'typeId' : 5, 'canModify' : 0}," +
                "{'title': 'Notes', 'titleArabic' : '', 'typeId' : 5, 'canModify' : 0}," +
                "{'title': 'Other', 'titleArabic' : '', 'typeId' : 5, 'canModify' : 0}," +


                //################ LISTENING
                //listening
                "{'title': 'Quran Arabic', 'titleArabic' : '', 'typeId' : 6, 'canModify' : 0}," +
                "{'title': 'Sermon (Khutbah)', 'titleArabic' : '', 'typeId' : 6, 'canModify' : 0}," +
                "{'title': 'Other', 'titleArabic' : '', 'typeId' : 6, 'canModify' : 0}," +



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
