/*
  This class is used to download the apk file from the firebase storage and install it
 */
package com.example.degoogle.ui.info;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Data;

import com.bumptech.glide.Glide;
import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.example.degoogle.data.entities.InstalledPackages;
import com.example.degoogle.databinding.FragmentAppInfoBinding;
import com.example.degoogle.dialogs.PermissionDialog;
import com.example.degoogle.installer.KotlinInstallMotor;
import com.example.degoogle.model.AppInfoModel;
import com.example.degoogle.network.Downloader;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.Objects;

public class AppInfoFragment extends BottomSheetDialogFragment {

    private static final String TAG = "AppInfoFragment";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FragmentAppInfoBinding binding;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference appReference;
    String apk = "";
    File file;
    String downloadUri;
    File outputFile;
    String packageName;
    private AppInfoViewModel appInfoViewModel;
    private String version;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentAppInfoBinding.inflate(inflater, container, false);
        requireActivity().registerReceiver(new installResultReceiver(), new IntentFilter("installSuccess"));

        new Downloader(requireContext()).setup();

        appInfoViewModel = new ViewModelProvider(this).get(AppInfoViewModel.class);
        observeData();
        if(ContextCompat.checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), new String[]{READ_EXTERNAL_STORAGE}, 1);
        }
        if(ContextCompat.checkSelfPermission(requireContext(), WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), new String[]{WRITE_EXTERNAL_STORAGE}, 1);
        }
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    private void setValues(AppInfoModel appInfoModel){
        binding.setViewModel(appInfoViewModel);
        //DataBinding for everything
//        binding.appVersion.setText(appInfoViewModel.getAppVersion());
        packageName = appInfoModel.getPackageName();
        outputFile = new File(file, appInfoModel.getPackageName() + ".apk");


        binding.appIcon.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(appInfoModel.getIcon()), "image/*");
            startActivity(intent);
        });
        binding.appDownload.setOnClickListener(v -> {
            downloadUri = appInfoModel.getDownloadUrl();
            downloader(downloadUri);
        });
        binding.appInstall.setOnClickListener(v -> {
//                installApk();
                appInfoViewModel.installApk(packageName, outputFile);
            });
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    private void observeData(){
//        Log.d(TAG, "observeData: observed data changed " + appInfoViewModel.getAllPackages().getValue());
//        appInfoViewModel.getAllPackages().observe(getViewLifecycleOwner(), installedPackages  -> {
//            Log.d(TAG, "observeData: observed data changed " + installedPackages.get(0).getPackageName() + " " + installedPackages.get(0).getPackageVersion());
//        });
//
        appInfoViewModel.getDataFromFirebase(args());
        appInfoViewModel.getPackageInfo().observe(getViewLifecycleOwner(), this::setValues);
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFiles();
        observeData();
    }


    private String args(){
            Bundle args = getArguments();
        return Objects.requireNonNull(args).getString("key");
    }






    private void getFiles(){
        file = requireContext().getExternalFilesDir("downloads");
    }




    
    public class installResultReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            appInfoViewModel.insert(new InstalledPackages(args(), version));
        }
    }
    private void downloader(String url){
        PRDownloader.download(url, requireContext().getExternalFilesDir("downloads").toString(), packageName + ".apk")
                .build()
                .setOnProgressListener(progress -> {
                    int downloadProgress = (int) ((progress.currentBytes * 1f /progress.totalBytes) * 100);
                    binding.downloaded.setText(String.valueOf(progress.currentBytes));
                    binding.total.setText(String.valueOf(progress.totalBytes));
                    binding.downloadProgressBar.setProgress(downloadProgress);
                })
                .start(new OnDownloadListener() {
                    @RequiresApi(api = Build.VERSION_CODES.S)
                    @Override
                    public void onDownloadComplete() {
                         appInfoViewModel.installApk(packageName, outputFile);
//                    installApk();
                    }


                    @Override
                    public void onError(Error error) {
                        Log.d(TAG, "onError: download error");
                    }
                });
    }

    private void installApk(){
        //TODO WorkManager/Service
        if (!requireContext().getPackageManager().canRequestPackageInstalls()) {
            PermissionDialog permissionDialog = new PermissionDialog();
            permissionDialog.show(getParentFragmentManager(), "test");
        } else {
            KotlinInstallMotor motor = new KotlinInstallMotor(requireActivity().getApplication());
            motor.install(Uri.parse(outputFile.toURI().toString()));
            Log.d("Worker Class (Not)", "installApk: " + Uri.parse(outputFile.toString()));

        }
    }

}


