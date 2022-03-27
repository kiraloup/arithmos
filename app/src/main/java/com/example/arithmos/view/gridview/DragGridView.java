package com.example.arithmos.view.gridview;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.arithmos.R;
import com.example.arithmos.model.GridItem;

import java.util.ArrayList;
import java.util.List;

public class DragGridView extends BaseAdapter {


    private final Context context;
    private final List<GridItem> listItems;
    private static final String IMAGEVIEW_TAG = "icon bitmap";

    public DragGridView(Context context, List<GridItem> listOfItems ) {
        this.context = context;
        //this.listItems = listOfItems;
        this.listItems = new ArrayList<>();


        this.listItems.add(new GridItem(1000, R.drawable.apple_mille));
        this.listItems.add(new GridItem(100, R.drawable.apple_cents));
        this.listItems.add(new GridItem(50, R.drawable.apple_cinquante));
        this.listItems.add(new GridItem(10, R.drawable.apple_dix));
        this.listItems.add(new GridItem(1, R.drawable.apple));


    }


    public DragGridView(Context context ) {
        this.context = context;
        //this.listItems = listOfItems;
        this.listItems = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridItemHolder holder;

        if(convertView == null) {
            Log.d("DragGridView", "add new items");
            holder = new GridItemHolder();

            ImageView imageView = new ImageView(this.context);
            imageView.setImageResource(listItems.get(position).getImageId());
            Log.d("DragGridView", "IMAGE ID : " + listItems.get(position).getImageId());
            Log.d("DragGridView", "position : " + position);
            Log.d("DragGridView", "Number of items  : " + getCount());

            convertView = imageView;
            convertView.setTag(holder);
        } else {
            holder = (GridItemHolder) convertView.getTag();
        }

        holder.item = listItems.get(position);
        holder.tag = IMAGEVIEW_TAG;

        return convertView;
    }

    public void removeItems(Object item) {
        listItems.remove(item);
        this.notifyDataSetChanged();
    }

    public boolean newItems(GridItem item) {
        return listItems.add(item);
    }
}
