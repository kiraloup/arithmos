package com.example.arithmos.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.arithmos.R;
import com.example.arithmos.databinding.FragmentDragAndDropBinding;
import com.example.arithmos.view.childviewdraganddrop.SingleDropFragment;
import com.example.arithmos.viewmodel.DragAndDropViewModel;
import com.example.arithmos.viewmodel.ExerciceViewModel;

public class DragAndDropFragment extends Fragment {

    private FragmentDragAndDropBinding binding;

    private ExerciceViewModel exerciceViewModel;
    private DragAndDropViewModel dragAndDropViewModel;

    private final String TAG = "DRAGANDDROPFRAGMENT";
    private static final String IMAGEVIEW = "Apple Image";



    public DragAndDropFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assert getArguments() != null;
        //letter or number
        int exerciseType = getArguments().getInt("exerciseType");

        int exerciseDifficulty = getArguments().getInt("exerciseDifficulty");

        //simple or drag and drop
        int exerciseSelect = getArguments().getInt("exerciseSelect");

        int tableselect = getArguments().getInt("table");

        //here we get the exercice "global" type like add, sub...
        String type = getArguments().getString("exeriseName");

        Log.d(TAG, exerciseDifficulty
                + " " + exerciseType
                + " " + exerciseSelect);

        binding.buttonSecond.setOnClickListener(v -> {
            Log.d(TAG, "button click");

            if(exerciceViewModel.currentQuestion != null) {
                Log.d(TAG, "current question is not null");
                exerciceViewModel.checkCurrentQuestion.postValue(true);
            }
        });

        exerciceViewModel.createExercice(type, exerciseDifficulty, exerciseSelect, exerciseType, tableselect);

        exerciceViewModel.currentQuestion.observe(getViewLifecycleOwner(), question -> {
            binding.textviewTitle.setText(question.getTitle());

            SingleDropFragment childFragment = new SingleDropFragment();

            FragmentTransaction fragmentTransaction = getChildFragmentManager().
                    beginTransaction();

            fragmentTransaction.replace(binding.childFragmentContainerDragAndDrop.getId(),
                    childFragment).commit();
        });

        exerciceViewModel.isExerciceFinish.observe(getViewLifecycleOwner(), isExerciseFinish -> {
            Log.d(TAG, "observer : isExerciceFinish");
            if(isExerciseFinish) {
                Log.d(TAG, "observer : exercice is finish");
                NavHostFragment.findNavController(DragAndDropFragment.this)
                        .navigate(R.id.action_dragAndDropFragment_to_FirstFragment);
            }
            Log.d(TAG, "observer : exercice is not finish");
        });

        binding.helpButton2.setOnClickListener(v -> {
            Log.d(TAG, "help is click");
            Toast.makeText(getActivity(), "Pour Réussir l'exercice, il faut déplacer ce que le fermier te demande dans le panier",
                    Toast.LENGTH_SHORT).show();
        });


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDragAndDropBinding.inflate(inflater, container, false);

        exerciceViewModel = new ViewModelProvider(this).get(ExerciceViewModel.class);
        dragAndDropViewModel = new ViewModelProvider(this).get(DragAndDropViewModel.class);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}