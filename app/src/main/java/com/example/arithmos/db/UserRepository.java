package com.example.arithmos.db;

import android.app.Application;
import android.util.Log;

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

    public void getUserStat(final RepositoryCallback<List<UserWithExoStat>> callback) {
        Log.d(TAG, "get user stat");
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<UserWithExoStat> queryRes = userDao.getUserWithExoStat();

                    Result<List<UserWithExoStat>> resultUserStat= new Result.Success<>(queryRes);

                    callback.onComplete(resultUserStat);
                } catch (Exception e) {
                    Log.d("QuestionRepository", "HE 3" + e.toString());
                    Result<List<UserWithExoStat>> error = new Result.Error<>(e);
                    callback.onComplete(error);
                }

            }
        });
    }


}