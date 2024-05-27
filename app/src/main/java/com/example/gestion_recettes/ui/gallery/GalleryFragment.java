package com.example.gestion_recettes.ui.gallery;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.gestion_recettes.Adaptors.CustomAdapterRecette;
import com.example.gestion_recettes.DBHelper;
import com.example.gestion_recettes.R;
import com.example.gestion_recettes.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    public static ListView recetteList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recetteList = root.findViewById(R.id.recettelist);
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