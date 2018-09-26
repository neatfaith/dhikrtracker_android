package com.neatfaith.dhikrtracker;

import android.app.Application;
import android.content.Context;

import com.neatfaith.dhikrtracker.core.manager.DBManager;
import com.neatfaith.dhikrtracker.core.model.ItemType;
import com.neatfaith.dhikrtracker.core.model.ItemTypeSubItem;
import com.neatfaith.dhikrtracker.core.model.ResponseStatus;
import com.neatfaith.dhikrtracker.core.model.User;

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
                "{'id': 1, 'title': 'Subhanallah', 'titleArabic' : 'سُبْحَنَ', 'typeId' : 1}," +
                "{'id': 2, 'title': 'Alhamdulillah', 'titleArabic' : 'اَلحَمْدُ', 'typeId' : 1}" +

                "]";


        try{
            JSONArray sub_arr = new JSONArray(type_subitems);
            DBManager.getInstance().insertItemTypeSubItems(sub_arr);

        }
        catch (JSONException e){
            e.printStackTrace();
        }






//        ResponseStatus status = DBManager.getInstance().insertUser("User1");
//        System.out.println("DB STATUS: "+status.toString());
//
//        ArrayList<User> users = new ArrayList<>();
//        DBManager.getInstance().getAllUsers(users);
//
//        System.out.println("Users:"+users.toString());
//
//        ArrayList<ItemType> itemTypes = new ArrayList<>();
//        DBManager.getInstance().getAllItemTypes(itemTypes);
//
//        System.out.println("ItemTypes:"+itemTypes.toString());
//
//
//
//        ArrayList<ItemTypeSubItem> subItems = new ArrayList<>();
//        DBManager.getInstance().getSubItemsForType(subItems,"1");
//
//        System.out.println("SUBITEMS:"+subItems.toString());


    }
}
