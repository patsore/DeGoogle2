package com.example.degoogle.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PublicApi {

    private static Retrofit retrofit;

    public static Retrofit getClient(){

        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("G:\\test\\DeGoogle2\\app\\src\\main\\assets\\")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;

    }


}
