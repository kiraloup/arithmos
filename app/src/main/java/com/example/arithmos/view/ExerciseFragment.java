package com.example.arithmos.view;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.arithmos.R;
import com.example.arithmos.databinding.FragmentSecondBinding;
import com.example.arithmos.viewmodel.ExerciceViewModel;

public class ExerciseFragment extends Fragment {

    private FragmentSecondBinding binding;
    private ExerciceViewModel exerciceViewModel;
    private final String TAG = "SECONDFRAGMENT";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        //all the lifecycle element are handle by the viewmodel provider
        exerciceViewModel = new ViewModelProvider(this).get(ExerciceViewModel.class);
        Log.d(TAG, "HERE 1");
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "HERE 2");

        assert getArguments() != null;
        //letter or number
        int exerciseType = getArguments().getInt("exerciseType");

        int exerciseDifficulty = getArguments().getInt("exerciseSelect");

        //simple or drag and drop
        int exerciseSelect = getArguments().getInt("exerciseDifficulty");

        //here we get the exercice "global" type like add, sub...
        String type = getArguments().getString("exeriseName");

        EditText input = binding.editTextTextResponse;

        if (exerciseType == 1) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            input.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        //we create the exercise that contains the question that will be display
        exerciceViewModel.createExercice(type, exerciseDifficulty, exerciseSelect, exerciseType);

        //The observer job is to observe the question and change what is display on the view
        //for that we use a mutable live data in the viewmodel
        exerciceViewModel.currentQuestion.observe(getViewLifecycleOwner(), question -> {
            binding.textviewTitle.setText(question.getTitle());
        });

        //we observe a boolean that will tell us is the exercice is finish
        //if this is the case we will change to the finish screen
        exerciceViewModel.isExerciceFinish.observe(getViewLifecycleOwner(), isExerciceFinish -> {
            if(isExerciceFinish) {
                //NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment);
                NavHostFragment.findNavController(ExerciseFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( exerciceViewModel.checkResponse(binding.editTextTextResponse.getText().toString()) ) {

                    //check if exercice is finish
                    //otherwise we display next quest
                    if(!exerciceViewModel.isExerciceFinish()){
                        Log.d(TAG,"Exercice is not finish");
                        //we display the next question, the observer will update the UI
                        exerciceViewModel.nextQuestion();
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}