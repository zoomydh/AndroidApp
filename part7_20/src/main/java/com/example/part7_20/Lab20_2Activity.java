package com.example.part7_20;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.part7_20_aidl.IPlayService;

public class Lab20_2Activity extends AppCompatActivity implements View.OnClickListener {

    ImageView playBtn;
    ImageView stopBtn;
    ProgressBar progressBar;
    TextView titleView;

    String filePath;
    boolean runThread;

    IPlayService pService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab20_2);

        playBtn=findViewById(R.id.lab2_play);
        stopBtn=findViewById(R.id.lab2_stop);
        progressBar=findViewById(R.id.lab2_progress);
        titleView=findViewById(R.id.lab2_title);

        playBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);

        titleView.setText("overworld_1.mp3");
        stopBtn.setEnabled(true);
        playBtn.setEnabled(true);

        filePath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/Music/overworld_1.mp3";

        Intent intent=new Intent("com.example.part7_20.ACTION_PLAY");
        intent.setPackage("com.example.part7_20_aidl");
        intent.putExtra("filePath", filePath);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            pService=IPlayService.Stub.asInterface(iBinder);
            playBtn.setEnabled(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            pService=null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
        runThread=false;
    }

    @Override
    public void onClick(View view) {
        if(view==playBtn){
            Log.d("Button","Play");
            if(pService != null){
                try{
                    pService.start();
                    Log.d("Button","enter in start");
                    progressBar.setMax(pService.getMaxDuration());
                    runThread=true;
                    ProgressThread thread=new ProgressThread();
                    thread.start();
                    playBtn.setEnabled(false);
                    stopBtn.setEnabled(true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else if(view==stopBtn){
            Log.d("Button","Stop");
            if(pService != null){
                try{
                    pService.stop();
                    Log.d("Button","stop in start");
                    runThread=false;
                    progressBar.setProgress(0);
                    playBtn.setEnabled(true);
                    stopBtn.setEnabled(false);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
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
}