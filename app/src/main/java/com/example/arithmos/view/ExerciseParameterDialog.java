package com.example.arithmos.view;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.arithmos.R;

public class ExerciseParameterDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        //no need to attach a parent when inflating in alertdialog
        //see : https://web.archive.org/web/20190528213406/https://possiblemobile.com/2013/05/layout-inflation-as-intended/
        builder.setView(inflater.inflate(R.layout.activity_dial,null)).setPositiveButton(
                "valider",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle resultParameter = new Bundle();
                resultParameter.putString("bundleKey", "test");
                //We send to the parent the parameter to launch the exercice
                getParentFragmentManager().setFragmentResult("exerciseParameter",
                        resultParameter);
                ExerciseParameterDialog.this.getDialog().cancel();
            }
        }).setNegativeButton("annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ExerciseParameterDialog.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    //apparently it's better to write if/else
    //TODO : change switch to if/else
    public int checkExerciseType(View view) {
        //is the radio button check ?
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.chiffre:
                if (checked)
                    return 1;
                break;
            case R.id.lettres:
                if (checked)
                    return 2;
                break;
        }

        return -1;
    }

    public int checkExerciseSelect(View view) {
        //is the radio button check ?
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.simple:
                if (checked)
                    return 1;
                break;
            case R.id.difficile:
                if (checked)
                    return 2;
                break;
        }

        return -1;
    }


    public int checkDifficulty(View view) {
        //is the radio button check ?
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.facile:
                if (checked)
                    return 1;
                break;
            case R.id.moyen:
                if (checked)
                    return 2;
                break;
            case R.id.difficile:
                if (checked)
                    return 3;
                break;
        }

        return -1;
    }
}
