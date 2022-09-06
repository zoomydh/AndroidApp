package com.example.part5_15;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    ArrayList<String> datas;
    ArrayAdapter<String> adapter;

    Button detailBtn;
    Button dialogBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.main_list);
        detailBtn=(Button)findViewById(R.id.main_btn_detail);
        dialogBtn=(Button)findViewById(R.id.main_btn_dialog);

        detailBtn.setOnClickListener(this);
        dialogBtn.setOnClickListener(this);

        datas=new ArrayList<>();

        datas.add("onCreate....");

        adapter=new ArrayAdapter<String>(this, R.layout.item_main_list, datas);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if(v==detailBtn){
            Intent intent=new Intent(this, DetailActivity.class);
            startActivity(intent);
        }else if(v==dialogBtn){
            Intent intent=new Intent(this, DialogActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        datas.add("onResume....");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();

        datas.add("onPause....");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();

        datas.add("onStart....");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();

        datas.add("onStop....");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        datas.add("onRestart....");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        datas.add("onDestory....");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        datas.add("onSaveInstanceState...");
        adapter.notifyDataSetChanged();
        outState.putString("data1","hello");
        outState.putInt("data2", 100);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        datas.add("onRestoreInstanceState...");
        adapter.notifyDataSetChanged();

        String data1 = savedInstanceState.getString("data1");
        int data2 = savedInstanceState.getInt("data2");

        Toast toast = Toast.makeText(this, data1+":"+data2, Toast.LENGTH_SHORT);
        toast.show();
    }
}