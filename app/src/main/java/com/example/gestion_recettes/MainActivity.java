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


        username =(EditText) findViewById(R.id.username);
        password =(EditText) findViewById(R.id.password);
        Btnlogin =(Button) findViewById(R.id.login);
        sginUp =(Button) findViewById(R.id.sginUp);
        DB=new DBHelper(this);

        Btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass =password.getText().toString();
                if(user.equals("")||pass.equals("")){
                    Toast.makeText(MainActivity.this,"Please inter your information!",Toast.LENGTH_SHORT).show();
                }else {
                    boolean checkuserpass=DB.checkUsernamePassword(user , pass);
                    if (checkuserpass==true){
                        Toast.makeText(MainActivity.this,"Sign in successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(MainActivity.this,"Username or password invalid",Toast.LENGTH_SHORT).show();
                    }
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