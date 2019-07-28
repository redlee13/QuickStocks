package com.example.android.quickstocks.Data;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.quickstocks.MainModel;

public class MainHelper {
    private static boolean setAsFavorite = true;
    private static MainDatabase database;

    public MainHelper(Context context) {
        database = MainDatabase.getInstance(context);
    }

    public void insert(MainModel mainModel){
        setAsFavorite = true;
        new FavoriteTask().execute(mainModel);
    }


    public void delete(MainModel mainModel){
        setAsFavorite = false;
        new FavoriteTask().execute(mainModel);
    }


    private static class FavoriteTask extends AsyncTask<MainModel, Void , Void>{
        @Override
        protected Void doInBackground(MainModel... mainModels) {
            if (setAsFavorite){
                database.mainDao().insert(mainModels[0]);
            } else {
                database.mainDao().delete(mainModels[0]);
            }
            return null;
        }
    }
}
