package com.example.part7_19;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.Person;
import androidx.core.graphics.drawable.IconCompat;

public class Lab19_2Activity extends AppCompatActivity implements View.OnClickListener{

    Button basicBtn;
    Button bigPictureBtn;
    Button bigTextBtn;
    Button inboxBtn;
    Button progressBtn;
    Button headsupBtn;
    Button messageBtn;

    NotificationManager manager;
    NotificationCompat.Builder builder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab19_2);

        basicBtn=(Button)findViewById(R.id.lab2_basic);
        bigPictureBtn=(Button)findViewById(R.id.lab2_bigpicture);
        bigTextBtn=(Button)findViewById(R.id.lab2_bigtext);
        inboxBtn=(Button)findViewById(R.id.lab2_inbox);
        progressBtn=(Button)findViewById(R.id.lab2_progress);
        headsupBtn=(Button)findViewById(R.id.lab2_headsup);
        messageBtn=(Button)findViewById(R.id.lab2_message);

        basicBtn.setOnClickListener(this);
        bigPictureBtn.setOnClickListener(this);
        bigTextBtn.setOnClickListener(this);
        inboxBtn.setOnClickListener(this);
        progressBtn.setOnClickListener(this);
        headsupBtn.setOnClickListener(this);
        messageBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "one-channel";
            String channelName = "My Channel One";
            String channelDescription = "My Channel One Description";

            NotificationChannel channel = null;
            if ( view == headsupBtn)
            {
                channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            }
            else
            {
                channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            }

            manager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(this, channelId);
        }
        else
        {
            builder = new NotificationCompat.Builder(this);
        }

        builder.setSmallIcon(android.R.drawable.ic_notification_overlay);
        builder.setContentTitle("Content Title");
        builder.setContentText("Cotent Message");
        builder.setAutoCancel(true);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pIntent);

        PendingIntent pIntent1 = PendingIntent.getBroadcast(this, 0, new Intent(this, NotiReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_share, "ACTION1", pIntent1).build());

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.noti_large);
        builder.setLargeIcon(largeIcon);

        if (view == bigPictureBtn)
        {
            Bitmap bigPicture = BitmapFactory.decodeResource(getResources(), R.drawable.noti_big);
            NotificationCompat.BigPictureStyle bigStyle = new NotificationCompat.BigPictureStyle(builder);
            bigStyle.bigPicture(bigPicture);
            builder.setStyle(bigStyle);
        }
        else if (view == bigTextBtn)
        {
            NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle(builder);
            bigTextStyle.setSummaryText("BigText Summary");
            bigTextStyle.bigText("동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라 만세!!!");
            builder.setStyle(bigTextStyle);
        }
        else if (view == inboxBtn)
        {
            NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle(builder);
            style.addLine("Activity");
            style.addLine("BroadcastReceiver");
            style.addLine("Service");
            style.addLine("ContentProvider");
            style.setSummaryText("Android Component");
            builder.setStyle(style);
        }
        else if (view == progressBtn)
        {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    for (int i = 1; i <= 10; i++)
                    {
                        builder.setAutoCancel(false);
                        builder.setOngoing(true);
                        builder.setProgress(10, i,false);
                        manager.notify(222, builder.build());

                        if (i >= 10)
                        {
                            manager.cancel(222);
                        }
                        SystemClock.sleep(1000);
                    }
                }
            };

            Thread t = new Thread(runnable);
            t.start();
        }
        else if (view == headsupBtn)
        {
            builder.setFullScreenIntent(pIntent, true);
        }
        else if (view == messageBtn)
        {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P)
            {
                Person sender1 = new Person.Builder().setName("kkang").setIcon(IconCompat.createWithResource(this, R.drawable.person1)).build();
                Person sender2 = new Person.Builder().setName("kim").setIcon(IconCompat.createWithResource(this, R.drawable.person2)).build();

                NotificationCompat.MessagingStyle.Message message = new NotificationCompat.MessagingStyle.Message("hello", System.currentTimeMillis(), sender2);
                NotificationCompat.MessagingStyle style = new NotificationCompat.MessagingStyle(sender1).addMessage("world", System.currentTimeMillis(), sender1).addMessage(message);

                builder.setStyle(style);
            }
        }
        manager.notify(222, builder.build());
    }
}