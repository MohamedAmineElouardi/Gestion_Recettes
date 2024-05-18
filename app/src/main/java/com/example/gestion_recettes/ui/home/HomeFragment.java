package com.example.gestion_recettes.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gestion_recettes.Adaptors.CustomAdapterRecette;
import com.example.gestion_recettes.DBHelper;
import com.example.gestion_recettes.R;
import com.example.gestion_recettes.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    ListView recetteList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recetteList = root.findViewById(R.id.recettelist);
        setadadper();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void setadadper(){
        DBHelper db = new DBHelper(getContext());
        CustomAdapterRecette customAdapterRecette = new CustomAdapterRecette(getContext(), 0, db.listerRecette());
        recetteList.setAdapter(customAdapterRecette);
    }
}