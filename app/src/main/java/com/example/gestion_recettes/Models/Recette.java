package com.example.gestion_recettes.Models;


import android.media.Image;
import android.net.Uri;

import java.util.List;

public class Recette {
    private int recette_id;
    private String recette_titre;
    private int recette_duree;
    private byte[] recette_image;
    private String recetteOwner;
    private String ingredients;
    private int categorie_id;


    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public Recette(int recette_id, String recette_titre, int recette_duree, byte[] recette_image, String recetteOwner, String ingredients, int categorie_id) {
        this.recette_id = recette_id;
        this.recette_titre = recette_titre;
        this.recette_duree = recette_duree;
        this.recette_image = recette_image;
        this.recetteOwner = recetteOwner;
        this.ingredients = ingredients;
        this.categorie_id = categorie_id;
    }

    public byte[] getRecette_image() {
        return recette_image;
    }

    public void setRecette_image(byte[] recette_image) {
        this.recette_image = recette_image;
    }

    public String getRecetteOwner() {
        return recetteOwner;
    }

    public void setRecetteOwner(String recetteOwner) {
        this.recetteOwner = recetteOwner;
    }

    public int getCategorie_id() {
        return categorie_id;
    }

    public void setCategorie_id(int categorie_id) {
        this.categorie_id = categorie_id;
    }



    public int getRecette_id() {
        return recette_id;
    }

    public void setRecette_id(int recette_id) {
        this.recette_id = recette_id;
    }

    public String getRecette_titre() {
        return recette_titre;
    }

    public void setRecette_titre(String recette_titre) {
        this.recette_titre = recette_titre;
    }

    public int getRecette_duree() {
        return recette_duree;
    }
    public void setRecette_duree(int recette_duree) {
        this.recette_duree = recette_duree;
    }




    public String getRecette_ingredient() {
        return ingredients;
    }
}
