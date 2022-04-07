package com.example.degoogle.ui.updates;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.degoogle.data.RoomPackagesDatabase;

public class UpdatesViewModel extends ViewModel {
    public RoomPackagesDatabase mDatabase;
    public MutableLiveData<String> installedPackages;
    public UpdatesViewModel() {

    }
    public UpdatesViewModel(Application application) {
        getInstalledPackages();
    }

    public void getInstalledPackages(){

    }
}
