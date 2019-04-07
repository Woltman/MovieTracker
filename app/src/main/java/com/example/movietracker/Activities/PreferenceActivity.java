package com.example.movietracker.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.movietracker.R;

import java.util.ArrayList;

import Core.Preference;
import Infrastructure.Preferences;

public class PreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        setSupportActionBar((Toolbar)findViewById(R.id.my_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<Preference> preferences = Preferences.getPreferences();
        Switch showmovietitle = findViewById(R.id.showmovietitle);
        for (Preference p : preferences) {
            if(p.getSetting().equals(Preferences.SHOWMOVIETITLES)){
                showmovietitle.setChecked(p.GetValue());
                break;
            }
        }

        showmovietitle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Preferences.changePreference(new Preference(Preferences.SHOWMOVIETITLES, isChecked));
                Preferences.savePreference(getSharedPreferences("shared prefernces", MODE_PRIVATE));
            }
        });
    }
}
