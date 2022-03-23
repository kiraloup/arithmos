package com.example.arithmos.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.arithmos.db.RepositoryCallback;
import com.example.arithmos.db.Result;
import com.example.arithmos.db.UserRepository;
import com.example.arithmos.model.ExoStat;
import com.example.arithmos.model.UserWithExoStat;

import java.util.List;

public class UserStatViewModel extends AndroidViewModel {

    private final UserRepository userRepository;

    private final String TAG = "USERSTATVIEWMODELE";

    public MutableLiveData<Boolean> isLoadingSuccess = new MutableLiveData<>();

    public UserStatViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void getUserStat() {
        userRepository.getUserStat(new RepositoryCallback<List<UserWithExoStat>>() {
            @Override
            public void onComplete(Result<List<UserWithExoStat>> result) {
                if(result instanceof Result.Success) {
                    List<UserWithExoStat> userWithExoStatList =
                            ((Result.Success<List<UserWithExoStat>>) result).data;

                    Log.d(TAG, "userWithExoStatList size " + userWithExoStatList.size());

                    Log.d(TAG, userWithExoStatList.get(0).user.toString());

                    List<ExoStat> userStat = userWithExoStatList.get(0).exoStatList;
                    Log.d(TAG, "userStat size : " + userStat.size());

                    for(int i = 0; i < userStat.size(); i++) {
                        Log.d(TAG, "user Stat : " + userStat.get(i).toString());
                    }

                    isLoadingSuccess.postValue(true);
                } else {
                    Log.d(TAG, "ERREUR DANS LE VIEWMODEL");
                    isLoadingSuccess.postValue(false);
                }
            }
        });
    }
}
