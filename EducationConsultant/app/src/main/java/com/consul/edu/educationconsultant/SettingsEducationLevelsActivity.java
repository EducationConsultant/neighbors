package com.consul.edu.educationconsultant;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class SettingsEducationLevelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_education_levels);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_edu_level);
        toolbar.setTitleTextColor(Color.WHITE);
        // To get the toolbar to behave like an app bar. Parameter: the toolbar you want to set as the activity’s app bar
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
