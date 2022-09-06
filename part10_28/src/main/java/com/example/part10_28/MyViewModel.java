package com.example.part10_28;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyViewModel extends ViewModel {
    private static final String QUERY = "flower";
    private static final String API_KEY = "f3d0e90cba864b2fba2e250e06d14ed5";
    RetrofitService networkService = RetrofitFactory.create();

    public MutableLiveData<List<ItemModel>> getNews() {
        MutableLiveData<List<ItemModel>> liveData = new MutableLiveData<>();
        networkService.getList(QUERY, API_KEY, 1, 10).enqueue(new Callback<PageListModel>() {
            @Override
            public void onResponse(Call<PageListModel> call, Response<PageListModel> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body().articles);
                }
            }

            @Override
            public void onFailure(Call<PageListModel> call, Throwable t) {

            }
        });
        return liveData;
    }
}
