package com.example.gestion_recettes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gestion_recettes.Models.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CreationRecette extends AppCompatActivity {
    ActivityResultLauncher<Intent> resultLauncher;
    ImageView imageView;
    public Uri image_url;
    public byte[] imageBlob;
    static Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_creation_recette);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        categorySpinner = findViewById(R.id.category_spinner);

        registerResult();
        populateSpinnerCategory();


        Spinner mspin= findViewById(R.id.spinner);
        Integer[] items = new Integer[]{5,10,15,20,25,30,35,40,45,50,55,60};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_list_item_1, items);
        mspin.setAdapter(adapter);

        EditText recetteTitre = (EditText) findViewById(R.id.RecetteTitre);
        Button addImage = findViewById(R.id.addImage);
        Button addEtape = (Button) findViewById(R.id.addEtape);
        LinearLayout EtapeLayout = findViewById(R.id.EtapesLayout);
        FloatingActionButton DoneButton = findViewById(R.id.DoneButton);
        Integer duration = (Integer) mspin.getSelectedItem();
        EditText ingredients = findViewById(R.id.ingredients);
        imageView =  findViewById(R.id.imageView);

        addEtape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newEditText = new EditText(getApplicationContext());
                newEditText.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                newEditText.setHint("Next Step?");
                EtapeLayout.addView(newEditText);
            }
        });

        addImage.setOnClickListener(view->pickImage());


        DoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                SharedPreferences sharedPreferences = getSharedPreferences("sharedData", MODE_PRIVATE);

                boolean res = false;
                if (!recetteTitre.getText().toString().equals("")||!imageBlob.equals(null)||!ingredients.getText().toString().equals("")) {
                    res = dbHelper.insertRecette(recetteTitre.getText().toString(), duration, imageBlob, sharedPreferences.getString("username", null), ingredients.getText().toString());
                }
                else{
                    Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
                if (res){
                    Toast.makeText(getApplicationContext(), "Recette ajouter avec succes", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void pickImage(){
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    public void registerResult(){
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result)  {
                try {
                    imageBlob = getBlobFromUri(result.getData().getData());

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void populateSpinnerCategory(){
        DBHelper db = new DBHelper(getApplicationContext());
        List<String> categoryList = db.listerCategories();
        ArrayAdapter<String> categoryArrayAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1, categoryList);
        categorySpinner.setAdapter(categoryArrayAdapter);
    }
    public byte[] getBlobFromUri(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        return outputStream.toByteArray();
    }
}