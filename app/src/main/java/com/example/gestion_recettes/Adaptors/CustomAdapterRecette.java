package com.example.gestion_recettes.Adaptors;

import android.content.Context;
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
        TextView nbrEtape = convertView.findViewById(R.id.NombreEtape);
        TextView dureeRecette = convertView.findViewById(R.id.DureeRecette);

        imageRecette.setImageResource(recetteArrayList.get(position).getRecette_image());

        titreRecettee.setText(recetteArrayList.get(position).getRecette_titre());
        recetteOwner.setText(recetteArrayList.get(position).getRecetteOwner().getUsername());
        nbrEtape.setText(recetteArrayList.get(position).getEtapeList().size());
        dureeRecette.setText(recetteArrayList.get(position).getRecette_duree());

        return convertView;

    }
}
