package com.example.degoogle.installer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.media.AudioManager
import android.media.ToneGenerator
import android.util.Log

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
            PackageInstaller.STATUS_SUCCESS ->
                Log.d(TAG, "onReceive: install success")

            else -> {
                val msg = intent.getStringExtra(PackageInstaller.EXTRA_STATUS_MESSAGE)
                Log.e(TAG, "received $status and $msg")

            }

        }

    }

}