package com.example.degoogle.ui.info;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.degoogle.data.dao.PackagesDao;
import com.example.degoogle.data.entities.InstalledPackages;
import com.example.degoogle.repositories.PackageRepository;
import com.google.api.LogDescriptor;

import java.util.List;

public class AppInfoViewModel extends AndroidViewModel{
    private PackageRepository packageRepository;

    private final LiveData<List<InstalledPackages>> installedPackages;

    public AppInfoViewModel(Application application) {
        super(application);
        packageRepository = new PackageRepository(application);
        installedPackages = packageRepository.getAllPackages();
    }

    LiveData<List<InstalledPackages>> getAllPackages() {
        return installedPackages;
    }
    public void insert(InstalledPackages installedPackages) { packageRepository.insert(installedPackages);
        Log.d("AppInfoViewModel: ", "inserted" + installedPackages.getPackageName() + " " + installedPackages.getPackageVersion());
        Log.d("AppInfoViewModel: ", "table content" + packageRepository.getAllPackages().getValue());
    }
}
