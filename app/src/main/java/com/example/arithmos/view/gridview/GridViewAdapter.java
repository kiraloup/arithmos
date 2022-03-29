package com.example.arithmos.view.gridview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.arithmos.R;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    private Context context;
    //this array contains all the id
    public ArrayList<Integer> imageID = new ArrayList<>();

    public GridViewAdapter(Context context, int[] listOfApple, int[] typeOfImage) {
        this.context = context;
        for(int i = 0; i < listOfApple.length; i++) {
            for(int j = 0; j < listOfApple[i]; j++) {
                switch (i) {
                    case 0:
                        imageID.add(typeOfImage[0]);
                        break;
                    case 1:
                        imageID.add(typeOfImage[1]);
                        break;
                    case 2:
                        imageID.add(typeOfImage[2]);
                        break;
                    case 3:
                        imageID.add(typeOfImage[3]);
                        break;
                    default:
                        imageID.add(typeOfImage[4]);
                }

            }
        }
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
        imageView.setImageResource(imageID.get(position));
        return imageView;
    }
}
