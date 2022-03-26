package com.example.arithmos.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DragAndDropViewModel extends ViewModel {

    public MutableLiveData<Boolean> isButtonClick = new MutableLiveData<>(false);


    public MutableLiveData<Boolean> getIsButtonClick() {
        return isButtonClick;
    }

    public void setIsButtonClick(Boolean isClick) {
        isButtonClick.postValue(isClick);
    }
}
