package com.example.android.quickstocks.Data;

import com.example.android.quickstocks.MainModel;

import java.util.List;

import androidx.room.Insert;
import androidx.room.Query;

@androidx.room.Dao
public interface MainDao {

    @Insert
    void insert(MainModel mainModel);

    @Query("DELETE FROM MainModel WHERE id = :id")
    void delete(int id);

    @Query("SELECT * FROM MainModel")
    List<MainModel> getAll();

    @Query("SELECT * FROM MainModel WHERE id = :id")
    MainModel getSingleCompany(int id);
}
