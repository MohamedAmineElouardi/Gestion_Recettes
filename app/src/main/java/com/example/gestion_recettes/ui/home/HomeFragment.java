package com.example.gestion_recettes.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gestion_recettes.Adaptors.CustomAdapterRecette;
import com.example.gestion_recettes.DBHelper;
import com.example.gestion_recettes.Models.Recette;
import com.example.gestion_recettes.R;
import com.example.gestion_recettes.databinding.FragmentHomeBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    ListView recetteList;
    EditText rechercher;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recetteList = root.findViewById(R.id.recettelist);
        rechercher = root.findViewById(R.id.chercher);
        try{
            setadadper();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void setadadper(){
        DBHelper db = new DBHelper(getContext());
        ArrayList<Recette> recetteListDb = db.listerRecette();
        CustomAdapterRecette customAdapterRecette = new CustomAdapterRecette(getContext(), 0, recetteListDb);
        recetteList.setAdapter(customAdapterRecette);
        rechercher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().isEmpty()){
                    CustomAdapterRecette customAdapterRecette = new CustomAdapterRecette(getContext(), 0, recetteListDb);
                    recetteList.setAdapter(customAdapterRecette);

                }
                else{
                    ArrayList<Recette> filteredRecettes = recetteListDb.stream()
                            .filter(recette -> recette.getRecette_titre().startsWith(s.toString()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    CustomAdapterRecette customAdapterRecette = new CustomAdapterRecette(getContext(), 0, filteredRecettes);
                    recetteList.setAdapter(customAdapterRecette);

                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

}