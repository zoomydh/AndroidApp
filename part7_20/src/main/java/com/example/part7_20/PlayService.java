package com.example.part7_20;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;

public class PlayService extends Service implements MediaPlayer.OnCompletionListener {
    MediaPlayer player;
    String filePath;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String mode=intent.getStringExtra("mode");
            if(mode != null){
                if(mode.equals("start")){
                    try{
                        if(player != null && player.isPlaying()){
                            player.stop();;
                            player.release();
                            player=null;
                        }
                        player=new MediaPlayer();
                        player.setDataSource(filePath);
                        player.prepare();
                        player.start();

                        Intent aIntent=new Intent("com.example.PLAY_TO_ACTIVITY");
                        aIntent.putExtra("mode","start");
                        aIntent.putExtra("duration", player.getDuration());
                        sendBroadcast(aIntent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else if(mode.equals("stop")){
                    if(player != null && player.isPlaying()){
                        player.stop();
                        player.release();
                        player=null;
                    }
                }
            }
        }
    };

    public PlayService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver(receiver, new IntentFilter("com.example.PLAY_TO_SERVICE"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        filePath=intent.getStringExtra("filePath");
        if(player != null){
            Intent aIntent=new Intent("com.example.PLAY_TO_ACTIVITY");
            aIntent.putExtra("mode", "restart");
            aIntent.putExtra("duration", player.getDuration());
            aIntent.putExtra("current", player.getCurrentPosition());
            sendBroadcast(aIntent);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Intent intent=new Intent("com.example.PLAY_TO_ACTIVITY");
        intent.putExtra("mode", "stop");
        sendBroadcast(intent);
        stopSelf();
    }
}