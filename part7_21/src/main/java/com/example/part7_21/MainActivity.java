package com.example.part7_21;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    ListView listView;
    EditText nameView;
    EditText phoneView;
    Button btn;

    boolean isUpdate;
    String _id;

    ArrayList<HashMap<String, String>> datas;

    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.lab1_listview);
        nameView=(EditText) findViewById(R.id.lab1_name);
        phoneView=(EditText) findViewById(R.id.lab1_phone);
        btn=(Button)findViewById(R.id.lab1_btn);

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        btn.setOnClickListener(this);

        uri = Uri.parse("content://com.example.part.Provider");
        setAdapter();
    }

    private void setAdapter() {
        datas = new ArrayList<>();
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null)
        {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("id", cursor.getString(0));
                    map.put("name", cursor.getString(1));
                    map.put("phone", cursor.getString(2));
                    datas.add(map);
                }
            } else {
                if (cursor.moveToFirst()) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("id", cursor.getString(0));
                    map.put("name", cursor.getString(1));
                    map.put("phone", cursor.getString(2));
                    datas.add(map);
                }
            }
        } else {
            Log.d("cursor", "is null");
        }

        SimpleAdapter adapter = new SimpleAdapter(this, datas, android.R.layout.simple_list_item_2,
                new String[]{"name", "phone"}, new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        if (isUpdate)
        {
            String name = nameView.getText().toString();
            String phone = phoneView.getText().toString();
            if (!name.equals("") && !phone.equals("")) {
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("phone", phone);
                getContentResolver().update(uri, values, "_id=?", new String[]{_id});
                setAdapter();
            }
            nameView.setText("");
            phoneView.setText("");
            isUpdate = false;
        }
        else
        {
            String name = nameView.getText().toString();
            String phone = phoneView.getText().toString();
            if (!name.equals("") && !phone.equals("")) {
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("phone", phone);
                getContentResolver().insert(uri, values);
                setAdapter();
            }
            nameView.setText("");
            phoneView.setText("");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        HashMap<String, String> map=datas.get(i);
        nameView.setText(map.get("name"));
        phoneView.setText(map.get("phone"));
        _id=map.get("id");
        isUpdate=true;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        HashMap<String, String> map = datas.get(i);
        getContentResolver().delete(uri, "_id=?", new String[]{map.get("id")});
        setAdapter();
        return false;
    }
}