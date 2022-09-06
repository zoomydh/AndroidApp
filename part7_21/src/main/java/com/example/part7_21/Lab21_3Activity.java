package com.example.part7_21;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class Lab21_3Activity extends AppCompatActivity {
    ImageView gifView;
    ImageView networkView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab21_3);
        gifView = findViewById(R.id.lab3_gif);
        networkView = findViewById(R.id.lab3_network);

        Glide.with(this).asGif().load(R.raw.loading).diskCacheStrategy(DiskCacheStrategy.RESOURCE).override(200, 200).into(gifView);
        String url = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";

        Glide.with(this).load(url).override(400, 400).error(R.drawable.error).into(networkView);
    }
}