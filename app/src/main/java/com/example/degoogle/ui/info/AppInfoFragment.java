/*
  This class is used to download the apk file from the firebase storage and install it
 */
package com.example.degoogle.ui.info;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import static com.google.gson.internal.$Gson$Types.arrayOf;

import android.Manifest;
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

import com.bumptech.glide.Glide;
import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.example.degoogle.data.entities.InstalledPackages;
import com.example.degoogle.databinding.FragmentAppInfoBinding;
import com.example.degoogle.dialogs.PermissionDialog;
import com.example.degoogle.installer.KotlinInstallMotor;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.Objects;

import javax.xml.transform.sax.SAXResult;

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
    private AppInfoViewModel appInfoViewModel;
    private String version;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAppInfoBinding.inflate(inflater, container, false);
        requireActivity().registerReceiver(new installResultReceiver(), new IntentFilter("installSuccess"));
        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setReadTimeout(30000)
                .setConnectTimeout(30000)
                .build();
        PRDownloader.initialize(requireContext(), config);
        appInfoViewModel = new ViewModelProvider(this).get(AppInfoViewModel.class);
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED || ContextCompat.checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[] {READ_EXTERNAL_STORAGE}, 1);
            ActivityCompat.requestPermissions(requireActivity(), new String[] {WRITE_EXTERNAL_STORAGE}, 1);
        }
        observeData();
        return binding.getRoot();
    }

    private void observeData(){
        appInfoViewModel.getAllPackages().observe(getViewLifecycleOwner(), InstalledPackages  -> {

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFiles();
        getDataFromFirebase(args());
        binding.downloadbutton.setOnClickListener(view1 -> downloadFile());
        binding.button1.setOnClickListener(view12 -> installApk());
    }


    private String args(){
            Bundle args = getArguments();
        return Objects.requireNonNull(args).getString("key");
    }

    private void getDataFromFirebase(String id) {

        db.collection("apps")
                .document(id)
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot document = task.getResult();
                    apk = Objects.requireNonNull(document).getString("file");
                    binding.appName.setText(document.getString("name"));
                    binding.appDescription.setText(document.getString("description"));
                    Glide.with(requireContext()).load(document.getString("icon")).into(binding.appIcon);
                    outputFile = new File(file, document.getString("name") + ".apk");
                    downloadUri = document.getString("downloadUrl");
                    version = document.getString("version");
                });


    }




    private void getFiles(){
        file = requireContext().getExternalFilesDir("downloads");
    }

    public void downloadFile() {
        if (downloadUri == null) {
            appReference = storage.getReference(apk + ".apk");
            appReference.getDownloadUrl().addOnSuccessListener(uri -> downloader(uri.toString())).addOnFailureListener(e -> Log.d(TAG, "downloadFile: " + "no file found"));
        }else{
            downloader(downloadUri);
        }
    }

    private void downloader(String uri){
        PRDownloader.download(uri, file.toString(), apk + ".apk")
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
                        installApk();
                    }

                    @Override
                    public void onError(Error error) {
                        Log.d(TAG, "onError: download error");
                    }
                });


    }
    
    public class installResultReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ;
            appInfoViewModel.insert(new InstalledPackages(args(), version));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    private void installApk(){
        if(!file.mkdirs()){
            Log.d(TAG, "installApk: failed to create directories");
        }
        
        if (!requireContext().getPackageManager().canRequestPackageInstalls()) {
            PermissionDialog permissionDialog = new PermissionDialog();
            permissionDialog.show(getParentFragmentManager(), "test");
        } else {
            KotlinInstallMotor motor = new KotlinInstallMotor(requireActivity().getApplication());
            motor.install(Uri.parse(outputFile.toURI().toString()));
            Log.d(TAG, "installApk: " + Uri.parse(outputFile.toURI().toString()));

        }
    }




}


