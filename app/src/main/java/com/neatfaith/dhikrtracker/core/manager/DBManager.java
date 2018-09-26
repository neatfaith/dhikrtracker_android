package com.neatfaith.dhikrtracker.core.manager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.neatfaith.dhikrtracker.App;
import com.neatfaith.dhikrtracker.core.model.ItemType;
import com.neatfaith.dhikrtracker.core.model.ItemTypeSubItem;
import com.neatfaith.dhikrtracker.core.model.ResponseStatus;
import com.neatfaith.dhikrtracker.core.model.User;
import com.neatfaith.dhikrtracker.core.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DBManager {

    private static DBManager instance = null;
    private SQLHelper sqlHelper;


    public static DBManager getInstance() {

        if(instance == null)
            instance = new DBManager();

        return instance;
    }

    public DBManager(){
        super();

        sqlHelper = new SQLHelper(App.getContext());

    }



    public synchronized void getAllUsers(ArrayList<User> users){

        String query = "select * from user order by created desc;";
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        while(c.moveToNext()){

            User m_user = new User();

            m_user.setId(c.getLong(c.getColumnIndex("id")));
            m_user.setName(c.getString(c.getColumnIndex("name")));
            m_user.setCreated(c.getLong(c.getColumnIndex("created")));


            users.add(m_user);
        }


        db.close();

    }


    public synchronized ResponseStatus insertUser(String name){
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.beginTransaction();


        ContentValues newValues = new ContentValues();
        newValues.put("name",name);
        newValues.put("created", Utils.getUnixTimestamp());

        ResponseStatus status = new ResponseStatus();
        long code = 0;
        //code = db.insert("user", null, newValues);
        //db.insertWithOnConflict("user", null, newValues, SQLiteDatabase.CONFLICT_REPLACE);



        try {
            code = db.insertOrThrow("user", null, newValues);
            status.setSuccess(true);

        }
        catch (SQLException ex){

            //System.out.println("MY EXCEPTION: "+ex.getMessage());
            status.setSuccess(false);
            status.setMessage(ex.getMessage());

        }

        //System.out.println("DB CODE: "+code);


        //finish up and close db
        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();

        return status;

    }


    public synchronized void getAllItemTypes(ArrayList<ItemType> itemTypes){

        String query = "select * from item_type order by id asc;";
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        while(c.moveToNext()){

            ItemType m_itemType = new ItemType();

            m_itemType.setId(c.getLong(c.getColumnIndex("id")));
            m_itemType.setTitle(c.getString(c.getColumnIndex("title")));


            itemTypes.add(m_itemType);
        }


        db.close();

    }

    public synchronized void insertItemType(long id,String title){
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.beginTransaction();


        ContentValues newValues = new ContentValues();
        newValues.put("id", id);
        newValues.put("title",title);


        db.insert("item_type", null, newValues);


        //finish up and close db
        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();

    }


    public synchronized void insertItemTypes(JSONArray arr){
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.beginTransaction();



        for (int i = 0 ; i < arr.length() ; i++){

            try{
                JSONObject obj = arr.getJSONObject(i);

                long id = obj.getLong("id");
                String title = obj.getString("title");

                ContentValues newValues = new ContentValues();
                newValues.put("id", id);
                newValues.put("title",title);

                db.insert("item_type", null, newValues);




            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }



        //finish up and close db
        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();

    }


    public synchronized void getSubItemsForType(ArrayList<ItemTypeSubItem> subitems, String typeId){

        String query = "select * from item_type_subitems where type_id=? order by title asc;";
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query, new String[]{typeId});

        while(c.moveToNext()){

            ItemTypeSubItem subItem = new ItemTypeSubItem();

            subItem.setId(c.getLong(c.getColumnIndex("id")));
            subItem.setTitle(c.getString(c.getColumnIndex("title")));
            subItem.setTitleArabic(c.getString(c.getColumnIndex("title_arabic")));
            subItem.setTypeId(c.getLong(c.getColumnIndex("type_id")));



            subitems.add(subItem);
        }


        db.close();

    }

    public synchronized void insertItemTypeSubItems(JSONArray arr){
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.beginTransaction();



        for (int i = 0 ; i < arr.length() ; i++){

            try{
                JSONObject obj = arr.getJSONObject(i);

                long id = obj.getLong("id");
                String title = obj.getString("title");
                String titleArabic = obj.getString("titleArabic");
                long typeId = obj.getLong("typeId");


                ContentValues newValues = new ContentValues();
                newValues.put("id", id);
                newValues.put("title",title);
                newValues.put("title_arabic",titleArabic);
                newValues.put("type_id", typeId);

                db.insert("item_type_subitems", null, newValues);




            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }



        //finish up and close db
        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();

    }

}
