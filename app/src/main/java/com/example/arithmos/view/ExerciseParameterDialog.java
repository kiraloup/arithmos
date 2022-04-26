package com.example.arithmos.view;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.arithmos.R;

import java.util.ArrayList;

public class ExerciseParameterDialog extends DialogFragment {


    private RadioButton radioButton_simple;

    private RadioButton radioButton_facile;
    private RadioButton radioButton_moyen;
    private RadioButton radioButton_difficile;

    private RadioButton radioButton_chiffres;

    private CheckBox radioButton_1;
    private CheckBox radioButton_2;
    private CheckBox radioButton_3;
    private CheckBox radioButton_4;
    private CheckBox radioButton_5;
    private CheckBox radioButton_6;
    private CheckBox radioButton_7;
    private CheckBox radioButton_8;
    private CheckBox radioButton_9;

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

        radioButton_1 = (CheckBox) view.findViewById(R.id.r1);
        radioButton_2 = (CheckBox) view.findViewById(R.id.r2);
        radioButton_3 = (CheckBox) view.findViewById(R.id.r3);
        radioButton_4 = (CheckBox) view.findViewById(R.id.r4);
        radioButton_5 = (CheckBox) view.findViewById(R.id.r5);
        radioButton_6 = (CheckBox) view.findViewById(R.id.r6);
        radioButton_7 = (CheckBox) view.findViewById(R.id.r7);
        radioButton_8 = (CheckBox) view.findViewById(R.id.r8);
        radioButton_9 = (CheckBox) view.findViewById(R.id.r9);


        builder.setView(view).setPositiveButton(
                "valider",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle resultParameter = new Bundle();

                resultParameter.putInt("exerciseType", checkExerciseType());
                resultParameter.putInt("exerciseSelect", checkExerciseSelect());
                resultParameter.putInt("exerciseDifficulty", checkDifficulty());
                resultParameter.putIntegerArrayList("table", checkTable());

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

    public ArrayList<Integer> checkTable() {
        //is the radio button check ?
        ArrayList<Integer> list = new ArrayList<Integer>();
        if (radioButton_1.isChecked() && radioButton_2.isChecked() && radioButton_3.isChecked() && radioButton_4.isChecked()
                && radioButton_5.isChecked() && radioButton_6.isChecked() && radioButton_7.isChecked() && radioButton_8.isChecked()
                && radioButton_9.isChecked()) {
            list.add(0);
        } else {
            if (radioButton_1.isChecked()) {
                list.add(1);
            }
            if (radioButton_2.isChecked()) {
                list.add(2);
            }
            if (radioButton_3.isChecked()) {
                list.add(3);
            }
            if (radioButton_4.isChecked()) {
                list.add(4);
            }
            if (radioButton_5.isChecked()) {
                list.add(5);
            }
            if (radioButton_6.isChecked()) {
                list.add(6);
            }
            if (radioButton_7.isChecked()) {
                list.add(7);
            }
            if (radioButton_8.isChecked()) {
                list.add(8);
            }
            if (radioButton_9.isChecked()) {
                list.add(9);
        }
    }
        return list;
    }
}
