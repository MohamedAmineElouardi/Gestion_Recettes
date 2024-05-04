package com.example.gestion_recettes.Models;

public class Category {
    public int etape_id;
    public String libelle;

    public int getEtape_id() {
        return etape_id;
    }

    public void setEtape_id(int etape_id) {
        this.etape_id = etape_id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Category(int etape_id, String libelle) {
        this.etape_id = etape_id;
        this.libelle = libelle;
    }
}
