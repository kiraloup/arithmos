package com.example.arithmos.view.gridview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.arithmos.R;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {

    private Context context;
    //this array contains all the id
    public ArrayList<Integer> imageID = new ArrayList<>();

    public GridViewAdapter(Context context) {
        this.context = context;
        for(int i = 0; i < 3; i++) {
            imageID.add(R.drawable.apple);
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
