package com.example.android.quickstocks.Data;

import com.example.android.quickstocks.MainModel;

import java.util.List;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@androidx.room.Dao
public interface MainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(MainModel mainModel);

    @Delete
    void delete(MainModel mainModel);

    @Query("SELECT * FROM MainModel")
    List<MainModel> getAll();

    @Query("SELECT * FROM MainModel WHERE companyName = :name")
    MainModel getSingleCompany(String name);
}
