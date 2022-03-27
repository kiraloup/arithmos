package com.example.arithmos.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arithmos.R;
import com.example.arithmos.databinding.FragmentDialogAnswerBinding;
import com.example.arithmos.model.TypeOfExercice;
import com.example.arithmos.utils.Utils;
import com.example.arithmos.viewmodel.ExerciceViewModel;


public class DialogAnswerFragment extends DialogFragment {

    private ExerciceViewModel exerciceViewModel;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_dialog_answer, null);

        exerciceViewModel = new ViewModelProvider(requireParentFragment())
                .get(ExerciceViewModel.class);

        builder.setView(view).setPositiveButton("suivant", (dialog, which) -> {
            //we change the question anyway
            exerciceViewModel.changeQuestion();

            this.getDialog().dismiss();
        });

        setupObserver(view);

        return builder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    public void setupObserver(View view) {
        /*
        * Note: When subscribing to lifecycle-aware components such as LiveData,
        * you should never use viewLifecycleOwner as the LifecycleOwner in a DialogFragment
        * that uses Dialogs.
        * Instead, use the DialogFragment itself.
        */
        exerciceViewModel.isResponseCorrect.observe(this, aBoolean -> {
            //pass the view as a parameter because this.getView doesn't return
            //the builder view but the fragment which is null
            ImageView imageView = view.findViewById(R.id.imageRep);
            TextView textView = view.findViewById(R.id.TextViewDialogResponse);
            if (aBoolean) {
                textView.setText("Bravo ! Tu as la bonne réponse !");
                imageView.setImageResource(R.drawable.good_rep);
            } else {
                if(exerciceViewModel.getExercice().getTypeOfExercice() == TypeOfExercice.NUMBER) {
                    textView.append(" " + exerciceViewModel.getResultOfQuestion());
                } else {
                    int val = Integer.parseInt(exerciceViewModel.getResultOfQuestion());
                    textView.append(" " + Utils.convertIntToStringMillier(val));
                }

                imageView.setImageResource(R.drawable.bad_rep);
            }
        });
    }
}