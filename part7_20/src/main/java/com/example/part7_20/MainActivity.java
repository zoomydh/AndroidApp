package com.example.part7_20;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView playBtn;
    ImageView stopBtn;
    ProgressBar progressBar;
    TextView titleView;

    String filePath;
    boolean runThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBtn=findViewById(R.id.lab1_play);
        stopBtn=findViewById(R.id.lab1_stop);
        progressBar=findViewById(R.id.lab1_progress);
        titleView=findViewById(R.id.lab1_title);

        playBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);

        titleView.setText("overworld_1.mp3");
        stopBtn.setEnabled(false);

        filePath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/Music/overworld_1.mp3";

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }
        registerReceiver(receiver, new IntentFilter("com.example.PLAY_TO_ACTIVITY"));

        Intent intent=new Intent(this, PlayService.class);
        intent.putExtra("filePath", filePath);
        startService(intent);
    }

    @Override
    public void onClick(View view) {
        if(view==playBtn){
            Intent intent=new Intent("com.example.PLAY_TO_SERVICE");
            intent.putExtra("mode", "start");
            sendBroadcast(intent);

            runThread=true;
            ProgressThread thread=new ProgressThread();
            thread.start();
            playBtn.setEnabled(false);
            stopBtn.setEnabled(true);
        }else if(view==stopBtn){
            Intent intent=new Intent("com.example.PLAY_TO_SERVICE");
            intent.putExtra("mode","stop");
            sendBroadcast(intent);
            runThread=false;
            progressBar.setProgress(0);
            playBtn.setEnabled(true);
            stopBtn.setEnabled(false);
        }
    }

    class ProgressThread extends Thread {
        @Override
        public void run() {
            while (runThread) {
                progressBar.incrementProgressBy(1000);
                SystemClock.sleep(1000);
                if (progressBar.getProgress() == progressBar.getMax()) {
                    runThread = false;
                }
            }
        }
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String mode=intent.getStringExtra("mode");
            if(mode != null){
                if(mode.equals("start")){
                    int duration=intent.getIntExtra("duration",0);
                    progressBar.setMax(duration);
                    progressBar.setProgress(0);
                }else if(mode.equals("stop")){
                    runThread=false;
                }else if(mode.equals("restart")){
                    int duration=intent.getIntExtra("duration",0);
                    int current=intent.getIntExtra("current", 0);
                    progressBar.setMax(duration);
                    progressBar.setProgress(current);
                    runThread=true;
                    ProgressThread thread=new ProgressThread();
                    thread.start();

                    playBtn.setEnabled(false);
                    stopBtn.setEnabled(true);
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}