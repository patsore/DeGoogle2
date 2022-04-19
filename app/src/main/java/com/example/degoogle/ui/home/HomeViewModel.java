package com.example.degoogle.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.degoogle.model.AllCategories;
import com.example.degoogle.model.CategoryChild;
import com.example.degoogle.network.JsonPlaceholderApi;
import com.example.degoogle.network.RetrofitClient;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeViewModel extends ViewModel {

    public String TAG = "HomeViewModel";

    private MutableLiveData<Map<String,ArrayList<CategoryChild>>> allCategories;
    Map<String, ArrayList<CategoryChild>> tempCategories = new HashMap<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public HomeViewModel() {
        firebaseIntegration();
    }

    private void getCategories() {

//        allCategories = new MutableLiveData<>();
        Retrofit retrofit = RetrofitClient.getInstance();
        JsonPlaceholderApi api = retrofit.create(JsonPlaceholderApi.class);
        Call<Map<String, ArrayList<CategoryChild>>> call = api.allCats();
        call.enqueue(new Callback<Map<String, ArrayList<CategoryChild>>>() {
            @Override
            public void onResponse(Call<Map<String, ArrayList<CategoryChild>>> call, Response<Map<String, ArrayList<CategoryChild>>> response) {
                Log.d(TAG, "onResponse: SUCCESS");
//                if(response.body()!=null)allCategories.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Map<String, ArrayList<CategoryChild>>> call, Throwable t) {
                Log.d(TAG, "onFailure: failed");
            }
        });
    }


    private void firebaseIntegration() {
        allCategories = new MutableLiveData<>();
        db.collection("apps")
                .get()
                .addOnCompleteListener(task -> {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        String category = document.getString("category");
                        if (!tempCategories.containsKey(category)) {
                            tempCategories.put(category, new ArrayList<>(Collections.singletonList(document.toObject(CategoryChild.class))));
                        }else{
                            tempCategories.get(category).add(document.toObject(CategoryChild.class));
                        }

                        allCategories.setValue(tempCategories);
                        allCategories.postValue(allCategories.getValue());
                    }
                }).addOnFailureListener(e -> Log.d(TAG, "onFailure: getting firebase failed"));
    }




    public LiveData<Map<String,ArrayList<CategoryChild>>> getFirebaseData(){return allCategories;}

}