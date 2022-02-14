package com.example.degoogle.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.degoogle.model.AllCategories;
import com.example.degoogle.retrofit.JsonPlaceholderApi;
import com.example.degoogle.retrofit.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeViewModel extends ViewModel {

    public String TAG = "HomeViewModel";

    private MutableLiveData<ArrayList<AllCategories>> allCategories;

    public HomeViewModel() {
        getCategories();
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
                if(response.body()!=null)allCategories.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<AllCategories>> call, Throwable t) {
                Log.d(TAG, "onFailure: failed");
            }
        });
    }



    public LiveData<ArrayList<AllCategories>> getAllCategories(){return allCategories;}

}