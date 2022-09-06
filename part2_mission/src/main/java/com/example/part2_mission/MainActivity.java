package com.example.part2_mission;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button check;
    TextView setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        check = findViewById(R.id.check);
        setting = findViewById(R.id.setting);
        check.setOnClickListener(this);
        setting.setOnClickListener(this);

    }

    private void showToast(String message)
    {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onClick(View v)
    {
        if(v == check)
        {
            showToast("check button click~~~");
        }
        else if (v == setting)
        {
            showToast("setting button click~~~");
        }

    }
}