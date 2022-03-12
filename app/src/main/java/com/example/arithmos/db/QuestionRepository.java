package com.example.arithmos.db;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;

import com.example.arithmos.R;
import com.example.arithmos.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;


public class QuestionRepository {

    private QuestionDAO questionDAO;
    // it make more sense to not use livedata but it would be easier to use it since
    // we to write a lot of logic to call background thread in java
    private List<Question> questions;
    // QuestionRepository is blocking the main thread when requesting for questions
    // So it takes an instance of Executor as opposed to ExecutorService because it's executing code
    // and not managing threads:
    private final Executor executor;

    public QuestionRepository(Application application) {
        RoomDatabaseA db = RoomDatabaseA.getDatabase(application);
        questionDAO = db.questionDAO();
        //questions = questionDAO.getAllQuestion();
        this.executor = db.getQueryExecutor();
    }

    //this method move the execution to the background thread
    public void getAllQuestion(final RepositoryCallback<List<Question>> callback) {
        //execute the method in a available thread
        Log.d("QuestionRepository", "getAllQuestion");
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("QuestionRepository", "HE 1");
                try {
                    Log.d("QuestionRepository", "HE 2");
                    Result<List<Question>> resultQuestion= new Result.Success<>(questionDAO.getAllQuestion());
                    callback.onComplete(resultQuestion);
                }catch (Exception e){
                    Log.d("QuestionRepository", "HE 3");
                    Result<List<Question>> error = new Result.Error<>(e);
                    callback.onComplete(error);
                }
            }
        });
    }

}
