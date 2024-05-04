package com.example.gestion_recettes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBHelper extends SQLiteOpenHelper {
    public static final String Db_name= "Gestion_Recette";

    public DBHelper( Context context) {
        super(context, Db_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // table users
        db.execSQL("create Table users ( username TEXT primary key, password TEXT)");
        // table recette
        db.execSQL("CREATE TABLE recette (" +
                "recette_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "recette_titre TEXT, " +
                "recette_duree INTEGER, " +
                "recette_image TEXT, " +
                "user_id INTEGER, " +
                "FOREIGN KEY(user_id) REFERENCES users(user_id))");

        // table etape
        db.execSQL("CREATE TABLE etape (" +
                "etape_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "recette_id INTEGER, " +
                "text TEXT, " +
                "FOREIGN KEY(recette_id) REFERENCES recette(recette_id))");

        // table category
        db.execSQL("CREATE TABLE category (" +
                "category_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "recette_id INTEGER, " +
                "libelle TEXT, " +
                "FOREIGN KEY(recette_id) REFERENCES recette(recette_id))");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists users");
        db.execSQL("drop Table if exists recette");
        db.execSQL("drop Table if exists etape");
        db.execSQL("drop Table if exists category");

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
    public boolean insertRecette(String titre, int duree, String image, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("recette_titre", titre);
        contentValues.put("recette_duree", duree);
        contentValues.put("recette_image", image);
        contentValues.put("user_id", userId);

        long result = db.insert("recette", null, contentValues);
        return result != -1;
    }

    public boolean insertEtape(int recetteId, String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("recette_id", recetteId);
        contentValues.put("text", text);

        long result = db.insert("etape", null, contentValues);
        return result != -1;
    }

    public boolean insertCategory(int recetteId, String libelle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("recette_id", recetteId);
        contentValues.put("text", libelle);

        long result = db.insert("etape", null, contentValues);
        return result != -1;
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
