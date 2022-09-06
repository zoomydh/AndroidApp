package com.example.part10_29;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.part10_29.model.ItemModel;
import com.example.part10_29.model.PageListModel;
import com.example.part10_29.retrofit.RetrofitFactory;
import com.example.part10_29.retrofit.RetrofitService;
import com.example.part10_29.room.AppDatabase;
import com.example.part10_29.room.ArticleDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyViewModel extends ViewModel {
    private static final String QUERY = "flower";
    private static final String API_KEY = "f3d0e90cba864b2fba2e250e06d14ed5";
    RetrofitService networkService = RetrofitFactory.create();

    AppDatabase db = Room.databaseBuilder(MyApplication.getAppContext(), AppDatabase.class, "database-name").build();
    ArticleDAO dao = db.articleDao();

    public MutableLiveData<List<ItemModel>> getNews() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return getNewsFromNetwork();
        } else {
            MutableLiveData<List<ItemModel>> liveData = new MutableLiveData<>();
            new GetAllThread(liveData).start();
            return liveData;
        }
    }

    private MutableLiveData<List<ItemModel>> getNewsFromNetwork() {
        Log.d("TEST", "getNewsFromNetwork......");
        MutableLiveData<List<ItemModel>> liveData = new MutableLiveData<>();
        networkService.getList(QUERY, API_KEY, 1, 10).enqueue(new Callback<PageListModel>() {
            @Override
            public void onResponse(Call<PageListModel> call, Response<PageListModel> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body().articles);
                    new InsertDataThread(response.body().articles).start();
                }
            }

            @Override
            public void onFailure(Call<PageListModel> call, Throwable t) {

            }
        });
        return liveData;
    }

    class GetAllThread extends Thread {
        MutableLiveData<List<ItemModel>> liveData;
        public GetAllThread(MutableLiveData<List<ItemModel>> liveData) {
            this.liveData = liveData;
        }

        @Override
        public void run() {
            Log.d("TEST", "db select......");
            List<ItemModel> daoData = dao.getAll();
            liveData.postValue(daoData);
        }
    }

    class InsertDataThread extends Thread {
        List<ItemModel> articles;
        public InsertDataThread(List<ItemModel> articles) {
            this.articles = articles;
        }

        @Override
        public void run() {
            Log.d("TEST", "db delete... insert...");
            dao.deleteAll();
            dao.insertAll(articles.toArray(new ItemModel[articles.size()]));
        }
    }
}
