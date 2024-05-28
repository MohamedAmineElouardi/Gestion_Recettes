package com.example.gestion_recettes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestion_recettes.Models.Recette;

public class UserModification extends AppCompatActivity {

    private EditText editTitreRecette;
    private EditText editDureeRecette;
    private EditText editIngredientRecette;
    private ImageView editImageRecette;
    private Button updateButton;
    private Button supprimerButton;
    private EditText editEtapeRecette;
    private int recetteId;
    private byte[] recetteImage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_modification);

        editTitreRecette = findViewById(R.id.editTitreRecette);
        editDureeRecette = findViewById(R.id.editDureeRecette);
        editIngredientRecette = findViewById(R.id.editIngredientRecette);
        editEtapeRecette = findViewById(R.id.editEtapeRecette);
        editImageRecette = findViewById(R.id.editImageRecette);
        updateButton = findViewById(R.id.updateButton);
        supprimerButton = findViewById(R.id.deleteButton);

        Intent intent = getIntent();
        recetteId = intent.getIntExtra("recette_id", -1);

        if (recetteId != -1) {
            DBHelper dbHelper = new DBHelper(this);
            Recette recette = dbHelper.getRecetteById(recetteId);
            if (recette != null) {
                populateFields(recette);
            }
        }
        supprimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRecette();
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRecette();
            }
        });


    }

    private void populateFields(Recette recette) {
        editTitreRecette.setText(recette.getRecette_titre());
        editDureeRecette.setText(String.valueOf(recette.getRecette_duree()));
        editIngredientRecette.setText(recette.getRecette_ingredient());
        editEtapeRecette.setText(recette.getEtapes());
        recetteImage = recette.getRecette_image();
        if (recetteImage != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(recetteImage, 0, recetteImage.length);
            editImageRecette.setImageBitmap(bitmap);
        }
    }

    private void updateRecette() {
        String newTitle = editTitreRecette.getText().toString();
        int newDuration = Integer.parseInt(editDureeRecette.getText().toString());
        String newIngredients = editIngredientRecette.getText().toString();
        String newEtapes = editEtapeRecette.getText().toString();

        DBHelper dbHelper = new DBHelper(this);
        dbHelper.updateRecette(recetteId, newTitle, newDuration, recetteImage, newIngredients, newEtapes);

        Intent intent = new Intent(this, NewHomePage.class);
        startActivity(intent);
        finish();
    }

    private void deleteRecette(){
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.deleteRecette(recetteId);
        Intent intent = new Intent(this, NewHomePage.class);
        startActivity(intent);
    }
}
