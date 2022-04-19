/*
  This class is used to download the apk file from the firebase storage and install it
 */
package com.example.degoogle.ui.info;

import android.Manifest;
import android.animation.Animator;
import android.animation.ValueAnimator;
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
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.degoogle.R;
import com.example.degoogle.adapter.ScreenshotRecyclerAdapter;
import com.example.degoogle.data.entities.InstalledPackages;
import com.example.degoogle.databinding.FragmentAppInfoBinding;
import com.example.degoogle.dialogs.PermissionDialog;
import com.example.degoogle.installer.KotlinInstallMotor;
import com.example.degoogle.model.Product;
import com.example.degoogle.network.Downloader;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class AppInfoFragment extends BottomSheetDialogFragment {
    private static final String TAG = "AppInfoFragment";
    private FragmentAppInfoBinding binding;
    File file;
    String downloadUri;
    File outputFile;
    String packageName;
    private AppInfoViewModel appInfoViewModel;
    private String version;
    private int whilecheckthing;
    private int defaultScreenshotRvHeight;
    Downloader downloader;
    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAppInfoBinding.inflate(inflater, container, false);
        requireActivity().registerReceiver(new installResultReceiver(), new IntentFilter("installSuccess"));
        appInfoViewModel = new ViewModelProvider(this).get(AppInfoViewModel.class);
        observeData();
        Dexter.withContext(requireContext()).withPermission(Manifest.permission.QUERY_ALL_PACKAGES).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                requireActivity().finishAffinity();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        });
        return binding.getRoot();
    }




    @RequiresApi(api = Build.VERSION_CODES.S)
    private void setValues(Product product){
        binding.progress.setVisibility(View.GONE);
        binding.setViewModel(appInfoViewModel);
        ArrayList<String> screenshots = product.getImageCarousel();
        setScreenshotData(screenshots);
        outputFile = new File(file, product.getPackageName() + ".apk");
            binding.size.setText("123451872");
        binding.cardExpansionIndicator.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
        packageName = product.getPackageName();
        version = product.getVersion();
        binding.installButton.setOnClickListener(v -> {

            binding.progress.setVisibility(View.VISIBLE);
            downloadUri = product.getDownloadUrl();
            Log.d(TAG, "setValues: "+ product.getDownloadUrl());
            if (!requireContext().getPackageManager().canRequestPackageInstalls()) {
                PermissionDialog permissionDialog = new PermissionDialog();
                permissionDialog.show(getChildFragmentManager(), "permissionDialog");
            }else{
                downloader.downloadStart(downloadUri, packageName, outputFile);
            }
        });
        while(binding.screenshotBlock.getVisibility() == View.VISIBLE && whilecheckthing != 1){
            defaultScreenshotRvHeight = binding.screenshotRv.getHeight();
            whilecheckthing = 1;
        }
        binding.screenshotText.setOnClickListener(v -> {
           if(binding.collapsibleScreenshotsBlock.getVisibility() == View.GONE){
               expand(binding.collapsibleScreenshotsBlock, 300, 0);
               binding.cardExpansionIndicator.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
           }else{
               collapse(binding.collapsibleScreenshotsBlock, 200, defaultScreenshotRvHeight);
//               TransitionManager.beginDelayedTransition(binding.collapsibleScreenshotsBlock, );
//               binding.collapsibleScreenshotsBlock.setVisibility(View.GONE);
               binding.cardExpansionIndicator.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
           }
        });
    }
    private void setScreenshotData(ArrayList<String> screenshotsUrlList){

        binding.screenshotRv.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        binding.screenshotRv.setAdapter(new ScreenshotRecyclerAdapter(requireContext(), screenshotsUrlList));

    }
    public static void expand(final View v, int duration, int targetHeight) {

        int prevHeight  = targetHeight - targetHeight;

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(animation -> {
            v.getLayoutParams().height = (int) animation.getAnimatedValue();
            v.requestLayout();
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void collapse(final View v, int duration, int targetHeight) {
        int prevHeight  = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            v.getLayoutParams().height = (int) animation.getAnimatedValue();
            v.requestLayout();
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();

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
//        binding.imageCarousel.registerLifecycle(getViewLifecycleOwner());
        downloader = new Downloader(requireContext(), binding, appInfoViewModel);
        downloader.setup();
    }

    public long getFileSize(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            return conn.getContentLengthLong();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


    private void installApk(){
        //TODO WorkManager/Service
        if (!requireContext().getPackageManager().canRequestPackageInstalls()) {
            PermissionDialog permissionDialog = new PermissionDialog();
            permissionDialog.show(getChildFragmentManager(), "test");
        } else {
            KotlinInstallMotor motor = new KotlinInstallMotor(requireActivity().getApplication());
            motor.install(Uri.parse(outputFile.toURI().toString()));
            Log.d("Worker Class (Not)", "installApk: " + Uri.parse(outputFile.toString()));

        }
    }

}


