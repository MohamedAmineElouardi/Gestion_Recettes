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
    EditText username , pass1 ,pass22;
    Button btnCreat,back;
    DBHelper DB;

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

        username = (EditText) findViewById(R.id.username1);
        pass1 = (EditText) findViewById(R.id.password1);
        pass22 = (EditText) findViewById(R.id.password2);
        btnCreat =(Button) findViewById(R.id.btnCreate);
        back =(Button) findViewById(R.id.back);
        DB = new DBHelper(this);

        btnCreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = pass1.getText().toString();
                String pass2 = pass22.getText().toString();

                if (user.equals("")||pass.equals("")||pass2.equals("")){
                    Toast.makeText(Register.this,"Please enter all the information ",Toast.LENGTH_SHORT).show();
                }else{
                    if (pass.equals(pass2)){
                        boolean checkuser = DB.checkUsername(user);
                        if (checkuser== false){
                            boolean insert =DB.insertData(user, pass);
                            if(insert == true){
                                Toast.makeText(Register.this,"Regidtered successfully",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(Register.this,"Registration failed",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(Register.this,"User alredy exist!",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(Register.this,"Password not matching ",Toast.LENGTH_SHORT).show();
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