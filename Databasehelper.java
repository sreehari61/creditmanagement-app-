package com.creditmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Databasehelper extends SQLiteOpenHelper
{
    private static final String dbname = "creditmanagement";
    public static final String tabelname1 = "users";
//    public static final String tabelname2 = "transfers";
    public static final String t1_col1 = "id";
    public static final String t1_col2 = "name";
    public static final String t1_col3 = "email";
    public static final String t1_col4 = "currentcredit";
//    public static final String t2_col1 = "id";
//    public static final String t2_col2 = "transferfrom";
//    public static final String t2_col3 = "transfeto";

    public Databasehelper(Context context) {
        super(context, dbname, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("create table users (id INTEGER PRIMARY KEY AUTOINCREMENT , name TEXT , email TEXT , currentcredit INTEGER)");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public boolean insertData(String name,String eamil,String credits) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(t1_col2,name);
        contentValues.put(t1_col3,eamil);
        contentValues.put(t1_col4,credits);
        long result = db.insert(tabelname1,null ,contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+tabelname1,null);
        return res;
    }

    public Cursor getUserData(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from users where name = '" + name +"'",null);

        return res;
    }

    public void update(int id,int credits)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String strSQL = "UPDATE users SET currentcredit = "+credits+" WHERE id = "+ id;
        db.execSQL(strSQL);
    }
}
