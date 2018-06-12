package com.consul.edu.educationconsultant.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.consul.edu.educationconsultant.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsLocationActivity extends AppCompatActivity {

    private SeekBar location;
    private TextView radius;
    private SharedPreferences sharedPreferences;
    private String sharedPrefName;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_location);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_location);
        toolbar.setTitle("Location Filter");
        toolbar.setTitleTextColor(Color.WHITE);
        // To get the toolbar to behave like an app bar. Parameter: the toolbar you want to set as the activity’s app bar
        setSupportActionBar(toolbar);

        // getSupportActionBar: using the toolbar from the Support Library
        ActionBar actionBar = getSupportActionBar();
        // This enables the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        location =  findViewById(R.id.location);
        radius = findViewById(R.id.radius);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        firebaseUser = auth.getCurrentUser();
        sharedPrefName = "Settings" + "_" + firebaseUser.getEmail();
        sharedPreferences = getSharedPreferences(sharedPrefName,MODE_PRIVATE);

        Integer radiusVal = sharedPreferences.getInt("radius", 1);
        radius.setText("Radius in kilometers: " + String.valueOf(radiusVal) );

        location.setProgress(radiusVal);

        // perform seek bar change listener event used for getting the progress value
        location.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 1;
            SharedPreferences.Editor editor = sharedPreferences.edit();

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                radius.setText("Radius in kilometers: " + progressChangedValue);
                editor.putInt("radius", progressChangedValue);
                editor.apply();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(SettingsLocationActivity.this, "Radius is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
                radius.setText("Radius in kilometers: " + progressChangedValue);
                editor.putInt("radius", progressChangedValue);
                editor.apply();
            }
        });
    }
}
