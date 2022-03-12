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

    //NOT IN DATABASE CLASS MEMBER
    @Ignore
    private QuestionResultat result;

    public Question(@NonNull String title, @NonNull String type, @NonNull int expectedNumValues) {
        this.title = title;
        this.type = type;
        this.expectedNumValues = expectedNumValues;
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

    public void setExpectedNumValues(int expectedNumValues) {
        expectedNumValues = expectedNumValues;
    }

    public QuestionResultat getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result.setStringResult(result);
    }

    public void setResult(int result) {
        this.result.setNumberResult(result);
    }
}
