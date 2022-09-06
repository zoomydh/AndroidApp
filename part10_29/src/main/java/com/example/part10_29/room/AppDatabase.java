package com.example.part10_29.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.part10_29.model.ItemModel;

@Database(entities = {ItemModel.class}, version = 1,  exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ArticleDAO articleDao();
}
