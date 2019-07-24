package com.example.android.quickstocks.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.quickstocks.MainModel;
import com.example.android.quickstocks.MainRecyclerAdapter;
import com.example.android.quickstocks.R;
import com.example.android.quickstocks.ViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.main_list_rv)
    RecyclerView mRecyclerView;

    private MainRecyclerAdapter mAdapter;
    List<MainModel> modelList;
    ViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        modelList = new ArrayList<>();

        mViewModel = ViewModelProviders.of(this).get(ViewModel.class);

        listLoader();

        mAdapter = new MainRecyclerAdapter(modelList, MainActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }

    private void listLoader(){
        if (getSort().equals(getString(R.string.pref_fav))){
            mViewModel.getLiveDate(true).observe(this, new Observer<List<MainModel>>() {
                @Override
                public void onChanged(List<MainModel> mainModels) {
                    modelList.clear();
                    if (mainModels != null){
                        modelList.addAll(mainModels);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        } else {
            mViewModel.getLiveDate(false).observe(this, new Observer<List<MainModel>>() {
                @Override
                public void onChanged(List<MainModel> mainModels) {
                    modelList.clear();
                    modelList.addAll(mainModels);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private String getSort() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString(getString(R.string.list_key), getString(R.string.pref_normal));
    }

}
