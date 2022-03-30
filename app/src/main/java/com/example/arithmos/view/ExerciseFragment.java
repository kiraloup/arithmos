package com.example.arithmos.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.arithmos.R;
import com.example.arithmos.databinding.FragmentSecondBinding;
import com.example.arithmos.utils.Utils;
import com.example.arithmos.view.gridview.GridViewAdapter;
import com.example.arithmos.viewmodel.ExerciceViewModel;

public class ExerciseFragment extends Fragment {

    private FragmentSecondBinding binding;
    private ExerciceViewModel exerciceViewModel;
    private final String TAG = "SECONDFRAGMENT";

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        //all the lifecycle element are handle by the viewmodel provider
        exerciceViewModel = new ViewModelProvider(this).get(ExerciceViewModel.class);
        Log.d(TAG, "HERE 1");
        binding.gridViewApple.setVisibility(View.GONE);
        return binding.getRoot();

    }

    @SuppressLint("ClickableViewAccessibility")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "HERE 2");

        assert getArguments() != null;
        //letter or number
        int exerciseType = getArguments().getInt("exerciseType");

        int exerciseDifficulty = getArguments().getInt("exerciseDifficulty");

        //simple or drag and drop
        int exerciseSelect = getArguments().getInt("exerciseSelect");

        int tableSelect = getArguments().getInt("table");
        //here we get the exercice "global" type like add, sub...
        String type = getArguments().getString("exeriseName");

        EditText input = binding.editTextTextResponse;

        Log.d(TAG, exerciseDifficulty
                + " " + exerciseType
                + " " + exerciseSelect);

        if (exerciseSelect == 1) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if(exerciseSelect == 2) {
            input.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        /*if (type == "random"){
            type = "add";
            // faire le code pour choisir le type en fonction du profil apprenant et l'exercice Type (de base standar)
            // la difficulté reste choisi par l'utilisateur
            // exercice selected (chiffre ou lettre)
        }*/

        //we create the exercise that contains the question that will be display
        exerciceViewModel.createExercice(type, exerciseDifficulty, exerciseSelect, exerciseType,tableSelect);

        //The observer job is to observe the question and change what is display on the view
        //for that we use a mutable live data in the viewmodel
        exerciceViewModel.currentQuestion.observe(getViewLifecycleOwner(), question -> {
            binding.textviewTitle.setText(question.getTitle());
            int[] arrayOfImage = exerciceViewModel.getArrayOfImages();
            int TypeOfImage = Utils.getTypeOfImages(exerciceViewModel.getImagesTypes());
            Log.d(TAG, "Type of question is : " + exerciceViewModel.getImagesTypes());

            if(TypeOfImage != -1) {
                binding.gridViewApple.setAdapter(new GridViewAdapter(getContext(),
                        arrayOfImage, TypeOfImage));
                binding.gridViewApple.setNumColumns(2);
            } else {
                Log.d(TAG, "Error when loading the type of images");
            }
        });

        //we observe a boolean that will tell us is the exercise is finish
        //if this is the case we will change to the finish screen
        exerciceViewModel.isExerciceFinish.observe(getViewLifecycleOwner(), isExerciceFinish -> {
            if(isExerciceFinish) {
                //NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment);
                NavHostFragment.findNavController(ExerciseFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        exerciceViewModel.isLoadingOK.observe(getViewLifecycleOwner(), isLoadingOk -> {
            if(!isLoadingOk) {
                Toast.makeText(getActivity(), "Error when loadinng question",
                        Toast.LENGTH_LONG).show();
            } else {
                Log.d(TAG, "Loading is ok");
            }
        });

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exerciceViewModel.checkCurrentQuestion.setValue(true);
            }
        });

        binding.helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.gridViewApple.getVisibility() == View.GONE){
                    binding.gridViewApple.setVisibility(View.VISIBLE);
                    binding.fermier.setVisibility(View.GONE);
                } else {
                    binding.gridViewApple.setVisibility(View.GONE);
                    binding.fermier.setVisibility(View.VISIBLE);
                }
            }
        });



        exerciceViewModel.checkCurrentQuestion.observe(getViewLifecycleOwner(), checkQuestion -> {
            if(checkQuestion) {
                String rep = binding.editTextTextResponse.getText().toString().toLowerCase().replaceAll("\\s+$", "").
                        replaceAll("\\s+", " ").
                        replaceAll("-", " ");

                if (!rep.isEmpty()) {
                    String correctResponse =  exerciceViewModel.getResultOfQuestion();

                    if( exerciceViewModel.checkResponse(rep, correctResponse) ) {
                        Log.d(TAG, "Response is wrong ");
                        //this is use to display that the toast message
                        exerciceViewModel.updateNumberOfError();
                    }
                    //the observe variable is put to false before switching question
                    //otherwise the question will be check for it's result
                    // before the user can enter the response
                    exerciceViewModel.checkCurrentQuestion.setValue(false);
                    binding.editTextTextResponse.setText("");
                    showResponseDialog();
                } else {
                    Log.d(TAG, "user response is empty");
                    Toast.makeText(getActivity(), "Une réponse ne peut pas etre vide !",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void showResponseDialog() {
        //we open the dialog here
        DialogAnswerFragment dialogAnswerFragment = new DialogAnswerFragment();

        dialogAnswerFragment.show(getChildFragmentManager(), "ExerciseFragment");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}