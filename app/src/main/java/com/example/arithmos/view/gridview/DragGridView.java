package com.example.arithmos.view.gridview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.room.util.StringUtil;

import com.example.arithmos.R;
import com.example.arithmos.model.GridItem;

import java.util.ArrayList;
import java.util.List;

public class DragGridView extends BaseAdapter {


    private final Context context;
    private final List<GridItem> listItems;
    private static final String IMAGEVIEW_TAG = "icon bitmap";
    private int typeOfImage;

    public DragGridView(Context context, int typeOfImage) {
        this.context = context;
        //this.listItems = listOfItems;
        this.listItems = new ArrayList<>();


        this.listItems.add(new GridItem(1000, R.drawable.mille));
        this.listItems.add(new GridItem(100, R.drawable.cents));
        this.listItems.add(new GridItem(50, R.drawable.cinquante));
        this.listItems.add(new GridItem(10, R.drawable.dix));
        this.listItems.add(new GridItem(1, R.drawable.un));

        this.typeOfImage = typeOfImage;
    }


    public DragGridView(Context context ) {
        this.context = context;
        this.listItems = new ArrayList<>();
        typeOfImage = -1;
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
            if(typeOfImage != -1) {
                Drawable backgroundImage = ContextCompat.getDrawable(context, typeOfImage);
                imageView.setBackground(backgroundImage);
            }
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

    public boolean removeItems(GridItem item) {
        return listItems.remove(item);
    }

    public List<GridItem> getListItems() {
        return listItems;
    }

    public void setTypeOfImage(int typeOfImage) {
        this.typeOfImage = typeOfImage;
    }

    public boolean newItems(GridItem item) {
        return listItems.add(item);
    }
}
