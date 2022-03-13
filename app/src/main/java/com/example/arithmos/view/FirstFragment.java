package com.example.arithmos.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.arithmos.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment implements ExerciseParameterDialog.ExerciseParameterDialogListener{

    private FragmentFirstBinding binding;
    private final String TAG = "FIRSTFRAGMENT";
    final Context context = getContext();

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


        binding.ButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonOpenDialogClicked();
            }
        });
    }

    private void buttonOpenDialogClicked()  {

        DialogFragment dialog = new ExerciseParameterDialog();
        dialog.show(getChildFragmentManager(), "CustomDialog");
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //methode that is implements from the dialog
    @Override
    public void onDialogPositiveClick() {
        //TODO : get the data from the dialog
    }
}