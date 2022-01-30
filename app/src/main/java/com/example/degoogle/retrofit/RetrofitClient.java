package com.example.degoogle.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String BASE_URL = "https://run.mocky.io/";
    private static Retrofit ourInstance;

    public static Retrofit getInstance() {
        if(ourInstance == null)
            ourInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return ourInstance;
    }

    private RetrofitClient() {
    }
}
