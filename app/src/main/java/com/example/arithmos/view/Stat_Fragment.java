package com.example.arithmos.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.arithmos.R;
import com.example.arithmos.databinding.FragmentStatBinding;
import com.example.arithmos.model.ExoStat;
import com.example.arithmos.viewmodel.UserStatViewModel;


public class Stat_Fragment extends Fragment {

    private FragmentStatBinding binding;
    private UserStatViewModel userStatViewModel;

    private final String TAG = "Stat_Fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatBinding.inflate(inflater, container, false);

        userStatViewModel= new ViewModelProvider(this).get(UserStatViewModel.class);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "before call to DATABASE");

        userStatViewModel.getUserStat();

        //here we check if data load successfully otherwise we display the user to warn him
        userStatViewModel.isLoadingSuccess.observe(getViewLifecycleOwner(), b -> {
            if(!b) {
                Toast.makeText(getActivity(), "Error When loading user profile",
                        Toast.LENGTH_LONG).show();
            } else {
                Log.d(TAG, "user profile load succesfull");
            }
        });

        //TODO : find a better way to do that
        userStatViewModel.listStats.observe(getViewLifecycleOwner(), listStats -> {
            ExoStat add = listStats.get(0);

            binding.totaladd.append(" " + String.valueOf(add.getNbErreur() + add.getNbOk()));
            binding.correctadd.append( " " + String.valueOf(add.getNbOk()));
            binding.incorrectadd.append(" " + String.valueOf(add.getNbErreur()));
            binding.Pourcentageadd.append(" " + String.valueOf(add.getPourcentage()));

            ExoStat sous = listStats.get(1);
            binding.total.append(" " + String.valueOf(sous.getNbErreur() + sous.getNbOk()));
            binding.correct.append( " " + String.valueOf(sous.getNbOk()));
            binding.incorrect.append(" " + String.valueOf(sous.getNbErreur()));
            binding.PourcentageSous.append(" " + String.valueOf(sous.getPourcentage()));

            ExoStat mult = listStats.get(2);
            binding.totalmult.append(" " + String.valueOf(mult.getNbErreur() + mult.getNbOk()));
            binding.correctmult.append( " " + String.valueOf(mult.getNbOk()));
            binding.incorrectmult.append(" " + String.valueOf(mult.getNbErreur()));
            binding.Pourcentagemult.append(" " + String.valueOf(mult.getPourcentage()));

            ExoStat div = listStats.get(3);
            binding.totaldiv.append(" " + String.valueOf(div.getNbErreur() + div.getNbOk()));
            binding.correctdiv.append( " " + String.valueOf(div.getNbOk()));
            binding.incorrectdiv.append(" " + String.valueOf(div.getNbErreur()));
            binding.Pourcentagediv.append(" " + String.valueOf(div.getPourcentage()));
        });

        Log.d(TAG, "after call to DATABASE");

    }
}