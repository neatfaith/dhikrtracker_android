package com.neatfaith.dhikrtracker.core.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dhikrtracker.db";
    private static final int DATABASE_VERSION = 1;

    //create
    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS user(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE, created INTEGER);";
    private static final String CREATE_TABLE_ITEM_TYPE = "CREATE TABLE IF NOT EXISTS item_type(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL);";
    private static final String CREATE_TABLE_ITEM_TYPE_SUBITEMS = "CREATE TABLE IF NOT EXISTS item_type_subitems(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, title_arabic TEXT, type_id INTEGER NOT NULL,FOREIGN KEY('type_id') REFERENCES 'item_type'('id'));";



    //drop
    private static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS user";
    private static final String DROP_TABLE_ITEM_TYPE = "DROP TABLE IF EXISTS item_type";
    private static final String DROP_TABLE_ITEM_TYPE_SUBITEMS = "DROP TABLE IF EXISTS item_type_subitems";



    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_ITEM_TYPE);
        db.execSQL(CREATE_TABLE_ITEM_TYPE_SUBITEMS);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        //db.execSQL(DROP_TABLE_USER);
        //db.execSQL(DROP_TABLE_ITEM_TYPE);


        //onCreate(db);

    }
}
