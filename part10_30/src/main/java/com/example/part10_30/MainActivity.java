package com.example.part10_30;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.part10_30.adapter.MyListAdapter;
import com.example.part10_30.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    static final String TAG = MainActivity.class.getSimpleName();
    ActivityMainBinding binding;
    MyListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "+++ onCreate : start");
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyListAdapter(getApplicationContext());

        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        viewModel.getNews().observe(this, news-> {adapter.submitList(news);});
        binding.recyclerView.setAdapter(adapter);
        Log.d(TAG, "--- onCreate : end");
    }

    @BindingAdapter("publishedAt")
    public static void publishedAt(TextView view, String date) {
        Log.d(TAG, "+++ publishedAt : start");
        view.setText(AppUtils.getDate(date) + " at " + AppUtils.getTime(date));
        Log.d(TAG, "--- publishedAt : end");
    }

    @BindingAdapter("urlToImage")
    public static void urlToImage(ImageView view, String url) {
        Log.d(TAG, "+++ urlToImage : start");
        Glide.with(MyApplication.context).load(url).override(250, 200).into(view);
        Log.d(TAG, "--- urlToImage : end");
    }
}