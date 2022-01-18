package com.example.degoogle.ui.updates;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UpdatesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UpdatesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is update fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
