package com.example.arithmos.view.gridview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.example.arithmos.R;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    private final Context context;
    //this array contains all the id
    public ArrayList<GridItemClickOnlyHolder> imageID = new ArrayList<>();

    public int idImageBackground;

    public GridViewAdapter(Context context, int[] listOfApple, int typeOfImage) {
        this.context = context;
        for(int i = 0; i < listOfApple.length; i++) {
            for(int j = 0; j < listOfApple[i]; j++) {
                switch (i) {
                    case 0:
                        imageID.add(new GridItemClickOnlyHolder(R.drawable.mille, false));
                        break;
                    case 1:
                        imageID.add(new GridItemClickOnlyHolder(R.drawable.cents, false));
                        break;
                    case 2:
                        imageID.add(new GridItemClickOnlyHolder(R.drawable.cinquante, false));
                        break;
                    case 3:
                        imageID.add(new GridItemClickOnlyHolder(R.drawable.dix, false));
                        break;
                    default:
                        imageID.add(new GridItemClickOnlyHolder(R.drawable.un, false));
                }

            }
        }

        this.idImageBackground = typeOfImage;
    }

    @Override
    public int getCount() {
        return imageID.size();
    }

    @Override
    public Object getItem(int position) {
        return imageID.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(this.context);
        Drawable backgroundImage = ContextCompat.getDrawable(context, idImageBackground);
        imageView.setBackground(backgroundImage);
        imageView.setImageResource(imageID.get(position).imgId);
        return imageView;
    }
}
