package com.example.part2_mission;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReadDBActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_dbactivity);

        TextView nameView = findViewById(R.id.read_name);
        TextView phoneView = findViewById(R.id.read_phone);
        TextView emailView = findViewById(R.id.read_email);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor =db.rawQuery("select name, phone, email from tb_contact order by _id desc limit 1",null);
        while (cursor.moveToNext())
        {
            nameView.setText(cursor.getString(0));
            phoneView.setText(cursor.getString(1));
            emailView.setText(cursor.getString(2));
        }
        db.close();
    }
}