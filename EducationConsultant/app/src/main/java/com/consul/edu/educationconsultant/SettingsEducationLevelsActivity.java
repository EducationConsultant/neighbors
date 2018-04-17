package com.consul.edu.educationconsultant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsEducationLevelsActivity extends AppCompatActivity {
    private Switch elementarySchool;
    private Switch middleSchool;
    private Switch highSchool;
    private Switch collage;
    private Switch masters;
    private Switch doctors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_education_levels);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_edu_level);
        toolbar.setTitleTextColor(Color.WHITE);
        // To get the toolbar to behave like an app bar. Parameter: the toolbar you want to set as the activity’s app bar
        setSupportActionBar(toolbar);

        // getSupportActionBar: using the toolbar from the Support Library
        ActionBar actionBar = getSupportActionBar();
        // This enables the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);

        elementarySchool = (Switch) findViewById(R.id.elementary_school);
        middleSchool = (Switch) findViewById(R.id.middle_school);
        highSchool = (Switch) findViewById(R.id.high_school);
        collage = (Switch) findViewById(R.id.collage);
        masters = (Switch) findViewById(R.id.masters);
        doctors = (Switch)findViewById(R.id.doctors);

        elementarySchool.setChecked(true);
        middleSchool.setChecked(true);
        highSchool.setChecked(true);
        collage.setChecked(true);
        masters.setChecked(true);
        doctors.setChecked(true);

    }

    /**
     * Called when the switch is clicked.
     *
     * @param view The view (in this case a switch) that was clicked.
     * */
    public void onClickSwitch(View view){
        boolean checked = ((Switch) view).isChecked();

        switch (view.getId()){
            case R.id.elementary_school:

                if(checked){
                    elementarySchool.setChecked(true);
                    Toast.makeText(SettingsEducationLevelsActivity.this, "Elementary checked", Toast.LENGTH_SHORT).show();
                }else{
                    elementarySchool.setChecked(false);
                }
                break;
            case R.id.middle_school:

                if(checked){
                    middleSchool.setChecked(true);
                    Toast.makeText(SettingsEducationLevelsActivity.this, "Middle checked", Toast.LENGTH_SHORT).show();
                }else{
                    middleSchool.setChecked(false);
                }
                break;
            case R.id.high_school:

                if(checked){
                    highSchool.setChecked(true);
                    Toast.makeText(SettingsEducationLevelsActivity.this, "High checked", Toast.LENGTH_SHORT).show();
                }else{
                    highSchool.setChecked(false);
                }
                break;
            case R.id.collage:

                if(checked){
                    collage.setChecked(true);
                    Toast.makeText(SettingsEducationLevelsActivity.this, "Collage checked", Toast.LENGTH_SHORT).show();
                }else{
                    collage.setChecked(false);
                }
                break;
            case R.id.masters:

                if(checked){
                    masters.setChecked(true);
                    Toast.makeText(SettingsEducationLevelsActivity.this, "Master's checked", Toast.LENGTH_SHORT).show();
                }else{
                    masters.setChecked(false);
                }
                break;
            case R.id.doctors:

                if(checked){
                    doctors.setChecked(true);
                    Toast.makeText(SettingsEducationLevelsActivity.this, "Doctor's checked", Toast.LENGTH_SHORT).show();
                }else{
                    doctors.setChecked(false);
                }
                break;
            default:
                Toast.makeText(SettingsEducationLevelsActivity.this, "Default statement", Toast.LENGTH_SHORT).show();

        }
    }
}
