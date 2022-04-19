package com.example.degoogle.ui.updates;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.degoogle.adapter.UpdatesRecyclerViewAdapter;
import com.example.degoogle.data.entities.InstalledPackages;
import com.example.degoogle.databinding.FragmentUpdatesBinding;
import com.example.degoogle.interfaces.UpdateButtonCallback;
import com.example.degoogle.model.Product;
import com.example.degoogle.network.Downloader;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UpdatesFragment extends Fragment implements UpdateButtonCallback {
    private UpdatesViewModel updatesViewModel;
    private FragmentUpdatesBinding binding;
    private Downloader downloader;
    Product product = new Product();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    File outputFile;
    LiveData<List<InstalledPackages>> installedPackages;
    List<PackageInfo> installedPackagesPhone = new ArrayList<>();
    ArrayList<String> installedPackagesNames = new ArrayList<>();
    MutableLiveData<ArrayList<Product>> productsToDisplayinUpdateRecyclerView = new MutableLiveData<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        updatesViewModel = new ViewModelProvider(this).get(UpdatesViewModel.class);
        downloader = new Downloader(getContext(), binding, updatesViewModel);
        downloader.setup();
        binding = FragmentUpdatesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        Log.d("testsetsetes", "onCreateView: " + requireActivity().getPackageManager().getInstalledPackages(0));
        installedPackages = updatesViewModel.getInstalledPackagesRoom();
        observe();
        installedPackagesPhone = requireActivity().getPackageManager().getInstalledPackages(0);
        for (PackageInfo packageInfo : installedPackagesPhone){
            installedPackagesNames.add(packageInfo.packageName);
        }
        productsToDisplayinUpdateRecyclerView.observe(getViewLifecycleOwner(), this::setRecyclerAdapter);
        return root;

    }

    private void checkIfPackageStillExists(List<InstalledPackages> installedPackages){
        ArrayList<InstalledPackages> toRemove = new ArrayList<>();
        for (InstalledPackages installedPackage : installedPackages){
            if (!installedPackagesNames.contains(installedPackage.getPackageName())){

                updatesViewModel.deletePackageFromRoom(installedPackage);
                toRemove.add(installedPackage);
            }
        }
        installedPackages.removeAll(toRemove);
        checkForUpdates(installedPackages);
    }
    private void observe(){
        installedPackages.observe(getViewLifecycleOwner(), this::checkIfPackageStillExists);
    }


    public void checkForUpdates(List<InstalledPackages> installedPackages){
        ArrayList<Product> updatesChecker = new ArrayList<>();
        for (InstalledPackages installedPackage: installedPackages) {
            db.collection("apps").document(installedPackage.getPackageName()).get().addOnCompleteListener(task -> {

                if(!task.getResult().get("version").toString().equals(installedPackage.getPackageVersion())){
                    Log.d("TAG", "checkForUpdates: " + task.getResult().get("version") + " " + installedPackage.getPackageVersion());
                    updatesChecker.add(task.getResult().toObject(Product.class));
                    Log.d("TAG", "checkForUpdates: " + task.getResult().toObject(Product.class));
                    productsToDisplayinUpdateRecyclerView.postValue(updatesChecker);
                }
            });
        }
    }



    public void setRecyclerAdapter(ArrayList<Product> products){
//        Log.d("TAG", "setRecyclerAdapter: " + pr);
        binding.updatesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        binding.updatesRecyclerView.setAdapter(new UpdatesRecyclerViewAdapter(requireContext(), products, this));
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onUpdateButtonClicked(String packageName) {

        updatesViewModel.getFirebase(packageName);
        db.collection("apps").document(packageName).get().addOnCompleteListener(task -> product = task.getResult().toObject(Product.class));
        outputFile = new File(getContext().getExternalFilesDir("downloads"), product.getPackageName() + ".apk");
        downloader.downloadStart(product.getDownloadUrl() ,packageName, outputFile);
    }
}
