package com.example.part10_30.datasource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.part10_30.MyApplication;
import com.example.part10_30.model.ItemModel;
import com.example.part10_30.model.PageListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDataSource extends PageKeyedDataSource<Long, ItemModel> {

    private static final String QUERY = "travel";
    private static final String API_KEY = "f3d0e90cba864b2fba2e250e06d14ed5";
    static final String TAG = MyDataSource.class.getSimpleName();
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, ItemModel> callback) {
        Log.d(TAG, "+++ loadInitial : start");
        MyApplication.networkService.getList(QUERY, API_KEY, 1, params.requestedLoadSize).enqueue(new Callback<PageListModel>() {
            @Override
            public void onResponse(Call<PageListModel> call, Response<PageListModel> response) {
                Log.d(TAG, "+++ onResponse1 : start"+response.isSuccessful());
                if (response.isSuccessful()) {
                    callback.onResult(response.body().articles, null, 2L);
                    new InsertDataThread(response.body().articles).start();
                }
                Log.d(TAG, "--- onResponse1 : end");
            }

            @Override
            public void onFailure(Call<PageListModel> call, Throwable t) {

            }
        });
        Log.d(TAG, "--- loadInitial : end");
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, ItemModel> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, ItemModel> callback) {
        Log.d(TAG, "+++ loadAfter : start");
        MyApplication.networkService.getList(QUERY, API_KEY, params.key, params.requestedLoadSize).enqueue(new Callback<PageListModel>() {
            @Override
            public void onResponse(Call<PageListModel> call, Response<PageListModel> response) {
                Log.d(TAG, "+++ onResponse2 : start");
                if (response.isSuccessful()) {
                    long nextKey = (params.key == response.body().totalResults) ? null : params.key + 1;
                    callback.onResult(response.body().articles, nextKey);
                }
                Log.d(TAG, "--- onResponse2 : end");
            }

            @Override
            public void onFailure(Call<PageListModel> call, Throwable t) {

            }
        });
        Log.d(TAG, "--- loadAfter : end");
    }

    class InsertDataThread extends Thread {
        List<ItemModel> articles;
        public InsertDataThread(List<ItemModel> articles) {
            this.articles = articles;
        }

        @Override
        public void run() {
            MyApplication.dao.deleteAll();
            MyApplication.dao.insertAll(articles.toArray(new ItemModel[articles.size()]));
        }
    }
}
