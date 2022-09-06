package com.example.part11_34

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context : Context) : SQLiteOpenHelper(context, "datadb", null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        val tableSql = "create table tb_data ("+"_id integer primary key autoincrement," + "name not null," + "date)"

        p0?.execSQL(tableSql)
        p0?.execSQL("insert into tb_data (name,date) values ('elle smith','2019-07-01')")
        p0?.execSQL("insert into tb_data (name,date) values ('jessica scott','2019-07-01')")
        p0?.execSQL("insert into tb_data (name,date) values ('jack thomas','2019-07-01')")
        p0?.execSQL("insert into tb_data (name,date) values ('mike willis','2019-06-30')")
        p0?.execSQL("insert into tb_data (name,date) values ('eden john ','2019-06-28')")
        p0?.execSQL("insert into tb_data (name,date) values ('paul anderson','2019-06-28')")
        p0?.execSQL("insert into tb_data (name,date) values ('jenny miller','2019-06-28')")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("drop table tb_data")
        onCreate(p0)
    }
}