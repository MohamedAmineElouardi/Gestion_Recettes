package com.example.gestion_recettes;

import static com.example.gestion_recettes.MainActivity.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gestion_recettes.databinding.ActivityNewHomePageBinding;

public class NewHomePage extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private ActivityNewHomePageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNewHomePage.toolbar);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedData",Context.MODE_PRIVATE);

        if (!sharedPreferences.contains("username")){
            binding.appBarNewHomePage.fab.setEnabled(false);
        }
        binding.appBarNewHomePage.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!sharedPreferences.contains("username")){
                        Toast.makeText(NewHomePage.this, "Seul l'admin peut ajouter des recettes ", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent ajouterRecette = new Intent(getApplicationContext(), CreationRecette.class);
                        startActivity(ajouterRecette);
                    }
                }
            });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_new_home_page);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        try{
            View navigation = navigationView.getHeaderView(0);
            TextView uername = navigation.findViewById(R.id.username);
            Menu menu = navigationView.getMenu();
            MenuItem connexionItem = menu.findItem(R.id.nav_slideshow);
            SharedPreferences sharedPref = getSharedPreferences("sharedData", Context.MODE_PRIVATE);
            if (!sharedPref.contains("username")){
                uername.setText("Utilisateur invite");
                connexionItem.setTitle("Se Connecter");
                connexionItem.setIcon(R.drawable.ic_login);
            }
            else{
                uername.setText(sharedPref.getString("username", "Utilisateur"));
                connexionItem.setTitle("Se Deconnecter");
                connexionItem.setIcon(R.drawable.ic_logout);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                SharedPreferences sharedPref = getSharedPreferences("sharedData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                if (menuItem.getTitle().toString().equals("Se Deconnecter")){
                    editor.remove("username");
                    editor.apply();
                    Intent login = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(login);
                }
                if (menuItem.getTitle().toString().equals("Se Connecter")){
                    Intent login = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(login);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_home_page, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_new_home_page);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}