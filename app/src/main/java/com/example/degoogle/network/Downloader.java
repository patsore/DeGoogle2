package com.example.degoogle.network;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;
import androidx.viewbinding.ViewBinding;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.example.degoogle.databinding.FragmentAppInfoBinding;
import com.example.degoogle.databinding.FragmentUpdatesBinding;
import com.example.degoogle.ui.info.AppInfoViewModel;
import com.example.degoogle.ui.updates.UpdatesViewModel;

import java.io.File;

public class Downloader {
    private Context context;
    private ViewBinding binding;
    private ViewModel viewModel;
    public Downloader(Context context, FragmentUpdatesBinding binding, UpdatesViewModel updatesViewModel) {
        this.context = context;
        this.binding = binding;
        this.viewModel = updatesViewModel;
    }
    public Downloader(Context context, FragmentAppInfoBinding binding, AppInfoViewModel appInfoViewModel) {
    this.context = context;
    this.binding = binding;
    this.viewModel = appInfoViewModel;
    }
    public void setup(){
        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setReadTimeout(30000)
                .setConnectTimeout(30000)
                .build();
        PRDownloader.initialize(context, config);
    }

    public void downloadStart(String url, String packageName, File outputFile){
        Log.d("TAG", "downloadStart: " + packageName + " " + outputFile.toURI().toString());
           PRDownloader.download(url, context.getExternalFilesDir("downloads").toString(), packageName + ".apk")
                    .build()
                    .setOnProgressListener(progress -> {
                        int downloadProgress = (int) ((progress.currentBytes * 1f /progress.totalBytes) * 100);
//                        binding.downloaded.setText(String.valueOf(progress.currentBytes));
//                        binding.total.setText(String.valueOf(progress.totalBytes));

                    if (binding instanceof FragmentAppInfoBinding) {
                        ((FragmentAppInfoBinding) binding).progress.setProgress(downloadProgress);
                    }


                    })
                    .start(new OnDownloadListener() {
                        @RequiresApi(api = Build.VERSION_CODES.S)
                        @Override
                        public void onDownloadComplete() {
                            Log.d("OnDownloadComplete", "onDownloadComplete: download complete");
                            if (viewModel instanceof AppInfoViewModel) {
                                Log.d("TAG", "onDownloadComplete: " + ((AppInfoViewModel) viewModel).getAllPackages().getValue());

                                ((AppInfoViewModel)viewModel).installApk(packageName, outputFile);
                            }
                            if (viewModel instanceof UpdatesViewModel) {
                                ((UpdatesViewModel)viewModel).installApk(packageName, outputFile);
                            }
//                            viewModel.installApk(packageName, new File(context.getExternalFilesDir("downloads").toString() + "/" + packageName + ".apk"));
//                    installApk();
                        }


                        @Override
                        public void onError(Error error) {
                            Log.d("Downloader Class", "onError: download error");
                        }
                    });
    }
}
