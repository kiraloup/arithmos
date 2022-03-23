package com.example.arithmos.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
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
    public MutableLiveData<List<ExoStat>> listStats = new MutableLiveData<>();

    public UserStatViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void getUserStat() {
        userRepository.getUserStat(new RepositoryCallback<List<ExoStat>>() {
            @Override
            public void onComplete(Result<List<ExoStat>> result) {
                if(result instanceof Result.Success) {
                    List<ExoStat> userExoStatList =
                            ((Result.Success<List<ExoStat>>) result).data;

                    Log.d(TAG, "userStat size : " + userExoStatList.size());

                    for(int i = 0; i < userExoStatList.size(); i++) {
                        Log.d(TAG, "user Stat : " + userExoStatList.get(i).toString());
                    }

                    listStats.postValue(userExoStatList);

                    isLoadingSuccess.postValue(true);
                } else {
                    Log.d(TAG, "ERREUR DANS LE VIEWMODEL");
                    isLoadingSuccess.postValue(false);
                }
            }
        });
    }
}
