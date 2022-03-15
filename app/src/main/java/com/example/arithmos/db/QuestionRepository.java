package com.example.arithmos.db;

import android.app.Application;
import android.util.Log;

import com.example.arithmos.model.Question;

import java.util.ArrayList;
import java.util.Collections;
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

    public void getTenQuestionType(final RepositoryCallback<List<Question>> callback,String type){
        Log.d("QuestionRepository", "getAllQuestion");
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("QuestionRepository", "HE 1");
                try {
                    Log.d("QuestionRepository", "HE 2");

                    List<Question> queryRes = questionDAO.getTenQuestionType(type);

                    Log.d("QuestionRepository", "queryRes : " + queryRes.size());

                    Result<List<Question>> resultQuestion= new Result.Success<>(queryRes);

                    callback.onComplete(resultQuestion);
                }catch (Exception e){
                    Log.d("QuestionRepository", "HE 3" + e.toString());
                    Result<List<Question>> error = new Result.Error<>(e);
                    callback.onComplete(error);
                }
            }
        });
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
                    Log.d("QuestionRepository", "HE 3" + e.toString());
                    Result<List<Question>> error = new Result.Error<>(e);
                    callback.onComplete(error);
                }
            }
        });
    }


    public static List<Integer> findNumberOfimage(ArrayList<Integer> TypeOfimages,ArrayList<Integer> NumberOfimages,int value) {
        int i, count = 0;
        List<Integer> res = Collections.nCopies(TypeOfimages.size(), 0);

        for(i = 0; i < TypeOfimages.size(); i++) {
            while(value >= TypeOfimages.get(i) && NumberOfimages.get(i) > 0) {
                //decremente the value since we select the image
                value -= TypeOfimages.get(i);

                // we check how many images of this type we can still display
                int rest = NumberOfimages.get(i) - 1;
                //we decremente this value
                NumberOfimages.set(i, rest);
                //now we incremente the result for this specific images
                //since we select it
                int newNumberImages = res.get(i) + 1;
                res.set(i, newNumberImages);
            }
        }

        return res;
    }

}
