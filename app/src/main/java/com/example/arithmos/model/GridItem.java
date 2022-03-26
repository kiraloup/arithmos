package com.example.arithmos.model;

public class GridItem {

    private int value;
    private int imageId;

    public GridItem(int value, int imageId) {
        this.value = value;
        this.imageId = imageId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
