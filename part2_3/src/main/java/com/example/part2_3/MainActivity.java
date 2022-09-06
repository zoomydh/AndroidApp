package com.example.part2_3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout linear = new LinearLayout(this);

        Button btn = new Button(this);
        btn.setText("Button 1");
        linear.addView(btn);

        Button btn2 = new Button(this);
        btn2.setText("Button 2");
        linear.addView(btn2);

        setContentView(linear);
    }
}