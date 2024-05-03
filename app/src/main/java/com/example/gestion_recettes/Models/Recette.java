package com.example.gestion_recettes.Models;


import java.util.List;

public class Recette {
    private int recette_id;
    private String recette_titre;
    private int recette_duree;
    private int recette_image;

    private User recetteOwner;

    private List<Etape> etapeList;

    public List<Etape> getEtapeList() {
        return etapeList;
    }

    public void setEtapeList(List<Etape> etapeList) {
        this.etapeList = etapeList;
    }

    public User getRecetteOwner() {
        return recetteOwner;
    }

    public void setRecetteOwner(User recetteOwner) {
        this.recetteOwner = recetteOwner;
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

    public int getRecette_image() {
        return recette_image;
    }

    public void setRecette_image(int recette_image) {
        this.recette_image = recette_image;
    }
}
