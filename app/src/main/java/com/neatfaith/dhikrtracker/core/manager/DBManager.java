package com.neatfaith.dhikrtracker.core.manager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.neatfaith.dhikrtracker.App;
import com.neatfaith.dhikrtracker.core.model.Item;
import com.neatfaith.dhikrtracker.core.model.ItemType;
import com.neatfaith.dhikrtracker.core.model.ItemTypeSubItem;
import com.neatfaith.dhikrtracker.core.model.ResponseStatus;
import com.neatfaith.dhikrtracker.core.model.User;
import com.neatfaith.dhikrtracker.core.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
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

        c.close();

        db.close();

    }

    public synchronized ResponseStatus insertUser(String name,String uid){
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.beginTransaction();


        ContentValues newValues = new ContentValues();
        newValues.put("name",name);
        newValues.put("created", Utils.getUnixTimestamp());

        if (uid != null){
            newValues.put("id",uid);
        }

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

    public synchronized ResponseStatus insertUser(String name){
        return insertUser(name,null);
    }

    public synchronized int deleteUserWithId(long uid){

        SQLiteDatabase db = sqlHelper.getReadableDatabase();

        int status = db.delete("user","id=?",new String[]{""+uid});

        db.close();

        return status;

    }

    public synchronized int updateUserWithId(long uid,String name){

        SQLiteDatabase db = sqlHelper.getReadableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put("name", name);

        int status = db.update("user",newValues,"id=?",new String[]{""+uid});

        db.close();

        return status;

    }


    public synchronized void getAllItems(ArrayList<Item> items){

        String query = "select * from item order by timestamp desc;";
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        while(c.moveToNext()){

            long id = c.getLong(c.getColumnIndex("id"));
            long subitem_id = c.getLong(c.getColumnIndex("subitem_id"));
            long user_id = c.getLong(c.getColumnIndex("user_id"));
            long tally = c.getLong(c.getColumnIndex("tally"));
            long minutes = c.getLong(c.getColumnIndex("minutes"));
            long timestamp = c.getLong(c.getColumnIndex("timestamp"));

            //Get the users for lookup
            ArrayList<User> allUsers = new ArrayList<>();
            ArrayList<ItemTypeSubItem> allSubItems = new ArrayList<>();
            this.getAllUsers(allUsers);
            this.getAllSubItems(allSubItems);

            User user = Utils.userForId(user_id,allUsers);
            ItemTypeSubItem subitem = Utils.subItemForId(subitem_id,allSubItems);


            Item m_item = new Item(id,subitem,user,tally,minutes,timestamp);


            items.add(m_item);
        }

        c.close();

        db.close();

    }

    public synchronized void getItemsForUserId(ArrayList<Item> items, long uid){

        String query = "select * from item where user_id=? order by timestamp desc;";
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query, new String[]{""+uid});

        while(c.moveToNext()){

            long id = c.getLong(c.getColumnIndex("id"));
            long subitem_id = c.getLong(c.getColumnIndex("subitem_id"));
            long user_id = c.getLong(c.getColumnIndex("user_id"));
            long tally = c.getLong(c.getColumnIndex("tally"));
            long minutes = c.getLong(c.getColumnIndex("minutes"));
            long timestamp = c.getLong(c.getColumnIndex("timestamp"));

            //Get the users for lookup
            ArrayList<User> allUsers = new ArrayList<>();
            ArrayList<ItemTypeSubItem> allSubItems = new ArrayList<>();
            this.getAllUsers(allUsers);
            this.getAllSubItems(allSubItems);

            User user = Utils.userForId(user_id,allUsers);
            ItemTypeSubItem subitem = Utils.subItemForId(subitem_id,allSubItems);


            Item m_item = new Item(id,subitem,user,tally,minutes,timestamp);


            items.add(m_item);
        }

        c.close();

        db.close();

    }

    public synchronized void insertItem(long subitem_id,long user_id, long tally, long minutes, long timestamp){
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.beginTransaction();


        ContentValues newValues = new ContentValues();
        newValues.put("subitem_id", subitem_id);
        newValues.put("user_id",user_id);
        newValues.put("tally", tally);
        newValues.put("minutes",minutes);
        newValues.put("timestamp", timestamp);


        db.insert("item", null, newValues);


        //finish up and close db
        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();

    }

    public synchronized void insertItem(long subitem_id,long user_id, long tally, long minutes){
        long timestamp = Utils.getUnixTimestamp();
        insertItem(subitem_id, user_id, tally, minutes, timestamp);
    }

    public synchronized int deleteItemWithId(long uid){

        SQLiteDatabase db = sqlHelper.getReadableDatabase();

        int status = db.delete("item","id=?",new String[]{""+uid});

        db.close();

        return status;

    }

    public synchronized int updateItemWithId(long uid,long subitem_id,long user_id, long tally, long minutes){

        SQLiteDatabase db = sqlHelper.getReadableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put("subitem_id", subitem_id);
        newValues.put("user_id",user_id);
        newValues.put("tally", tally);
        newValues.put("minutes",minutes);


        int status = db.update("item",newValues,"id=?",new String[]{""+uid});

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


        c.close();

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


                ContentValues newValues = new ContentValues();

                //id
                if (obj.has("id")){
                    long id = obj.getLong("id");
                    newValues.put("id", id);
                }

                //title
                if (obj.has("title")){
                    String title = obj.getString("title");
                    newValues.put("title",title);
                }


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

        SQLiteDatabase db = sqlHelper.getReadableDatabase();

        String query = null;
        Cursor c = null;
        if (typeId == null){ //return all
            query = "select * from item_type_subitems order by title asc;";
            c = db.rawQuery(query, null);

        }
        else {
            query = "select * from item_type_subitems where type_id=? order by title asc;";
            c = db.rawQuery(query, new String[]{typeId});
        }

        while(c.moveToNext()){

            ItemTypeSubItem subItem = new ItemTypeSubItem();

            subItem.setId(c.getLong(c.getColumnIndex("id")));
            subItem.setTitle(c.getString(c.getColumnIndex("title")));
            subItem.setTitleArabic(c.getString(c.getColumnIndex("title_arabic")));
            subItem.setCanModify(c.getInt(c.getColumnIndex("can_modify")));

            //itemType
            long type_id = c.getLong(c.getColumnIndex("type_id"));

            ArrayList<ItemType> allItemTypes = new ArrayList<>();
            this.getAllItemTypes(allItemTypes);

            ItemType type = Utils.itemTypeForId(type_id,allItemTypes);
            subItem.setType(type);



            subitems.add(subItem);
        }

        c.close();

        db.close();

    }

    public synchronized void getAllSubItems(ArrayList<ItemTypeSubItem> subitems){
        this.getSubItemsForType(subitems,null);
    }
    public synchronized void insertItemTypeSubItems(JSONArray arr){
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.beginTransaction();



        for (int i = 0 ; i < arr.length() ; i++){

            try{
                JSONObject obj = arr.getJSONObject(i);

                ContentValues newValues = new ContentValues();


                //title
                if (obj.has("title")){
                    String title = obj.getString("title");
                    newValues.put("title",title);
                }

                //titleArabic
                if (obj.has("titleArabic")){
                    String titleArabic = obj.getString("titleArabic");
                    newValues.put("title_arabic",titleArabic);
                }

                //typeId
                if (obj.has("typeId")){
                    long typeId = obj.getLong("typeId");
                    newValues.put("type_id", typeId);
                }


                //canModify
                if (obj.has("canModify")){
                    long canModify = obj.getLong("canModify");
                    newValues.put("can_modify", canModify);
                }

                //meaning
                if (obj.has("meaning")){
                    String meaning = obj.getString("meaning");
                    newValues.put("meaning", meaning);
                }


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

    public synchronized void insertItemTypeSubItem(String title, long type_id){
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.beginTransaction();


        ContentValues newValues = new ContentValues();
        newValues.put("title",title);
        newValues.put("type_id", type_id);

        db.insert("item_type_subitems", null, newValues);




        //finish up and close db
        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();

    }

    public synchronized int deleteItemTypeSubItemWithId(long uid){

        SQLiteDatabase db = sqlHelper.getReadableDatabase();

        int status = db.delete("item_type_subitems","id=?",new String[]{""+uid});

        db.close();

        return status;

    }

    public synchronized int updateItemTypeSubItemWithId(long uid,String title, long type_id){

        SQLiteDatabase db = sqlHelper.getReadableDatabase();

        ContentValues newValues = new ContentValues();
        newValues.put("title",title);
        newValues.put("type_id", type_id);


        int status = db.update("item_type_subitems",newValues,"id=?",new String[]{""+uid});

        db.close();

        return status;

    }

}
