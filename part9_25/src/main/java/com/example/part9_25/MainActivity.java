package com.example.part9_25;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    TextView titleView;
    TextView dateView;
    TextView contentView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleView=(TextView)findViewById(R.id.lab1_title);
        dateView=(TextView)findViewById(R.id.lab1_date);
        contentView=(TextView)findViewById(R.id.lab1_content);
        imageView=(ImageView)findViewById(R.id.lab1_image);

        //서버에 전송할 데이터
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "kkang");

        //문자열 http 요청
        HttpRequester httpRequester = new HttpRequester();
        httpRequester.request("http://172.30.1.45:8000/files/test.json", map, httpCallback);
    }

    //문자열 결과 획득 callback 클래스
    HttpCallback httpCallback = new HttpCallback() {
        @Override
        public void onResult(String result) {
            try {
                JSONObject root = new JSONObject(result);
                titleView.setText(root.getString("title"));
                dateView.setText(root.getString("date"));
                contentView.setText(root.getString("content"));

                String imageFile = root.getString("file");
                if (imageFile != null && !imageFile.equals(""))
                {
                    HttpImageRequester imageRequester = new HttpImageRequester();
                    imageRequester.request("http://172.30.1.45:8000/files/"+imageFile, null, imageCallback);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    HttpImageCallback imageCallback = new HttpImageCallback() {
        @Override
        public void onResult(Bitmap d) {
            imageView.setImageBitmap(d);
        }
    };
}