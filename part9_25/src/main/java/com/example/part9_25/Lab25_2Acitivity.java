package com.example.part9_25;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Lab25_2Acitivity extends AppCompatActivity {

    TextView titleView;
    TextView dateView;
    TextView contentView;
    NetworkImageView imageView;

    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab25_2);

        titleView = (TextView) findViewById(R.id.lab2_title);
        dateView = (TextView) findViewById(R.id.lab2_date);
        contentView = (TextView) findViewById(R.id.lab2_content);
        imageView = findViewById(R.id.lab2_image);

        queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, "http://172.30.1.45:8000/files/test.json", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    titleView.setText(response.getString("title"));
                    dateView.setText(response.getString("date"));
                    contentView.setText(response.getString("content"));

                    String imagefile = response.getString("file");
                    if (imagefile != null && !imagefile.equals("")) {
                        ImageLoader imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
                            @Nullable
                            @Override
                            public Bitmap getBitmap(String url) {
                                return null;
                            }

                            @Override
                            public void putBitmap(String url, Bitmap bitmap) {

                            }
                        });

                        imageView.setImageUrl("http://172.30.1.45:8000/files/"+imagefile, imageLoader);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonRequest);
    }
}