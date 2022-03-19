/*
  This class is used to download the apk file from the firebase storage and install it
 */
package com.example.degoogle.ui.info;

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

import com.bumptech.glide.Glide;
import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
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

public class AppInfoFragment extends BottomSheetDialogFragment {

    private static final String TAG = "AppInfoFragment";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FragmentAppInfoBinding binding;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference appReference;
    String apk = "";
    File file;
    File outputFile;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAppInfoBinding.inflate(inflater, container, false);
        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setReadTimeout(30000)
                .setConnectTimeout(30000)
                .build();
        PRDownloader.initialize(requireContext(), config);
        return binding.getRoot();
    }
    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFiles();
        getDataFromFirebase(getArgs());
        binding.downloadbutton.setOnClickListener(view1 -> downloadFile());
        binding.button1.setOnClickListener(view12 -> installApk());
    }


    private String getArgs(){
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
                    outputFile = new File(file, apk + ".apk");

                });


    }

    private void getFiles(){
        file = requireContext().getExternalFilesDir("downloads");
    }

    public void downloadFile() {
        appReference = storage.getReference(apk + ".apk");
        appReference.getDownloadUrl().addOnSuccessListener(uri ->

            PRDownloader.download(uri.toString(), file.toString(), apk + ".apk")
                        .build()
                        .setOnProgressListener(progress -> {
                            int downloadProgress = (int) ((progress.currentBytes * 1f /progress.totalBytes) * 100);
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
                        }));
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


