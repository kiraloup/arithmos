package com.example.arithmos.db;

import android.app.Application;
import android.util.Log;

import com.example.arithmos.model.ExoStat;
import com.example.arithmos.model.Question;
import com.example.arithmos.model.UserWithExoStat;

import java.util.List;
import java.util.concurrent.Executor;

public class UserRepository {

    private UserDao userDao;
    private final Executor executor;
    private static final String TAG = "USERREPOSITORY";



    public UserRepository(Application application) {
        RoomDatabaseA db = RoomDatabaseA.getDatabase(application);
        userDao = db.userDao();
        //questions = questionDAO.getAllQuestion();
        this.executor = db.getQueryExecutor();
    }

    public void getUserStat(final RepositoryCallback<List<ExoStat>> callback) {
        Log.d(TAG, "get user stat");
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<ExoStat> queryRes = userDao.getUserWithExoStat();

                    Result<List<ExoStat>> resultUserStat= new Result.Success<>(queryRes);

                    callback.onComplete(resultUserStat);
                } catch (Exception e) {
                    Log.d("QuestionRepository", "HE 3" + e.toString());
                    Result<List<ExoStat>> error = new Result.Error<>(e);
                    callback.onComplete(error);
                }

            }
        });
    }

    //rather than calling this function each time the user
    // this function is call at the end of an exercise and update the profile once
    public void setUserStat(String typeOfExercice, int nbCorrectAnswer, int nbWrongAnswer) {
        Log.d(TAG, "get user stat");
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //both row are update for a select type of exercise(add, sous...)
                    userDao.updateNbCorrect(typeOfExercice, nbCorrectAnswer);
                    userDao.updateNbError(typeOfExercice, nbWrongAnswer);
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }

            }
        });
    }


}
