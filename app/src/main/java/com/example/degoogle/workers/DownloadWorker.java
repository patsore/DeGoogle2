package com.example.degoogle.workers;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.downloader.utils.Utils;
import com.example.degoogle.dialogs.PermissionDialog;
import com.example.degoogle.installer.KotlinInstallMotor;
import com.example.degoogle.ui.info.AppInfoFragment;

public class DownloadWorker extends Worker {
    public Context context;

    public boolean foregrounded(){
        ActivityManager.RunningAppProcessInfo appProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(appProcessInfo);
        return (appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE);
    }

    public DownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;

    }

    @NonNull
    @Override
    public Result doWork() {
        installApk();
        return null;
    }

    private void installApk(){
        //TODO WorkManager/Service
        Data data = getInputData();

        if (foregrounded()) {
            if (!context.getPackageManager().canRequestPackageInstalls()) {
                PermissionDialog permissionDialog = new PermissionDialog();
                permissionDialog.show(permissionDialog.getParentFragmentManager(), "test");
            } else {
                KotlinInstallMotor motor = new KotlinInstallMotor((Application) getApplicationContext());
                motor.install(Uri.parse(data.getString("file")));
                Log.d("Worker Class", "installApk: " + Uri.parse(data.getString("file")));

            }
        }else{
            Log.d("this", "installApk: "  + "not foregrounded");
        }
        }

}
