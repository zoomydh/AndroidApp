package com.example.part10_30.retrofit;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private static String BASE_URL ="https://mewsapi.org";
    static final String TAG = RetrofitFactory.class.getSimpleName();
    public static RetrofitService create() {
        Log.d(TAG,"+++ create : start");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Log.d(TAG,"--- create : end");
        return retrofit.create(RetrofitService.class);
    }
}
