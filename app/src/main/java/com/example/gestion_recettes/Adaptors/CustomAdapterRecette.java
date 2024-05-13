package com.example.gestion_recettes.Adaptors;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gestion_recettes.Models.Recette;
import com.example.gestion_recettes.R;
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
        TextView recetteOwner = convertView.findViewById(R.id.recetteOwner);
        Chip dureeRecette = convertView.findViewById(R.id.dureeChip);

        byte[] imageByte = recetteArrayList.get(position).getRecette_image();
        imageRecette.setImageBitmap(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length));
        titreRecettee.setText(recetteArrayList.get(position).getRecette_titre());
        recetteOwner.setText(recetteArrayList.get(position).getRecetteOwner());
        dureeRecette.setText(String.valueOf(recetteArrayList.get(position).getRecette_duree()) + " min");

        return convertView;

    }
}
