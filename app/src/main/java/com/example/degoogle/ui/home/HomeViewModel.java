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
//        getCategories();
        firebaseIntegration();

    }

    private void getCategories() {

        allCategories = new MutableLiveData<>();
        Retrofit retrofit = RetrofitClient.getInstance();
        JsonPlaceholderApi api = retrofit.create(JsonPlaceholderApi.class);
        Call<ArrayList<AllCategories>> call = api.allCats();
        call.enqueue(new Callback<ArrayList<AllCategories>>() {
            @Override
            public void onResponse(Call<ArrayList<AllCategories>> call, Response<ArrayList<AllCategories>> response) {
                Log.d(TAG, "onResponse: SUCCESS");
//                if(response.body()!=null)allCategories.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<AllCategories>> call, Throwable t) {
                Log.d(TAG, "onFailure: failed");
            }
        });
    }


    private void firebaseIntegration() {


        ArrayList<CategoryChild> categoryChildren = new ArrayList<>();

        allCategories = new MutableLiveData<>();
        db.collection("messaging")
                .get()
                .addOnCompleteListener(task -> {





                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        String category = document.getString("category");
                        long longItemPos = document.getLong("id");
                        int id = Math.toIntExact(longItemPos);
                        boolean exists = tempCategories.containsKey(category);

                        categoryChildren.add(document.toObject(CategoryChild.class));
                        if (!exists) {
                            tempCategories.put(category, new ArrayList<>(Collections.singletonList(document.toObject(CategoryChild.class))));
                        }else{
                            tempCategories.get(category).add(document.toObject(CategoryChild.class));

                        }

                        Log.d(TAG, "firebaseIntegration: " + allCategories.getValue());
                        allCategories.setValue(tempCategories);
                        allCategories.postValue(allCategories.getValue());
                    }


//
//
//                        if (!apps.containsKey(id)) {
//
//                            apps.put(id, new ArrayList<CategoryChild>(Collections.singletonList(new CategoryChild(document.getString("description"), document.getString("name"), document.getString("icon"), id))));
//
//                        }
//
//
//                        tempCategories.put(category, new ArrayList<CategoryChild>(Arrays.asList(new CategoryChild(document.getString("description"), document.getString("name"), document.getString("icon"), id))));
//                        Objects.requireNonNull(tempCategories.get(category)).add(new CategoryChild(document.getString("description"), document.getString("name"), document.getString("icon"), id));
//
////                        Objects.requireNonNull(allCategories.getValue().get(category)).sort(new Comparator<CategoryChild>() {
////                            @Override
////                            public int compare(CategoryChild categoryChild, CategoryChild t1) {
////                                return Integer.compare(categoryChild.getAppId(), t1.getAppId());
////                            }
////                        });

//}
//                    for (CategoryChild item:
//                    categoryChildren) {
//                        switch (item.getCategory()){
//
//                            case "testing": {
//
//
//
//                            }
//
//                            case "messaging": {
//
//
//
//                            }
//
//                        }
//                    }

                }).addOnFailureListener(e ->




                Log.d(TAG, "onFailure: getting firebase failed")



                                        );


//        allCategories.postValue(allCategories.getValue());
        Log.d(TAG, "firebaseIntegration: success");
    }




    public LiveData<Map<String,ArrayList<CategoryChild>>> getFirebaseData(){return allCategories;}

}