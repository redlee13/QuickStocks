package com.example.android.quickstocks;

import android.app.Application;
import android.os.AsyncTask;

import com.example.android.quickstocks.Data.GetData;
import com.example.android.quickstocks.Data.MainDatabase;
import com.example.android.quickstocks.Data.RetrofitInstance;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewModel extends AndroidViewModel {
    private MainDatabase mDatabase;
    private MutableLiveData<List<MainModel>> mLiveData = new MutableLiveData<>();
    private List<MainModel> modelList = new ArrayList<>();


    public ViewModel(@NonNull Application application) {
        super(application);
        mDatabase = MainDatabase.getInstance(application);
    }

    public LiveData<List<MainModel>> getLiveDate(boolean fav){
        if (fav){
            new FavoriteTask().execute();
            return mLiveData;
        } else {
            Call<List<MainModel>> call = RetrofitInstance.getRetrofit().create(GetData.class).getTasi();
            call.enqueue(new Callback<List<MainModel>>() {
                @Override
                public void onResponse(Call<List<MainModel>> call, Response<List<MainModel>> response) {
                    Document document = (Document) response.body();


                    Elements links = document.select("span > a[href]");
                    for (Element link : links) {
                        modelList.add(new MainModel(link.text(), link.attr("href")));
                        mLiveData.postValue(modelList);
                    }

                }

                @Override
                public void onFailure(Call<List<MainModel>> call, Throwable t) {

                }
            });
            return mLiveData;
        }
    }


    private class FavoriteTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            mLiveData.postValue(mDatabase.mainDao().getAll());
            return null;
        }
    }
}
