package com.example.arithmos.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DragAndDropViewModel extends ViewModel {

    public MutableLiveData<Boolean> isHelpAsked = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> getIsHelpAsked() {
        return isHelpAsked;
    }

    public void setIsHelpAsked(MutableLiveData<Boolean> isHelpAsked) {
        this.isHelpAsked = isHelpAsked;
    }
}
