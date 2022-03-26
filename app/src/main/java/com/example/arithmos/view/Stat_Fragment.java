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
            ExoStat addnumber = listStats.get(0);
            ExoStat addletter = listStats.get(1);
            ExoStat adddd = listStats.get(2);
            // type de réponse num pour exo simple
            if (addnumber.getTypeReponse() == 0){
                binding.totaladdnombre.append(" " + String.valueOf(addnumber.getNbErreur() + addnumber.getNbOk()));
                binding.correctaddnombre.append( " " + String.valueOf(addnumber.getNbOk()));
                binding.incorrectaddnombre.append(" " + String.valueOf(addnumber.getNbErreur()));
                binding.Pourcentageaddnombre.append(" " + String.valueOf(addnumber.getPourcentage()));
                // type de réponse lettre pour exo simple
            } if (addletter.getTypeReponse() == 1){
                binding.totaladdlettre.append(" " + String.valueOf(addletter.getNbErreur() + addletter.getNbOk()));
                binding.correctaddlettre.append( " " + String.valueOf(addletter.getNbOk()));
                binding.incorrectaddlettre.append(" " + String.valueOf(addletter.getNbErreur()));
                binding.Pourcentageaddlettre.append(" " + String.valueOf(addletter.getPourcentage()));
            }  if (adddd.getTypeReponse() == 2){
                // exo drag and drop
                binding.totaladdDragandDrop.append(" " + String.valueOf(adddd.getNbErreur() + adddd.getNbOk()));
                binding.correctaddDragandDrop.append( " " + String.valueOf(adddd.getNbOk()));
                binding.incorrectaddDragandDrop.append(" " + String.valueOf(adddd.getNbErreur()));
                binding.PourcentageaddDragandDrop.append(" " + String.valueOf(adddd.getPourcentage()));
            }


            ExoStat sousnum = listStats.get(3);
            ExoStat sousletter = listStats.get(4);
            ExoStat sousdd = listStats.get(5);
            if (addnumber.getTypeReponse() == 0){
                binding.totalsousnombre.append(" " + String.valueOf(sousnum.getNbErreur() + sousnum.getNbOk()));
                binding.correctsousnombre.append( " " + String.valueOf(sousnum.getNbOk()));
                binding.incorrectsousnombre.append(" " + String.valueOf(sousnum.getNbErreur()));
                binding.Pourcentagesousnombre.append(" " + String.valueOf(sousnum.getPourcentage()));
            } if (addletter.getTypeReponse() == 1){
                binding.totalsouslettre.append(" " + String.valueOf(sousletter.getNbErreur() + sousletter.getNbOk()));
                binding.correctsouslettre.append( " " + String.valueOf(sousletter.getNbOk()));
                binding.incorrectsouslettre.append(" " + String.valueOf(sousletter.getNbErreur()));
                binding.Pourcentagesouslettre.append(" " + String.valueOf(sousletter.getPourcentage()));
            }  if (adddd.getTypeReponse() == 2){
                // exo drag and drop
                binding.totalsousDragandDrop.append(" " + String.valueOf(sousdd.getNbErreur() + sousdd.getNbOk()));
                binding.correctsousDragandDrop.append( " " + String.valueOf(sousdd.getNbOk()));
                binding.incorrectsousDragandDrop.append(" " + String.valueOf(sousdd.getNbErreur()));
                binding.PourcentagesousDragandDrop.append(" " + String.valueOf(sousdd.getPourcentage()));
            }

            ExoStat multnum = listStats.get(6);
            ExoStat multletter = listStats.get(7);
            ExoStat multdd = listStats.get(8);
            if (addnumber.getTypeReponse() == 0){
                binding.totalsousmult.append(" " + String.valueOf(multnum.getNbErreur() + multnum.getNbOk()));
                binding.correctmult.append( " " + String.valueOf(multnum.getNbOk()));
                binding.incorrectmultnombre.append(" " + String.valueOf(multnum.getNbErreur()));
                binding.Pourcentagemultnombre.append(" " + String.valueOf(multnum.getPourcentage()));
            } if (addletter.getTypeReponse() == 1){
                binding.totalmultlettre.append(" " + String.valueOf(multletter.getNbErreur() + multletter.getNbOk()));
                binding.correctmultlettre.append( " " + String.valueOf(multletter.getNbOk()));
                binding.incorrectmultlettre.append(" " + String.valueOf(multletter.getNbErreur()));
                binding.Pourcentagemultlettre.append(" " + String.valueOf(multletter.getPourcentage()));
            }  if (adddd.getTypeReponse() == 2){
                // exo drag and drop
                binding.totalmultDragandDrop.append(" " + String.valueOf(multdd.getNbErreur() + multdd.getNbOk()));
                binding.correctmultDragandDrop.append( " " + String.valueOf(multdd.getNbOk()));
                binding.incorrectmultDragandDrop.append(" " + String.valueOf(multdd.getNbErreur()));
                binding.PourcentagemultDragandDrop.append(" " + String.valueOf(multdd.getPourcentage()));
            }

            ExoStat divnum = listStats.get(9);
            ExoStat divletter = listStats.get(10);
            ExoStat divdd = listStats.get(11);
            if (addnumber.getTypeReponse() == 0){
                binding.totaldiv.append(" " + String.valueOf(divnum.getNbErreur() + divnum.getNbOk()));
                binding.correctdiv.append( " " + String.valueOf(divnum.getNbOk()));
                binding.incorrectdivnombre.append(" " + String.valueOf(divnum.getNbErreur()));
                binding.Pourcentagedivnombre.append(" " + String.valueOf(divnum.getPourcentage()));
            } if (addletter.getTypeReponse() == 1){
                binding.totaldivlettre.append(" " + String.valueOf(divletter.getNbErreur() + divletter.getNbOk()));
                binding.correctdivlettre.append( " " + String.valueOf(divletter.getNbOk()));
                binding.incorrectdivlettre.append(" " + String.valueOf(divletter.getNbErreur()));
                binding.Pourcentagedivlettre.append(" " + String.valueOf(divletter.getPourcentage()));
            }  if (adddd.getTypeReponse() == 2){
                // exo drag and drop
                binding.totaldivDragandDrop.append(" " + String.valueOf(divdd.getNbErreur() + divdd.getNbOk()));
                binding.correctdivDragandDrop.append( " " + String.valueOf(divdd.getNbOk()));
                binding.incorrectdivDragandDrop.append(" " + String.valueOf(divdd.getNbErreur()));
                binding.PourcentagedivDragandDrop.append(" " + String.valueOf(divdd.getPourcentage()));
            }
        });

        Log.d(TAG, "after call to DATABASE");

    }
}