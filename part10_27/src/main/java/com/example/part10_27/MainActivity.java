package com.example.part10_27;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.part10_27.databinding.ActivityMainBinding;
import com.example.part10_27.databinding.ItemMainBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String QUERY = "movies";
    private static final String API_KEY = "f3d0e90cba864b2fba2e250e06d14ed5";

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RetrofitService networkService = RetrofitFactory.create();

        networkService.getList(QUERY, API_KEY, 1, 10).enqueue(new Callback<PageListModel>() {
            @Override
            public void onResponse(Call<PageListModel> call, Response<PageListModel> response) {
                if (response.isSuccessful()) {
                    MyAdapter adapter = new MyAdapter(response.body().articles);
                    binding.recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<PageListModel> call, Throwable t) {

            }
        });
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ItemMainBinding binding;

        public ItemViewHolder(ItemMainBinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    class MyAdapter extends RecyclerView.Adapter<ItemViewHolder> {
        List<ItemModel> articles;

        public MyAdapter(List<ItemModel> articles) {
            this.articles = articles;
        }

        @Override
        public int getItemCount() {
            return articles.size();
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            ItemMainBinding binding=ItemMainBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
            return new ItemViewHolder((binding));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
            ItemModel model=articles.get(i);
            itemViewHolder.binding.setItem(model);
        }
    }

    @BindingAdapter("publishedAt")
    public static void publishedAt(TextView view, String date){
        view.setText(AppUtils.getDate(date)+" at "+AppUtils.getTime(date));
    }
    @BindingAdapter("urlToImage")
    public static void urlToImage(ImageView view, String url){
        Glide.with(MyApplication.getAppContext()).load(url).override(250, 200).into(view);
    }
}