package com.example.arithmos.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;

import com.example.arithmos.R;
import com.example.arithmos.databinding.FragmentFirstBinding;

public class MainMenuFragment extends Fragment{

    private FragmentFirstBinding binding;
    private final String TAG = "FIRSTFRAGMENT";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "HERE 1");
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0);
        binding.ButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pref.getBoolean("add", true)) {
                    buttonOpenDialogClicked("add");
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.customtoast,(ViewGroup)view.findViewById(R.id.toastfrag));

                    final Toast toast= new Toast(getContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    TextView tv = layout.findViewById(R.id.info);
                    tv.setText("Vous avez désactivé l'addition dans les paramètres");
                    toast.show();
                }

            }
        });
        binding.ButtonSous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pref.getBoolean("sous", true)) {
                    buttonOpenDialogClicked("sous");
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.customtoast,(ViewGroup)view.findViewById(R.id.toastfrag));

                    final Toast toast= new Toast(getContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    TextView tv = layout.findViewById(R.id.info);
                    tv.setText("Vous avez désactivé la soustraction dans les paramètres");
                    toast.show();
                }
            }
        });
        binding.ButtonDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pref.getBoolean("div", true)) {
                    buttonOpenDialogClicked("div");
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.customtoast,(ViewGroup)view.findViewById(R.id.toastfrag));

                    final Toast toast= new Toast(getContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    TextView tv = layout.findViewById(R.id.info);
                    tv.setText("Vous avez désactivé la division dans les paramètres");
                    toast.show();
                }
            }
        });
        binding.ButtonMult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pref.getBoolean("mult", true)) {
                    buttonOpenDialogClicked("mult");
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.customtoast,(ViewGroup)view.findViewById(R.id.toastfrag));

                    final Toast toast= new Toast(getContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    TextView tv = layout.findViewById(R.id.info);
                    tv.setText("Vous avez désactivé la multiplication dans les paramètres");
                    toast.show();
                }
            }
        });
        binding.ButtonParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainMenuFragment.this).
                        navigate(R.id.action_FirstFragment_to_settingFragment);
            }
        });


        binding.ButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainMenuFragment.this).
                        navigate(R.id.action_FirstFragment_to_stat_Fragment);
            }
        });

        binding.ButtonRand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pref.getBoolean("mult", true) || pref.getBoolean("add", true) ||pref.getBoolean("div", true) ||pref.getBoolean("sous", true)) {
                    buttonOpenDialogClicked("random");
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.customtoast,(ViewGroup)view.findViewById(R.id.toastfrag));

                    final Toast toast= new Toast(getContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    TextView tv = layout.findViewById(R.id.info);
                    tv.setText("Il faut réactiver des exercices dans les paramètres !");
                    toast.show();

                }

            }
        });

    }

    private void buttonOpenDialogClicked(String exeriseName)  {
        DialogFragment dialog = null;

        if (!exeriseName.equals("random")){
            dialog = new ExerciseParameterDialog(exeriseName);
            //using a result listener to pass data from dialog fragment to this one
            //the dialog is a child of this fragment so we must carefull to use  getChildFragmentManager
            //otherwise this is the same when we receive the result
            //see : https://developer.android.com/guide/fragments/communicate#pass-parent-child
            getChildFragmentManager().setFragmentResultListener("exerciseParameter",
                    this,
                    new FragmentResultListener() {
                        @Override
                        public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                            //letter or number
                            int exerciseType = bundle.getInt("exerciseType");
                            int exerciseDifficulty = bundle.getInt("exerciseDifficulty");
                            //simple or drag and drop
                            int exerciseSelect = bundle.getInt("exerciseSelect");

                            int tableSelect = bundle.getInt("table");
                            //here we add if the exercice "global" type like add, sub...
                            bundle.putString("exeriseName", exeriseName);

                            if(!bundle.isEmpty() && (exerciseType != 0 && exerciseDifficulty != 0 &&
                                    exerciseSelect != 0)) {
                                //here we start the activity
                                Log.d(TAG, exerciseSelect
                                        + " " + exerciseType
                                        + " " + exerciseDifficulty);

                                if (exerciseType == 1) {
                                    NavHostFragment.findNavController(MainMenuFragment.this).
                                            navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
                                } else {
                                    NavHostFragment.findNavController(MainMenuFragment.this)
                                            .navigate(R.id.action_FirstFragment_to_dragAndDropFragment, bundle);
                                }
                            } else {
                                Log.d(TAG, "Error: in the result of the dialog return -1");
                            }
                        }
                    });
        } else {
            dialog = new DialogueDifficulty();
            getChildFragmentManager().setFragmentResultListener("exerciseParameter",
                    this,
                    new FragmentResultListener() {
                        @Override
                        public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                            int exerciseDifficulty = bundle.getInt("exerciseDifficulty");
                            bundle.putString("exeriseName", exeriseName);
                            NavHostFragment.findNavController(MainMenuFragment.this).
                                    navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
                        }
                    });
        }

        dialog.show(getChildFragmentManager(), "CustomDialog");
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}