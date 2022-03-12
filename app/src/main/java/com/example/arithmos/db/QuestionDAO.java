package com.example.arithmos.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.arithmos.model.Question;

import java.util.List;

@Dao
public interface QuestionDAO {

    @Insert
    void insert(Question q);

    @Delete
    void deleteQuestion(Question q);

    @Query("Select distinct * From QUESTION_TABLE")
    List<Question> getAllQuestion();

    @Query("Select distinct * From QUESTION_TABLE LIMIT 10")
    LiveData<List<Question>> getTenQuestion();

    @Query("Delete from question_table")
    void deleteAll();
}
