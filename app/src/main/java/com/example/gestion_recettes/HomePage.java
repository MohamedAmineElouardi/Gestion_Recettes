package com.example.gestion_recettes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import android.widget.ListView;
import android.widget.TextView;

import com.example.gestion_recettes.Adaptors.CustomAdapterRecette;
import com.example.gestion_recettes.ui.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.result.ActivityResultLauncher;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gestion_recettes.databinding.ActivityHomePageBinding;



public class HomePage extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomePageBinding binding;
    public static ListView recetteList;

    private static final int PERMISSION_REQUEST_CODE = 100;
    ActivityResultLauncher<String[]> storagePermissionRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarHomePage.toolbar);

        loadFragment(new HomeFragment());
        binding.appBarHomePage.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ajouterRecette = new Intent(getApplicationContext(), CreationRecette.class);
                startActivity(ajouterRecette);

            }
        });
        //recetteList = findViewById(R.id.recettelist);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_page);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        try{
            TextView user = navigationView.getHeaderView(0).findViewById(R.id.username_textview);
            SharedPreferences sharedPref = getSharedPreferences("sharedData",Context.MODE_PRIVATE);
            user.setText(sharedPref.getString("username", "USER"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        /*Own functions*/
        try {
            //setadadper();
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                System.out.println("GOOD !!!!!");
            } else {
                System.out.println("PERMISSION NOT GRANTED ###############");

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_page);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setadadper(){
        DBHelper db = new DBHelper(getApplicationContext());
        CustomAdapterRecette customAdapterRecette = new CustomAdapterRecette(this, 0, db.listerRecette());
        recetteList.setAdapter(customAdapterRecette);
    }


    private void loadFragment(Fragment fragment) {
        // Create a FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Replace the FrameLayout with the new Fragment
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit(); // Save the changes
    }
}