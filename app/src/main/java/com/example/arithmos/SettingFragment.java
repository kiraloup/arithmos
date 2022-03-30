package com.example.arithmos;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.arithmos.databinding.FragmentSettingBinding;
import com.google.android.material.switchmaterial.SwitchMaterial;


public class SettingFragment extends Fragment {

    private SwitchMaterial addSwitch;
    private SwitchMaterial sousSwitch;
    private SwitchMaterial multSwitch;
    private SwitchMaterial divSwitch;
    private FragmentSettingBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addSwitch = binding.SwitchAdd;
        sousSwitch = binding.switchsoustraction;
        multSwitch = binding.switchmultiplication;
        divSwitch = binding.switchdivision;

        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();

        addSwitch.setChecked(pref.getBoolean("add", true));
        sousSwitch.setChecked(pref.getBoolean("sous", true));
        multSwitch.setChecked(pref.getBoolean("mult", true));
        divSwitch.setChecked(pref.getBoolean("div", true));


        addSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pref.getBoolean("add", true)) {
                    Log.d("SettingFragment","dans if de clic add");
                    addSwitch.setChecked(false);
                    editor.remove("add");
                    editor.putBoolean("add", false);
                } else {
                    Log.d("SettingFragment","dans else de clic add");
                    addSwitch.setChecked(true);
                    editor.remove("add");
                    editor.putBoolean("add", true);
                }
                editor.apply();
            }
        });

        sousSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pref.getBoolean("sous", true)) {
                    sousSwitch.setChecked(false);
                    editor.remove("sous");
                    editor.putBoolean("sous", false);
                } else {
                    sousSwitch.setChecked(true);
                    editor.remove("sous");
                    editor.putBoolean("sous", true);
                }
                editor.apply();
            }
        });

        multSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pref.getBoolean("mult", true)) {
                    multSwitch.setChecked(false);
                    editor.remove("mult");
                    editor.putBoolean("mult", false);
                } else {
                    multSwitch.setChecked(true);
                    editor.remove("mult");
                    editor.putBoolean("mult", true);
                }
                editor.apply();
            }
        });

        divSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pref.getBoolean("div", true)) {
                    divSwitch.setChecked(false);
                    editor.remove("div");
                    editor.putBoolean("div", false);
                } else {
                    divSwitch.setChecked(true);
                    editor.remove("div");
                    editor.putBoolean("div", true);
                }
                editor.apply();
            }
        });
    }
}