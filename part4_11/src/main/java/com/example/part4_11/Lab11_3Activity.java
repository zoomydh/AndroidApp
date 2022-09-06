package com.example.part4_11;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Lab11_3Activity extends AppCompatActivity {

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab113);

        Spinner spinner = findViewById(R.id.spinner);
        String[] datas =getResources().getStringArray(R.array.spinner_array);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, datas);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        AutoCompleteTextView  autoCompleteTextView = findViewById(R.id.auto);
        String[] autoDatas = getResources().getStringArray(R.array.auto_array);
        ArrayAdapter<String> autoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, autoDatas);
        autoCompleteTextView.setAdapter(autoAdapter);

        progressBar = findViewById(R.id.progress);
        ProgressThread thread = new ProgressThread();
        thread.start();
    }

    class ProgressThread extends Thread
    {
        @Override
        public void run() {
            for (int i=0; i<10; i++)
            {
                SystemClock.sleep(1000);
                progressBar.incrementProgressBy(10);
                progressBar.incrementSecondaryProgressBy(15);
            }
        }
    }
}