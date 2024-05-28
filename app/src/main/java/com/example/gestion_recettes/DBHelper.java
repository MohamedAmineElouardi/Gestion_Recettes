package com.example.gestion_recettes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.widget.Toast;

import com.example.gestion_recettes.Models.Category;
import com.example.gestion_recettes.Models.Recette;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String Db_name= "Gestion_Recette";

    public DBHelper( Context context) {
        super(context, Db_name, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        // table users
        db.execSQL("CREATE TABLE users (username TEXT PRIMARY KEY, password TEXT)");

        // table recette
        db.execSQL("CREATE TABLE recette (" +
                "recette_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "recette_titre TEXT, " +
                "recette_duree INTEGER, " +
                "recette_image BLOB, " +
                "recette_ingredient TEXT, " +
                "recette_etape TEXT, " +
                "username TEXT, " +
                "category_id INTEGER, " +
                "FOREIGN KEY(username) REFERENCES users(username), " +
                "FOREIGN KEY(category_id) REFERENCES category(category_id))"
        );

        // table etape
        db.execSQL("CREATE TABLE etape (" +
                "etape_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "recette_id INTEGER, " +
                "text TEXT, " +
                "FOREIGN KEY(recette_id) REFERENCES recette(recette_id))"
        );

        // table category
        db.execSQL("CREATE TABLE category (" +
                "category_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "libelle TEXT)"
        );

        // table user_liked
        db.execSQL("CREATE TABLE user_like(username TEXT, " +
                "recette_id INTEGER, " +
                "FOREIGN KEY(username) REFERENCES users(username)," +
                "FOREIGN KEY(recette_id) REFERENCES recette(recette_id),"+
                "PRIMARY KEY(username, recette_id))"
        );
        /*db.execSQL("Insert into user_like values("+"Ahmed"+",1)");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists etape");
        db.execSQL("drop Table if exists category");
        db.execSQL("drop Table if exists users");
        db.execSQL("drop Table if exists recette");

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
    public boolean insertRecette(String titre, int duree, byte[] image, String ingredients, String etapes, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("recette_titre", titre);
        contentValues.put("recette_duree", duree);
        contentValues.put("recette_image", image);
        contentValues.put("recette_ingredient", ingredients);
        contentValues.put("recette_etape", etapes);
        contentValues.put("username", username);
        long result = db.insert("recette", null, contentValues);
        return result != -1;
    }
    public void deleteCategories(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from category");
    }

    public boolean insertEtape(int recetteId, String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("recette_id", recetteId);
        contentValues.put("text", text);

        long result = db.insert("etape", null, contentValues);
        return result != -1;
    }

    public boolean insertCategory(String libelle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("libelle", libelle);

        long result = db.insert("category", null, contentValues);
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
    public List<String> listerCategories(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from category", new String[]{});
        List<String> categoryList = new ArrayList<>();
        while (res.moveToNext()) {
            categoryList.add(res.getString(1).toString());
        }
        res.close();
        return categoryList;
    }

    public String getCategoryId(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select id from category where libelle=?", new String[]{name});
        if (res.getCount()>0){
            return res.getString(0);
        }
        else{
            return null;
        }
    }

    public ArrayList<Recette> listerRecette(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select * from Recette", new String[]{});
        ArrayList<Recette> recetteList = new ArrayList<>();
        while (cur.moveToNext()){
            recetteList.add(new Recette(cur.getInt(0),
                    cur.getString(1),
                    cur.getInt(2),
                    cur.getBlob(3),
                    cur.getString(4),
                    cur.getString(5),
                    cur.getString(6),
                    cur.getInt(7)
                    ));
        }
        /*"recette_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "recette_titre TEXT, " +
                "recette_duree INTEGER, " +
                "recette_image BLOB, " +
                "recette_ingredient TEXT, " +
                "recette_etape TEXT, " +
                "username TEXT, " +
                "category_id INTEGER, " +*/
        return recetteList;
    }
    public ArrayList<Recette> listerRecetteByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM Recette WHERE username = ?", new String[]{username});
        ArrayList<Recette> recetteList = new ArrayList<>();
        while (cur.moveToNext()) {
            recetteList.add(new Recette(cur.getInt(0),
                    cur.getString(1),
                    cur.getInt(2),
                    cur.getBlob(3),
                    cur.getString(4),
                    cur.getString(5),
                    cur.getString(6),
                    cur.getInt(7)
            ));
        }
        cur.close();
        return recetteList;
    }
    public Recette getRecetteById(int recette_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM recette WHERE recette_id = ?", new String[]{String.valueOf(recette_id)});

        if (cursor != null && cursor.moveToFirst()) {
            String titre = cursor.getString(cursor.getColumnIndexOrThrow("recette_titre"));
            int duree = cursor.getInt(cursor.getColumnIndexOrThrow("recette_duree"));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("recette_image"));
            String ingredient = cursor.getString(cursor.getColumnIndexOrThrow("recette_ingredient"));
            String etape = cursor.getString(cursor.getColumnIndexOrThrow("recette_etape"));
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            int category_id = cursor.getInt(cursor.getColumnIndexOrThrow("category_id"));

            Recette recette = new Recette(recette_id, titre, duree, image, ingredient, etape,username, category_id);

            cursor.close();
            return recette;
        }
        if (cursor != null) {
            cursor.close();     }
        return null;
    }
    public void updateRecette(int recetteId, String newTitle, int newDuration, byte[] newImage, String newIngredients, String newEtapes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("recette_titre", newTitle);
        contentValues.put("recette_duree", newDuration);
        contentValues.put("recette_image", newImage);
        contentValues.put("recette_ingredient", newIngredients);
        contentValues.put("recette_etape",newEtapes);

        db.update("recette", contentValues, "recette_id = ?", new String[]{String.valueOf(recetteId)});
    }

    public ArrayList<Recette> chercherRecette(String titre){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select * from Recette where recette_titre = ?", new String[]{titre});
        ArrayList<Recette> recetteList = new ArrayList<>();
        while (cur.moveToNext()){
            recetteList.add(new Recette(cur.getInt(0),
                    cur.getString(1),
                    cur.getInt(2),
                    cur.getBlob(3),
                    cur.getString(4),
                    cur.getString(5),
                    cur.getString(6),
                    cur.getInt(7)
            ));
        }
        return recetteList;
    }
}
