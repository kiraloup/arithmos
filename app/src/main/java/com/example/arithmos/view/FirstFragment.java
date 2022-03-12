package com.example.arithmos.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import com.example.arithmos.R;
import com.example.arithmos.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

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
                CustomDialog.FullNameListener listener = new CustomDialog.FullNameListener() {
                    @Override
                    public void fullNameEntered(String fullName) {
                    }
                };
                final CustomDialog dialog = new CustomDialog(this.getContext(),"add",listener);
                dialog.show();
            }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}