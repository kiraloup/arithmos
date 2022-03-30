package com.example.arithmos.view.childviewdraganddrop;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.Toast;

import com.example.arithmos.databinding.FragmentSingleDropBinding;
import com.example.arithmos.model.GridItem;
import com.example.arithmos.utils.Utils;
import com.example.arithmos.view.DialogAnswerFragment;
import com.example.arithmos.view.gridview.DragGridView;
import com.example.arithmos.view.gridview.GridItemHolder;
import com.example.arithmos.viewmodel.DragAndDropViewModel;
import com.example.arithmos.viewmodel.ExerciceViewModel;

import java.util.ArrayList;
import java.util.List;

public class SingleDropFragment extends Fragment {

    private FragmentSingleDropBinding binding;
    private GridItem gridItem;
    private GridView gridViewDropZone;
    private ExerciceViewModel exerciceViewModel;

    private DragGridView dragGridViewDropZone;


    public SingleDropFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSingleDropBinding.inflate(inflater, container, false);

        exerciceViewModel = new ViewModelProvider(requireParentFragment())
                .get(ExerciceViewModel.class);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int idImgTypes = Utils.getTypeOfImage(exerciceViewModel.getImagesTypes());
        int idImgTypesBackground = Utils.getIdImageBackground(exerciceViewModel.getImagesTypes());
        Log.d("SingleDropFragment",  "idImgTypes " + exerciceViewModel.getImagesTypes());
        Log.d("SingleDropFragment",  "idImgTypes " + idImgTypes);
        Log.d("SingleDropFragment",  "idImgTypesBackground" + idImgTypesBackground);

        if(idImgTypes != -1 && idImgTypesBackground != -1) {

            createDropZone(idImgTypes, idImgTypesBackground);

            createDragZone(idImgTypes);

            //we need to do that since the button is not in child view but the parent
            exerciceViewModel.checkCurrentQuestion.observe(getViewLifecycleOwner(), checkQuestion -> {

                List<GridItem> listItems = dragGridViewDropZone.getListItems();
                if (listItems.size() != 0) {
                    if(checkQuestion) {
                        int rep = 0;
                        for(int i = 0; i < listItems.size(); i++) {
                            rep += listItems.get(i).getValue();
                        }

                        String correctResponse =  exerciceViewModel.getResultOfQuestion();

                        if( !exerciceViewModel.checkResponse(String.valueOf(rep), correctResponse) ) {
                            Log.d("SingleDropFragment", "Response is wrong ");
                            //this is use to display that the toast message
                            exerciceViewModel.updateNumberOfError();
                        }
                        //the observe variable is put to false before switching question
                        //otherwise the question will be check before the user can enter the response
                        exerciceViewModel.checkCurrentQuestion.setValue(false);

                        showResponseDialog();
                    }
                } else {
                    Log.d("SingleDropFragment", "The dropzone is empty");
                    Toast.makeText(getActivity(), "Attention ! Tu dois aider le fermier !",
                            Toast.LENGTH_LONG).show();
                }

            });
        } else {
            Toast.makeText(getActivity(), "Erreur au chargment des background",
                    Toast.LENGTH_SHORT).show();
            Log.d("SingleDropFragment",  "Erreur when loading image types");
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void createDropZone(int typeOfImage, int idImgTypesBackground) {
        gridViewDropZone = binding.gridViewDropZone;

        dragGridViewDropZone = new DragGridView(getContext());
        dragGridViewDropZone.setTypeOfImage(typeOfImage);

        gridViewDropZone.setAdapter(dragGridViewDropZone);

        gridViewDropZone.setNumColumns(2);

        //change background base on the type of object
        Drawable backgroundImage = ContextCompat.getDrawable(getContext(), idImgTypesBackground);
        binding.gridViewDropZone.setBackground(backgroundImage);

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

                    //Here we update the drop zone gridview to add the items that was drop
                    if (dragGridViewDropZone.newItems(gridItem)) {
                        Log.d("SingleDropFragment",
                                "New items successfully add to the grid layout");
                        //the views are rebuild and redraws
                        //if I don't do that the new apple display in grid view is not the right one
                        //but the first one that was add
                        gridViewDropZone.invalidateViews();
                        gridViewDropZone.setAdapter(dragGridViewDropZone);
                    }

                    int res = 0;
                    for(int i = 0; i < dragGridViewDropZone.getListItems().size(); i++) {
                        res += dragGridViewDropZone.getListItems().get(i).getValue();
                    }

                    Log.d("SingleDropFragment", "current res " +  res);

                    v.invalidate();

                    return true;
                case DragEvent.ACTION_DRAG_EXITED:

                    //Here we update the drop zone gridview to add the items that was drop
                    if (dragGridViewDropZone.removeItems(gridItem)) {
                        Log.d("SingleDropFragment",
                                "New items successfully remove to the grid layout");
                        //the views are rebuild and redraws
                        //if I don't do that the new images display in grid view is not the right one
                        //but the first one that was add
                        gridViewDropZone.invalidateViews();
                        gridViewDropZone.setAdapter(dragGridViewDropZone);
                    }

                    /*if(allDropValue.remove(gridItem.getValue()) == gridItem.getValue()) {
                         Log.d("SingleDropFragment",
                            "The element was remove from the list of drop value");
                    }*/
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

        binding.gridViewDropZone.setOnTouchListener((v, event) -> {
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
        });


    }

    @SuppressLint("ClickableViewAccessibility")
    private void createDragZone(int idImgTypes) {
        binding.gridViewDragAndDrop.setNumColumns(2);

        binding.gridViewDragAndDrop.setAdapter(new DragGridView(getContext(), idImgTypes));

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

    private void showResponseDialog() {
        //we open the dialog here
        DialogAnswerFragment dialogAnswerFragment = new DialogAnswerFragment();
        Log.d("SingleDropFragment", getParentFragmentManager().toString());
        //the parent fragment get 2 child otherwise the viewmodel is null
        //pretty cool
        dialogAnswerFragment.show(getParentFragmentManager(), "ExerciseFragment");

    }
}