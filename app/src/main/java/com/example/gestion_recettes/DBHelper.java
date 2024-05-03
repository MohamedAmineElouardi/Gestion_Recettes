package com.example.gestion_recettes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String Db_name= "Gestion_Recette";

    public DBHelper( Context context) {
        super(context, Db_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table users ( username TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists users");

    }
    public boolean insertData(String username, String password){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues CV= new ContentValues();
        CV.put("username",username);
        CV.put("password",password);
        long result =db.insert("users",null,CV);
        if (result==-1) {
            return false;
        }
        else {
            return true;
        }

    }
    public boolean checkUsername (String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery("select * from users where username= ?",new String[]{username});
        if (cursor.getCount()>0)return true;
        else return false;
    }
    public boolean checkUsernamePassword (String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery("select * from users where username =? and password =?",new String[]{username,password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
}
