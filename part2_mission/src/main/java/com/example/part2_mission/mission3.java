package com.example.part2_mission;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class mission3 extends AppCompatActivity implements View.OnClickListener{
    final String TAG = getClass().getName().toString();
    EditText nameView;
    EditText phoneView;
    EditText emailView;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission3);
        Log.d(TAG, "+++Start+++");
        nameView = findViewById(R.id.name);
        phoneView = findViewById(R.id.phone);
        emailView = findViewById(R.id.email);
        addBtn = findViewById(R.id.add);

        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = nameView.getText().toString();
        String phone = phoneView.getText().toString();
        String email = emailView.getText().toString();
        try {
            DBHelper helper = new DBHelper(this);
            SQLiteDatabase db = helper.getWritableDatabase();
            db.execSQL("insert into tb_contact (name, phone, email) values (?, ?, ?)", new String[]{name, phone, email});
            db.close();

            Intent intent = new Intent(this, ReadDBActivity.class);
            startActivity(intent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}