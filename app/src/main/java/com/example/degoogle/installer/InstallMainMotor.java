package com.example.degoogle.installer;

import android.app.Application;
import android.content.ContentResolver;
import android.content.pm.PackageInstaller;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class InstallMainMotor extends AndroidViewModel {

    private PackageInstaller packageInstaller = getApplication().getPackageManager().getPackageInstaller();
    private ContentResolver contentResolver = getApplication().getContentResolver();

    public InstallMainMotor(@NonNull Application application) {
        super(application);
    }

    void install(Uri apkUri){


    }

//    private void install(Uri )


}
