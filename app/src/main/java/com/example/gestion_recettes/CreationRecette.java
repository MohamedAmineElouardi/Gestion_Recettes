package com.example.gestion_recettes;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreationRecette extends AppCompatActivity {


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

        Spinner mspin=(Spinner) findViewById(R.id.spinner);
        Integer[] items = new Integer[]{5,10,15,20,25,30,35,40,45,50,55,60};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, items);
        mspin.setAdapter(adapter);

        EditText recetteTitre = (EditText) findViewById(R.id.RecetteTitre);
        Button addEtape = (Button) findViewById(R.id.addEtape);
        LinearLayout EtapeLayout = findViewById(R.id.EtapesLayout);
        Button DoneButton = (Button) findViewById(R.id.DoneButton);
        Integer duration = (Integer)mspin.getSelectedItem();


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

        DoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                dbHelper.insertRecette();
            }
        });
    }
}