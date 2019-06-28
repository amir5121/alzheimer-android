package com.amir.alzheimer.infrastructure

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

import com.vansuita.sqliteparser.SqlParser

import java.util.ArrayList

class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER) {

//    private val db: SQLiteDatabase = writableDatabase

    //hp = new HashMap();
    val allContacts: ArrayList<String>
        get() {
            val arrayList = ArrayList<String>()
            val db = this.readableDatabase
            val res = db.rawQuery(SELECT_NAMES, null)
            res.moveToFirst()

            while (!res.isAfterLast) {
                arrayList.add(res.getString(res.getColumnIndex(C_NAME)))
                res.moveToNext()
            }

            res.close()
            return arrayList
        }


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
                SqlParser.create(T_GALLERY).str(C_NAME).str(C_ADDRESS).build())

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("Drop TABLE IF EXISTS $T_GALLERY")
        Log.w(javaClass.simpleName,
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data")
        onCreate(db)
    }

    fun getAddressesByName(name: String): ArrayList<String> {
        val array_list = ArrayList<String>()

        //hp = new HashMap();
        val db = this.readableDatabase
        val res = db.rawQuery(SELECT_ADDRESSES + name, null)
        res.moveToFirst()

        while (!res.isAfterLast) {
            array_list.add(res.getString(res.getColumnIndex(C_ADDRESS)))
            res.moveToNext()
        }

        res.close()
        return array_list
    }

    fun insertContact(name: String, address: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(C_NAME, name)
        contentValues.put(C_ADDRESS, address)
        db.insert(T_GALLERY, null, contentValues)
        return true
    }

    companion object {


        private const val DATABASE_NAME = "alzheimer.db"
        private const val DATABASE_VER = 1
        private const val T_GALLERY = "galley"
        private const val C_NAME = "name"
        private const val C_ADDRESS = "address"
        private const val SELECT_NAMES = "SELECT DISTINCT $C_NAME FROM $T_GALLERY WHERE 1;"
        private const val SELECT_ADDRESSES = "SELECT DISTINCT $C_ADDRESS FROM $T_GALLERY WHERE $C_NAME = "
        private const val TAG = "Database"
    }
}
