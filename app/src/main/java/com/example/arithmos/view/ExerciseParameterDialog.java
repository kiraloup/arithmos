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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.arithmos.R;

public class ExerciseParameterDialog extends DialogFragment {


    private RadioButton radioButton_simple;

    private RadioButton radioButton_facile;
    private RadioButton radioButton_moyen;
    private RadioButton radioButton_difficile;

    private RadioButton radioButton_chiffres;

    private RadioButton radioButton_toutes_tables;
    private RadioButton radioButton_1;
    private RadioButton radioButton_2;
    private RadioButton radioButton_3;
    private RadioButton radioButton_4;
    private RadioButton radioButton_5;
    private RadioButton radioButton_6;
    private RadioButton radioButton_7;
    private RadioButton radioButton_8;

    private final String exericeName;

    public ExerciseParameterDialog(String e) {
        this.exericeName = e;
    }

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
        radioButton_difficile = (RadioButton) view.findViewById(R.id.difficile);

        radioButton_chiffres =  (RadioButton) view.findViewById(R.id.chiffre);

        RadioGroup radioGroup_table = (RadioGroup) view.findViewById(R.id.rg_table);
        TextView textView_table = (TextView) view.findViewById(R.id.choixTable);
        if (!(exericeName.equals("add") ||exericeName.equals("mult"))){
            radioGroup_table.setVisibility(View.GONE);
            textView_table.setVisibility(View.GONE);
        }

        if ((exericeName.equals("div") || exericeName.equals("mult"))){
            radioButton_facile.setText("Facile (chiffres)");
            radioButton_moyen.setText("Moyen (nombre < 100)");
            radioButton_difficile.setText("Difficile (nombres < 1000)");
        }

        radioButton_toutes_tables = (RadioButton) view.findViewById(R.id.rr);
        radioButton_1 = (RadioButton) view.findViewById(R.id.r1);
        radioButton_2 = (RadioButton) view.findViewById(R.id.r2);
        radioButton_3 = (RadioButton) view.findViewById(R.id.r3);
        radioButton_4 = (RadioButton) view.findViewById(R.id.r4);
        radioButton_5 = (RadioButton) view.findViewById(R.id.r5);
        radioButton_6 = (RadioButton) view.findViewById(R.id.r6);
        radioButton_7 = (RadioButton) view.findViewById(R.id.r7);
        radioButton_8 = (RadioButton) view.findViewById(R.id.r8);


        builder.setView(view).setPositiveButton(
                "valider",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle resultParameter = new Bundle();

                resultParameter.putInt("exerciseType", checkExerciseType());
                resultParameter.putInt("exerciseSelect", checkExerciseSelect());
                resultParameter.putInt("exerciseDifficulty", checkDifficulty());
                resultParameter.putInt("table", checkTable());

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

    public int checkTable() {
        //is the radio button check ?
        if(radioButton_toutes_tables.isChecked()) {
            return 0;
        } else if (radioButton_1.isChecked()) {
            return 1;
        } else if (radioButton_2.isChecked()) {
            return 2;
        } else if (radioButton_3.isChecked()) {
            return 3;
        } else if (radioButton_4.isChecked()) {
            return 4;
        } else if (radioButton_5.isChecked()) {
            return 5;
        } else if (radioButton_6.isChecked()) {
            return 6;
        } else if (radioButton_7.isChecked()) {
            return 7;
        }else if (radioButton_8.isChecked()) {
            return 8;
        } else {
            return 9;
        }
    }
}
