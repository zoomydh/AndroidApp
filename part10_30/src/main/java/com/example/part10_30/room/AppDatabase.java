package com.example.part10_30.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.part10_30.model.ItemModel;

@Database(entities = {ItemModel.class}, version = 1,  exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ArticleDAO articleDao();
}

