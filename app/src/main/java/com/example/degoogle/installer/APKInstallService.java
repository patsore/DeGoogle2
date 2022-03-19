package com.example.degoogle.installer;


import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class APKInstallService extends Service {
    private static final String TAG = "APKInstallService";



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int status = intent.getIntExtra(PackageInstaller.EXTRA_STATUS, -999);

        switch (status) {
            case PackageInstaller.STATUS_PENDING_USER_ACTION:
                Log.d(TAG, "Requesting user confirmation for installation");
                // This is a way to get the intent that was passed to the service.
                Intent confirmationIntent = intent.getParcelableExtra(Intent.EXTRA_INTENT);
                confirmationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    startActivity(confirmationIntent);
                } catch (Exception e) {
                    //No action

                }
                break;
            case PackageInstaller.STATUS_SUCCESS:
                Log.d(TAG, "Installation succeed");
                break;
            default:
                Log.d(TAG, "Installation failed");
                break;
        }
        stopSelf();
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
