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

public class Register extends AppCompatActivity {
    EditText username , pass1 ,pass2;
    Button btnCreat,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnCreat =(Button) findViewById(R.id.btnCreate);
        back =(Button) findViewById(R.id.back);

        btnCreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = (EditText) findViewById(R.id.username);
                pass1 = (EditText) findViewById(R.id.password1);
                pass2 = (EditText) findViewById(R.id.password2);
                if (pass1.getText().toString().equals(pass2.getText().toString())){
                    String message = "Passwords don't match";
                    Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
                }
                else{
                    DBHelper db = new DBHelper(getApplicationContext());
                    boolean result = db.insertData(username.getText().toString(), pass1.getText().toString());
                    if (result){
                        Toast.makeText(getApplicationContext(),"Account created successfully you can login now.", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"An error has occurred please try again.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });


    }
}