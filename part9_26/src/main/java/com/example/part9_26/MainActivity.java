package com.example.part9_26;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<ChatMessage> list;
    MyAdapter ap;

    ListView listView;
    ImageView sendBtn;
    EditText msgEdit;

    boolean flagConnection = true;
    boolean isConnected = false;
    boolean flagRead = true;

    Handler writeHandler;

    Socket socket;
    BufferedInputStream bin;
    BufferedOutputStream bout;

    SocketThread st;
    ReadThread rt;
    WriteThread wt;

    String serverIp = "172.30.1.45";
    int serverport = 7070;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lab1_list);
        sendBtn = (ImageView) findViewById(R.id.lab1_send_btn);
        msgEdit = (EditText) findViewById(R.id.lab1_send_text);

        sendBtn.setOnClickListener(this);

        list = new ArrayList<ChatMessage>();
        ap = new MyAdapter(this, R.layout.chat_item, list);
        listView.setAdapter(ap);

        sendBtn.setEnabled(false);
        msgEdit.setEnabled(false);
        getToken();
    }

    private void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    return;
                }

                String currToken = task.getResult();
                Log.d("Token", "Token : "+currToken);
            }
        });
    }

    private void addMessage(String who, String msg) {
        ChatMessage vo = new ChatMessage();
        vo.who = who;
        vo.msg = msg;
        list.add(vo);
        ap.notifyDataSetChanged();
        listView.setSelection(list.size()-1);
    }

    @Override
    public void onClick(View view) {
        if (!msgEdit.getText().toString().trim().equals("")) {
            Message msg = new Message();
            msg.obj = msgEdit.getText().toString();
            writeHandler.sendMessage(msg);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        st = new SocketThread();
        st.start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        flagConnection = false;
        isConnected = false;

        if (socket != null) {
            flagRead = false;
            writeHandler.getLooper().quit();
            try {
                bout.close();
                bin.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    Handler mainHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if (message.what == 10) {

                showToast("connection ok!");
                sendBtn.setEnabled(true);
                msgEdit.setEnabled(true);
            } else if (message.what == 20) {

                showToast("connection fail!");
                sendBtn.setEnabled(true);
                msgEdit.setEnabled(true);
            } else if (message.what == 100) {
                addMessage("you", (String) message.obj);
            } else if (message.what == 200) {
                addMessage("me", (String) message.obj);
            }
            return false;
        }
    });

    class SocketThread extends Thread {
        @Override
        public void run() {
            while (flagConnection) {
                try {
                    if (!isConnected) {
                        socket = new Socket();
                        SocketAddress remoteAddr = new InetSocketAddress(serverIp, serverport);
                        socket.connect(remoteAddr, 10*1000);

                        bout = new BufferedOutputStream(socket.getOutputStream());
                        bin = new BufferedInputStream(socket.getInputStream());

                        if (rt != null) {
                            flagRead = false;
                        }
                        if (wt != null) {
                            writeHandler.getLooper().quit();
                        }

                        wt = new WriteThread();
                        wt.start();
                        rt = new ReadThread();
                        rt.start();

                        isConnected = true;

                        Message msg = new Message();
                        msg.what = 10;
                        mainHandler.sendMessage(msg);
                    } else {
                        SystemClock.sleep(10000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    SystemClock.sleep(10000);
                }
            }
        }
    }

    class WriteThread extends Thread {
        @Override
        public void run() {
            Looper.prepare();
            writeHandler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(@NonNull Message message) {
                    try {
                        Log.d("kkang", "WriteThread......"+(String)message.obj);
                        bout.write(((String) message.obj).getBytes());
                        bout.flush();
                        Message msg = new Message();
                        msg.what = 200;
                        msg.obj = message.obj;
                        mainHandler.sendMessage(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                        isConnected = false;
                        writeHandler.getLooper().quit();
                        try {
                            flagRead = false;
                        } catch (Exception e1) {
                            e.printStackTrace();
                        }
                    }
                    return false;
                }
            });
            Looper.loop();
        }
    }

    class ReadThread extends Thread {
        @Override
        public void run() {
            byte[] buffer = null;
            while (flagRead) {
                buffer = new byte[1024];

                try {
                    String message = null;
                    int size = bin.read(buffer);
                    Log.d("kkang", "ReadThread : "+size);
                    if (size > 0) {
                        message = new String(buffer, 0, size, "utf-8");
                        if (message != null && !message.equals("")) {
                            Message msg = new Message();
                            msg.what = 100;
                            msg.obj = message;
                            mainHandler.sendMessage(msg);
                        } else {
                            flagRead = false;
                            isConnected = false;
                        }
                    }
                } catch (IOException e) {
                    flagRead = false;
                    isConnected = false;
                }
            }
            Message msg = new Message();
            msg.what = 20;
            mainHandler.sendMessage(msg);
        }
    }

    class ChatMessage {
        String who;
        String msg;
    }

    class MyAdapter extends ArrayAdapter<ChatMessage> {
        ArrayList<ChatMessage> list;
        int resId;
        Context context;

        public MyAdapter(Context context, int resId, ArrayList<ChatMessage> list) {
            super(context, resId, list);
            this.context = context;
            this.resId = resId;
            this.list = list;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resId, null);

            TextView msgView = convertView.findViewById(R.id.lab1_item_msg);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) msgView.getLayoutParams();

            ChatMessage msg = list.get(position);
            if (msg.who.equals("me")) {
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                msgView.setTextColor(Color.WHITE);
                msgView.setBackgroundResource(R.drawable.chat_right);
            } else if (msg.who.equals("you")) {
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                msgView.setBackgroundResource(R.drawable.chat_left);
            }
            msgView.setText(msg.msg);

            return convertView;
        }
    }
}