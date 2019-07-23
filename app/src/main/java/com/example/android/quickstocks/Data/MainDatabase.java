package com.example.android.quickstocks.Data;

import android.content.Context;

import com.example.android.quickstocks.MainModel;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = MainModel.class, version = 1 , exportSchema = false)
public abstract class MainDatabase extends RoomDatabase {

    private static MainDatabase instance;
    public abstract MainDao mainDao();

    public static synchronized MainDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context, MainDatabase.class, "main_database.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
