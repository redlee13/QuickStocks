package com.example.android.quickstocks.UI;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.android.quickstocks.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            Preference preference = findPreference(getString(R.string.list_key));
            if (preference != null){
                setUpPreference(preference);
            }
        }


        private void setUpPreference(Preference preference) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String selectedSortValue = sharedPreferences.getString(preference.getKey(), getString(R.string.pref_fav));
            preference.setOnPreferenceChangeListener(this);
            onPreferenceChange(preference, selectedSortValue);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            return true;
        }
    }
}