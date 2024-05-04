package com.example.gestion_recettes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button Btnlogin,sginUp;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Btnlogin =(Button) findViewById(R.id.login);
        sginUp =(Button) findViewById(R.id.sginUp);
        DB=new DBHelper(this);

        Btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username =(EditText) findViewById(R.id.username);
                password =(EditText) findViewById(R.id.password);
                DBHelper db = new DBHelper(getApplicationContext());
                Boolean res = db.checkUsernamePassword(username.getText().toString(),password.getText().toString());
                if (res){
                    Bundle user_bundle = new Bundle();
                    user_bundle.putString("username",username.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), HomePage.class);

                    startActivity(intent, user_bundle);
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