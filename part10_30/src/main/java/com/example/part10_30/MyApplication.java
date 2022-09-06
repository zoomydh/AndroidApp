package com.example.part10_30;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.example.part10_30.retrofit.RetrofitFactory;
import com.example.part10_30.retrofit.RetrofitService;
import com.example.part10_30.room.AppDatabase;
import com.example.part10_30.room.ArticleDAO;

public class MyApplication extends Application {
    public static Context context;
    public static ArticleDAO dao;
    public static RetrofitService networkService;
    static final String TAG = MyApplication.class.getSimpleName();
    @Override
    public void onCreate() {
        Log.d(TAG, "+++ onCreate : start");
        super.onCreate();
        context = getApplicationContext();
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "database-name").build();
        dao = db.articleDao();
        networkService = RetrofitFactory.create();
        Log.d(TAG, "--- onCreate : end");
    }
}
