package com.example.arithmos.view;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.DragAndDropPermissions;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.arithmos.R;
import com.example.arithmos.databinding.FragmentDragAndDropBinding;
import com.example.arithmos.viewmodel.ExerciceViewModel;

public class DragAndDropFragment extends Fragment {

    private FragmentDragAndDropBinding binding;
    private ExerciceViewModel exerciceViewModel;
    private final String TAG = "DRAGANDDROPFRAGMENT";
    private static final String IMAGEVIEW = "Apple Image";



    public DragAndDropFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imageViewSource.setTag(IMAGEVIEW);

        binding.imageViewSource.setOnLongClickListener(v -> {
            ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
            ClipData dragData = new ClipData(getTag(),
                    new String[] {ClipDescription.MIMETYPE_TEXT_PLAIN}, item);

            View.DragShadowBuilder shadow = new View.DragShadowBuilder(binding.imageViewSource);

            v.startDragAndDrop(dragData, shadow, null, 0);
            return true;

        });

        binding.imageViewSource.setOnDragListener((v, e) -> {
            switch (e.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:

                    // Determines if this View can accept the dragged data.
                    if (e.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                        // As an example of what your application might do, applies a blue color tint
                        // to the View to indicate that it can accept data.
                        ((ImageView)v).setColorFilter(Color.BLUE);

                        // Invalidate the view to force a redraw in the new tint.
                        v.invalidate();

                        // Returns true to indicate that the View can accept the dragged data.
                        return true;

                    }

                    // Returns false to indicate that, during the current drag and drop operation,
                    // this View will not receive events again until ACTION_DRAG_ENDED is sent.
                    return false;
                case DragEvent.ACTION_DRAG_ENDED:

                    ((ImageView)v).clearColorFilter();

                    // Invalidates the view to force a redraw.
                    v.invalidate();

                    // Does a getResult(), and displays what happened.
                    if (e.getResult()) {
                        Toast.makeText(getActivity(),
                                "The drop was handled.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(),
                                "The drop didn't work.", Toast.LENGTH_LONG).show();
                    }

                    // Returns true; the value is ignored.
                    return true;
                default:
                    Log.e("DragDrop Example","Unknown action type: " +  e.getAction() +
                            " received by View.OnDragListener.");
                    break;
            }

            return false;
        });

        binding.constraintLayoutTarget.setOnDragListener((v,e) -> {
            switch (e.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    ((ConstraintLayout)v).setBackgroundColor(Color.GREEN);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    ((ConstraintLayout)v).setBackgroundColor(Color.WHITE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DROP :
                    Log.e("DragDrop Example","DRAG DROP constraintLayoutTarget !!!");

                    ImageView imageView = binding.imageViewSource;

                    ViewGroup parent = (ViewGroup) binding.imageViewSource.getParent();

                    if(parent != null) {
                        parent.removeView(imageView);
                    }

                    ((ConstraintLayout)v).addView(imageView);

                    v.invalidate();

                    return true;
                default:
                    Log.e("DragDrop Example","Unknown action type :" +  e.getAction() +
                            " received by View.OnDragListener.");
                    break;
            }
            return false;
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDragAndDropBinding.inflate(inflater, container, false);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}