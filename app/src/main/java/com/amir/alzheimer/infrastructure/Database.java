package com.amir.alzheimer.infrastructure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vansuita.sqliteparser.SqlParser;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "alzheimer.db";
    private static final int DATABASE_VER = 1;
    private static final String T_GALLERY = "galley";
    private static final String C_NAME = "name";
    private static final String C_ADDRESS = "address";
    private static final String SELECT_NAMES = "SELECT DISTINCT " + C_NAME + " FROM " + T_GALLERY + " WHERE 1;";
    private static final String SELECT_ADDRESSES = "SELECT DISTINCT " + C_ADDRESS + " FROM " + T_GALLERY + " WHERE " + C_NAME + " = ";
    private static final String TAG = "Database";

    private SQLiteDatabase db;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
        db = getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                SqlParser.
                        create(T_GALLERY).
                        str(C_NAME).
                        str(C_ADDRESS).
                        build());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop TABLE IF EXISTS " + T_GALLERY);
        Log.w(getClass().getSimpleName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        onCreate(db);
    }

    public ArrayList<String> getAllContacts() {
        ArrayList<String> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( SELECT_NAMES, null);
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(C_NAME)));
            res.moveToNext();
        }

        res.close();
        return array_list;
    }

    public ArrayList<String> getAddressesByName(String name) {
        ArrayList<String> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( SELECT_ADDRESSES + name, null);
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(C_ADDRESS)));
            res.moveToNext();
        }

        res.close();
        return array_list;
    }

    public boolean insertContact (String name, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(C_NAME, name);
        contentValues.put(C_ADDRESS, address);
        db.insert(T_GALLERY, null, contentValues);
        return true;
    }
}
