package com.example.arithmos.view;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.arithmos.R;

public class ExerciseParameterDialog extends DialogFragment {

    public Context context;


    private RadioGroup radio_type;
    private RadioButton radioButton_simple;
    private RadioButton radioButton_dd;

    private RadioGroup radio_diff;
    private RadioButton radioButton_facile;
    private RadioButton radioButton_moyen;
    private RadioButton radioButton_difficile;

    private TextView text_format;

    private RadioGroup radio_rep ;
    private RadioButton radioButton_chiffres;
    private RadioButton radioButton_lettres;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_dial,null)).setPositiveButton(
                "valider",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ExerciseParameterDialog.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        radioButton_simple = view.findViewById(R.id.simple);
        radioButton_dd = view.findViewById(R.id.draganddrop);


        radioButton_facile = (RadioButton) view.findViewById(R.id.facile);
        radioButton_moyen = (RadioButton) view.findViewById(R.id.moyen);
        radioButton_difficile = (RadioButton) view.findViewById(R.id.difficile);

        radioButton_chiffres = (RadioButton) view.findViewById(R.id.chiffre);
        radioButton_lettres = (RadioButton) view.findViewById(R.id.lettres);
    }
}
