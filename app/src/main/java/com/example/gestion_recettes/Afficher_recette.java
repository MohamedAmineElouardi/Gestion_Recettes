package com.example.gestion_recettes;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestion_recettes.Models.Recette;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Afficher_recette extends AppCompatActivity {

    private TextView titreRecette;
    private TextView dureeRecette;
    private TextView ingredientRecette;
    private TextView etapeRecette;
    private ImageView imageRecette;
    private FloatingActionButton shr;
    private Recette recette;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_recette);

        titreRecette = findViewById(R.id.titreRecette);
        dureeRecette = findViewById(R.id.dureeRecette);
        ingredientRecette = findViewById(R.id.ingredientRecette);
        etapeRecette = findViewById(R.id.etapes);
        imageRecette = findViewById(R.id.imageRecette);
        shr = findViewById(R.id.share);

        shr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(Intent.ACTION_SEND);
                myintent.setType("text/plain");
                String shareBody = "Title: " + titreRecette.getText().toString()
                        + "\nDuration: " + dureeRecette.getText().toString()
                        + "\nIngredient: " + ingredientRecette.getText().toString()
                        + "\nEtapes : " + etapeRecette.getText().toString();
                String shareSub = "Recette a partage";
                myintent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                myintent.putExtra(Intent.EXTRA_TEXT, shareBody);

                startActivity(Intent.createChooser(myintent, "Partager cette recette"));
            }
        });

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
        dureeRecette.setText(recette.getRecette_duree()+ " min");
        ingredientRecette.setText(recette.getRecette_ingredient());
        etapeRecette.setText(recette.getEtapes());
        System.out.println(recette.getRecette_ingredient());
        byte[] imageByte = recette.getRecette_image();
        if (imageByte != null) {
            imageRecette.setImageBitmap(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length));
        }
    }
}
