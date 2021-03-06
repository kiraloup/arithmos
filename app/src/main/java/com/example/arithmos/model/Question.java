package com.example.arithmos.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "question_table")
public class Question {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "type")
    private String type;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "expected_num_values")
    private int expectedNumValues;

    @NonNull
    @ColumnInfo
    private String imageType;

    //NOT IN DATABASE CLASS MEMBER
    @Ignore
    private int result;

    public Question(@NonNull String title, @NonNull String type,
                    @NonNull int expectedNumValues, @NonNull String imageType) {
        this.title = title;
        this.type = type;
        this.expectedNumValues = expectedNumValues;
        this.imageType = imageType;
        this.result = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    public int getExpectedNumValues() {
        return expectedNumValues;
    }

    @NonNull
    public String getImageType() {
        return imageType;
    }

    public void setImageType(@NonNull String imageType) {
        this.imageType = imageType;
    }

    @Ignore
    public int getResult() {
        return result;
    }

    @Ignore
    public void setResult(int result) {
        this.result = result;
    }
}
