package com.example.degoogle.retrofit;

import com.example.degoogle.model.AllCategories;
import com.example.degoogle.model.CategoryChild;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface jsonPlaceholderApi {


    @GET("v3/212af42b-bbb9-454f-be8f-8cbc8c66695b")
    Call<ArrayList<AllCategories>> allCats();

}
