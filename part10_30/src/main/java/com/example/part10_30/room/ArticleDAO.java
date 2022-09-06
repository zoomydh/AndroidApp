package com.example.part10_30.room;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.part10_30.model.ItemModel;

@Dao
public interface ArticleDAO {
    @Query("SELECT * FROM article")
    DataSource.Factory<Integer, ItemModel> getAll();

    @Insert
    void insertAll (ItemModel... users);

    @Query("DELETE FROM article")
    void deleteAll();
}
