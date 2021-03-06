package com.example.arithmos.view;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

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

        ArrayList<Integer> tableselect = getArguments().getIntegerArrayList("table");

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.customtoast,(ViewGroup)view.findViewById(R.id.toastfrag));

        final Toast toast= new Toast(getContext());
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        //TextView tv = layout.findViewById(R.id.info);
        //tv.setText();


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
            toast.show();
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