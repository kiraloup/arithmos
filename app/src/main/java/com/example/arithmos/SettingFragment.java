package com.example.arithmos;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.example.arithmos.databinding.FragmentSettingBinding;
import com.google.android.material.switchmaterial.SwitchMaterial;


public class SettingFragment extends Fragment {

    private SwitchMaterial addSwitch;
    private SwitchMaterial sousSwitch;
    private SwitchMaterial multSwitch;
    private SwitchMaterial divSwitch;
    private FragmentSettingBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
        if (pref.getBoolean("add", true)) {
            addSwitch.setChecked(true);
        } else {
            addSwitch.setChecked(false);
        }
        if (pref.getBoolean("sous", true)) {
            sousSwitch.setChecked(true);
        } else {
            sousSwitch.setChecked(false);
        }
        if (pref.getBoolean("mult", true)) {
            Log.d("val","dans le if mult");
            multSwitch.setChecked(true);
        } else {
            Log.d("val","dans le else mult");
            multSwitch.setChecked(false);
        }
        if (pref.getBoolean("div", true)) {
            divSwitch.setChecked(true);
        } else {
            divSwitch.setChecked(false);
        }

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