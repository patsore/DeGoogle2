package com.example.degoogle.network;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;

public class Downloader {
    private Context context;


    public Downloader(Context context) {
    this.context = context;

    }
    public void setup(){
        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setReadTimeout(30000)
                .setConnectTimeout(30000)
                .build();
        PRDownloader.initialize(context, config);
    }

    public void downloadStart(String url, String packageName){

    }
}
