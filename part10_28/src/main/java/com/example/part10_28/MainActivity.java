package com.example.part10_28;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.part10_28.databinding.ActivityMainBinding;
import com.example.part10_28.databinding.ItemMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        viewModel.getNews().observe(this, news -> {
            MyAdapter adapter = new MyAdapter(news);
            binding.recyclerView.setAdapter(adapter);
        });

    }

    class ItemviewHolder extends RecyclerView.ViewHolder {
        ItemMainBinding binding;

        public ItemviewHolder(ItemMainBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class MyAdapter extends RecyclerView.Adapter<ItemviewHolder> {
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
        public ItemviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemMainBinding binding = ItemMainBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ItemviewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemviewHolder holder, int position) {
            ItemModel model = articles.get(position);
            holder.binding.setItem(model);
        }
    }

    @BindingAdapter("publishedAt")
    public static void publishedAt(TextView view, String date) {
        view.setText(AppUtils.getDate(date) + " at "+AppUtils.getTime(date));
    }

    @BindingAdapter("urlToImage")
    public static void urlToImage(ImageView view,String url) {
        Glide.with(MyApplication.getAppContext()).load(url).override(250, 200).into(view);
    }
}