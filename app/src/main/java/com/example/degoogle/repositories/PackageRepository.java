package com.example.degoogle.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.degoogle.data.RoomPackagesDatabase;
import com.example.degoogle.data.dao.PackagesDao;
import com.example.degoogle.data.entities.InstalledPackages;

import java.util.List;

public class PackageRepository {
    private PackagesDao packagesDao;
    private LiveData<List<InstalledPackages>> packages;

    public PackageRepository(Application application) {
        RoomPackagesDatabase db = RoomPackagesDatabase.getDatabase(application);
        packagesDao = db.packagesDao();
        packages = packagesDao.getAllPackages();
    }

    public LiveData<List<InstalledPackages>> getAllPackages() {
        return packages;
    }

    public void insert(InstalledPackages installedPackages) {
    RoomPackagesDatabase.databaseWriteExecutor.execute(() -> packagesDao.insert(installedPackages));
    }
    public void delete(InstalledPackages installedPackages){
        RoomPackagesDatabase.databaseWriteExecutor.execute(() -> packagesDao.delete(installedPackages));
    }

}
