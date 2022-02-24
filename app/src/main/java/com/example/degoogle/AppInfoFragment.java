package com.example.degoogle;

import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.degoogle.databinding.FragmentAppInfoBinding;
import com.example.degoogle.installer.APKInstallService;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.Objects;

public class AppInfoFragment extends Fragment {

    private static final String TAG = "adasd";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FragmentAppInfoBinding binding;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference appReference;
    String apk = "";
    DownloadManager downloadmanager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAppInfoBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppInfoFragmentArgs id = AppInfoFragmentArgs.fromBundle(getArguments());
        int appId = id.getId();
        firebaseIntegration(appId);
        Log.d(TAG, "onViewCreated: " + appId);
        binding.downloadbutton.setOnClickListener(view1 -> downloadFile());
        binding.button1.setOnClickListener(view12 -> {
            installApk();
        });

    }


    private void firebaseIntegration(int id) {
        db.collection("messaging")
                .document(String.valueOf(id + 1))
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot document = task.getResult();
                    Log.d(TAG, "firebaseIntegration1: " + Objects.requireNonNull(task.getResult()));
                    apk = Objects.requireNonNull(document).getString("file");
                    String name = document.getString("name");


                    binding.appName.setText(document.getString("name"));
                    binding.appDescription.setText(document.getString("description"));
                    Glide.with(requireContext()).load(document.getString("icon")).into(binding.appIcon);


                }).addOnFailureListener(e -> {

        });


    }

    public class DownloadsObserver extends FileObserver {

        private static final int flags =
                FileObserver.CLOSE_WRITE
                        | FileObserver.OPEN
                        | FileObserver.MODIFY
                        | FileObserver.DELETE
                        | FileObserver.MOVED_FROM;
        // Received three of these after the delete event while deleting a video through a separate file manager app:
        // 01-16 15:52:27.627: D/APP(4316): DownloadsObserver: onEvent(1073741856, null)

        public DownloadsObserver(String path) {
            super(path);

        }

        @Override
        public void onEvent(int event, String path) {
            Log.d(TAG, "onEvent(" + event + ", " + path + ")");

            if (path == null) {
                return;
            }

            switch (event) {
                case FileObserver.CLOSE_WRITE:
                    // Download complete, or paused when wifi is disconnected. Possibly reported more than once in a row.
                    // Useful for noticing when a download has been paused. For completions, register a receiver for
                    // DownloadManager.ACTION_DOWNLOAD_COMPLETE.
                    break;
                case FileObserver.OPEN:
                    // Called for both read and write modes.
                    // Useful for noticing a download has been started or resumed.
                    break;
                case FileObserver.DELETE:
                case FileObserver.MOVED_FROM:
                    // These might come in handy for obvious reasons.
                    break;
                case FileObserver.MODIFY: {

                    DownloadManager.Query query = new DownloadManager.Query();

//                        Cursor c = downloadmanager.query(query);
//                        int bytesDownloaded = c.getInt(Integer.parseInt(CharMatcher.inRange('0', '9').retainFrom(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)));
//                        int bytesTotal = c.getInt(Integer.parseInt(CharMatcher.inRange('0', '9').retainFrom(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)));
//                    Log.d(TAG, "onEvent: " + downloadmanager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
//                        double progress = 0.0;
//                        progress = bytesDownloaded*100.0/bytesTotal;

//                        binding.downloadProgressBar.setProgress((int) progress);
                    // At this point you have the progress as a percentage.


                }
                // Called very frequently while a download is ongoing (~1 per ms).
                // This could be used to trigger a progress update, but that should probably be done less often than this.
                break;
            }
        }
    }


    public void downloadFile() {
        downloadmanager = (DownloadManager) requireContext().
                getSystemService(Context.DOWNLOAD_SERVICE);
        FileObserver fileObserver = new DownloadsObserver(Environment.DIRECTORY_DOWNLOADS);


        fileObserver.startWatching();
        fileObserver.onEvent(FileObserver.MODIFY, Environment.DIRECTORY_DOWNLOADS);

        appReference = storage.getReference(apk + ".apk");
        appReference.getDownloadUrl().addOnSuccessListener(uri -> {
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalFilesDir(requireContext(), String.valueOf(Environment.DIRECTORY_DOWNLOADS), apk + ".apk");
            downloadmanager.enqueue(request);
//                PackageInstaller.Session


        });
    }
    private void installApk(){

        PackageInstaller.Session session = null;
        PackageInstaller packageInstaller = null;
        try {
            try {
                session = packageInstaller.openSession(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent callbackIntent = new Intent(requireContext(), APKInstallService.class);
            PendingIntent pendingIntent = PendingIntent.getService(requireContext(), 0, callbackIntent, 0);
            session.commit(pendingIntent.getIntentSender());
        } finally{
            session.close();

        }

    }



    }


