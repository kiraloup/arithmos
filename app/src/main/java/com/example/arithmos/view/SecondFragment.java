package com.example.arithmos.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.arithmos.databinding.FragmentSecondBinding;
import com.example.arithmos.viewmodel.ExerciceViewModel;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private ExerciceViewModel repository;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);

        repository = new ViewModelProvider(this).get(ExerciceViewModel.class);
        //we create the exercice that contains the question that will be display
        repository.createExercice("addition");

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //The observer job is to observe the question and change what is display on the view
        //for that we use a mutable live data in the viewmodel
        repository.getQuestion().observe(getViewLifecycleOwner(), question -> {
            binding.textviewTitle.setText(question.getTitle());
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}