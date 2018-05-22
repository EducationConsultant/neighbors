package com.consul.edu.educationconsultant.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.consul.edu.educationconsultant.R;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = (Toolbar) findViewById(R.id.toolbar_settings);
        toolbar.setTitleTextColor(Color.WHITE);
        // To get the toolbar to behave like an app bar. Parameter: the toolbar you want to set as the activity’s app bar
        setSupportActionBar(toolbar);

        // getSupportActionBar: using the toolbar from the Support Library
        actionBar = getSupportActionBar();
        // This enables the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    public void onClickChangePassword(View view){
        Intent intent = new Intent(SettingsActivity.this,SettingsChangePasswordActivity.class);
        startActivity(intent);
    }

    public void onClickEducationLevels(View view){
        Intent intent = new Intent(SettingsActivity.this, SettingsEducationLevelsActivity.class);
        startActivity(intent);
    }

    public void onClickCategoryFilter(View view) {
        Intent intent = new Intent(SettingsActivity.this, SettingsCategoryActivity.class);
        startActivity(intent);
    }

    public void onClickLocationFilter(View view) {
        Intent intent = new Intent(SettingsActivity.this, SettingsLocationActivity.class);
        startActivity(intent);
    }
}
