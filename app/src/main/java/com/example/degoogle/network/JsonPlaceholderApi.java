package com.example.degoogle.network;

import com.example.degoogle.model.AllCategories;
import com.example.degoogle.model.CategoryChild;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceholderApi {


    @GET("https://ggvvzojafamcdfyvzfbv.supabase.co")
    Call<Map<String, ArrayList<CategoryChild>>> allCats();

}
