package com.example.part7_21_provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=6;

    public DBHelper(Context context){
        super(context, "datadb", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tableSql="create table tb_data ("+
                "_id integer primary key autoincrement," +
                "name not null," +
                "phone)";

        sqLiteDatabase.execSQL(tableSql);

        sqLiteDatabase.execSQL("insert into tb_data (name,phone) values ('박찬호','0101111111')");
        sqLiteDatabase.execSQL("insert into tb_data (name,phone) values ('류현진','0102222222')");
        sqLiteDatabase.execSQL("insert into tb_data (name,phone) values ('오승환','0103333333')");
        sqLiteDatabase.execSQL("insert into tb_data (name,phone) values ('김현수','0105555555')");
        sqLiteDatabase.execSQL("insert into tb_data (name,phone) values ('추신수','0106666666')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i1 == DATABASE_VERSION){
            sqLiteDatabase.execSQL("drop table tb_data");
            onCreate(sqLiteDatabase);
        }
    }
}
