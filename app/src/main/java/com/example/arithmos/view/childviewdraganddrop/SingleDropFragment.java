package com.example.arithmos.view.childviewdraganddrop;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.arithmos.databinding.FragmentSingleDropBinding;
import com.example.arithmos.model.GridItem;
import com.example.arithmos.view.gridview.DragGridView;
import com.example.arithmos.view.gridview.GridItemHolder;
import com.example.arithmos.viewmodel.DragAndDropViewModel;
import com.example.arithmos.viewmodel.ExerciceViewModel;

import java.util.ArrayList;
import java.util.List;

public class SingleDropFragment extends Fragment {

    private FragmentSingleDropBinding binding;
    private GridItem gridItem;
    private List<Integer> allDropValue = new ArrayList<>();
    private GridView gridViewDropZone;

    private ExerciceViewModel exerciceViewModel;
    private DragAndDropViewModel dragAndDropViewModel;


    public SingleDropFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSingleDropBinding.inflate(inflater, container, false);

        exerciceViewModel = new ViewModelProvider(requireParentFragment())
                .get(ExerciceViewModel.class);

        dragAndDropViewModel = new ViewModelProvider(requireParentFragment()).
                get(DragAndDropViewModel.class);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //we need to do that since the button is not in child view but the parent
        dragAndDropViewModel.isButtonClick.observe(getViewLifecycleOwner(), isButtonClick -> {
            Log.d("SingleDropFragment", "button is click");
            if(isButtonClick) {
                isAnswerCorrect(allDropValue);
            }
        });

        binding.gridViewDragAndDrop.setNumColumns(2);
        binding.gridViewDragAndDrop.setAdapter(new DragGridView(getContext(), null));

        gridViewDropZone = binding.gridViewDropZone;
        gridViewDropZone.setAdapter(new DragGridView(getContext()));
        gridViewDropZone.setNumColumns(2);

        binding.constraintLayoutDropZone.setOnDragListener((v,e) -> {
            switch (e.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    ((ConstraintLayout)v).setBackgroundColor(Color.TRANSPARENT);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DROP :
                    Log.e("SingleDropFragment","DRAG DROP constraintLayoutTarget !!!");

                    ClipData.Item item = e.getClipData().getItemAt(0);

                    Log.d("SingleDropFragment", "URI " + item.getUri());
                    DragGridView adapter = (DragGridView) gridViewDropZone.getAdapter();
                    //Here we update the drop zone gridview to add the items that was drop
                    if (adapter.newItems(gridItem)) {
                        Log.d("SingleDropFragment",
                                "New items successfully add to the grid layout");
                        //the views are rebuild and redraws
                        //if I don't do that the new apple display in grid view is not the right one
                        //but the first one that was add
                        gridViewDropZone.invalidateViews();
                        gridViewDropZone.setAdapter(adapter);
                    }

                    allDropValue.add(gridItem.getValue());

                    int res = 0;
                    for(int i = 0; i < allDropValue.size(); i++) {
                        res += allDropValue.get(i);
                    }

                    Log.d("SingleDropFragment", "current res " +  res);

                    v.invalidate();

                    return true;
                default:
                    Log.e("SingleDropFragment","constraintLayoutDropZone Unknown action type :"
                            +  e.getAction() +
                            " received by View.OnDragListener.");
                    break;
            }
            return false;
        });

        binding.gridViewDragAndDrop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    GridView parent = (GridView) v;

                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    int position = parent.pointToPosition(x, y);

                    int relativePosition = position - parent.getFirstVisiblePosition();

                    View target = (View) parent.getChildAt(relativePosition);
                    if(target != null) {
                        GridItemHolder gridItemHolder = (GridItemHolder) target.getTag();

                        gridItem = (GridItem) gridItemHolder.item;

                        Log.d("SingleDropFragment",
                                " item = " + gridItem.getValue());


                        ClipData.Item item = new ClipData.Item((CharSequence) gridItemHolder.tag);

                        ClipData dragData = new ClipData(getTag(),
                                new String[] {ClipDescription.MIMETYPE_TEXT_PLAIN}, item);

                        View.DragShadowBuilder shadow = new View.DragShadowBuilder(target);

                        v.startDragAndDrop(dragData, shadow, gridItemHolder.item, 0);

                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void isAnswerCorrect(List<Integer> allDropValue) {
        int res = 0;
        for(int i = 0; i < allDropValue.size(); i++) {
            res += allDropValue.get(i);
        }
        Log.d("SingleDropFragment",
                " res = " + res);

        String correctResponse =  exerciceViewModel.getResultOfQuestion();

        if(!exerciceViewModel.checkResponse(String.valueOf(res), correctResponse) ) {
            Log.d("SingleDropFragment", "Response is wrong ");
            exerciceViewModel.updateNumberOfError();
        }
        //all case we change the question
        changeQuestion();
    }



    private void changeQuestion() {
        if(exerciceViewModel.isExerciseFinish()){
            Log.d("SingleDropFragment","Exercise is not finish");
            //we display the next question, the observer will update the UI
            exerciceViewModel.nextQuestion();
        }
    }
}