package com.example.part10_30;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.part10_30.datasource.MyDataFactory;
import com.example.part10_30.model.ItemModel;

public class MyViewModel extends ViewModel {
    private LiveData<PagedList<ItemModel>> itemLiveData;
    PagedList.Config pagedListConfig;
    static final String TAG = MyViewModel.class.getSimpleName();
    public  MyViewModel() {
        Log.d(TAG, "+++ MyViewModel : start");
        pagedListConfig = new PagedList.Config.Builder().setPageSize(1).setPrefetchDistance(3).setEnablePlaceholders(true).build();
        Log.d(TAG, "--- MyViewModel : end");
    }

    public LiveData<PagedList<ItemModel>> getNews() {
        Log.d(TAG, "+++ getNews : start");
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            MyDataFactory dataFactory = new MyDataFactory();
            itemLiveData = new LivePagedListBuilder<>(dataFactory, pagedListConfig).build();
            Log.d(TAG, "--- getNews1 : end");
            return itemLiveData;
        } else {
            DataSource.Factory<Integer, ItemModel> factory = MyApplication.dao.getAll();
            itemLiveData = new LivePagedListBuilder<>(factory, pagedListConfig).build();
            Log.d(TAG, "--- getNews2 : end");
            return itemLiveData;
        }
    }
}
