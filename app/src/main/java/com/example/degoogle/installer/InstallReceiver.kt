package com.example.degoogle.installer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.util.Log
import com.example.degoogle.interfaces.AddPackageItem
import com.example.degoogle.ui.info.AppInfoFragment

private const val TAG = "AppInstaller"

class InstallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {



        when (val status = intent.getIntExtra(PackageInstaller.EXTRA_STATUS, -1)){
            PackageInstaller.STATUS_PENDING_USER_ACTION -> {
                val activityIntent =
                        intent.getParcelableExtra<Intent>(Intent.EXTRA_INTENT)
                if (activityIntent != null) {
                    context.startActivity(activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                }
            }
            PackageInstaller.STATUS_SUCCESS -> {
                Log.d(
                    TAG, "onReceive: install success")

                val successIntent:Intent = Intent("installSuccess")
                context.sendBroadcast(successIntent)
            }
            else -> {
                val msg = intent.getStringExtra(PackageInstaller.EXTRA_STATUS_MESSAGE)
                Log.e(TAG, "received $status and $msg")

            }

        }

    }
}