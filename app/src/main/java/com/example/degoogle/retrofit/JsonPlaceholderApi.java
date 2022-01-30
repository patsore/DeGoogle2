package com.example.degoogle.retrofit;

import com.example.degoogle.model.AllCategories;
import com.example.degoogle.model.CategoryChild;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceholderApi {


    @GET("index-v1.json")
    Call<ArrayList<AllCategories>> allCats();

}
