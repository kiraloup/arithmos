package com.example.arithmos.view;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.arithmos.R;

public class ExerciseParameterDialog extends DialogFragment {


    private RadioButton radioButton_simple;

    private RadioButton radioButton_facile;
    private RadioButton radioButton_moyen;

    private RadioButton radioButton_chiffres;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        //no need to attach a parent when inflating in alertdialog
        //see : https://web.archive.org/web/20190528213406/https://possiblemobile.com/2013/05/layout-inflation-as-intended/
        View view = inflater.inflate(R.layout.activity_dial, null);

        //since the view in OnViewCreate is different we are force to declare the radio button here
        radioButton_simple = (RadioButton) view.findViewById(R.id.simple);

        radioButton_facile =  (RadioButton) view.findViewById(R.id.facile);
        radioButton_moyen =  (RadioButton) view.findViewById(R.id.moyen);

        radioButton_chiffres =  (RadioButton) view.findViewById(R.id.chiffre);

        builder.setView(view).setPositiveButton(
                "valider",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle resultParameter = new Bundle();

                resultParameter.putInt("exerciseType", checkExerciseType());
                resultParameter.putInt("exerciseSelect", checkExerciseSelect());
                resultParameter.putInt("exerciseDifficulty", checkDifficulty());

                //We send to the parent the parameter to launch the exercice
                getParentFragmentManager().setFragmentResult("exerciseParameter",
                        resultParameter);
                ExerciseParameterDialog.this.getDialog().cancel();
            }
        });

        builder.setNegativeButton("annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ExerciseParameterDialog.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    public int checkExerciseType() {
        //is the radio button check ?
        if(radioButton_simple.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }

    public int checkExerciseSelect() {
        //is the radio button check ?
        if(radioButton_chiffres.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }


    public int checkDifficulty() {

        //is the radio button check ?
        if(radioButton_facile.isChecked()) {
            return 1;
        } else if(radioButton_moyen.isChecked()) {
            return 2;
        } else {
            return 3;
        }

    }
}
