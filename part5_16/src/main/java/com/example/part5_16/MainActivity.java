package com.example.part5_16;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "Handler";
    ImageView startView;
    ImageView pauseView;
    TextView textView;
    boolean loopFlag = true;
    boolean isFirst = true;
    boolean isRun;

    MyThread thread;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == 1)
            {
                textView.setText(String.valueOf(msg.arg1));
            }
            else if (msg.what == 2)
            {
                textView.setText((String) msg.obj);
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startView = (ImageView) findViewById(R.id.main_startBtn);
        pauseView = (ImageView) findViewById(R.id.main_pauseBtn);
        textView = (TextView) findViewById(R.id.main_textView);

        startView.setOnClickListener(this);
        pauseView.setOnClickListener(this);

        thread = new MyThread();
    }

    @Override
    public void onClick(View v) {
        if (v == startView) {
            Log.d(TAG,"Start");
            if (isFirst) {
                isFirst = false;
                isRun = true;
                thread.start();
            } else {
                isRun = true;
            }
        } else if (v == pauseView) {
            Log.d(TAG,"Stop");
            isRun = false;
        }
    }


    class MyThread extends Thread
    {
        @Override
        public void run() {
            super.run();
            try {
                int count = 10;
                while (loopFlag)
                {
                    Thread.sleep(1000);
                    if (isRun)
                    {
                        count--;
                        Message message = new Message();
                        message.what = 1;
                        message.arg1 = count;
                        handler.sendMessage(message);
                        if (count == 0)
                        {
                            message = new Message();
                            message.what = 2;
                            message.obj = "Finish!!";
                            handler.sendMessage(message);
                            loopFlag = false;
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}