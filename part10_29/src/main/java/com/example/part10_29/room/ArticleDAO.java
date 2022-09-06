package com.example.part10_29.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.part10_29.model.ItemModel;

import java.util.List;

@Dao
public interface ArticleDAO {
    @Query("SELECT * FROM article")
    List<ItemModel> getAll();

    @Insert
    void insertAll (ItemModel... users);

    @Query("DELETE FROM article")
    void deleteAll();
}
