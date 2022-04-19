package com.example.degoogle.ui.updates;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.degoogle.data.RoomPackagesDatabase;
import com.example.degoogle.data.dao.PackagesDao;
import com.example.degoogle.data.entities.InstalledPackages;
import com.example.degoogle.model.Product;
import com.example.degoogle.repositories.PackageRepository;
import com.example.degoogle.workers.DownloadWorker;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.util.List;

public class UpdatesViewModel extends AndroidViewModel {
    public RoomPackagesDatabase mDatabase;
    public MutableLiveData<String> installedPackages;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    private PackageRepository packageRepository;
    WorkManager workManager;
    MutableLiveData<Product> product = new MutableLiveData<>();
    private final LiveData<List<InstalledPackages>> installedPackagesRoom;

    public UpdatesViewModel(Application application) {
        super(application);
        workManager = WorkManager.getInstance(application);
        packageRepository = new PackageRepository(application);
        installedPackagesRoom = packageRepository.getAllPackages();
        Log.d("TAG", "UpdatesViewModel: " + packageRepository.getAllPackages());
    }

    public void deletePackageFromRoom(InstalledPackages installedPackage){
        packageRepository.delete(installedPackage);
    }
    LiveData<List<InstalledPackages>> getInstalledPackagesRoom(){
        return installedPackagesRoom;
    }

    public void getFirebase(String id){
        db.collection("apps").document(id).get().addOnCompleteListener(task -> {

        });

    }

    public void changePackage(InstalledPackages installedPackage){

    }

    public LiveData<Product> getPackageInfo() {return product;}
    public void installApk(String packageName, File file) {
        Data data = new Data.Builder().putString("file", file.toURI().toString()).build();
        OneTimeWorkRequest.Builder downloadWorker = new OneTimeWorkRequest.Builder(DownloadWorker.class).setInputData(data);
        workManager.enqueueUniqueWork("packageInstall", ExistingWorkPolicy.KEEP ,downloadWorker.build());
    }

}
