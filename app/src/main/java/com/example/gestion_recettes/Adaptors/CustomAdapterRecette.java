package com.example.gestion_recettes.Adaptors;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;; // Import your modification activity
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.*;
import com.example.gestion_recettes.Afficher_recette;
import com.example.gestion_recettes.DBHelper;
import com.example.gestion_recettes.HomePage;
import com.example.gestion_recettes.Models.Recette;
import com.example.gestion_recettes.R;
import com.example.gestion_recettes.UserModification;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterRecette extends ArrayAdapter {

    ArrayList<Recette> recetteArrayList;
    LayoutInflater inf;

    public CustomAdapterRecette(@NonNull Context context, int resource, @NonNull ArrayList<Recette> listRecette) {
        super(context, resource, listRecette);
        this.inf = LayoutInflater.from(context);
        this.recetteArrayList = listRecette;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = inf.inflate(R.layout.custom_listview_recette, null);

        ImageView imageRecette = convertView.findViewById(R.id.imagerecette);
        TextView titreRecettee = convertView.findViewById(R.id.recetteTitle);
        Chip dureeRecette = convertView.findViewById(R.id.dureeChip);
        byte[] imageByte = recetteArrayList.get(position).getRecette_image();
        imageRecette.setImageBitmap(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length));
        titreRecettee.setText(recetteArrayList.get(position).getRecette_titre());
        dureeRecette.setText(String.valueOf(recetteArrayList.get(position).getRecette_duree()) + " min");
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Afficher_recette.class);
                intent.putExtra("recette_id", recetteArrayList.get(position).getRecette_id());
                getContext().startActivity(intent);
            }
        });
        Button modifyButton = convertView.findViewById(R.id.modifer);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("sharedData",Context.MODE_PRIVATE);
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserModification.class);
                intent.putExtra("recette_id",recetteArrayList.get(position).getRecette_id());
                getContext().startActivity(intent);
            }
        });
        if (!sharedPreferences.contains("username")){
            modifyButton.setEnabled(false);
            modifyButton.setVisibility(View.INVISIBLE);
        }

        return convertView;

    }

}
