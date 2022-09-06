package com.example.part5_14;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> datas;

    String category;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK)
                    {
                        Log.d("intent", "+++onActivityResult+++");
                        Intent data = result.getData();
                        String location = data.getStringExtra("location");
                        Toast toast = Toast.makeText(MainActivity.this, category+":"+location, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.main_list);
        listView.setOnItemClickListener(this);
        Log.d("intent", "+++onCreate1+++");
        DBHelper helper =new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select location from tb_data where category='0'",null);
        Log.d("intent", "+++onCreate2+++");
        datas = new ArrayList<>();
        while (cursor.moveToNext())
        {
            datas.add(cursor.getString(0));
        }
        db.close();
        Log.d("intent", "+++onCreate3+++");
        adapter = new ArrayAdapter<String >(this, android.R.layout.simple_list_item_1, datas);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView textView = (TextView)view;
        category = textView.getText().toString();
        Log.d("intent", "+++onItemClick1+++");

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("category", category);
        Log.d("intent", "+++onItemClick2+++");
        activityResultLauncher.launch(intent);

    }
}