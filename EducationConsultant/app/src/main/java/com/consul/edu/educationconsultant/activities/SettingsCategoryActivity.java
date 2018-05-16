package com.consul.edu.educationconsultant.activities;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.consul.edu.educationconsultant.R;

public class SettingsCategoryActivity extends AppCompatActivity {

    private Switch mathematics, sport, english, other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_category);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        // TODO: Send these values to layout
        String[] categories_arr = getResources().getStringArray(R.array.categories_arr);
        // getResources().getStringArray(R.array.categories_arr)[position];

        mathematics = (Switch) findViewById(R.id.mathematics);
        sport = (Switch) findViewById(R.id.sport);
        english = (Switch) findViewById(R.id.english);
        other = (Switch) findViewById(R.id.other);

        mathematics.setChecked(true);
        sport.setChecked(true);
        english.setChecked(true);
        other.setChecked(true);

    }


    public void onClickSwitchCategory(View view) {
        boolean checked = ((Switch) view).isChecked();

        switch (view.getId()) {
            case R.id.mathematics:
                if (checked) {
                    mathematics.setChecked(true);
                    Toast.makeText(SettingsCategoryActivity.this, "Mathematics checked", Toast.LENGTH_SHORT).show();
                } else {
                    mathematics.setChecked(false);
                }
                break;
            case R.id.english:
                if (checked) {
                    english.setChecked(true);
                    Toast.makeText(SettingsCategoryActivity.this, "English checked", Toast.LENGTH_SHORT).show();
                } else {
                    english.setChecked(false);
                }
                break;
            case R.id.sport:
                if (checked) {
                    sport.setChecked(true);
                    Toast.makeText(SettingsCategoryActivity.this, "Sport checked", Toast.LENGTH_SHORT).show();
                } else {
                    sport.setChecked(false);
                }
                break;
            case R.id.other:
                if (checked) {
                    other.setChecked(true);
                    Toast.makeText(SettingsCategoryActivity.this, "Other checked", Toast.LENGTH_SHORT).show();
                } else {
                    other.setChecked(false);
                }
                break;
            default:
                Toast.makeText(SettingsCategoryActivity.this, "Default statement", Toast.LENGTH_SHORT).show();

        }
    }
}
