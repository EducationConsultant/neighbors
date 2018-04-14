package com.consul.edu.educationconsultant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Svetlana on 4/14/2018.
 */

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = "DetailsActivity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Log.d(TAG, "onCreate: started.");

        getIncomingIntent();
    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents");
        if(getIntent().hasExtra("title") && getIntent().hasExtra("year") && getIntent().hasExtra("genre")) {
            Log.d(TAG, "getIncomingIntent: found intent extras");

            String title = getIntent().getStringExtra("title");
            String year = getIntent().getStringExtra("year");
            String genre = getIntent().getStringExtra("genre");

            setMovie(title, year, genre);
        }
    }

    private void setMovie(String title, String year, String genre) {
        TextView titleView = findViewById(R.id.title);
        TextView yearView  = findViewById(R.id.year);
        TextView genreView = findViewById(R.id.genre);

        titleView.setText(title);
        yearView.setText(year);
        genreView.setText(genre);
    }
}
