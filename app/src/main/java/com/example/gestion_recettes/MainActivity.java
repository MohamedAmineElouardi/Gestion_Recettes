package com.example.gestion_recettes;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    public static String user;
    EditText username, password;
    Button Btnlogin,sginUp;
    DBHelper DB;
    ListView recetteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DBHelper db = new DBHelper(getApplicationContext());

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /*
        * Insertion des donnes dans la base donnee
        * */


         /*
        * FIN INSERTION*/

        Btnlogin = findViewById(R.id.login);
        sginUp = findViewById(R.id.sginUp);
        DB=new DBHelper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username =(EditText) findViewById(R.id.username);
                password =(EditText) findViewById(R.id.password);
                Boolean res = db.checkUsernamePassword(username.getText().toString(),password.getText().toString());
                if (res){

                    editor.putString("username", username.getText().toString());
                    editor.apply();
                    System.out.println(sharedPreferences.getAll());
                    Intent intent = new Intent(getApplicationContext(), HomePage.class);
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
                }
            }
        });
        sginUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
    }


}