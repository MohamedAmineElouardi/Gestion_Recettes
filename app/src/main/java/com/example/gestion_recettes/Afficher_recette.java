// In Afficher_recette.java
package com.example.gestion_recettes;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestion_recettes.Models.Recette;

public class Afficher_recette extends AppCompatActivity {

    private TextView titreRecette;
    private TextView dureeRecette;
    private TextView ingredientRecette;
    private ImageView imageRecette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_recette);

        titreRecette = findViewById(R.id.titreRecette);
        dureeRecette = findViewById(R.id.dureeRecette);
        ingredientRecette = findViewById(R.id.ingredientRecette);
        imageRecette = findViewById(R.id.imageRecette);

        int recette_id = getIntent().getIntExtra("recette_id", -1);
        if (recette_id != -1) {
            DBHelper dbHelper = new DBHelper(this);
            Recette recette = dbHelper.getRecetteById(recette_id);
            if (recette != null) {
                displayRecetteDetails(recette);
            }
        }
    }

    private void displayRecetteDetails(Recette recette) {
        titreRecette.setText(recette.getRecette_titre());
        dureeRecette.setText(String.valueOf(recette.getRecette_duree()) + " min");
        ingredientRecette.setText(recette.getRecette_ingredient());

        byte[] imageByte = recette.getRecette_image();
        if (imageByte != null) {
            imageRecette.setImageBitmap(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length));
        }
    }
}
