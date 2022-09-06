package com.example.part10_30.datasource;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

public class MyDataFactory extends DataSource.Factory {
    @NonNull
    @Override
    public DataSource create() {
        return new MyDataSource();
    }
}
