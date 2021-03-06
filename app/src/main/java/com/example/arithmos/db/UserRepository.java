package com.example.arithmos.db;

import android.app.Application;
import android.util.Log;

import com.example.arithmos.model.ExoStat;
import com.example.arithmos.model.Question;
import com.example.arithmos.model.User;
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

    public void geAlltUserStat(final RepositoryCallback<List<allUserStat>> callback) {
        Log.d(TAG, "get user stat");
        executor.execute(() -> {
            try {
                List<allUserStat> queryRes = userDao.getTotalReponse();

                Log.d(TAG, "queryRes" + queryRes.size());
                Result<List<allUserStat>> resultUserStat= new Result.Success<>(queryRes);

                callback.onComplete(resultUserStat);
            } catch (Exception e) {
                Log.d(TAG, "Error geAlltUserStat " + e.toString());
                Result<List<allUserStat>> error = new Result.Error<>(e);
                callback.onComplete(error);
            }
        });
    }


    public void getUserStat(final RepositoryCallback<List<ExoStat>> callback) {
        Log.d(TAG, "get user stat");
        executor.execute(() -> {
            try {
                List<ExoStat> queryRes = userDao.getUserWithExoStat();

                Result<List<ExoStat>> resultUserStat= new Result.Success<>(queryRes);

                callback.onComplete(resultUserStat);
            } catch (Exception e) {
                Log.d(TAG, "HE 3" + e.toString());
                Result<List<ExoStat>> error = new Result.Error<>(e);
                callback.onComplete(error);
            }

        });
    }

    //rather than calling this function each time the user
    // this function is call at the end of an exercise and update the profile once
    public void setUserStat(String typeOfExercice, int nbCorrectAnswer, int nbWrongAnswer,int typeReponse) {
        Log.d(TAG, "get user stat");
        executor.execute(() -> {
            try {
                //both row are update for a select type of exercise(add, sous...)
                userDao.updateNbCorrect(typeOfExercice, nbCorrectAnswer, typeReponse);
                userDao.updateNbError(typeOfExercice, nbWrongAnswer, typeReponse);
                userDao.updatePourcentage(typeOfExercice, typeReponse);
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }

        });
    }


}
