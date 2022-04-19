package com.example.degoogle.ui.info;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.degoogle.data.entities.InstalledPackages;
import com.example.degoogle.model.Product;
import com.example.degoogle.repositories.PackageRepository;
import com.example.degoogle.workers.DownloadWorker;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.util.List;

public class AppInfoViewModel extends AndroidViewModel{
    private PackageRepository packageRepository;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MutableLiveData<Product> packageInfo = new MutableLiveData<>();
    private WorkManager workManager;
    private final LiveData<List<InstalledPackages>> installedPackages;

    public AppInfoViewModel(Application application) {
        super(application);
        packageRepository = new PackageRepository(application);
        installedPackages = packageRepository.getAllPackages();
        workManager = WorkManager.getInstance(application);
    }

    public LiveData<List<InstalledPackages>> getAllPackages() {
        return installedPackages;
    }
    public void insert(InstalledPackages installedPackages) { packageRepository.insert(installedPackages);
        Log.d("AppInfoViewModel: ", "inserted" + installedPackages.getPackageName() + " " + installedPackages.getPackageVersion());
        Log.d("AppInfoViewModel: ", "table content" + packageRepository.getAllPackages().getValue());
    }


    public void getDataFromFirebase(String id) {

        db.collection("apps")
                .document(id)
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot document = task.getResult();
//                    packageInfo.setValue(document.toObject(AppInfoModel.class));
                    packageInfo.postValue(document.toObject(Product.class));
                    Log.d("ViewModelAppInfo", "getDataFromFirebase: " + packageInfo.getValue());
                    Log.d("ViewModelAppInfo", "getDataFromFirebase: " + document.toObject(Product.class));
                 });


    }
    public Product getModel(){return packageInfo.getValue();}
    public LiveData<Product> getPackageInfo() {return packageInfo;}

    public void installApk(String packageName, File file) {
        Data data = new Data.Builder().putString("file", file.toURI().toString()).build();
        OneTimeWorkRequest.Builder downloadWorker = new OneTimeWorkRequest.Builder(DownloadWorker.class).setInputData(data);
        workManager.enqueueUniqueWork("packageInstall", ExistingWorkPolicy.KEEP ,downloadWorker.build());
    }
}
